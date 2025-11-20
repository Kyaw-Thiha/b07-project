package com.example.b07project.services;

import com.example.b07project.model.CheckIn;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class CheckInRepository {
  private final Service service;

  public CheckInRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, CheckIn checkIn) {
    DatabaseReference ref = service
        .checkInDatabase()
        .child(userId)
        .push();

    ref.setValue(checkIn);
  }

  public void update(String userId, String checkInId, Map<String, Object> updates) {
    DatabaseReference ref = service
        .checkInDatabase()
        .child(userId)
        .child(checkInId);

    ref.updateChildren(updates);
  }

  public void delete(String userId, String checkInId) {
    DatabaseReference ref = service
        .checkInDatabase()
        .child(userId)
        .child(checkInId);

    ref.removeValue();
  }

  public void get(String userId, String checkInId, ValueEventListener listener) {
    DatabaseReference ref = service
        .checkInDatabase()
        .child(userId)
        .child(checkInId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void getAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .checkInDatabase()
        .child(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .checkInDatabase()
        .child(userId);

    ref.addValueEventListener(listener);
  }
}
