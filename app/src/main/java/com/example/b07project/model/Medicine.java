package com.example.b07project.model;

public class Medicine {
  private String purchase_date;
  private String expiry_date;
  private int canister_puffs;
  private String replacement_reminder;
  private String uid;

  public Medicine() {
  }

  public Medicine(String name, Date purchase_date, Date expiry_date, int canister_puffs, String replacement_reminder,
      String uid) {
    this.name = name;
    this.purchase_date = purchase_date;
    this.expiry_date = expiry_date;
    this.canister_puffs = canister_puffs;
    this.replacement_reminder = replacement_reminder;
    this.uid = uid;
  }

  public String getPurchase_date() {
    return purchase_date;
  }

  public void setPurchase_date(String purchase_date) {
    this.purchase_date = purchase_date;
  }

  public String getExpiry_date() {
    return expiry_date;
  }

  public void setExpiry_date(String expiry_date) {
    this.expiry_date = expiry_date;
  }

  public String getCanister_puffs() {
    return expiry_date;
  }

  public void setCanister_puffs(String canister_puffs) {
    this.canister_puffs = canister_puffs;
  }

  public String getReplacement_reminder() {
    return replacement_reminder;
  }

  public void setReplacement_reminder(String replacement_reminder) {
    this.replacement_reminder = replacement_reminder;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }
}
