package com.example.b07project.model;

import java.util.Date;

public class Medecine {
  String name;
  Date purchaseDate;
  Date expiryDate;
  int canisterPuffs;
  String replacementRemainder;

  public Medecine() {
  }

  public Medecine(String name, Date purchaseDate, Date expiryDate, int canisterPuffs, String replacementRemainder) {
    this.name = name;
    this.purchaseDate = purchaseDate;
    this.expiryDate = expiryDate;
    this.amountLeft = amountLeft;
    this.replacementRemainder = replacementRemainder;
  }
}
