package com.example.b07project.view.parent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Notification;
import com.example.b07project.model.Report;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.view.child.LogChildMedicineActivity;
import com.example.b07project.view.child.PefEntryActivity;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.NotificationViewModel;
import com.example.b07project.viewModel.ParentProfileViewModel;
import com.example.b07project.viewModel.ReportViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ParentDashboardActivity extends BackButtonActivity {

    private static final String PREF_SELECTED_CHILD = "PARENT_SELECTED_CHILD";

    private ParentProfileViewModel parentProfileViewModel;
    private ChildProfileViewModel childProfileViewModel;
    private ReportViewModel reportViewModel;
    private NotificationViewModel notificationViewModel;

    private TextView selectedChildText;
    private TextView weeklyRescueText;
    private TextView childZoneText;
    private TextView lastRescueText;
    private TextView trendSnippetText;
    private TextView notificationBadge;
    private ToggleButton chartToggle;
    private Button chooseChildButton;

    private final List<ChildUser> availableChildren = new ArrayList<>();
    private ParentUser currentParent;
    private String selectedChildId;
    private SharedPreferences prefs;
    private FirebaseAuth auth;
    private Report latestReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("APP_DATA", MODE_PRIVATE);
        selectedChildId = prefs.getString(PREF_SELECTED_CHILD, null);

        initViews();
        setupViewModels();

        String parentUid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
        if (parentUid == null) {
            Toast.makeText(this, "Unable to load parent profile", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        parentProfileViewModel.loadParent(parentUid);
        childProfileViewModel.observeChildrenForParent(parentUid);
    }

    private void initViews() {
        selectedChildText = findViewById(R.id.textSelectedChild);
        weeklyRescueText = findViewById(R.id.textWeeklyRescue);
        childZoneText = findViewById(R.id.textChildZone);
        lastRescueText = findViewById(R.id.textLastRescue);
        trendSnippetText = findViewById(R.id.textTrendSnippet);
        notificationBadge = findViewById(R.id.textNotificationBadge);
        chartToggle = findViewById(R.id.toggleButton);
        chooseChildButton = findViewById(R.id.chooseChild);

        chartToggle.setOnCheckedChangeListener((buttonView, isChecked) -> updateTrendSnippet(isChecked ? 30 : 7));
        chooseChildButton.setOnClickListener(v -> showChildPicker());
    }

    private void setupViewModels() {
        parentProfileViewModel = new ViewModelProvider(this).get(ParentProfileViewModel.class);
        childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        parentProfileViewModel.getParent().observe(this, parent -> currentParent = parent);

        childProfileViewModel.getChildren().observe(this, this::onChildrenLoaded);
        childProfileViewModel.getChild().observe(this, this::bindChild);
        reportViewModel.getReports().observe(this, this::bindReports);
        notificationViewModel.getNotification().observe(this, this::bindNotifications);
    }

    private void onChildrenLoaded(List<ChildUser> children) {
        availableChildren.clear();
        if (children != null) {
            availableChildren.addAll(children);
        }
        if (availableChildren.isEmpty()) {
            bindChild(null);
            return;
        }
        if (selectedChildId == null || !hasChild(selectedChildId)) {
            selectedChildId = availableChildren.get(0).getUid();
        }
        selectChild(selectedChildId, false);
    }

    private boolean hasChild(String childId) {
        if (childId == null) return false;
        for (ChildUser child : availableChildren) {
            if (childId.equals(child.getUid())) {
                return true;
            }
        }
        return false;
    }

    private void showChildPicker() {
        if (availableChildren.isEmpty()) {
            Toast.makeText(this, "No children linked to this account yet.", Toast.LENGTH_SHORT).show();
            return;
        }
        CharSequence[] names = new CharSequence[availableChildren.size()];
        for (int i = 0; i < availableChildren.size(); i++) {
            names[i] = availableChildren.get(i).getName();
        }
        new AlertDialog.Builder(this)
                .setTitle("Choose child")
                .setItems(names, (dialog, which) -> {
                    ChildUser chosen = availableChildren.get(which);
                    selectChild(chosen.getUid(), true);
                })
                .show();
    }

    private void selectChild(String childId, boolean persistSelection) {
        if (TextUtils.isEmpty(childId)) {
            return;
        }
        selectedChildId = childId;
        if (persistSelection) {
            prefs.edit().putString(PREF_SELECTED_CHILD, childId).apply();
        }
        childProfileViewModel.loadChild(childId);
        reportViewModel.loadReportsByChild(childId);
        notificationViewModel.loadNotificationByUser(childId);
        updateChooseButtonLabel(childId);
        updateTrendSnippet(chartToggle.isChecked() ? 30 : 7);
    }

    private void updateChooseButtonLabel(String childId) {
        for (ChildUser child : availableChildren) {
            if (childId.equals(child.getUid())) {
                chooseChildButton.setText(child.getName());
                return;
            }
        }
        chooseChildButton.setText(R.string.child_choose_button);
    }

    private void bindChild(ChildUser child) {
        if (child == null) {
            selectedChildText.setText(getString(R.string.no_child_selected));
            childZoneText.setText(R.string.child_zone_text);
            return;
        }
        selectedChildText.setText(child.getName());
        String zone = child.getCurrentZone();
        if (TextUtils.isEmpty(zone)) {
            childZoneText.setText(getString(R.string.child_zone_template, "N/A"));
        } else {
            childZoneText.setText(getString(R.string.child_zone_template, zone));
        }
    }

    private void bindReports(List<Report> reports) {
        if (reports == null || reports.isEmpty()) {
            latestReport = null;
            weeklyRescueText.setText(R.string.weekly_rescue_count);
            lastRescueText.setText(R.string.last_rescue_time);
            trendSnippetText.setText(R.string.dashboard_no_reports);
            return;
        }
        Report newest = reports.get(0);
        for (Report report : reports) {
            if (report != null && (newest == null || report.getCreatedAt() > newest.getCreatedAt())) {
                newest = report;
            }
        }
        latestReport = newest;
        Report.Summary summary = newest != null ? newest.getSummary() : null;
        if (summary == null) {
            weeklyRescueText.setText(R.string.weekly_rescue_count);
            lastRescueText.setText(R.string.last_rescue_time);
            trendSnippetText.setText(R.string.dashboard_no_reports);
            return;
        }
        int weeklyRescues = computeRescues(summary, 7);
        weeklyRescueText.setText(getString(R.string.weekly_rescue_template, weeklyRescues));

        long lastRescue = summary.getLastRescueTime();
        if (lastRescue > 0) {
            String formatted = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
                    .format(lastRescue);
            lastRescueText.setText(getString(R.string.last_rescue_template, formatted));
        } else {
            lastRescueText.setText(getString(R.string.last_rescue_template, "N/A"));
        }

        updateTrendSnippet(chartToggle.isChecked() ? 30 : 7);
    }

    private int computeRescues(Report.Summary summary, int days) {
        if (summary == null || summary.getTimeSeries() == null) {
            return 0;
        }
        long now = System.currentTimeMillis();
        long threshold = now - TimeUnit.DAYS.toMillis(days);
        int count = 0;
        for (Report.Summary.TimeSeriesPoint point : summary.getTimeSeries()) {
            if (point != null && point.getTimestamp() >= threshold) {
                count += point.getValue();
            }
        }
        return count;
    }

    private void updateTrendSnippet(int days) {
        if (latestReport == null || latestReport.getSummary() == null) {
            trendSnippetText.setText(R.string.dashboard_no_reports);
            return;
        }
        int rescues = computeRescues(latestReport.getSummary(), days);
        trendSnippetText.setText(getString(R.string.trend_snippet_template, rescues, days));
    }

    private void bindNotifications(List<Notification> notifications) {
        if (notifications == null || notifications.isEmpty()) {
            notificationBadge.setVisibility(View.GONE);
            return;
        }
        int unread = 0;
        for (Notification notification : notifications) {
            if (notification != null && !"read".equalsIgnoreCase(notification.getStatus())) {
                unread++;
            }
        }
        if (unread <= 0) {
            notificationBadge.setVisibility(View.GONE);
        } else {
            notificationBadge.setVisibility(View.VISIBLE);
            String label = unread > 99 ? "99+" : String.valueOf(unread);
            notificationBadge.setText(label);
        }
    }

    public void manage_child(View view)
    {
        Intent intent = new Intent(this, ManageChildActivity.class);
        startActivity(intent);
    }

    public void Inventory(View view)
    {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }
    public void medicine_log(View view)
    {
        Intent intent = new Intent(this, LogChildMedicineActivity.class);
        startActivity(intent);
    }
    public void notifications(View view)
    {
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }
    public void peak_flow(View view) {
        Intent intent = new Intent(this,PefEntryActivity.class);
        startActivity(intent);
    }

    public void incident_log(View view){
        Intent intent = new Intent(this, IncidentLogActivity.class);
        startActivity(intent);
    }

    public void provider(View view){
        Intent intent = new Intent(this, InviteProviderActivity.class);
        startActivity(intent);
    }

    public void add_action_plan(View view)
    {
        Intent intent = new Intent(this,AddActionPlan.class);
        startActivity(intent);
    }
}
