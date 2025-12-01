package com.example.b07project.model;

import java.util.List;

public class Report {
  private String uid;
  private String parentId;
  private String parentName;
  private String childId;
  private String childName;
  private String providerId;
  private String providerName;
  private long createdAt;
  private long startDate;
  private long endDate;
  private ShareSettings shareSettings;
  private Summary summary;
  private List<Medicine> medicines;
  private List<MedicineLog> medicineLogs;
  private List<PEF> pefLogs;
  private List<CheckIn> checkIns;
  private List<Incident> incidents;

  public Report() {
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public String getChildName() {
    return childName;
  }

  public void setChildName(String childName) {
    this.childName = childName;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public String getProviderName() {
    return providerName;
  }

  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getStartDate() {
    return startDate;
  }

  public void setStartDate(long startDate) {
    this.startDate = startDate;
  }

  public long getEndDate() {
    return endDate;
  }

  public void setEndDate(long endDate) {
    this.endDate = endDate;
  }

  public ShareSettings getShareSettings() {
    return shareSettings;
  }

  public void setShareSettings(ShareSettings shareSettings) {
    this.shareSettings = shareSettings;
  }

  public Summary getSummary() {
    return summary;
  }

  public void setSummary(Summary summary) {
    this.summary = summary;
  }

  public List<Medicine> getMedicines() {
    return medicines;
  }

  public void setMedicines(List<Medicine> medicines) {
    this.medicines = medicines;
  }

  public List<MedicineLog> getMedicineLogs() {
    return medicineLogs;
  }

  public void setMedicineLogs(List<MedicineLog> medicineLogs) {
    this.medicineLogs = medicineLogs;
  }

  public List<PEF> getPefLogs() {
    return pefLogs;
  }

  public void setPefLogs(List<PEF> pefLogs) {
    this.pefLogs = pefLogs;
  }

  public List<CheckIn> getCheckIns() {
    return checkIns;
  }

  public void setCheckIns(List<CheckIn> checkIns) {
    this.checkIns = checkIns;
  }

  public List<Incident> getIncidents() {
    return incidents;
  }

  public void setIncidents(List<Incident> incidents) {
    this.incidents = incidents;
  }

  public static class Summary {
    private int rescueCount;
    private long lastRescueTime;
    private double controllerAdherencePercent;
    private int controllerScheduledDoses;
    private int controllerTakenDoses;
    private java.util.Map<String, Integer> symptomBurden;
    private java.util.Map<String, Integer> zoneDistribution;
    private java.util.List<TimeSeriesPoint> timeSeries;
    private java.util.List<CategorySlice> categorical;

    public Summary() {
    }

    public int getRescueCount() {
      return rescueCount;
    }

    public void setRescueCount(int rescueCount) {
      this.rescueCount = rescueCount;
    }

    public long getLastRescueTime() {
      return lastRescueTime;
    }

    public void setLastRescueTime(long lastRescueTime) {
      this.lastRescueTime = lastRescueTime;
    }

    public double getControllerAdherencePercent() {
      return controllerAdherencePercent;
    }

    public void setControllerAdherencePercent(double controllerAdherencePercent) {
      this.controllerAdherencePercent = controllerAdherencePercent;
    }

    public int getControllerScheduledDoses() {
      return controllerScheduledDoses;
    }

    public void setControllerScheduledDoses(int controllerScheduledDoses) {
      this.controllerScheduledDoses = controllerScheduledDoses;
    }

    public int getControllerTakenDoses() {
      return controllerTakenDoses;
    }

    public void setControllerTakenDoses(int controllerTakenDoses) {
      this.controllerTakenDoses = controllerTakenDoses;
    }

    public java.util.Map<String, Integer> getSymptomBurden() {
      return symptomBurden;
    }

    public void setSymptomBurden(java.util.Map<String, Integer> symptomBurden) {
      this.symptomBurden = symptomBurden;
    }

    public java.util.Map<String, Integer> getZoneDistribution() {
      return zoneDistribution;
    }

    public void setZoneDistribution(java.util.Map<String, Integer> zoneDistribution) {
      this.zoneDistribution = zoneDistribution;
    }

    public java.util.List<TimeSeriesPoint> getTimeSeries() {
      return timeSeries;
    }

    public void setTimeSeries(java.util.List<TimeSeriesPoint> timeSeries) {
      this.timeSeries = timeSeries;
    }

    public java.util.List<CategorySlice> getCategorical() {
      return categorical;
    }

    public void setCategorical(java.util.List<CategorySlice> categorical) {
      this.categorical = categorical;
    }

    public static class TimeSeriesPoint {
      private long timestamp;
      private int value;
      private String label;

      public TimeSeriesPoint() {
      }

      public long getTimestamp() {
        return timestamp;
      }

      public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
      }

      public int getValue() {
        return value;
      }

      public void setValue(int value) {
        this.value = value;
      }

      public String getLabel() {
        return label;
      }

      public void setLabel(String label) {
        this.label = label;
      }
    }

    public static class CategorySlice {
      private String label;
      private int value;

      public CategorySlice() {
      }

      public String getLabel() {
        return label;
      }

      public void setLabel(String label) {
        this.label = label;
      }

      public int getValue() {
        return value;
      }

      public void setValue(int value) {
        this.value = value;
      }
    }
  }
}
