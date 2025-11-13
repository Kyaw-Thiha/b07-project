package com.b07proj.model;

public class CounterModel {
  private int count = 0;

  public int get() {
    return count;
  }

  public void increment() {
    count++;
  }

  public void reset() {
    count = 0;
  }
}
