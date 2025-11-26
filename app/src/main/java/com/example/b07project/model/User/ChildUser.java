package com.example.b07project.model.User;

import com.example.b07project.model.*;

import java.util.Map;

public class ChildUser extends User {
    private Boolean ageBelow9;

    public ChildUser(){
    }
    public ChildUser(String uid, String name, String email, Map<String, Boolean> roles, Boolean ageBelow9) {
        super(uid, name, email, roles);
        this.ageBelow9 = ageBelow9;
    }

    public Boolean isAgeBelow9() {
        return ageBelow9;
    }
}
