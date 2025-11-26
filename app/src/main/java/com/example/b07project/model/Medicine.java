package com.example.b07project.model;

import java.util.Date;

public class Medicine {
  private String name;
  private Date purchaseDate;
  private Date expiryDate;
  private int canisterPuffs;
  private String replacementRemainder;

  public Medicine() {
  }

  public Medicine(String name, Date purchaseDate, Date expiryDate, int canisterPuffs, String replacementRemainder) {
    this.name = name;
    this.purchaseDate = purchaseDate;
    this.expiryDate = expiryDate;
    this.canisterPuffs = canisterPuffs;
    this.replacementRemainder = replacementRemainder;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public int getCanisterPuffs() {
    return canisterPuffs;
  }

  public void setCanisterPuffs(int canisterPuffs) {
    this.canisterPuffs = canisterPuffs;
  }

  public int getReplacementRemainder() {
    return replacementRemainder;
  }

  public void setReplacementRemainder(int replacementRemainder) {
    this.replacementRemainder = replacementRemainder;
  }
}
