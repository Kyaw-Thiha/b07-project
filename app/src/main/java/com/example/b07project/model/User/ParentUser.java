package com.example.b07project.model.User;

import com.example.b07project.model.Medicine;

import java.util.ArrayList;

public class ParentUser extends User {
    private String email;
    private ArrayList<ChildUser> children;
    private ArrayList<ProviderUser> providers;
    private ArrayList<Medicine> inventory;

    public ParentUser() {
        super();
    }

    public ParentUser(String uid, String name, String email) {
        super(uid, name);
        this.email = email;
    }
}
