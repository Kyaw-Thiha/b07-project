package com.example.b07project.view.provider;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.b07project.R;
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
  private String reportId;
  @Nullable private Report currentReport;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityProviderReportBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setupRecycler();
    setupActions();

    reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
    reportId = getIntent().getStringExtra(EXTRA_REPORT_ID);
    if (reportId == null) {
      Toast.makeText(this, R.string.error_missing_report, Toast.LENGTH_SHORT).show();
      finish();
      return;
    }

    reportViewModel.getSelectedReport().observe(this, this::bindReport);
    reportViewModel.getErrorMessage().observe(this, this::showError);
    reportViewModel.getExportMessage().observe(this, this::handleExportMessage);
    reportViewModel.getExportInProgress().observe(this, this::setExportInProgress);
    reportViewModel.loadReportById(reportId);
  }

  private void setupRecycler() {
    RecyclerView recyclerView = binding.reportTriageList;
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(triageAdapter);
  }

  private void setupActions() {
    binding.reportExportButton.setOnClickListener(v -> {
      if (currentReport == null || reportId == null) {
        Toast.makeText(this, R.string.report_export_missing, Toast.LENGTH_SHORT).show();
        return;
      }
      reportViewModel.exportReport(reportId);
    });
  }

  private void bindReport(Report report) {
    currentReport = report;
    if (report == null) {
      showEmptyReportState();
      return;
    }
    binding.reportChildName.setText(safeText(report.getChildName()));
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
        getString(R.string.percentage_format, percent));
    binding.reportSymptomSummary.setText(formatSymptomBurden(summary.getSymptomBurden()));
  }

  private void bindTriage(List<Incident> incidents) {
    triageAdapter.submitList(incidents);
  }

  private void attachCharts(TrendInput input) {
    if (input == null || binding == null) {
      return;
    }
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
    if (report == null) {
      return TrendInput.empty();
    }
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

  private void setExportInProgress(Boolean exporting) {
    boolean inProgress = exporting != null && exporting;
    binding.reportExportButton.setEnabled(!inProgress);
    binding.reportExportButton.setText(inProgress
        ? getString(R.string.report_export_in_progress)
        : getString(R.string.report_export_pdf));
  }

  private void handleExportMessage(String message) {
    if (TextUtils.isEmpty(message)) {
      return;
    }
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    reportViewModel.clearExportMessage();
  }

  private void showError(String message) {
    if (TextUtils.isEmpty(message)) {
      return;
    }
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private void showEmptyReportState() {
    binding.reportChildName.setText(R.string.report_loading_placeholder);
    binding.reportDateRange.setText("-");
    bindSummary(null);
    bindTriage(null);
  }

  private String safeText(String value) {
    return TextUtils.isEmpty(value) ? getString(R.string.report_loading_placeholder) : value;
  }
}
