package com.example.b07project.services;

import com.example.b07project.model.Medicine;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
public class MedicineRepository {
  private final Service service;

  public MedicineRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, Medicine medicine) {
    DatabaseReference ref = service
        .medicineInventoryDatabase(userId)
        .push();

    ref.setValue(medicine);
  }

  public void update(String userId, String medicineId, Map<String, Object> updates) {
    DatabaseReference ref = service
        .medicineInventoryDatabase(userId)
        .child(medicineId);

    ref.updateChildren(updates);
  }

  public void delete(String userId, String medicineId) {
    DatabaseReference ref = service
        .medicineInventoryDatabase(userId)
        .child(medicineId);

    ref.removeValue();
  }

  public void get(String userId, String medicineId, ValueEventListener listener) {
    DatabaseReference ref = service
        .medicineInventoryDatabase(userId)
        .child(medicineId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void getAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .medicineInventoryDatabase(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .medicineInventoryDatabase(userId);

    ref.addValueEventListener(listener);
  }
}
