package com.example.b07project.view.parent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.Notification;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.view.common.NotificationsAdapter;
import com.example.b07project.viewModel.NotificationViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class NotificationsActivity extends BackButtonActivity {

    private NotificationViewModel notificationViewModel;
    private NotificationsAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_notifcations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        parentId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        recyclerView = findViewById(R.id.notificationsList);
        emptyView = findViewById(R.id.emptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationsAdapter(new NotificationsAdapter.NotificationActionListener() {
            @Override
            public void onMarkRead(Notification notification) {
                if (parentId == null || notification.getNotificationId() == null) return;
                Map<String, Object> updates = new HashMap<>();
                updates.put("status", "read");
                updates.put("deliveredAt", System.currentTimeMillis());
                notificationViewModel.updateNotification(parentId, notification.getNotificationId(), updates);
            }

            @Override
            public void onDelete(Notification notification) {
                if (parentId == null || notification.getNotificationId() == null) return;
                notificationViewModel.deleteNotification(parentId, notification.getNotificationId());
            }
        });
        recyclerView.setAdapter(adapter);

        setupViewModel();
    }

    private void setupViewModel() {
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        if (parentId != null) {
            notificationViewModel.loadNotificationByUser(parentId);
        }
        notificationViewModel.getNotification().observe(this, list -> {
            adapter.submitList(list);
            emptyView.setVisibility(list == null || list.isEmpty() ? View.VISIBLE : View.GONE);
        });
        notificationViewModel.getLogError().observe(this, error -> {
            if (error != null) {
                emptyView.setText(error);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }
}
