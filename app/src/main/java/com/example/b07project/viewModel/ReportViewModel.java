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

    public LiveData<Boolean> getReportCreated() {
        return reportCreated;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<Report>> getReports() {
        return reports;
    }

    public void createReport(ParentUser parent, ChildUser child, ProviderUser provider,
                             List<Medicine> medicines,
                             List<MedicineLog> medicineLogs,
                             List<PEF> pefLogs,
                             List<CheckIn> checkIns,
                             List<Incident> incidents,
                             ShareSettings shareSettings) {
        Report report = new Report();
        report.setParentId(parent != null ? parent.getUid() : null);
        report.setParentName(parent != null ? parent.getName() : null);
        report.setChildId(child != null ? child.getUid() : null);
        report.setChildName(child != null ? child.getName() : null);
        report.setProviderId(provider != null ? provider.getUid() : null);
        report.setProviderName(provider != null ? provider.getName() : null);
        report.setCreatedAt(System.currentTimeMillis());
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

    private ValueEventListener buildReportsListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Report> data = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Report report = childSnapshot.getValue(Report.class);
                    if (report != null) {
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
}
