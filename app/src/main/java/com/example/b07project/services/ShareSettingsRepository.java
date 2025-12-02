package com.example.b07project.services;

import com.example.b07project.model.ShareSettings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ShareSettingsRepository {
    private final Service service;

    public ShareSettingsRepository(Service service) {
        this.service = service;
    }

    public void get(String parentId, String childId, ValueEventListener listener) {
        DatabaseReference ref = service.shareSettingsDatabase(parentId).child(childId);
        ref.addListenerForSingleValueEvent(listener);
    }

    public void observe(String parentId, String childId, ValueEventListener listener) {
        DatabaseReference ref = service.shareSettingsDatabase(parentId).child(childId);
        ref.addValueEventListener(listener);
    }

    public void save(String parentId, String childId, ShareSettings settings) {
        DatabaseReference ref = service.shareSettingsDatabase(parentId).child(childId);
        ref.setValue(settings);
    }

    public void update(String parentId, String childId, Map<String, Object> updates) {
        DatabaseReference ref = service.shareSettingsDatabase(parentId).child(childId);
        ref.updateChildren(updates);
    }
}
