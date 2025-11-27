package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.Medicine;
import com.example.b07project.services.MedicineRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MedicineViewModel extends ViewModel {
    private final Service service = new Service();
    private final MedicineRepository medicineRepository = new MedicineRepository(service);

    private final MutableLiveData<List<Medicine>> medicineInventory = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Getters
    public LiveData<List<Medicine>> getInventory() {
        return this.medicineInventory;
    }
    public LiveData<String> getLogError() {
        return this.errorMessage;
    }

    // GET
    public void loadInventoryByUser(String uid) {
        medicineRepository.getAll(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Medicine> inventories = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    Medicine inventory = child.getValue(Medicine.class);
                    if (inventory != null) {
                        inventories.add(inventory);
                    }
                }

                medicineInventory.setValue(inventories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load Medicine Inventories");
            }
        });
    }

    // CREATE
    public void addInventory(String uid, Medicine item) {
        medicineRepository.add(uid, item);
        loadInventoryByUser(uid);
    }

    // UPDATE
    public void updateInventory(String uid, String medicineId, Map<String, Object> updates) {
        medicineRepository.update(uid, medicineId, updates);
         loadInventoryByUser(uid);
    }

    // DELETE
    public void deleteInventory(String uid, String medicineId) {
        medicineRepository.delete(uid, medicineId);
         loadInventoryByUser(uid);
    }
}
