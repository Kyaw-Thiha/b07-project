package com.example.b07project.model;

public class Incident {
  String flags;
  boolean guidanceShown;
  String userResponse;
  PEF pef;

  public Incident(String flags, boolean guidanceShown, String userResponse, PEF pef) {
    this.flags = flags;
    this.guidanceShown = guidanceShown;
    this.userResponse = userResponse;
    this.pef = pef;
  }

  public Incident(String flags, boolean guidanceShown, String userResponse) {
    this.flags = flags;
    this.guidanceShown = guidanceShown;
    this.userResponse = userResponse;
  }
}
