package com.example.b07project.model;

public class ShareSettings {
  private String parentId;
  private String childId;
  private String providerId;
  private boolean includeRescueLogs = true;
  private boolean includeControllerSummary = true;
  private boolean includeSymptoms = true;
  private boolean includeTriggers = true;
  private boolean includePefLogs = true;
  private boolean includeIncidents = true;
  private boolean includeSummaryCharts = true;
  private boolean includeMedicineLogs = true;
  private boolean includeMedicines = true;
  private boolean includeCheckIns = true;

  public ShareSettings() {
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public boolean isIncludeRescueLogs() {
    return includeRescueLogs;
  }

  public void setIncludeRescueLogs(boolean includeRescueLogs) {
    this.includeRescueLogs = includeRescueLogs;
  }

  public boolean isIncludeControllerSummary() {
    return includeControllerSummary;
  }

  public void setIncludeControllerSummary(boolean includeControllerSummary) {
    this.includeControllerSummary = includeControllerSummary;
  }

  public boolean isIncludeSymptoms() {
    return includeSymptoms;
  }

  public void setIncludeSymptoms(boolean includeSymptoms) {
    this.includeSymptoms = includeSymptoms;
  }

  public boolean isIncludeTriggers() {
    return includeTriggers;
  }

  public void setIncludeTriggers(boolean includeTriggers) {
    this.includeTriggers = includeTriggers;
  }

  public boolean isIncludePefLogs() {
    return includePefLogs;
  }

  public void setIncludePefLogs(boolean includePefLogs) {
    this.includePefLogs = includePefLogs;
  }

  public boolean isIncludeIncidents() {
    return includeIncidents;
  }

  public void setIncludeIncidents(boolean includeIncidents) {
    this.includeIncidents = includeIncidents;
  }

  public boolean isIncludeSummaryCharts() {
    return includeSummaryCharts;
  }

  public void setIncludeSummaryCharts(boolean includeSummaryCharts) {
    this.includeSummaryCharts = includeSummaryCharts;
  }

  public boolean isIncludeMedicineLogs() {
    return includeMedicineLogs;
  }

  public void setIncludeMedicineLogs(boolean includeMedicineLogs) {
    this.includeMedicineLogs = includeMedicineLogs;
  }

  public boolean isIncludeMedicines() {
    return includeMedicines;
  }

  public void setIncludeMedicines(boolean includeMedicines) {
    this.includeMedicines = includeMedicines;
  }

  public boolean isIncludeCheckIns() {
    return includeCheckIns;
  }

  public void setIncludeCheckIns(boolean includeCheckIns) {
    this.includeCheckIns = includeCheckIns;
  }
}
