package com.example.b07project.model.User;

import com.example.b07project.model.*;

import java.util.Map;

public class ChildUser extends User {
    private Boolean ageBelow9;
    private String parentId;

    public ChildUser(){
    }
    public ChildUser(String uid, String name, String email, Map<String, Boolean> roles, Boolean ageBelow9, String parentId) {
        super(uid, name, email, roles);
        this.ageBelow9 = ageBelow9;
        this.parentId = parentId;
    }

    public Boolean isAgeBelow9() {
        return ageBelow9;
    }

    public void setAgeBelow9(Boolean ageBelow9) {
        this.ageBelow9 = ageBelow9;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
