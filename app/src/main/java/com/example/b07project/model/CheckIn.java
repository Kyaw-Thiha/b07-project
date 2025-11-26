package com.example.b07project.model;

import java.util.Date;

public class CheckIn {
  private String nightWalking;
  private String activityLimits;
  private int cough;
  private String author; // TODO: Need to update
  private String triggers; // TODO: Need to update

  public CheckIn() {
  }

  public CheckIn(String nightWalking, String activityLimits, int cough) {
    this.nightWalking = nightWalking;
    this.activityLimits = activityLimits;
    this.cough = cough;
  }

  public String getNightWalking() {
    return nightWalking;
  }

  public void setNightWalking(String nightWalking) {
    this.nightWalking = nightWalking;
  }

  public String getActivityLimits() {
    return activityLimits;
  }

  public void setActivityLimits(String activityLimits) {
    this.activityLimits = activityLimits;
  }

  public int getCough() {
    return cough;
  }

  public void setCough(int cough) {
    this.cough = cough;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTriggers() {
    return triggers;
  }

  public void setTriggers(String triggers) {
    this.triggers = triggers;
  }
}
