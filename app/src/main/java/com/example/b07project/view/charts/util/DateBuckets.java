package com.example.b07project.view.charts.util;

import java.util.concurrent.TimeUnit;

public final class DateBuckets {

  private static final long DAY_MILLIS = TimeUnit.DAYS.toMillis(1);

  private DateBuckets() {
  }

  public static long toDay(long timestamp) {
    return timestamp - (timestamp % DAY_MILLIS);
  }
}
