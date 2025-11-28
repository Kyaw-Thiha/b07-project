package com.example.b07project.services;

import com.example.b07project.model.MedicineLog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MedicineLogRepository {
  private final Service service;

  public MedicineLogRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, MedicineLog medicine) {
    DatabaseReference ref = service
        .medicineLogDatabase(userId)
        .push();

    ref.setValue(medicine);
  }

  public void update(String userId, String medicineId, Map<String, Object> updates) {
    DatabaseReference ref = service
        .medicineLogDatabase(userId)
        .child(medicineId);

    ref.updateChildren(updates);
  }

  public void delete(String userId, String medicineId) {
    DatabaseReference ref = service
        .medicineLogDatabase(userId)
        .child(medicineId);

    ref.removeValue();
  }

  public void get(String userId, String medicineLogId, ValueEventListener listener) {
    DatabaseReference ref = service
        .medicineLogDatabase(userId)
        .child(medicineLogId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void getAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .medicineLogDatabase(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .medicineLogDatabase(userId);

    ref.addValueEventListener(listener);
  }
}
