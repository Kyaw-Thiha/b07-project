package com.example.b07project.model;

import java.util.List;

public class ControllerPlan {
  private String planId;
  private String childId;
  private String medicineId;
  private String planName;
  private int dosesPerDay;
  private List<String> timesOfDay;
  private String startDate;
  private String endDate;
  private String notes;
  private boolean active = true;
  private long createdAt;
  private long updatedAt;

  public ControllerPlan() {
  }

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public String getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(String medicineId) {
    this.medicineId = medicineId;
  }

  public String getPlanName() {
    return planName;
  }

  public void setPlanName(String planName) {
    this.planName = planName;
  }

  public int getDosesPerDay() {
    return dosesPerDay;
  }

  public void setDosesPerDay(int dosesPerDay) {
    this.dosesPerDay = dosesPerDay;
  }

  public List<String> getTimesOfDay() {
    return timesOfDay;
  }

  public void setTimesOfDay(List<String> timesOfDay) {
    this.timesOfDay = timesOfDay;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(long updatedAt) {
    this.updatedAt = updatedAt;
  }
}
