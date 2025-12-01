package com.example.b07project.services;

import com.example.b07project.model.TriageSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class TriageSessionRepository {
  private final Service service;

  public TriageSessionRepository(Service service) {
    this.service = service;
  }

  public void add(String childId, TriageSession session) {
    DatabaseReference ref = service.triageSessionDatabase(childId).push();
    session.setSessionId(ref.getKey());
    session.setChildId(childId);
    if (session.getStartedAt() == 0) {
      session.setStartedAt(System.currentTimeMillis());
    }
    ref.setValue(session);
  }

  public void update(String childId, String sessionId, Map<String, Object> updates) {
    service.triageSessionDatabase(childId).child(sessionId).updateChildren(updates);
  }

  public void delete(String childId, String sessionId) {
    service.triageSessionDatabase(childId).child(sessionId).removeValue();
  }

  public void getAll(String childId, ValueEventListener listener) {
    service.triageSessionDatabase(childId).addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String childId, ValueEventListener listener) {
    service.triageSessionDatabase(childId).addValueEventListener(listener);
  }
}
