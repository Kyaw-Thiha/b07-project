package com.example.b07project.model;

import java.util.Date;

public class MedicineInventory {
  String name;
  Date purchaseDate;
  Date expiryDate;
  int canisterPuffs;

  public MedicineInventory() {
  }

  public MedicineInventory(String name, Date purchaseDate, Date expiryDate, int canisterPuffs) {
    this.name = name;
    this.purchaseDate = purchaseDate;
    this.expiryDate = expiryDate;
    this.canisterPuffs = canisterPuffs;
  }
}
