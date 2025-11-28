package com.example.b07project.model.User;

public abstract class User {
    protected String uid;
    protected String name;

    public User(){}
    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }
    public String getName() {
        return name;
    }
}
