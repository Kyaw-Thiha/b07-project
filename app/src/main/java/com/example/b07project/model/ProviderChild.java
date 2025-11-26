package com.example.b07project.model;

public class ProviderChild {

    private String id;            // childId under providersChildren/{providerId}
    private String dashboardID;

    private String name;
    private int age;
    private String parentName;
    private String todayZone;
    private int rescue7d;
    private int controllerAdherence;
    private String lastUpdated;

    public ProviderChild() {
        // Needed for Firebase
    }

    public ProviderChild(String id,
                         String dashboardID,
                         String name,
                         int age,
                         String parentName,
                         String todayZone,
                         int rescue7d,
                         int controllerAdherence,
                         String lastUpdated) {

        this.id = id;
        this.dashboardID = dashboardID;
        this.name = name;
        this.age = age;
        this.parentName = parentName;
        this.todayZone = todayZone;
        this.rescue7d = rescue7d;
        this.controllerAdherence = controllerAdherence;
        this.lastUpdated = lastUpdated;
    }

    // getters
    public String getId() { return id; }
    public String getDashboardID() { return dashboardID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getParentName() { return parentName; }
    public String getTodayZone() { return todayZone; }
    public int getRescue7d() { return rescue7d; }
    public int getControllerAdherence() { return controllerAdherence; }
    public String getLastUpdated() { return lastUpdated; }

    // setters — required for Firebase
    public void setId(String id) { this.id = id; }
    public void setDashboardID(String dashboardID) { this.dashboardID = dashboardID; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setParentName(String parentName) { this.parentName = parentName; }
    public void setTodayZone(String todayZone) { this.todayZone = todayZone; }
    public void setRescue7d(int rescue7d) { this.rescue7d = rescue7d; }
    public void setControllerAdherence(int val) { this.controllerAdherence = val; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}

