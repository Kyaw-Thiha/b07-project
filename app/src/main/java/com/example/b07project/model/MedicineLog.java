package com.example.b07project.model;

import java.util.Date;

public class MedicineLog {
  long timestamp;
  int doseCount;
  float preCheck;
  float postCheck;
  String breathRating;

  public MedicineLog() {
  }

  public MedicineLog(long timestamp, int doseCount, float preCheck, float postCheck, String breathRating) {
    this.timestamp = timestamp;
    this.doseCount = doseCount;
    this.preCheck = preCheck;
    this.postCheck = postCheck;
    this.breathRating = breathRating;
  }
}
