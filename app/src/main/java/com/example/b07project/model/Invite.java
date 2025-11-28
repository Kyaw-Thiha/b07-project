package com.example.b07project.model;

public class Invite {
  private String code;
  private long expiresAt;
  private boolean active;
  private String parentId;

  public Invite() {
  }

  public Invite(String code, long expiresAt, boolean active, String parentId) {
    this.code = code;
    this.expiresAt = expiresAt;
    this.active = active;
    this.parentId = parentId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public long getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(long expiresAt) {
    this.expiresAt = expiresAt;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
}
