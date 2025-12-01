package com.example.b07project.model;

public class MedicineLog {
  private long time;
  private int dose;
  private String before;
  private String after;
  private String uid;
  private String medicineId;
  private String controllerPlanId;
  private String medicineType;

  public MedicineLog() {
  }

  public MedicineLog(long time, int dose, String before, String after, String uid) {
    this(time, dose, before, after, uid, null, null, null);
  }

  public MedicineLog(long time, int dose, String before, String after, String uid, String medicineId,
      String medicineType, String controllerPlanId) {
    this.time = time;
    this.dose = dose;
    this.before = before;
    this.after = after;
    this.uid = uid;
    this.medicineId = medicineId;
    this.medicineType = medicineType;
    this.controllerPlanId = controllerPlanId;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getDose() {
    return dose;
  }

  public void setDose(int dose) {
    this.dose = dose;
  }

  public String getBefore() {
    return before;
  }

  public void setBefore(String before) {
    this.before = before;
  }

  public String getAfter() {
    return after;
  }

  public void setAfter(String after) {
    this.after = after;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(String medicineId) {
    this.medicineId = medicineId;
  }

  public String getControllerPlanId() {
    return controllerPlanId;
  }

  public void setControllerPlanId(String controllerPlanId) {
    this.controllerPlanId = controllerPlanId;
  }

  public String getMedicineType() {
    return medicineType;
  }

  public void setMedicineType(String medicineType) {
    this.medicineType = medicineType;
  }
}
