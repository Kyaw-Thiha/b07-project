package com.example.b07project.model;

public class ProviderChild {
    private String id;
    private String name;
    private int age;
    private String parentName;
    private String todayZone;       // "Green" / "Yellow" / "Red"
    private int rescue7d;
    private int controllerAdherence; // 30d percentage，like 90
    private String lastUpdated;     // for now use simple String，like "2025-11-15"

    public ProviderChild() {}

    public ProviderChild(String id,
                         String name,
                         int age,
                         String parentName,
                         String todayZone,
                         int rescue7d,
                         int controllerAdherence,
                         String lastUpdated) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.parentName = parentName;
        this.todayZone = todayZone;
        this.rescue7d = rescue7d;
        this.controllerAdherence = controllerAdherence;
        this.lastUpdated = lastUpdated;
    }
    // getters (help automatically create)
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getParentName() { return parentName; }
    public String getTodayZone() { return todayZone; }
    public int getRescue7d() { return rescue7d; }
    public int getControllerAdherence() { return controllerAdherence; }
    public String getLastUpdated() { return lastUpdated; }
    public void setId(String id){}
}
