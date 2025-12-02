package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.Notification;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.IncidentViewModel;
import com.example.b07project.viewModel.NotificationViewModel;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.TriageSessionViewModel;

public class RescueDecisionActivity extends BackButtonActivity {

    public static final String EXTRA_SESSION_ID = "session_id";
    public static final String EXTRA_CHILD_ID = "child_id";
    public static final String EXTRA_INCIDENT_ID = "incident_id";

    private String sessionId;
    private String childId;
    private String incidentId;
    private String parentId;
    private TriageSessionViewModel triageSessionViewModel;
    private IncidentViewModel incidentViewModel;
    private NotificationViewModel notificationViewModel;
    private ChildProfileViewModel childProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rescue_decision);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sessionId = getIntent().getStringExtra(EXTRA_SESSION_ID);
        childId = getIntent().getStringExtra(EXTRA_CHILD_ID);
        incidentId = getIntent().getStringExtra(EXTRA_INCIDENT_ID);

        triageSessionViewModel = new ViewModelProvider(this).get(TriageSessionViewModel.class);
        incidentViewModel = new ViewModelProvider(this).get(IncidentViewModel.class);
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
        childProfileViewModel.getChild().observe(this, child -> {
            if (child != null) {
                parentId = child.getParentId();
            }
        });
        if (childId != null) {
            childProfileViewModel.loadChild(childId);
        }
    }

    public void rescueEmergency(View view){
        if (childId == null || sessionId == null) {
            Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
            return;
        }
        String msg = getString(R.string.child_rescue_emergency_toast);
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        long now = System.currentTimeMillis();
        triageSessionViewModel.updateSession(childId, sessionId,
                buildSessionUpdate("emergency", now));
        if (incidentId != null) {
            incidentViewModel.updateIncident(childId, incidentId,
                    buildIncidentUpdate("emergency", now, true));
        }
        if (parentId != null) {
            Notification notification = new Notification(
                    getString(R.string.child_rescue_notification_title),
                    getString(R.string.child_rescue_emergency_notification));
            notification.setType("emergency");
            notification.setChildId(childId);
            notificationViewModel.addNotification(parentId, notification);
        }
        //go back to dashboard
        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);
    }

    public void rescueActionPlan(View view){
        if (childId == null || sessionId == null) {
            Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
            return;
        }
        long now = System.currentTimeMillis();
        triageSessionViewModel.updateSession(childId, sessionId,
                buildSessionUpdate("action_plan", now));
        if (incidentId != null) {
            incidentViewModel.updateIncident(childId, incidentId,
                    buildIncidentUpdate("action_plan", null, false));
        }
        Intent intent = new Intent(this, TechniqueHelperActivity.class);
        startActivity(intent);
    }

    private java.util.Map<String, Object> buildSessionUpdate(String decision, long resolvedAt) {
        java.util.Map<String, Object> updates = new java.util.HashMap<>();
        updates.put("decision", decision);
        updates.put("status", "resolved");
        updates.put("resolvedAt", resolvedAt);
        return updates;
    }

    private java.util.Map<String, Object> buildIncidentUpdate(String decision, Long alertedAt, boolean alertParent) {
        java.util.Map<String, Object> updates = new java.util.HashMap<>();
        updates.put("decision", decision);
        if (alertParent && alertedAt != null) {
            updates.put("parentAlertSent", true);
            updates.put("parentAlertSentAt", alertedAt);
        }
        return updates;
    }
}
