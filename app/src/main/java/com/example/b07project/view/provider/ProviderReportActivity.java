package com.example.b07project.view.provider;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.b07project.databinding.ActivityProviderReportBinding;
import com.example.b07project.model.Incident;
import com.example.b07project.model.Report;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.fragments.BaseTrendFragment;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ReportViewModel;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProviderReportActivity extends BackButtonActivity {

  public static final String EXTRA_REPORT_ID = "reportId";

  private ActivityProviderReportBinding binding;
  private ReportViewModel reportViewModel;
  private final TriageIncidentAdapter triageAdapter = new TriageIncidentAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityProviderReportBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setupRecycler();
    setupActions();

    reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
    String reportId = getIntent().getStringExtra(EXTRA_REPORT_ID);
    reportViewModel.getSelectedReport().observe(this, this::bindReport);
    reportViewModel.loadReportById(reportId);
  }

  private void setupRecycler() {
    RecyclerView recyclerView = binding.reportTriageList;
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(triageAdapter);
  }

  private void setupActions() {
    binding.reportExportButton.setOnClickListener(
        v -> Toast.makeText(this, "Export coming soon", Toast.LENGTH_SHORT).show());
  }

  private void bindReport(Report report) {
    if (report == null) {
      return;
    }
    binding.reportChildName.setText(report.getChildName());
    binding.reportDateRange.setText(formatDateRange(report.getStartDate(), report.getEndDate()));
    bindSummary(report.getSummary());
    bindTriage(report.getIncidents());
    attachCharts(buildTrendInput(report));
  }

  private void bindSummary(Report.Summary summary) {
    if (summary == null) {
      binding.reportRescueCount.setText("-");
      binding.reportControllerPercent.setText("-");
      binding.reportSymptomSummary.setText("-");
      return;
    }
    binding.reportRescueCount.setText(String.valueOf(summary.getRescueCount()));
    int percent = (int) Math.round(summary.getControllerAdherencePercent());
    binding.reportControllerPercent.setText(
        getString(com.example.b07project.R.string.percentage_format, percent));
    binding.reportSymptomSummary.setText(formatSymptomBurden(summary.getSymptomBurden()));
  }

  private void bindTriage(List<Incident> incidents) {
    triageAdapter.submitList(incidents);
  }

  private void attachCharts(TrendInput input) {
    setTrendInput(binding.chartRescueUsage.getId(), input);
    setTrendInput(binding.chartControllerAdherence.getId(), input);
    setTrendInput(binding.chartZoneDistribution.getId(), input);
    setTrendInput(binding.chartPefTrend.getId(), input);
  }

  private void setTrendInput(@IdRes int containerId, TrendInput input) {
    Fragment fragment = getSupportFragmentManager().findFragmentById(containerId);
    if (fragment instanceof BaseTrendFragment) {
      ((BaseTrendFragment) fragment).setTrendInput(input);
    }
  }

  private TrendInput buildTrendInput(Report report) {
    return new TrendInput(report.getMedicineLogs(), report.getPefLogs(), report.getCheckIns());
  }

  private String formatDateRange(long start, long end) {
    DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    return format.format(new Date(start)) + " – " + format.format(new Date(end));
  }

  private String formatSymptomBurden(Map<String, Integer> map) {
    if (map == null || map.isEmpty()) {
      return "-";
    }
    int total = 0;
    for (Integer value : map.values()) {
      total += value != null ? value : 0;
    }
    return total + " days";
  }
}
