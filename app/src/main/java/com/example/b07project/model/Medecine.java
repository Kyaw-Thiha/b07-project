package com.example.b07project.model;

import java.util.Date;

public class Medecine {
  private String name;
  private Date purchaseDate;
  private Date expiryDate;
  private int canisterPuffs;
  private String replacementRemainder;

  public Medecine() {
  }

  public Medecine(String name, Date purchaseDate, Date expiryDate, int canisterPuffs, String replacementRemainder) {
    this.name = name;
    this.purchaseDate = purchaseDate;
    this.expiryDate = expiryDate;
    this.amountLeft = amountLeft;
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

  public int getAmountLeft() {
    return amountLeft;
  }

  public void setAmountLeft(int amountLeft) {
    this.amountLeft = amountLeft;
  }
}
