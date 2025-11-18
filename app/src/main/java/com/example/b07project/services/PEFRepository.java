package com.example.b07project;

public class PEFRepository {
  private final Service service;

  public PEFRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, Notification notification) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId)
        .push();

    ref.setValue(medicine);
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

  public void get(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observe(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .pefDatabase()
        .child(userId);

    ref.addValueEventListener(listener);
  }
}
