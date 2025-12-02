package com.example.b07project.view.charts.util;

import java.util.Locale;

public final class ZoneUtils {
  private ZoneUtils() {
  }

  public static String normalize(String zoneRaw) {
    if (zoneRaw == null) {
      return null;
    }
    String normalized = zoneRaw.trim();
    if (normalized.isEmpty()) {
      return null;
    }
    String lower = normalized.toLowerCase(Locale.US);
    switch (lower) {
      case "red":
        return "Red";
      case "yellow":
        return "Yellow";
      case "green":
        return "Green";
      default:
        return normalized;
    }
  }

  public static Float toOrdinal(String zoneRaw) {
    if (zoneRaw == null) {
      return null;
    }
    String lower = zoneRaw.trim().toLowerCase(Locale.US);
    switch (lower) {
      case "red":
        return 1f;
      case "yellow":
        return 2f;
      case "green":
        return 3f;
      default:
        return null;
    }
  }
}
