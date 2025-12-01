package com.example.b07project.view.charts.util;

import java.util.concurrent.TimeUnit;

public final class DateRange {
  private final long startMillis;
  private final long endMillis;

  private DateRange(long durationDays) {
    this.endMillis = System.currentTimeMillis();
    this.startMillis = endMillis - TimeUnit.DAYS.toMillis(durationDays);
  }

  private DateRange(long startMillis, long endMillis) {
    this.startMillis = startMillis;
    this.endMillis = endMillis;
  }

  public static DateRange sevenDays() {
    return new DateRange(7);
  }

  public static DateRange thirtyDays() {
    return new DateRange(30);
  }

  public static DateRange of(long startMillis, long endMillis) {
    return new DateRange(startMillis, endMillis);
  }

  public long getStartMillis() {
    return startMillis;
  }

  public long getEndMillis() {
    return endMillis;
  }

  public boolean contains(long timestamp) {
    return timestamp >= startMillis && timestamp <= endMillis;
  }
}
