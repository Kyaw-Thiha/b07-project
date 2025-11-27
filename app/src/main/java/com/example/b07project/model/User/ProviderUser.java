package com.example.b07project.model.User;

import java.util.Map;

public class ProviderUser extends User {
    public ProviderUser() {
        super();
    }

    public ProviderUser(String uid, String name, String email, Map<String, Boolean> roles) {
        super(uid, name, email, roles);
    }
}
