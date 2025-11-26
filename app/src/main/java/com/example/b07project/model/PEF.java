package com.example.b07project.model;

import java.util.Date;

public class PEF {
  private float value;
  private float preMedicine;
  private float postMedicine;

  public PEF() {
  }

  public PEF(float value, float preMedicine, float postMedicine) {
    this.value = value;
    this.preMedicine = preMedicine;
    this.postMedicine = postMedicine;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public float getPreMedicine() {
    return preMedicine;
  }

  public void setPreMedicine(float preMedicine) {
    this.preMedicine = preMedicine;
  }

  public float getPostMedicine() {
    return postMedicine;
  }

  public void setPostMedicine(float postMedicine) {
    this.postMedicine = postMedicine;
  }
}
