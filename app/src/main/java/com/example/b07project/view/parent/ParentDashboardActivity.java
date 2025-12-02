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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Notification;
import com.example.b07project.model.Report;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.model.User.SessionManager;
import com.example.b07project.view.child.LogChildMedicineActivity;
import com.example.b07project.view.child.PefEntryActivity;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.view.charts.ChartBindingUtils;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.TrendPoint;
import com.example.b07project.view.charts.strategy.RescueUsageStrategy;
import com.example.b07project.view.charts.util.DateRange;
import com.example.b07project.view.login.AskUsertypeActivity;
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

    public static final String EXTRA_PARENT_UID = "extra_parent_uid";

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
    private com.github.mikephil.charting.charts.LineChart rescueTrendChart;

    private final List<ChildUser> availableChildren = new ArrayList<>();
    private ParentUser currentParent;
    private String parentUid;
    private String selectedChildId;
    private SharedPreferences prefs;
    private FirebaseAuth auth;
    private Report latestReport;
    private ActivityResultLauncher<Intent> chooseChildLauncher;
    private FirebaseAuth.AuthStateListener authStateListener;
    private boolean dataInitialized;
    private final RescueUsageStrategy rescueUsageStrategy = new RescueUsageStrategy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseChildLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String childId = result.getData().getStringExtra("selectedChildId");
                        if (!TextUtils.isEmpty(childId)) {
                            selectChild(childId, true);
                        }
                    }
                });

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

        parentUid = resolveParentUid();

        initViews();
        setupViewModels();

        if (TextUtils.isEmpty(parentUid)) {
            Toast.makeText(this, "Loading parent account...", Toast.LENGTH_SHORT).show();
            registerAuthListener();
        } else {
            loadParentData();
        }
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
        rescueTrendChart = findViewById(R.id.chartRescueTrend);

        chartToggle.setOnCheckedChangeListener((buttonView, isChecked) -> updateTrendSnippet(isChecked ? 30 : 7));
        chooseChildButton.setOnClickListener(v -> openChooseChildActivity());
        ChartBindingUtils.styleLineChart(rescueTrendChart);
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

    private String resolveParentUid() {
        String extraUid = getIntent().getStringExtra(EXTRA_PARENT_UID);
        if (!TextUtils.isEmpty(extraUid)) {
            return extraUid;
        }
        if (SessionManager.getUser() instanceof ParentUser) {
            ParentUser cached = (ParentUser) SessionManager.getUser();
            if (cached != null && !TextUtils.isEmpty(cached.getUid())) {
                return cached.getUid();
            }
        }
        if (auth != null && auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        }
        return null;
    }

    private void registerAuthListener() {
        if (auth == null || authStateListener != null) {
            return;
        }
        authStateListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                parentUid = firebaseAuth.getCurrentUser().getUid();
                loadParentData();
            }
        };
        auth.addAuthStateListener(authStateListener);
    }

    private void loadParentData() {
        if (dataInitialized || TextUtils.isEmpty(parentUid)) {
            return;
        }
        dataInitialized = true;
        if (auth != null && authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
            authStateListener = null;
        }
        parentProfileViewModel.loadParent(parentUid);
        childProfileViewModel.observeChildrenForParent(parentUid);
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

    private void openChooseChildActivity() {
        Intent intent = new Intent(this, ChooseChildActivity.class);
        chooseChildLauncher.launch(intent);
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
            updateRescueTrendChart();
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
        updateRescueTrendChart();
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

    private void updateRescueTrendChart() {
        if (rescueTrendChart == null) {
            return;
        }
        if (latestReport == null) {
            rescueTrendChart.clear();
            rescueTrendChart.setNoDataText(getString(R.string.dashboard_no_reports));
            return;
        }
        TrendInput input = new TrendInput(
                latestReport.getMedicineLogs(),
                latestReport.getPefLogs(),
                latestReport.getCheckIns());
        List<TrendPoint> points = rescueUsageStrategy.compute(input, DateRange.thirtyDays());
        rescueTrendChart.setData(ChartBindingUtils.toLineData(points));
        rescueTrendChart.invalidate();
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

    public void Inventory(View view) {
        Intent intent = new Intent(this, InventoryListActivity.class);
        intent.putExtra(InventoryActivity.EXTRA_PARENT_UID, parentUid);
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

    public void signOut(View view) {
        Intent intent = new Intent(this, AskUsertypeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (auth != null && authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
            authStateListener = null;
        }
        super.onDestroy();
    }
}
