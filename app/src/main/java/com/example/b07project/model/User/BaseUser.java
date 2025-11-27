package com.example.b07project.model.User;

import java.util.Map;

public class BaseUser extends User {
    public BaseUser() {
        super();
    }

    public BaseUser(String uid, String name, String email, Map<String, Boolean> roles) {
        super(uid, name, email, roles);
    }
}
