package com.example.b07project.model;

import java.util.Map;

public class Motivation {
  private Map<String, Streak> streaks;
  private Map<String, Badge> badges;

  public Motivation() {
  }

  public Map<String, Streak> getStreaks() {
    return streaks;
  }

  public void setStreaks(Map<String, Streak> streaks) {
    this.streaks = streaks;
  }

  public Map<String, Badge> getBadges() {
    return badges;
  }

  public void setBadges(Map<String, Badge> badges) {
    this.badges = badges;
  }

  public static class Streak {
    private long current;
    private long best;
    private long updatedAt;

    public Streak() {
    }

    public long getCurrent() {
      return current;
    }

    public void setCurrent(long current) {
      this.current = current;
    }

    public long getBest() {
      return best;
    }

    public void setBest(long best) {
      this.best = best;
    }

    public long getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
      this.updatedAt = updatedAt;
    }
  }

  public static class Badge {
    private boolean achieved;
    private long achievedAt;
    private int progress;
    private int target;

    public Badge() {
    }

    public boolean isAchieved() {
      return achieved;
    }

    public void setAchieved(boolean achieved) {
      this.achieved = achieved;
    }

    public long getAchievedAt() {
      return achievedAt;
    }

    public void setAchievedAt(long achievedAt) {
      this.achievedAt = achievedAt;
    }

    public int getProgress() {
      return progress;
    }

    public void setProgress(int progress) {
      this.progress = progress;
    }

    public int getTarget() {
      return target;
    }

    public void setTarget(int target) {
      this.target = target;
    }
  }
}
