package com.example.b07project.model;

public class PEF {
  private long time;
  private float pre_med;
  private float post_med;
  private String uid;
  private Integer personalBestAtEntry;
  private String zone;

  public PEF() {
  }

  public PEF(long time, float pre_med, float post_med, String uid) {
    this.time = time;
    this.pre_med = pre_med;
    this.post_med = post_med;
    this.uid = uid;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public float getPre_med() {
    return pre_med;
  }

  public void setPre_med(float pre_med) {
    this.pre_med = pre_med;
  }

  public float getPost_med() {
    return post_med;
  }

  public void setPost_med(float post_med) {
    this.post_med = post_med;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public Integer getPersonalBestAtEntry() {
    return personalBestAtEntry;
  }

  public void setPersonalBestAtEntry(Integer personalBestAtEntry) {
    this.personalBestAtEntry = personalBestAtEntry;
  }

  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }
}
