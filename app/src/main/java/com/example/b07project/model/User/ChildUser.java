package com.example.b07project.model.User;

import com.example.b07project.model.*;

import java.util.Map;

public class ChildUser extends User {
  private Boolean ageBelow9;
  private String dateOfBirth;
  private String parentNotes;
  private String parentId;

  public ChildUser() {
  }

  public ChildUser(String uid, String name, String email, Map<String, Boolean> roles, Boolean ageBelow9,
      String dateOfBirth, String parentNotes, String parentId) {
    super(uid, name, email, roles);
    this.ageBelow9 = ageBelow9;
    this.dateOfBirth = dateOfBirth;
    this.parentNotes = parentNotes;
    this.parentId = parentId;
  }

  public Boolean getIsAgeBelow9() {
    return ageBelow9;
  }

  public void setAgeBelow9(Boolean ageBelow9) {
    this.ageBelow9 = ageBelow9;
  }

  public String getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getParentNotes() {
    return this.parentNotes;
  }

  public void setParentNotes(String parentNotes) {
    this.parentNotes = parentNotes;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
}
