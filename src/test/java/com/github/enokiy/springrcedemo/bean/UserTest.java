package com.github.enokiy.springrcedemo.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


class UserTest {

    @Test
    public void testPrintProperties(){
        Class clazz = Module.class;
        System.out.println(clazz.getName());
        for (PropertyDescriptor pdesc: BeanUtils.getPropertyDescriptors(clazz)){
            if (pdesc.getReadMethod()!=null){
                System.out.print("[R]");
            }else {
                System.out.print("[ ]");
            }
            if (pdesc.getWriteMethod()!=null){
                System.out.print("[W]");
            }else {
                System.out.print("[ ]");
            }
            System.out.println(pdesc.getName());
        }
    }

    @Test
    public void testGetAllProperties() {
        Set<Object> allExposedProperties = new HashSet<>();
        int maxLevel = 20;
        Queue<Target> queue = new LinkedList<>();
        queue.add(new Target(Class.class,"class",0,true,true));
        while (!queue.isEmpty()){
            Target target  = queue.poll();
            if (allExposedProperties.contains(target.object))
                continue;
            allExposedProperties.add(target.object);

            Class clazz = target.object.getClass();
            if(clazz.isPrimitive()||clazz.getName().contains("java.lang.reflect")|| clazz.getName().contains("java.lang.String")|| clazz.getName().contains("java.lang.Package"))
                continue;

            System.out.print(target.readable?"[R]":"[]");
            System.out.print(target.writeable?"[W]":"[]");
            System.out.println(target.path);

            if (target.object.getClass().isArray()){

            }else if (target.object instanceof List){

            }else if (target.object instanceof Set){

            }else if (target.object instanceof Map){

            }else {
                for (PropertyDescriptor pd :
                        BeanUtils.getPropertyDescriptors(clazz)) {
                    String newPath = target.path + "." + pd.getName();
                    boolean isRead = pd.getReadMethod()!=null;
                    boolean isWrite = pd.getWriteMethod() != null;

                    if (isWrite){
//                        System.out.print("| ");
//                        System.out.print(isRead?"[R]":"[]");
//                        System.out.print(isWrite?"[W]":"[]");
//                        System.out.println(newPath + " |");
                    }
                    if (isRead){
                        Method method = pd.getReadMethod();
//                        System.out.println(method);

                        Object newObject = null;
                        try {
                            newObject = method.invoke(target.object,null);
                        } catch (Exception e) {

                        }
                        if (newObject !=null){
                            if (!allExposedProperties.contains(newObject)&&target.level<maxLevel){
                                queue.add(new Target(newObject,newPath, target.level+1,isRead,isWrite));
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public  void testIntrospector() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Class.class.getModule().getClass());
        for (PropertyDescriptor pdesc:beanInfo.getPropertyDescriptors()){
            System.out.println("Property: " + pdesc.getName());
        }
//        for (MethodDescriptor md:beanInfo.getMethodDescriptors()) {
//            System.out.println("Method: " + md.getName());
//        }
    }
}