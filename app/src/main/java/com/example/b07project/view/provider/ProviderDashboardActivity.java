package com.example.b07project.view.provider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.ProviderChild;
import com.example.b07project.model.Report;
import com.example.b07project.view.common.OnboardingActivity;
import com.example.b07project.view.login.AskUsertypeActivity;
import com.example.b07project.viewModel.ReportViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProviderDashboardActivity extends OnboardingActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RecyclerView recyclerView;
    private ProviderChildAdapter adapter;
    private final List<ProviderChild> children = new ArrayList<>();
    private ProgressBar loading;
    private ReportViewModel reportViewModel;
    private final DateFormat dateFormat =
            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

    @Override
    protected String getOnboardingPrefKey() {
        return "provider_dashboard";
    }

    @Override
    protected void showOnboarding() {
        recyclerView.post(() -> {
            RecyclerView.ViewHolder vh =
                    recyclerView.findViewHolderForAdapterPosition(0);
            if (vh == null) return;

            View card = vh.itemView;
            View name = card.findViewById(R.id.textChildName);

            addStep(card, "This card shows the basic information of a child shared with you.");
            addStep(name, "Tap the name to view this child's detailed report.");

            startOnboardingSequence();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_provider_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser == null) {
            Toast.makeText(this, "Error: no logged-in provider.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.childrenRecycler);
        loading = findViewById(R.id.loading);
        if (loading != null) loading.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProviderChildAdapter(this, children);
        recyclerView.setAdapter(adapter);

        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        reportViewModel.getReports().observe(this, this::onReportsLoaded);
        reportViewModel.getErrorMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        reportViewModel.loadReportsByProvider(mUser.getUid());
        runOnboardingIfFirstTime();
    }

    private void onReportsLoaded(List<Report> reports) {
        children.clear();
        if (reports != null) {
            for (Report report : reports) {
                ProviderChild child = mapReportToChild(report);
                if (child != null) {
                    children.add(child);
                }
            }
        }
        adapter.notifyDataSetChanged();
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }
    }

    private ProviderChild mapReportToChild(Report report) {
        if (report == null || report.getChildId() == null) {
            return null;
        }
        ProviderChild child = new ProviderChild();
        child.setId(report.getChildId());
        child.setName(report.getChildName() != null ? report.getChildName() : "Unknown child");
        child.setAge(0);
        child.setParentName(report.getParentName() != null ? report.getParentName() : "-");
        child.setTodayZone(resolveTodayZone(report.getSummary()));
        child.setRescue7d(resolveRescueCount(report.getSummary()));
        child.setControllerAdherence(resolveAdherencePercent(report.getSummary()));
        child.setLastUpdated(formatLastUpdated(report.getEndDate()));
        return child;
    }

    private String formatLastUpdated(long endDate) {
        if (endDate <= 0) {
            return "-";
        }
        return dateFormat.format(new Date(endDate));
    }

    private String resolveTodayZone(Report.Summary summary) {
        if (summary == null) {
            return "-";
        }
        Map<String, Integer> distribution = summary.getZoneDistribution();
        if (distribution == null || distribution.isEmpty()) {
            return "-";
        }
        String bestZone = null;
        int bestValue = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            int value = entry.getValue() != null ? entry.getValue() : 0;
            if (value > bestValue) {
                bestValue = value;
                bestZone = entry.getKey();
            }
        }
        return bestZone != null ? bestZone : "-";
    }

    private int resolveRescueCount(Report.Summary summary) {
        return summary != null ? summary.getRescueCount() : 0;
    }

    private int resolveAdherencePercent(Report.Summary summary) {
        if (summary == null) {
            return 0;
        }
        return (int) Math.round(summary.getControllerAdherencePercent());
    }

    public void signOut(View view) {
        Intent intent = new Intent(this, AskUsertypeActivity.class);
        startActivity(intent);
        finish();
    }
}
