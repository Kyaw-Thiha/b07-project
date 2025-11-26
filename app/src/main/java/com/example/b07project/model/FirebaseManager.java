package com.example.b07project.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    private static FirebaseDatabase db;

    public FirebaseManager() {
        db = FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getRefParent() {
        return db.getReference().child("users").child("parents");
    }

    public static DatabaseReference getRefProvider() {
        return db.getReference().child("users").child("providers");
    }
}
