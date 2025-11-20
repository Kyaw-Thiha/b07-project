package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.Incident;
import com.example.b07project.services.IncidentRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IncidentViewModel extends ViewModel {
    private final Service service = new Service();
    private final IncidentRepository incidentRepository = new IncidentRepository(service);

    private final MutableLiveData<List<Incident>> incident = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Getters
    public LiveData<List<Incident>> getInventory() {
        return this.incident;
    }
    public LiveData<String> getIncidentError() {
        return this.errorMessage;
    }

    // GET
    public void loadIncidentByUser(String uid) {
        incidentRepository.getAll(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Incident> inventories = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    Incident inventory = child.getValue(Incident.class);
                    if (inventory != null) {
                        inventories.add(inventory);
                    }
                }

                incident.setValue(inventories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load Incidents");
            }
        });
    }

    // CREATE
    public void addIncident(String uid, Incident item) {
        incidentRepository.add(uid, item);
        loadIncidentByUser(uid);
    }

    // UPDATE
    public void updateIncident(String uid, String itemId, Map<String, Object> updates) {
        incidentRepository.update(uid, itemId, updates);
        loadIncidentByUser(uid);
    }

    // DELETE
    public void deleteIncident(String uid, String itemId) {
        incidentRepository.delete(uid, itemId);
        loadIncidentByUser(uid);
    }
}
