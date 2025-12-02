package com.example.b07project.model;

public class CheckIn {
  private long time;
  private NightWalking night_walking;
  private String activity_limits;
  private String cough;
  private String uid;
  private String authorId;
  private boolean enteredByParent;

  public CheckIn() {
  }

  public CheckIn(long time, NightWalking night_walking, String activity_limits,
                 String cough, String uid) {
    this.time = time;
    this.night_walking = night_walking;
    this.activity_limits = activity_limits;
    this.cough = cough;
    this.uid = uid;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public NightWalking getNight_walking() {
    return night_walking;
  }

  public void setNight_walking(NightWalking night_walking) {
    this.night_walking = night_walking;
  }

  public String getActivity_limits() {
    return activity_limits;
  }

  public void setActivity_limits(String activity_limits) {
    this.activity_limits = activity_limits;
  }

  public String getCough() {
    return cough;
  }

  public void setCough(String cough) {
    this.cough = cough;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public boolean isEnteredByParent() {
    return enteredByParent;
  }

  public void setEnteredByParent(boolean enteredByParent) {
    this.enteredByParent = enteredByParent;
  }

  public static class NightWalking {
    private boolean entry_by_parent;
    private Triggers triggers;
    private String note;

    public NightWalking() {
    }

    public NightWalking(boolean entry_by_parent, Triggers triggers, String note) {
      this.entry_by_parent = entry_by_parent;
      this.triggers = triggers;
      this.note = note;
    }

    public boolean isEntry_by_parent() {
      return entry_by_parent;
    }

    public void setEntry_by_parent(boolean entry_by_parent) {
      this.entry_by_parent = entry_by_parent;
    }

    public Triggers getTriggers() {
      return triggers;
    }

    public void setTriggers(Triggers triggers) {
      this.triggers = triggers;
    }

    public String getNote() {
      return note;
    }

    public void setNote(String note) {
      this.note = note;
    }
  }

  public static class Triggers {
    private boolean exercise;
    private boolean cold_air;
    private boolean dust;
    private boolean pets;
    private boolean smoke;
    private boolean illness;
    private boolean perfume_odors;

    public Triggers() {
    }

    public Triggers(boolean exercise, boolean cold_air, boolean dust,
                    boolean pets, boolean smoke, boolean illness, boolean perfume_odors) {
      this.exercise = exercise;
      this.cold_air = cold_air;
      this.dust = dust;
      this.pets = pets;
      this.smoke = smoke;
      this.illness = illness;
      this.perfume_odors = perfume_odors;
    }

    public boolean isExercise() {
      return exercise;
    }

    public void setExercise(boolean exercise) {
      this.exercise = exercise;
    }

    public boolean isCold_air() {
      return cold_air;
    }

    public void setCold_air(boolean cold_air) {
      this.cold_air = cold_air;
    }

    public boolean isDust() {
      return dust;
    }

    public void setDust(boolean dust) {
      this.dust = dust;
    }

    public boolean isPets() {
      return pets;
    }

    public void setPets(boolean pets) {
      this.pets = pets;
    }

    public boolean isSmoke() {
      return smoke;
    }

    public void setSmoke(boolean smoke) {
      this.smoke = smoke;
    }

    public boolean isIllness() {
      return illness;
    }

    public void setIllness(boolean illness) {
      this.illness = illness;
    }

    public boolean isPerfume_odors() {
      return perfume_odors;
    }

    public void setPerfume_odors(boolean perfume_odors) {
      this.perfume_odors = perfume_odors;
    }
  }
}
