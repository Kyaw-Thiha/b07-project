package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.CheckIn;
import com.example.b07project.model.Incident;
import com.example.b07project.model.Medicine;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.model.PEF;
import com.example.b07project.model.Report;
import com.example.b07project.model.ShareSettings;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.model.User.ProviderUser;
import com.example.b07project.services.ReportRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportViewModel extends ViewModel {
  private final Service service = new Service();
  private final ReportRepository repository = new ReportRepository(service);

  private final MutableLiveData<Boolean> reportCreated = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
  private final MutableLiveData<List<Report>> reports = new MutableLiveData<>();
  private final MutableLiveData<Report> selectedReport = new MutableLiveData<>();
  private final MutableLiveData<Boolean> exportInProgress = new MutableLiveData<>(false);
  private final MutableLiveData<String> exportMessage = new MutableLiveData<>();

  public LiveData<Boolean> getReportCreated() {
    return reportCreated;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  public LiveData<List<Report>> getReports() {
    return reports;
  }

  public LiveData<Report> getSelectedReport() {
    return selectedReport;
  }

  public LiveData<Boolean> getExportInProgress() {
    return exportInProgress;
  }

  public LiveData<String> getExportMessage() {
    return exportMessage;
  }

  public void clearExportMessage() {
    exportMessage.setValue(null);
  }

  public void createReport(ParentUser parent, ChildUser child, ProviderUser provider,
      List<Medicine> medicines,
      List<MedicineLog> medicineLogs,
      List<PEF> pefLogs,
      List<CheckIn> checkIns,
      List<Incident> incidents,
      long startDate,
      long endDate,
      ShareSettings shareSettings) {
    Report report = new Report();
    report.setParentId(parent != null ? parent.getUid() : null);
    report.setParentName(parent != null ? parent.getName() : null);
    report.setChildId(child != null ? child.getUid() : null);
    report.setChildName(child != null ? child.getName() : null);
    report.setProviderId(provider != null ? provider.getUid() : null);
    report.setProviderName(provider != null ? provider.getName() : null);
    long now = System.currentTimeMillis();
    report.setCreatedAt(now);
    report.setStartDate(startDate);
    report.setEndDate(endDate);
    ShareSettings settings = shareSettings != null ? shareSettings : new ShareSettings();
    if (settings.getParentId() == null && parent != null) {
      settings.setParentId(parent.getUid());
    }
    if (settings.getChildId() == null && child != null) {
      settings.setChildId(child.getUid());
    }
    if (settings.getProviderId() == null && provider != null) {
      settings.setProviderId(provider.getUid());
    }
    report.setShareSettings(settings);
    report.setSummary(buildSummary(medicineLogs, pefLogs, checkIns, incidents, startDate, endDate));

    if (!settings.isIncludeMedicines()) {
      report.setMedicines(null);
    } else {
      report.setMedicines(medicines);
    }

    if (!settings.isIncludeMedicineLogs()) {
      report.setMedicineLogs(null);
    } else {
      report.setMedicineLogs(medicineLogs);
    }

    if (!settings.isIncludePefLogs()) {
      report.setPefLogs(null);
    } else {
      report.setPefLogs(pefLogs);
    }

    if (!settings.isIncludeCheckIns()) {
      report.setCheckIns(null);
    } else {
      report.setCheckIns(checkIns);
    }

    if (!settings.isIncludeIncidents()) {
      report.setIncidents(null);
    } else {
      report.setIncidents(incidents);
    }

    repository.createReport(report, new DatabaseReference.CompletionListener() {
      @Override
      public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
        if (error != null) {
          errorMessage.postValue("Failed to create report");
          reportCreated.postValue(false);
        } else {
          reportCreated.postValue(true);
        }
      }
    });
  }

  public void loadReportsByParent(String parentId) {
    repository.observeReportsByParent(parentId, buildReportsListener());
  }

  public void loadReportsByChild(String childId) {
    repository.observeReportsByChild(childId, buildReportsListener());
  }

  public void loadReportsByProvider(String providerId) {
    repository.observeReportsByProvider(providerId, buildReportsListener());
  }

  public void loadReportById(String reportId) {
    if (reportId == null) {
      selectedReport.postValue(null);
      return;
    }
    repository.observeReport(reportId, new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        Report report = snapshot.getValue(Report.class);
        if (report != null) {
          report.setUid(snapshot.getKey());
        }
        selectedReport.postValue(report);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        errorMessage.postValue("Failed to load report");
      }
    });
  }

  public void exportReport(String reportId) {
    if (reportId == null) {
      exportMessage.setValue("Missing report id");
      return;
    }
    exportInProgress.setValue(true);
    repository.exportReport(reportId, (error, ref) -> {
      exportInProgress.postValue(false);
      if (error != null) {
        exportMessage.postValue("Failed to request export: " + error.getMessage());
      } else {
        exportMessage.postValue("Report export requested. We will notify you when it is ready.");
      }
    });
  }

  private ValueEventListener buildReportsListener() {
    return new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        List<Report> data = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
          Report report = childSnapshot.getValue(Report.class);
          if (report != null) {
            report.setUid(childSnapshot.getKey());
            data.add(report);
          }
        }
        reports.postValue(data);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        errorMessage.postValue("Failed to load reports");
      }
    };
  }

  private Report.Summary buildSummary(List<MedicineLog> medicineLogs,
      List<PEF> pefLogs,
      List<CheckIn> checkIns,
      List<Incident> incidents,
      long startDate,
      long endDate) {
    // Here, we are building the summary by aggregating the required values
    Report.Summary summary = new Report.Summary();

    // Computing the rescue counts
    int rescueCount = 0;
    long lastRescue = 0L;
    java.util.Map<Long, Integer> dailyRescues = new java.util.HashMap<>();
    if (medicineLogs != null) {
      for (MedicineLog log : medicineLogs) {
        if ("rescue".equalsIgnoreCase(log.getMedicineType())) {
          rescueCount++;
          lastRescue = Math.max(lastRescue, log.getTime());
          long day = truncateToDay(log.getTime());
          dailyRescues.put(day, dailyRescues.getOrDefault(day, 0) + 1);
        }
      }
    }
    summary.setRescueCount(rescueCount);
    summary.setLastRescueTime(lastRescue);

    // Computing the controller adherence logs
    int controllerTaken = 0;
    if (medicineLogs != null) {
      for (MedicineLog log : medicineLogs) {
        if ("controller".equalsIgnoreCase(log.getMedicineType())) {
          controllerTaken += log.getDose();
        }
      }
    }
    summary.setControllerTakenDoses(controllerTaken);
    summary.setControllerScheduledDoses(0);
    summary.setControllerAdherencePercent(summary.getControllerScheduledDoses() == 0 ? 0
        : (controllerTaken * 100.0 / summary.getControllerScheduledDoses()));

    java.util.Map<String, Integer> symptomMap = new java.util.HashMap<>();
    if (checkIns != null) {
      for (CheckIn checkIn : checkIns) {
        if (checkIn.getNight_walking() != null) {
          increment(symptomMap, "night_walking");
        }
        if (checkIn.getActivity_limits() != null && !checkIn.getActivity_limits().isEmpty()) {
          increment(symptomMap, "activity_limits");
        }
        if (checkIn.getCough() != null && !checkIn.getCough().isEmpty()) {
          increment(symptomMap, "cough");
        }
      }
    }
    summary.setSymptomBurden(symptomMap);

    // Computing the zone distribution
    java.util.Map<String, Integer> zoneMap = new java.util.HashMap<>();
    if (pefLogs != null) {
      for (PEF pef : pefLogs) {
        String zone = pef.getZone() != null ? pef.getZone() : "Unknown";
        zoneMap.put(zone, zoneMap.getOrDefault(zone, 0) + 1);
      }
    }
    summary.setZoneDistribution(zoneMap);

    java.util.List<Report.Summary.TimeSeriesPoint> timeSeries = new java.util.ArrayList<>();
    for (java.util.Map.Entry<Long, Integer> entry : dailyRescues.entrySet()) {
      Report.Summary.TimeSeriesPoint point = new Report.Summary.TimeSeriesPoint();
      point.setTimestamp(entry.getKey());
      point.setValue(entry.getValue());
      point.setLabel("Rescue");
      timeSeries.add(point);
    }
    summary.setTimeSeries(timeSeries);

    // Computing the triggers
    java.util.Map<String, Integer> triggerMap = new java.util.HashMap<>();
    if (checkIns != null) {
      for (CheckIn checkIn : checkIns) {
        CheckIn.NightWalking nw = checkIn.getNight_walking();
        if (nw != null && nw.getTriggers() != null) {
          CheckIn.Triggers triggers = nw.getTriggers();
          if (triggers.isExercise())
            increment(triggerMap, "exercise");
          if (triggers.isCold_air())
            increment(triggerMap, "cold_air");
          if (triggers.isDust())
            increment(triggerMap, "dust");
          if (triggers.isPets())
            increment(triggerMap, "pets");
          if (triggers.isSmoke())
            increment(triggerMap, "smoke");
          if (triggers.isIllness())
            increment(triggerMap, "illness");
          if (triggers.isPerfume_odors())
            increment(triggerMap, "perfume_odors");
        }
      }
    }
    java.util.List<Report.Summary.CategorySlice> categorical = new java.util.ArrayList<>();
    for (java.util.Map.Entry<String, Integer> entry : triggerMap.entrySet()) {
      Report.Summary.CategorySlice slice = new Report.Summary.CategorySlice();
      slice.setLabel(entry.getKey());
      slice.setValue(entry.getValue());
      categorical.add(slice);
    }
    summary.setCategorical(categorical);

    return summary;
  }

  private void increment(java.util.Map<String, Integer> map, String key) {
    map.put(key, map.getOrDefault(key, 0) + 1);
  }

  private long truncateToDay(long timeMillis) {
    return timeMillis - (timeMillis % (24L * 60L * 60L * 1000L));
  }
}
