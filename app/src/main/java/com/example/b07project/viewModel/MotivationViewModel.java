package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.Motivation;
import com.example.b07project.services.MotivationRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MotivationViewModel extends ViewModel {
    private final Service service = new Service();
    private final MotivationRepository repository = new MotivationRepository(service);

    private final MutableLiveData<Motivation> motivation = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Motivation> getMotivation() {
        return motivation;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadMotivation(String childId) {
        repository.observe(childId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Motivation value = snapshot.getValue(Motivation.class);
                motivation.postValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.postValue("Failed to load motivation");
            }
        });
    }

    public void updateStreak(String childId, String streakKey, Map<String, Object> updates) {
        repository.updateStreak(childId, streakKey, updates);
    }

    public void updateBadge(String childId, String badgeKey, Map<String, Object> updates) {
        repository.updateBadge(childId, badgeKey, updates);
    }
}
