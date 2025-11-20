package com.example.b07project.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Service {
  private final FirebaseDatabase database;

  public Service() {
    this.database = FirebaseDatabase.getInstance();
  }

  public DatabaseReference root() {
    return this.database.getReference();
  }

  public DatabaseReference userDatabase() {
    return this.database.getReference("user");
  }

  public DatabaseReference medicineInventoryDatabase() {
    return this.database.getReference("medicine-inventory");
  }

  public DatabaseReference medicineLogDatabase() {
    return this.database.getReference("medicine-log");
  }

  public DatabaseReference pefDatabase() {
    return this.database.getReference("pef");
  }

  public DatabaseReference checkInDatabase() {
    return this.database.getReference("check-in");
  }

  public DatabaseReference notificationDatabase() {
    return this.database.getReference("notification");
  }

  public DatabaseReference incidentDatabase() {
    return this.database.getReference("incident");
  }
}
