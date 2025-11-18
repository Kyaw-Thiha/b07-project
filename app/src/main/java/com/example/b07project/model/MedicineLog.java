package com.example.b07project;

import java.util.Date;

public class MedicineInventory {
  long timestamp;
  int doseCount;
  float preCheck;
  float postCheck;
  float breathRating;

  public MedicineInventory(long timestamp, int doseCount, float preCheck, float postCheck, float breathRating) {
    this.timestamp = timestamp;
    this.doseCount = doseCount;
    this.preCheck = preCheck;
    this.postCheck = postCheck;
    this.breathRating = breathRating;
  }
}
