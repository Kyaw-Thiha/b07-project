package com.example.b07project.model;

import java.util.ArrayList;

public class Incident {
  private long time;
  private Flags flags;
  private String guidance;
  private int pefNumber;
  private String uid;
  private String triageSessionId;
  private String decision;
  private int rescueAttempts;
  private boolean parentAlertSent;
  private Long parentAlertSentAt;

  public Incident() {
  }

  public Incident(long time, Flags flags, String guidance, int pefNumber, String uid) {
    this.time = time;
    this.flags = flags;
    this.guidance = guidance;
    this.pefNumber = pefNumber;
    this.uid = uid;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public Flags getFlags() {
    return flags;
  }

  public void setFlags(Flags flags) {
    this.flags = flags;
  }

  public String getGuidance() {
    return guidance;
  }

  public void setGuidance(String guidance) {
    this.guidance = guidance;
  }

  public int getPefNumber() {
    return pefNumber;
  }

  public void setPefNumber(int pefNumber) {
    this.pefNumber = pefNumber;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getTriageSessionId() {
    return triageSessionId;
  }

  public void setTriageSessionId(String triageSessionId) {
    this.triageSessionId = triageSessionId;
  }

  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }

  public int getRescueAttempts() {
    return rescueAttempts;
  }

  public void setRescueAttempts(int rescueAttempts) {
    this.rescueAttempts = rescueAttempts;
  }

  public boolean isParentAlertSent() {
    return parentAlertSent;
  }

  public void setParentAlertSent(boolean parentAlertSent) {
    this.parentAlertSent = parentAlertSent;
  }

  public Long getParentAlertSentAt() {
    return parentAlertSentAt;
  }

  public void setParentAlertSentAt(Long parentAlertSentAt) {
    this.parentAlertSentAt = parentAlertSentAt;
  }

  public static class Flags {
    private boolean cantSpeakFullSentences;
    private boolean chestPulling;
    private boolean blueLips;
    private boolean severeWheeze;
    private boolean unableToLieFlat;

    public Flags() {
    }

    public Flags(boolean cantSpeakFullSentences, boolean chestPulling, boolean blueLips,
        boolean severeWheeze, boolean unableToLieFlat) {
      this.cantSpeakFullSentences = cantSpeakFullSentences;
      this.chestPulling = chestPulling;
      this.blueLips = blueLips;
      this.severeWheeze = severeWheeze;
      this.unableToLieFlat = unableToLieFlat;
    }

    public boolean isCantSpeakFullSentences() {
      return cantSpeakFullSentences;
    }

    public void setCantSpeakFullSentences(boolean cantSpeakFullSentences) {
      this.cantSpeakFullSentences = cantSpeakFullSentences;
    }

    public boolean isChestPulling() {
      return chestPulling;
    }

    public void setChestPulling(boolean chestPulling) {
      this.chestPulling = chestPulling;
    }

    public boolean isBlueLips() {
      return blueLips;
    }

    public void setBlueLips(boolean blueLips) {
      this.blueLips = blueLips;
    }

    public boolean isSevereWheeze() {
      return severeWheeze;
    }

    public void setSevereWheeze(boolean severeWheeze) {
      this.severeWheeze = severeWheeze;
    }

    public boolean isUnableToLieFlat() {
      return unableToLieFlat;
    }

    public void setUnableToLieFlat(boolean unableToLieFlat) {
      this.unableToLieFlat = unableToLieFlat;
    }
  }
}
