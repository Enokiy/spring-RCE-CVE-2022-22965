package com.github.enokiy.springrcedemo.controller;

import com.github.enokiy.springrcedemo.bean.Target;
import com.github.enokiy.springrcedemo.bean.User;
import com.github.enokiy.springrcedemo.bean.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

@RestController
public class DemoController {
    public DemoController() {
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(UserInfo userInfo) {
        System.out.println("password:"+userInfo.getPassword());
        System.out.println("password:"+userInfo.getUser().getAge());
        System.out.println("class:"+userInfo.getClass());
        System.out.println("user.name:"+userInfo.getUser().getName());
        System.out.println("names[0]:"+ userInfo.getNames()[0]);
        System.out.println("classLoader:"+ userInfo.getClass().getClassLoader());
        return userInfo.toString();
    }

    @RequestMapping({"/get-user-info"})
    public String getUserInfo(UserInfo userInfo) {
        System.out.println("Name:"  + userInfo.getUser().getName());
        System.out.println("Age:"  + userInfo.getUser().getAge());
        System.out.println("password:" + userInfo.getPassword());
        System.out.println("classLoader:" + userInfo.getClass().getClassLoader());

        return userInfo.toString();
    }

    @RequestMapping({"/get-all-access-property"})
    public String analysebfs(UserInfo userInfo) {
        StringBuilder sb = new StringBuilder();
        Set<Object> allExposedProperties = new HashSet<>();
        int maxLevel = 20;
        Queue<Target> queue = new LinkedList<>();
        queue.add(new Target(userInfo.getClass().getModule(),"class.module",0,true,true));
        while (!queue.isEmpty()){
            Target target  = queue.poll();
            if (allExposedProperties.contains(target.object))
                continue;
            allExposedProperties.add(target.object);

            Class clazz = target.object.getClass();
            if(clazz.isPrimitive()||clazz.getName().contains("java.lang.reflect")|| clazz.getName().contains("java.lang.String")|| clazz.getName().contains("java.lang.Package"))
                continue;
            if (target.readable && target.writeable){
                sb.append("[R][W]"+target.path + "\n");
            }
            System.out.print(target.readable?"[R]":"[]");
            System.out.print(target.writeable?"[W]":"[]");
            System.out.println(target.path);

            if (target.object.getClass().isArray()){

            }else if (target.object instanceof List){

            }else if (target.object instanceof Set){

            }else if (target.object instanceof Map){

            }else {
                for (PropertyDescriptor pd :BeanUtils.getPropertyDescriptors(clazz)) {
                    String newPath = target.path + "." + pd.getName();
                    boolean isRead = pd.getReadMethod()!=null;
                    boolean isWrite = pd.getWriteMethod() != null;
                    Method method = pd.getReadMethod();
                    if (isWrite&isRead){
                        sb.append("[R][W]"+newPath + "\n");
                    }
                    if (isRead){
                        try {
                            Object newObject = null;
                            newObject = method.invoke(target.object,null);
                        if (newObject !=null){
                            if (!allExposedProperties.contains(newObject)&&target.level<maxLevel){
                                queue.add(new Target(newObject,newPath, target.level+1,isRead,isWrite));
                            }
                        }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}