package com.example.b07project.viewModel;

import android.app.Notification;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.services.NotificationRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NotificationViewModel extends ViewModel {
    private final Service service = new Service();
    private final NotificationRepository notificationRepository = new NotificationRepository(service);

    private final MutableLiveData<List<Notification>> notification = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Getters
    public LiveData<List<Notification>> getNotification() {
        return this.notification;
    }
    public LiveData<String> getLogError() {
        return this.errorMessage;
    }

    // GET
    public void loadNotificationByUser(String uid) {
        notificationRepository.getAll(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Notification> inventories = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    Notification inventory = child.getValue(Notification.class);
                    if (inventory != null) {
                        inventories.add(inventory);
                    }
                }

                notification.setValue(inventories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load Notifications");
            }
        });
    }

    // CREATE
    public void addNotification(String uid, Notification item) {
        notificationRepository.add(uid, item);
        loadNotificationByUser(uid);
    }

    // UPDATE
    public void updateNotification(String uid, String itemId, Map<String, Object> updates) {
        notificationRepository.update(uid, itemId, updates);
         loadNotificationByUser(uid);
    }

    // DELETE
    public void deleteNotification(String uid, String itemId) {
        notificationRepository.delete(uid, itemId);
         loadNotificationByUser(uid);
    }
}
