package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.User.ProviderUser;
import com.example.b07project.services.ProviderProfileRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProviderProfileViewModel extends ViewModel {
    private final Service service = new Service();
    private final ProviderProfileRepository repository = new ProviderProfileRepository(service);

    private final MutableLiveData<ProviderUser> provider = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<ProviderUser> getProvider() {
        return provider;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadProvider(String uid) {
        repository.get(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProviderUser value = snapshot.getValue(ProviderUser.class);
                provider.setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load provider profile");
            }
        });
    }

    public void createProvider(String uid, ProviderUser providerUser) {
        repository.create(uid, providerUser);
    }

    public void updateProvider(String uid, Map<String, Object> updates) {
        repository.update(uid, updates);
        loadProvider(uid);
    }
}
