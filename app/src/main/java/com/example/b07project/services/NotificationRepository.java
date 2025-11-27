package com.example.b07project.services;

import com.example.b07project.model.Notification;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class NotificationRepository {
  private final Service service;

  public NotificationRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, Notification notification) {
    DatabaseReference ref = service
        .notificationDatabase(userId)
        .push();

    ref.setValue(notification);
  }

  public void update(String userId, String notificationId, Map<String, Object> updates) {
    DatabaseReference ref = service
        .notificationDatabase(userId)
        .child(notificationId);

    ref.updateChildren(updates);
  }

  public void delete(String userId, String notificationId) {
    DatabaseReference ref = service
        .notificationDatabase(userId)
        .child(notificationId);

    ref.removeValue();
  }

  public void get(String userId, String notificationId, ValueEventListener listener) {
    DatabaseReference ref = service
        .notificationDatabase(userId)
        .child(notificationId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void getAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .notificationDatabase(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .notificationDatabase(userId);

    ref.addValueEventListener(listener);
  }
}
