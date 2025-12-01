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

  // For the reports
  public DatabaseReference reportDatabase() {
    return database.getReference("reports");
  }

  public DatabaseReference parentReportsIndex(String parentId) {
    return database.getReference("users")
        .child("parents")
        .child(parentId)
        .child("reports");
  }

  public DatabaseReference childReportsIndex(String childId) {
    return database.getReference("users")
        .child("children")
        .child(childId)
        .child("reports");
  }

  public DatabaseReference providerReportsIndex(String providerId) {
    return database.getReference("users")
        .child("providers")
        .child(providerId)
        .child("reports");
  }

  public DatabaseReference parentInviteDatabase(String parentId) {
    return database.getReference("users")
        .child("parents")
        .child(parentId)
        .child("invite");
  }

  public DatabaseReference inviteCodeIndex() {
    return database.getReference("invites");
  }

  public DatabaseReference motivationDatabase(String childId) {
    return database.getReference("motivation").child(childId);
  }

  // The Logs
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

  public DatabaseReference controllerPlanDatabase(String childId) {
    return database.getReference("users")
        .child("children")
        .child(childId)
        .child("controllerPlans");
  }

  public DatabaseReference triageSessionDatabase(String childId) {
    return database.getReference("users")
        .child("children")
        .child(childId)
        .child("triageSessions");
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
