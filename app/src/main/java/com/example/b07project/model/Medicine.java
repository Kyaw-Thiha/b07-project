package com.example.b07project.model;

public class Medicine {
  private String inventoryId;
  private String name;
  private String purchase_date;
  private String expiry_date;
  private int canister_puffs;
  private int initialCanisterPuffs;
  private long lastUpdated;
  private String type;
  private String replacement_reminder;
  private String uid;

  public Medicine() {
  }

  public Medicine(String name, String purchase_date, String expiry_date, int canister_puffs,
      String replacement_reminder,
      String uid) {
    this(name, purchase_date, expiry_date, canister_puffs, replacement_reminder, uid, null, 0,
        System.currentTimeMillis());
  }

  public Medicine(String name, String purchase_date, String expiry_date, int canister_puffs,
      String replacement_reminder, String uid, String type, int initialCanisterPuffs, long lastUpdated) {
    this.name = name;
    this.purchase_date = purchase_date;
    this.expiry_date = expiry_date;
    this.canister_puffs = canister_puffs;
    this.replacement_reminder = replacement_reminder;
    this.uid = uid;
    this.type = type;
    this.initialCanisterPuffs = initialCanisterPuffs;
    this.lastUpdated = lastUpdated;
  }

  public String getInventoryId() {
    return inventoryId;
  }

  public void setInventoryId(String inventoryId) {
    this.inventoryId = inventoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public int getCanister_puffs() {
    return canister_puffs;
  }

  public void setCanister_puffs(int canister_puffs) {
    this.canister_puffs = canister_puffs;
  }

  public String getReplacement_reminder() {
    return replacement_reminder;
  }

  public void setReplacement_reminder(String replacement_reminder) {
    this.replacement_reminder = replacement_reminder;
  }

  public int getInitialCanisterPuffs() {
    return initialCanisterPuffs;
  }

  public void setInitialCanisterPuffs(int initialCanisterPuffs) {
    this.initialCanisterPuffs = initialCanisterPuffs;
  }

  public long getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(long lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }
}
