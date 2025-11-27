package com.example.b07project.services;

import com.example.b07project.model.User.BaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UserRepository {
    private final Service service;

    public UserRepository(Service service) {
        this.service = service;
    }

    public void create(String uid, BaseUser user) {
        DatabaseReference ref = service.baseUserDatabase().child(uid);
        ref.setValue(user);
    }

    public void update(String uid, Map<String, Object> updates) {
        DatabaseReference ref = service.baseUserDatabase().child(uid);
        ref.updateChildren(updates);
    }

    public void get(String uid, ValueEventListener listener) {
        DatabaseReference ref = service.baseUserDatabase().child(uid);
        ref.addListenerForSingleValueEvent(listener);
    }

    public void observe(String uid, ValueEventListener listener) {
        DatabaseReference ref = service.baseUserDatabase().child(uid);
        ref.addValueEventListener(listener);
    }
}
