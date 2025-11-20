package com.example.b07project.services;

import com.example.b07project.model.PEF;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
public class PEFRepository {
  private final Service service;

  public PEFRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, PEF pef) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId)
        .push();

    ref.setValue(pef);
  }

  public void update(String userId, String pefId, Map<String, Object> updates) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId)
        .child(pefId);

    ref.updateChildren(updates);
  }

  public void delete(String userId, String notificationId) {
    DatabaseReference ref = service
        .notificationDatabase()
        .child(userId)
        .child(notificationId);

    ref.removeValue();
  }

  public void get(String userId, String pefId, ValueEventListener listener) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId)
        .child(pefId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void getAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId);

    ref.addValueEventListener(listener);
  }
}
