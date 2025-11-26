package com.example.b07project.model;

import java.util.Date;

public class MedicineLog {
  private long timestamp;
  private int doseCount;
  private float preCheck;
  private float postCheck;
  private float breathRating;

  public MedicineLog() {
  }

  public MedicineLog(long timestamp, int doseCount, float preCheck, float postCheck, String breathRating) {
    this.timestamp = timestamp;
    this.doseCount = doseCount;
    this.preCheck = preCheck;
    this.postCheck = postCheck;
    this.breathRating = breathRating;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getDoseCount() {
    return doseCount;
  }

  public void setDoseCount(int doseCount) {
    this.doseCount = doseCount;
  }

  public float getPreCheck() {
    return preCheck;
  }

  public void setPreCheck(float preCheck) {
    this.preCheck = preCheck;
  }

  public float getPostCheck() {
    return postCheck;
  }

  public void setPostCheck(float postCheck) {
    this.postCheck = postCheck;
  }

  public float getBreathRating() {
    return breathRating;
  }

  public void setBreathRating(float breathRating) {
    this.breathRating = breathRating;
  }
}
