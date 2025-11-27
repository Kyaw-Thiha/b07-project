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
  private ShareOptions shareOptions;
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

  public ShareOptions getShareOptions() {
    return shareOptions;
  }

  public void setShareOptions(ShareOptions shareOptions) {
    this.shareOptions = shareOptions;
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

  public static class ShareOptions {
    private boolean includeMedicines = true;
    private boolean includeMedicineLogs = true;
    private boolean includePefLogs = true;
    private boolean includeCheckIns = true;
    private boolean includeIncidents = true;

    public ShareOptions() {
    }

    public boolean isIncludeMedicines() {
      return includeMedicines;
    }

    public void setIncludeMedicines(boolean includeMedicines) {
      this.includeMedicines = includeMedicines;
    }

    public boolean isIncludeMedicineLogs() {
      return includeMedicineLogs;
    }

    public void setIncludeMedicineLogs(boolean includeMedicineLogs) {
      this.includeMedicineLogs = includeMedicineLogs;
    }

    public boolean isIncludePefLogs() {
      return includePefLogs;
    }

    public void setIncludePefLogs(boolean includePefLogs) {
      this.includePefLogs = includePefLogs;
    }

    public boolean isIncludeCheckIns() {
      return includeCheckIns;
    }

    public void setIncludeCheckIns(boolean includeCheckIns) {
      this.includeCheckIns = includeCheckIns;
    }

    public boolean isIncludeIncidents() {
      return includeIncidents;
    }

    public void setIncludeIncidents(boolean includeIncidents) {
      this.includeIncidents = includeIncidents;
    }
  }
}
