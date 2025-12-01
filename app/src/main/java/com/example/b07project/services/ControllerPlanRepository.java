package com.example.b07project.services;

import com.example.b07project.model.ControllerPlan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ControllerPlanRepository {
  private final Service service;

  public ControllerPlanRepository(Service service) {
    this.service = service;
  }

  public void add(String childId, ControllerPlan plan) {
    DatabaseReference ref = service.controllerPlanDatabase(childId).push();
    plan.setPlanId(ref.getKey());
    plan.setChildId(childId);
    long now = System.currentTimeMillis();
    if (plan.getCreatedAt() == 0) {
      plan.setCreatedAt(now);
    }
    plan.setUpdatedAt(now);
    ref.setValue(plan);
  }

  public void update(String childId, String planId, Map<String, Object> updates) {
    DatabaseReference ref = service.controllerPlanDatabase(childId).child(planId);
    updates.put("updatedAt", System.currentTimeMillis());
    ref.updateChildren(updates);
  }

  public void delete(String childId, String planId) {
    service.controllerPlanDatabase(childId).child(planId).removeValue();
  }

  public void getAll(String childId, ValueEventListener listener) {
    service.controllerPlanDatabase(childId).addListenerForSingleValueEvent(listener);
  }

  public void observeAll(String childId, ValueEventListener listener) {
    service.controllerPlanDatabase(childId).addValueEventListener(listener);
  }
}
