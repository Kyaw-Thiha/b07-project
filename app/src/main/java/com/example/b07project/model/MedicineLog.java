package com.example.b07project.model;

public class MedicineLog {
  private long time;
  private int dose;
  private String before;
  private String after;
  private String uid;

  public MedicineLog() {
  }

  public MedicineLog(long time, int dose, String before, String after, String uid) {
    this.time = time;
    this.dose = dose;
    this.before = before;
    this.after = after;
    this.uid = uid;
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
}
