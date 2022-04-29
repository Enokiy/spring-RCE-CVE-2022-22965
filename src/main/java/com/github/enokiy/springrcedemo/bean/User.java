package com.github.enokiy.springrcedemo.bean;

public class User {
    private String name;
    private int age = 18;

    public User() {
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + getAge() +
                '}';
    }
}
