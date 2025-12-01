package com.example.b07project.view.charts;

/**
 * Represents a single data point for any chart type.
 * Line/bar charts rely on {@link #timestamp} and value fields, while pie charts
 * use {@link #label} for each slice.
 */
public final class TrendPoint {
  private final long timestamp;
  private final float primary;
  private final Float secondary;
  private final String label;

  public TrendPoint(long timestamp, float primary) {
    this(timestamp, primary, null, null);
  }

  public TrendPoint(long timestamp, float primary, Float secondary) {
    this(timestamp, primary, secondary, null);
  }

  public TrendPoint(long timestamp, float primary, Float secondary, String label) {
    this.timestamp = timestamp;
    this.primary = primary;
    this.secondary = secondary;
    this.label = label;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public float getPrimary() {
    return primary;
  }

  public Float getSecondary() {
    return secondary;
  }

  public String getLabel() {
    return label;
  }
}
