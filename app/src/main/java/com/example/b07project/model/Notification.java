package com.example.b07project.model;

import java.util.Date;

public class Notification {
  private String title;
  private String text;
  // User sender;
  // User receiver;

  public Notification() {
  }

  public Notification(String title, String text) {
    this.title = title;
    this.text = text;
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
}
