package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.Invite;
import com.example.b07project.services.InviteRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InviteViewModel extends ViewModel {
    private static final long SEVEN_DAYS_MILLIS = 7L * 24 * 60 * 60 * 1000;

    private final Service service = new Service();
    private final InviteRepository repository = new InviteRepository(service);

    private final MutableLiveData<Invite> currentInvite = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> actionSuccess = new MutableLiveData<>();

    public LiveData<Invite> getCurrentInvite() {
        return currentInvite;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getActionSuccess() {
        return actionSuccess;
    }

    public void observeInvite(String parentId) {
        repository.observeInviteForParent(parentId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Invite invite = snapshot.getValue(Invite.class);
                currentInvite.postValue(invite);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.postValue("Failed to load invite");
            }
        });
    }

    public void generateInvite(String parentId) {
        long issuedAt = System.currentTimeMillis();
        Map<String, Boolean> childIds = new HashMap<>();
        Invite invite = new Invite(
                UUID.randomUUID().toString(),
                generateCode(),
                issuedAt,
                issuedAt + SEVEN_DAYS_MILLIS,
                parentId,
                childIds);
        String previousCode = currentInvite.getValue() != null ? currentInvite.getValue().getCode() : null;
        repository.saveInvite(parentId, invite, previousCode, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    errorMessage.postValue("Failed to save invite");
                    actionSuccess.postValue(false);
                } else {
                    currentInvite.postValue(invite);
                    actionSuccess.postValue(true);
                }
            }
        });
    }

    public void revokeInvite(String parentId) {
        Invite invite = currentInvite.getValue();
        if (invite == null) {
            errorMessage.postValue("No active invite to revoke");
            actionSuccess.postValue(false);
            return;
        }
        if (invite.getParentId() == null) {
            invite.setParentId(parentId);
        }
        repository.revokeInvite(invite, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    errorMessage.postValue("Failed to revoke invite");
                    actionSuccess.postValue(false);
                } else {
                    currentInvite.postValue(null);
                    actionSuccess.postValue(true);
                }
            }
        });
    }

    public void lookupInviteByCode(String code) {
        repository.getInviteByCode(code, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Invite invite = snapshot.getValue(Invite.class);
                currentInvite.postValue(invite);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.postValue("Failed to lookup invite");
            }
        });
    }

    private String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
