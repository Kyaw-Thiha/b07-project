package com.example.b07project.model.User;

import java.util.Map;

public abstract class User {
    protected String uid;
    protected String name;
    protected String email;
    protected Map<String, Boolean> roles;

    public User(){}
    public User(String uid, String name, String email, Map<String, Boolean> roles) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public String getUid() {
        return uid;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public Map<String, Boolean> getRoles() {
        return roles;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Map<String, Boolean> roles) {
        this.roles = roles;
    }
}
