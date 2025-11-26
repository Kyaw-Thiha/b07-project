package com.example.b07project.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Service {
  private FirebaseDatabase database;

  public Service() {
      database = FirebaseDatabase.getInstance();
  }

  public DatabaseReference getRoot() {
      return database.getReference();
  }

  public DatabaseReference getUserDatabase() {
      return database.getReference("users");
  }

  public DatabaseReference baseUserDatabase() {
      return database.getReference("users").child("profiles");
  }

  public DatabaseReference parentUserDatabase() {
      return database.getReference("users").child("parents");
  }

  public DatabaseReference childUserDatabase() {
      return database.getReference("users").child("children");
  }

  public DatabaseReference providerUserDatabase() {
      return database.getReference("users").child("providers");
  }

  public DatabaseReference medicineInventoryDatabase(String parentId) {
      return database.getReference("users")
          .child("parents")
          .child(parentId)
          .child("inventory");
  }

  public DatabaseReference medicineLogDatabase(String childId) {
      return database.getReference("users")
          .child("children")
          .child(childId)
          .child("medicineLog");
  }

  public DatabaseReference pefDatabase(String childId) {
      return database.getReference("users")
          .child("children")
          .child(childId)
          .child("pefLog");
  }

  public DatabaseReference checkInDatabase(String childId) {
      return database.getReference("users")
          .child("children")
          .child(childId)
          .child("checkIn");
  }

  public DatabaseReference notificationDatabase(String childId) {
      return database.getReference("users")
          .child("children")
          .child(childId)
          .child("notification");
  }

  public DatabaseReference incidentDatabase(String childId) {
      return database.getReference("users")
          .child("children")
          .child(childId)
          .child("incidentLog");
  }
}
