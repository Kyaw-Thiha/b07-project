package com.example.b07project.view.provider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Report;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ReportViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChildReportActivity extends BackButtonActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ReportViewModel reportViewModel;
    private ChildReportAdapter adapter;
    private ProgressBar loading;
    private TextView emptyView;
    private RecyclerView recyclerView;
    private String providerId;
    private String childId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_report_page);

        loading = findViewById(R.id.child_report_loading);
        emptyView = findViewById(R.id.child_report_empty);
        recyclerView = findViewById(R.id.child_report_list);

        adapter = new ChildReportAdapter();
        adapter.setOnReportClickListener(this::openReportDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser == null) {
            Toast.makeText(this, R.string.error_missing_provider, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        providerId = mUser.getUid();
        childId = getIntent().getStringExtra("childId");
        if (childId == null) {
            Toast.makeText(this, R.string.error_missing_child, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        reportViewModel.getReports().observe(this, this::onReportsLoaded);
        reportViewModel.getErrorMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        loading.setVisibility(View.VISIBLE);
        reportViewModel.loadReportsByChild(childId);
    }

    private void onReportsLoaded(List<Report> reports) {
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }
        List<Report> filtered = new ArrayList<>();
        if (reports != null) {
            for (Report report : reports) {
                if (report != null && providerId.equals(report.getProviderId())) {
                    filtered.add(report);
                }
            }
        }
        adapter.submitList(filtered);
        emptyView.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(filtered.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void openReportDetail(Report report) {
        if (report == null || report.getUid() == null) {
            Toast.makeText(this, R.string.error_missing_report, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ProviderReportActivity.class);
        intent.putExtra(ProviderReportActivity.EXTRA_REPORT_ID, report.getUid());
        startActivity(intent);
    }
}
