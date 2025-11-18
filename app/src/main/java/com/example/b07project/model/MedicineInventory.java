package com.example.b07project;

import java.util.Date;

public class MedicineInventory {
  String name;
  Date purchaseDate;
  Date expiryDate;
  int amountLeft;

  public MedicineInventory(String name, Date purchaseDate, Date expiryDate, int amountLeft) {
    this.name = name;
    this.purchaseDate = purchaseDate;
    this.expiryDate = expiryDate;
    this.amountLeft = amountLeft;
  }
}
