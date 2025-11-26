package com.example.b07project.services;
import com.example.b07project.model.Incident;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
public class IncidentRepository {
  private final Service service;

  public IncidentRepository(Service service) {
    this.service = service;
  }

  public void add(String userId, Incident incident) {
    DatabaseReference ref = service
        .incidentDatabase(userId)
        .push();

    ref.setValue(incident);
  }

  public void update(String userId, String incidentId, Map<String, Object> updates) {
    DatabaseReference ref = service
        .incidentDatabase(userId)
        .child(incidentId);

    ref.updateChildren(updates);
  }

  public void delete(String userId, String incidentId) {
    DatabaseReference ref = service
        .incidentDatabase(userId)
        .child(incidentId);

    ref.removeValue();
  }

  public void get(String userId, String incidentId, ValueEventListener listener) {
    DatabaseReference ref = service
        .incidentDatabase(userId)
        .child(incidentId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void getAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .incidentDatabase(userId);

    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String userId, ValueEventListener listener) {
    DatabaseReference ref = service
        .incidentDatabase(userId);

    ref.addValueEventListener(listener);
  }
}
