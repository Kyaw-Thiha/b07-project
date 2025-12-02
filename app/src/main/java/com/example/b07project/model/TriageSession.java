package com.example.b07project.model;

public class TriageSession {
  private String sessionId;
  private String childId;
  private long startedAt;
  private Long resolvedAt;
  private Incident.Flags flags;
  private int rescueAttempts;
  private Integer pefNumber;
  private String decision;
  private String status;
  private boolean parentAlertSent;
  private Long parentAlertSentAt;
  private String guidanceShown;
  private String userResponse;
  private String incidentId;

  public TriageSession() {
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public long getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(long startedAt) {
    this.startedAt = startedAt;
  }

  public Long getResolvedAt() {
    return resolvedAt;
  }

  public void setResolvedAt(Long resolvedAt) {
    this.resolvedAt = resolvedAt;
  }

  public Incident.Flags getFlags() {
    return flags;
  }

  public void setFlags(Incident.Flags flags) {
    this.flags = flags;
  }

  public int getRescueAttempts() {
    return rescueAttempts;
  }

  public void setRescueAttempts(int rescueAttempts) {
    this.rescueAttempts = rescueAttempts;
  }

  public Integer getPefNumber() {
    return pefNumber;
  }

  public void setPefNumber(Integer pefNumber) {
    this.pefNumber = pefNumber;
  }

  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean isParentAlertSent() {
    return parentAlertSent;
  }

  public void setParentAlertSent(boolean parentAlertSent) {
    this.parentAlertSent = parentAlertSent;
  }

  public Long getParentAlertSentAt() {
    return parentAlertSentAt;
  }

  public void setParentAlertSentAt(Long parentAlertSentAt) {
    this.parentAlertSentAt = parentAlertSentAt;
  }

  public String getGuidanceShown() {
    return guidanceShown;
  }

  public void setGuidanceShown(String guidanceShown) {
    this.guidanceShown = guidanceShown;
  }

  public String getUserResponse() {
    return userResponse;
  }

  public void setUserResponse(String userResponse) {
    this.userResponse = userResponse;
  }

  public String getIncidentId() {
    return incidentId;
  }

  public void setIncidentId(String incidentId) {
    this.incidentId = incidentId;
  }
}
