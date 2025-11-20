package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.CheckIn;
import com.example.b07project.services.CheckInRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CheckInViewModel extends ViewModel {
    private final Service service = new Service();
    private final CheckInRepository checkInRepository = new CheckInRepository(service);

    private final MutableLiveData<List<CheckIn>> checkIn = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Getters
    public LiveData<List<CheckIn>> getCheckIn() {
        return this.checkIn;
    }
    public LiveData<String> getLogError() {
        return this.errorMessage;
    }

    // GET
    public void loadCheckInByUser(String uid) {
        checkInRepository.getAll(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CheckIn> inventories = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    CheckIn inventory = child.getValue(CheckIn.class);
                    if (inventory != null) {
                        inventories.add(inventory);
                    }
                }

                checkIn.setValue(inventories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load Check-Ins");
            }
        });
    }

    // CREATE
    public void addCheckIn(String uid, CheckIn item) {
        checkInRepository.add(uid, item);
        loadCheckInByUser(uid);
    }

    // UPDATE
    public void updateCheckIn(String uid, String itemId, Map<String, Object> updates) {
        checkInRepository.update(uid, itemId, updates);
         loadCheckInByUser(uid);
    }

    // DELETE
    public void deleteCheckIn(String uid, String itemId) {
        checkInRepository.delete(uid, itemId);
         loadCheckInByUser(uid);
    }
}
