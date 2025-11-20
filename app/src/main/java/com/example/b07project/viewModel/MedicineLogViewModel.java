package com.example.b07project.viewModel;

import com.example.b07project.model.MedicineLog;
import com.example.b07project.services.MedicineLogRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicineLogViewModel extends ViewModel {
  private final Service service = new Service();
    private final MedicineLogRepository medicineLogRepository = new MedicineLogRepository(service);

    private final MutableLiveData<List<MedicineLog>> medicineLogs = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

  // Getters
  public LiveData<List<MedicineLog>> getLog() {
    return this.medicineLogs;
  }

    public LiveData<String> getLogError() {
        return this.errorMessage;
    }


    // Loaders
    public void loadLogByUser(String uid) {
        medicineLogRepository.getAll(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MedicineLog> logs = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    MedicineLog log = child.getValue(MedicineLog.class);
                    if (log != null) {
                        logs.add(log);
                    }
                }

                medicineLogs.setValue(logs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load Medicine Logs");
            }
        });
    }

    public void loadLogByUserById(String uid, String logId) {
        medicineLogRepository.get(uid, logId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MedicineLog log = snapshot.getValue(MedicineLog.class);
//                medicineLog.setValue(log);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

    // CREATE
    public void addLog(String uid, MedicineLog item) {
        medicineLogRepository.add(uid, item);
        loadLogByUser(uid);
    }

    // UPDATE
    public void updateInventory(String uid, String medicineId, Map<String, Object> updates) {
        medicineLogRepository.update(uid, medicineId, updates);
        loadLogByUser(uid);
    }

    // DELETE
    public void deleteInventory(String uid, String medicineId) {
        medicineLogRepository.delete(uid, medicineId);
        loadLogByUser(uid);
    }
}
