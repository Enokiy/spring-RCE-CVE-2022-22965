package com.github.enokiy.springrcedemo.bean;

public class Target {
    public Object object;
    public String path;
    public int level = 0;
    public boolean readable;
    public boolean writeable;

    public Target(Object object, String path, int level, boolean readable, boolean writeable) {
        this.object = object;
        this.path = path;
        this.level = level;
        this.readable = readable;
        this.writeable = writeable;
    }
}
