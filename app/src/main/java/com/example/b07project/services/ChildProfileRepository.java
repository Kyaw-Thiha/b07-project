package com.example.b07project.services;

import com.example.b07project.model.User.ChildUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ChildProfileRepository {
    private final Service service;

    public ChildProfileRepository(Service service) {
        this.service = service;
    }

    public void create(String uid, ChildUser user) {
        DatabaseReference ref = service.childUserDatabase().child(uid);
        ref.setValue(user);
    }

    public void update(String uid, Map<String, Object> updates) {
        DatabaseReference ref = service.childUserDatabase().child(uid);
        ref.updateChildren(updates);
    }

    public void get(String uid, ValueEventListener listener) {
        DatabaseReference ref = service.childUserDatabase().child(uid);
        ref.addListenerForSingleValueEvent(listener);
    }

    public void observe(String uid, ValueEventListener listener) {
        DatabaseReference ref = service.childUserDatabase().child(uid);
        ref.addValueEventListener(listener);
    }

    public void queryByParent(String parentId, ValueEventListener listener) {
        service.childUserDatabase()
                .orderByChild("parentId")
                .equalTo(parentId)
                .addListenerForSingleValueEvent(listener);
    }

    public void observeByParent(String parentId, ValueEventListener listener) {
        service.childUserDatabase()
                .orderByChild("parentId")
                .equalTo(parentId)
                .addValueEventListener(listener);
    }
}
