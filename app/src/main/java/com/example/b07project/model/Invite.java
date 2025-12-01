package com.example.b07project.model;

import java.util.Map;

public class Invite {
  private String inviteId;
  private String code;
  private long issuedAt;
  private long expiresAt;
  private Long revokedAt;
  private Long redeemedAt;
  private String parentId;
  private String redeemedByProviderId;
  private Map<String, Boolean> childIds;
  private String status; // pending, redeemed, revoked

  public Invite() {
  }

  public Invite(String inviteId, String code, long issuedAt, long expiresAt, String parentId,
      Map<String, Boolean> childIds) {
    this.inviteId = inviteId;
    this.code = code;
    this.issuedAt = issuedAt;
    this.expiresAt = expiresAt;
    this.parentId = parentId;
    this.childIds = childIds;
    this.status = "pending";
  }

  public String getInviteId() {
    return inviteId;
  }

  public void setInviteId(String inviteId) {
    this.inviteId = inviteId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public long getIssuedAt() {
    return issuedAt;
  }

  public void setIssuedAt(long issuedAt) {
    this.issuedAt = issuedAt;
  }

  public long getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(long expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Long getRevokedAt() {
    return revokedAt;
  }

  public void setRevokedAt(Long revokedAt) {
    this.revokedAt = revokedAt;
  }

  public Long getRedeemedAt() {
    return redeemedAt;
  }

  public void setRedeemedAt(Long redeemedAt) {
    this.redeemedAt = redeemedAt;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getRedeemedByProviderId() {
    return redeemedByProviderId;
  }

  public void setRedeemedByProviderId(String redeemedByProviderId) {
    this.redeemedByProviderId = redeemedByProviderId;
  }

  public Map<String, Boolean> getChildIds() {
    return childIds;
  }

  public void setChildIds(Map<String, Boolean> childIds) {
    this.childIds = childIds;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
