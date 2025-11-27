package com.example.b07project.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MotivationRepository {
  private final Service service;

  public MotivationRepository(Service service) {
    this.service = service;
  }

  public void get(String childId, ValueEventListener listener) {
    service.motivationDatabase(childId).addListenerForSingleValueEvent(listener);
  }

  public void observe(String childId, ValueEventListener listener) {
    service.motivationDatabase(childId).addValueEventListener(listener);
  }

  public void updateStreak(String childId, String streakKey, Map<String, Object> updates) {
    DatabaseReference ref = service
        .motivationDatabase(childId)
        .child("streaks")
        .child(streakKey);

    ref.updateChildren(updates);
  }

  public void updateBadge(String childId, String badgeKey, Map<String, Object> updates) {
    DatabaseReference ref = service
        .motivationDatabase(childId)
        .child("badges")
        .child(badgeKey);

    ref.updateChildren(updates);
  }
}
