package com.github.enokiy.springrcedemo.bean;

public class UserInfo {
    private String names[] =new String[]{"testName"};;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getNames() {
        return names;
    }

    private User user;
    private String password;

    @Override
    public String toString() {
        return "UserInfo{" +
                "user=" + user +
                ", password='" + password + '\'' +
                '}';
    }
}
