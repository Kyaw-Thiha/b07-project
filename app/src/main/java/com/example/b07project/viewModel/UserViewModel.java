package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.User.BaseUser;
import com.example.b07project.services.Service;
import com.example.b07project.services.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UserViewModel extends ViewModel {
    private final Service service = new Service();
    private final UserRepository repository = new UserRepository(service);

    private final MutableLiveData<BaseUser> user = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<BaseUser> getUser() {
        return user;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadUser(String uid) {
        repository.get(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BaseUser value = snapshot.getValue(BaseUser.class);
                user.setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load user profile");
            }
        });
    }

    public void createUser(String uid, BaseUser baseUser) {
        repository.create(uid, baseUser);
    }

    public void updateUser(String uid, Map<String, Object> updates) {
        repository.update(uid, updates);
        loadUser(uid);
    }
}
