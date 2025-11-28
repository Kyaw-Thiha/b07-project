package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.User.ParentUser;
import com.example.b07project.services.ParentProfileRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ParentProfileViewModel extends ViewModel {
    private final Service service = new Service();
    private final ParentProfileRepository repository = new ParentProfileRepository(service);

    private final MutableLiveData<ParentUser> parent = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<ParentUser> getParent() {
        return parent;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadParent(String uid) {
        repository.get(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ParentUser value = snapshot.getValue(ParentUser.class);
                parent.setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load parent profile");
            }
        });
    }

    public void createParent(String uid, ParentUser parentUser) {
        repository.create(uid, parentUser);
    }

    public void updateParent(String uid, Map<String, Object> updates) {
        repository.update(uid, updates);
        loadParent(uid);
    }
}
