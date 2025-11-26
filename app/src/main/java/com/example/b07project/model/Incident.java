package com.example.b07project.model;

import java.util.ArrayList;

public class Incident {
  private String flags;
  private boolean guidanceShown;
  private String userResponse;
  private PEF pef;

  public Incident() {
  }

  public Incident(ArrayList<Boolean> flags, String guidanceText, String userResponse, PEF pef) {
    this.flags = flags;
    this.guidanceText = guidanceText;
    this.userResponse = userResponse;
    this.pef = pef;
  }

  public Incident(ArrayList<Boolean> flags, String guidanceText, String userResponse) {
    this.flags = flags;
    this.guidanceText = guidanceText;
    this.userResponse = userResponse;
  }

  public String getFlags() {
    return flags;
  }

  public void setFlags(String flags) {
    this.flags = flags;
  }

  public boolean isGuidanceShown() {
    return guidanceShown;
  }

  public void setGuidanceShown(boolean guidanceShown) {
    this.guidanceShown = guidanceShown;
  }

  public String getUserResponse() {
    return userResponse;
  }

  public void setUserResponse(String userResponse) {
    this.userResponse = userResponse;
  }

  public PEF getPef() {
    return pef;
  }

  public void setPef(PEF pef) {
    this.pef = pef;
  }
}
