package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.PEF;
import com.example.b07project.services.PEFRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PEFViewModel extends ViewModel {
    private final Service service = new Service();
    private final PEFRepository pefRepository = new PEFRepository(service);

    private final MutableLiveData<List<PEF>> pef = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Getters
    public LiveData<List<PEF>> getPEF() {
        return this.pef;
    }
    public LiveData<String> getPEFError() {
        return this.errorMessage;
    }

    // GET
    public void loadPEFByUser(String uid) {
        pefRepository.getAll(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PEF> items = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    PEF item = child.getValue(PEF.class);
                    if (item != null) {
                        items.add(item);
                    }
                }

                pef.setValue(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load Medicine Inventories");
            }
        });
    }

    // CREATE
    public void addPEF(String uid, PEF item) {
        pefRepository.add(uid, item);
        loadPEFByUser(uid);
    }

    // UPDATE
    public void updatePEF(String uid, String pefId, Map<String, Object> updates) {
        pefRepository.update(uid, pefId, updates);
        loadPEFByUser(uid);
    }

    // DELETE
    public void deletePEF(String uid, String pefId) {
        pefRepository.delete(uid, pefId);
        loadPEFByUser(uid);
    }
}
