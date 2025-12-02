package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.User.ChildUser;
import com.example.b07project.services.ChildProfileRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ChildProfileViewModel extends ViewModel {
    private final Service service = new Service();
    private final ChildProfileRepository repository = new ChildProfileRepository(service);

    private final MutableLiveData<ChildUser> child = new MutableLiveData<>();
    private final MutableLiveData<java.util.List<ChildUser>> children = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<ChildUser> getChild() {
        return child;
    }

    public LiveData<java.util.List<ChildUser>> getChildren() {
        return children;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadChild(String uid) {
        repository.get(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChildUser value = snapshot.getValue(ChildUser.class);
                child.setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load child profile");
            }
        });
    }

    public void createChild(String uid, ChildUser childUser) {
        repository.create(uid, childUser);
    }

    public void updateChild(String uid, Map<String, Object> updates) {
        repository.update(uid, updates);
        loadChild(uid);
    }

    public void observeChildrenForParent(String parentId) {
        if (parentId == null) {
            children.setValue(java.util.Collections.emptyList());
            return;
        }
        repository.observeByParent(parentId, buildChildrenListener());
    }

    public void loadChildrenForParent(String parentId) {
        if (parentId == null) {
            children.setValue(java.util.Collections.emptyList());
            return;
        }
        repository.queryByParent(parentId, buildChildrenListener());
    }

    private ValueEventListener buildChildrenListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                java.util.List<ChildUser> data = new java.util.ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ChildUser value = childSnapshot.getValue(ChildUser.class);
                    if (value != null) {
                        data.add(value);
                    }
                }
                children.setValue(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load children");
            }
        };
    }
}
