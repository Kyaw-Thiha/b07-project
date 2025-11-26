package com.example.b07project.model;

import java.util.ArrayList;

public class Incident {
  ArrayList<Boolean> flags;
  String guidanceText;
  String userResponse;
  PEF pef;

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
}
