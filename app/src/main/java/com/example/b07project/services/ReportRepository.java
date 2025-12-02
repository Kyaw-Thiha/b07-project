package com.example.b07project.services;

import com.example.b07project.model.Report;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReportRepository {
  private final Service service;

  public ReportRepository(Service service) {
    this.service = service;
  }

  public void createReport(Report report, DatabaseReference.CompletionListener listener) {
    DatabaseReference reportRef = service.reportDatabase().push();
    String reportId = reportRef.getKey();
    report.setUid(reportId);

    reportRef.setValue(report, (error, ref) -> {
      if (error == null) {
        if (report.getParentId() != null) {
          service.parentReportsIndex(report.getParentId()).child(reportId).setValue(true);
        }
        if (report.getChildId() != null) {
          service.childReportsIndex(report.getChildId()).child(reportId).setValue(true);
        }
        if (report.getProviderId() != null) {
          service.providerReportsIndex(report.getProviderId()).child(reportId).setValue(true);
        }
      }
      if (listener != null) {
        listener.onComplete(error, ref);
      }
    });
  }

  public void getReport(String reportId, ValueEventListener listener) {
    DatabaseReference ref = service.reportDatabase().child(reportId);
    ref.addListenerForSingleValueEvent(listener);
  }

  public void observeReport(String reportId, ValueEventListener listener) {
    service.reportDatabase()
        .child(reportId)
        .addValueEventListener(listener);
  }

  public void observeReportsByParent(String parentId, ValueEventListener listener) {
    service.reportDatabase()
        .orderByChild("parentId")
        .equalTo(parentId)
        .addValueEventListener(listener);
  }

  public void observeReportsByChild(String childId, ValueEventListener listener) {
    service.reportDatabase()
        .orderByChild("childId")
        .equalTo(childId)
        .addValueEventListener(listener);
  }

  public void observeReportsByProvider(String providerId, ValueEventListener listener) {
    service.reportDatabase()
        .orderByChild("providerId")
        .equalTo(providerId)
        .addValueEventListener(listener);
  }

  public void deleteReport(String reportId, DatabaseReference.CompletionListener listener) {
    DatabaseReference ref = service.reportDatabase().child(reportId);
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        Report report = snapshot.getValue(Report.class);
        ref.removeValue((error, reference) -> {
          if (error == null && report != null) {
            if (report.getParentId() != null) {
              service.parentReportsIndex(report.getParentId()).child(reportId).removeValue();
            }
            if (report.getChildId() != null) {
              service.childReportsIndex(report.getChildId()).child(reportId).removeValue();
            }
            if (report.getProviderId() != null) {
              service.providerReportsIndex(report.getProviderId()).child(reportId).removeValue();
            }
          }
          if (listener != null) {
            listener.onComplete(error, reference);
          }
        });
      }

      @Override
      public void onCancelled(DatabaseError error) {
        if (listener != null) {
          listener.onComplete(error, ref);
        }
      }
    });
  }
}
