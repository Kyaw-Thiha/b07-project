package com.example.b07project.model.User;

import com.example.b07project.model.*;

import java.util.Map;

public class ChildUser extends User {
  private Boolean ageBelow9;
  private String dateOfBirth;
  private String parentNotes;
  private String parentId;
  private Integer personalBest;
  private String currentZone;
  private Map<String, ZoneHistoryEntry> zoneHistory;

  public ChildUser() {
  }

  public ChildUser(String uid, String name, String email, Map<String, Boolean> roles, Boolean ageBelow9,
      String dateOfBirth, String parentNotes, String parentId, Integer personalBest, String currentZone,
      Map<String, ZoneHistoryEntry> zoneHistory) {
    super(uid, name, email, roles);
    this.ageBelow9 = ageBelow9;
    this.dateOfBirth = dateOfBirth;
    this.parentNotes = parentNotes;
    this.parentId = parentId;
    this.personalBest = personalBest;
    this.currentZone = currentZone;
    this.zoneHistory = zoneHistory;
  }

  public Boolean getIsAgeBelow9() {
    return ageBelow9;
  }

  public void setAgeBelow9(Boolean ageBelow9) {
    this.ageBelow9 = ageBelow9;
  }

  public String getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getParentNotes() {
    return this.parentNotes;
  }

  public void setParentNotes(String parentNotes) {
    this.parentNotes = parentNotes;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Integer getPersonalBest() {
    return personalBest;
  }

  public void setPersonalBest(Integer personalBest) {
    this.personalBest = personalBest;
  }

  public String getCurrentZone() {
    return currentZone;
  }

  public void setCurrentZone(String currentZone) {
    this.currentZone = currentZone;
  }

  public Map<String, ZoneHistoryEntry> getZoneHistory() {
    return zoneHistory;
  }

  public void setZoneHistory(Map<String, ZoneHistoryEntry> zoneHistory) {
    this.zoneHistory = zoneHistory;
  }

  public static class ZoneHistoryEntry {
    private String zone;
    private long computedAt;
    private String pefLogId;

    public ZoneHistoryEntry() {
    }

    public String getZone() {
      return zone;
    }

    public void setZone(String zone) {
      this.zone = zone;
    }

    public long getComputedAt() {
      return computedAt;
    }

    public void setComputedAt(long computedAt) {
      this.computedAt = computedAt;
    }

    public String getPefLogId() {
      return pefLogId;
    }

    public void setPefLogId(String pefLogId) {
      this.pefLogId = pefLogId;
    }
  }
}
