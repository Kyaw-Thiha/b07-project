package com.example.b07project.model;

import java.util.Date;

public class CheckIn {
  String nightWalking;
  String activityLimits;
  int cough;
  String author; // TODO: Need to update
  String triggers; // TODO: Need to update

  public CheckIn(String nightWalking, String activityLimits, int cough) {
    this.nightWalking = nightWalking;
    this.activityLimits = activityLimits;
    this.cough = cough;
  }
}
