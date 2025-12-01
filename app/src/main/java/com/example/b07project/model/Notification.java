package com.example.b07project.model;

import java.util.Date;

public class Notification {
  private String notificationId;
  private String title;
  private String text;
  private String type;
  private String childId;
  private long createdAt;
  private Long deliveredAt;
  private String status;
  private String metadata;

  public Notification() {
  }

  public Notification(String title, String text) {
    this.title = title;
    this.text = text;
    this.createdAt = System.currentTimeMillis();
  }

  public String getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(String notificationId) {
    this.notificationId = notificationId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public Long getDeliveredAt() {
    return deliveredAt;
  }

  public void setDeliveredAt(Long deliveredAt) {
    this.deliveredAt = deliveredAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }
}
