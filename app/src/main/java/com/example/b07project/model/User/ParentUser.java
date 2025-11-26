package com.example.b07project.model.User;

import com.example.b07project.model.Medicine;

import java.util.ArrayList;
import java.util.Map;

public class ParentUser extends User {
    private ArrayList<ChildUser> children;
    private ArrayList<ProviderUser> providers;
    private ArrayList<Medicine> inventory;

    public ParentUser() {
        super();
    }

    public ParentUser(String uid, String name, String email, Map<String, Boolean> roles) {
        super(uid, name, email, roles);
    }
}
