package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.ControllerPlan;
import com.example.b07project.services.ControllerPlanRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerPlanViewModel extends ViewModel {
  private final Service service = new Service();
  private final ControllerPlanRepository repository = new ControllerPlanRepository(service);

  private final MutableLiveData<List<ControllerPlan>> plans = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

  public LiveData<List<ControllerPlan>> getPlans() {
    return plans;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  public void loadPlans(String childId) {
    repository.getAll(childId, buildListener());
  }

  public void observePlans(String childId) {
    repository.observeAll(childId, buildListener());
  }

  public void addPlan(String childId, ControllerPlan plan) {
    repository.add(childId, plan);
    loadPlans(childId);
  }

  public void updatePlan(String childId, String planId, Map<String, Object> updates) {
    repository.update(childId, planId, updates);
    loadPlans(childId);
  }

  public void deletePlan(String childId, String planId) {
    repository.delete(childId, planId);
    loadPlans(childId);
  }

  private ValueEventListener buildListener() {
    return new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        List<ControllerPlan> data = new ArrayList<>();
        for (DataSnapshot child : snapshot.getChildren()) {
          ControllerPlan plan = child.getValue(ControllerPlan.class);
          if (plan != null) {
            if (plan.getPlanId() == null) {
              plan.setPlanId(child.getKey());
            }
            data.add(plan);
          }
        }
        plans.postValue(data);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        errorMessage.postValue("Failed to load controller plans");
      }
    };
  }
}
