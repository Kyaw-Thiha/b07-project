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

  public DatabaseReference medicineInventoryDatabase() {
      return database.getReference("medicineInventory");
  }

  public DatabaseReference medicineLogDatabase() {
      return database.getReference("medicineLog");
  }

  public DatabaseReference pefDatabase() {
      return database.getReference("pefLog");
  }

  public DatabaseReference checkInDatabase() {
      return database.getReference("check-in");
  }

  public DatabaseReference notificationDatabase() {
    return this.database.getReference("notification");
  }

  public DatabaseReference incidentDatabase() {
      return database.getReference("incidentLog");
  }
}
