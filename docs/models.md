# Models Reference

## CheckIn (R5 – symptom logging)
Use to record daily symptoms, including who entered the entry and relevant triggers.

```java
CheckIn.Triggers triggers = new CheckIn.Triggers(
    true,   // exercise
    false,  // cold_air
    true,   // dust
    false,  // pets
    false,  // smoke
    false,  // illness
    true    // perfume_odors
);
CheckIn.NightWalking night = new CheckIn.NightWalking(false, triggers, "Minor wake-up");
CheckIn checkIn = new CheckIn(System.currentTimeMillis(), night, "Skipped gym", "Light cough", childId);
checkIn.setAuthorId(parentId);
checkIn.setEnteredByParent(true);
checkInViewModel.addCheckIn(childId, checkIn);
```

## Medicine (R3 – parent inventory)
Represents a canister with type and remaining puffs.

```java
Medicine controller = new Medicine("Flovent", "2025-05-01", "2026-05-01", 200, "Replace next May", parentId);
controller.setType("controller");
controller.setInitialCanisterPuffs(200);
medicineViewModel.addInventory(parentId, controller);
```

## MedicineLog (R3 – logged doses)
```java
MedicineLog log = new MedicineLog(System.currentTimeMillis(), 2, "worse", "better", childId);
log.setMedicineId(controller.getInventoryId());
log.setControllerPlanId(plan.getPlanId());
log.setMedicineType("controller");
medicineLogViewModel.addLog(childId, log);
```

## ControllerPlan (R3 – dosing schedule)
```java
ControllerPlan plan = new ControllerPlan();
plan.setChildId(childId);
plan.setMedicineId(controller.getInventoryId());
plan.setPlanName("Controller morning/evening");
plan.setDosesPerDay(2);
plan.setTimesOfDay(Arrays.asList("07:00", "19:00"));
controllerPlanViewModel.addPlan(childId, plan);
```

## PEF (R4 – peak-flow entry)
```java
PEF pef = new PEF(System.currentTimeMillis(), 250, 300, childId);
pef.setPersonalBestAtEntry(child.getPersonalBest());
pef.setZone("Green");
pefViewModel.addPEF(childId, pef);
```

## Incident & TriageSession (R4 – safety log)
```java
TriageSession session = new TriageSession();
session.setChildId(childId);
session.setFlags(new Incident.Flags(true,false,false,true,false));
session.setRescueAttempts(1);
session.setDecision("HOME_STEPS");
triageSessionViewModel.addSession(childId, session);

Incident incident = new Incident(System.currentTimeMillis(), session.getFlags(), "Home steps shown", 280, childId);
incident.setTriageSessionId(session.getSessionId());
incident.setDecision(session.getDecision());
incidentViewModel.addIncident(childId, incident);
```

## Notification (R4 – alerts)
```java
Notification notification = new Notification("Rapid rescue alert", "3 uses within 3 hours");
notification.setType("rapid_rescue");
notification.setChildId(childId);
notificationViewModel.addNotification(childId, notification);
```

## Report (R6 – provider summary)
```java
ShareSettings settings = new ShareSettings();
settings.setIncludeRescueLogs(true);
long end = System.currentTimeMillis();
long start = end - TimeUnit.DAYS.toMillis(30);
reportViewModel.createReport(
    parentUser,
    childUser,
    providerUser,
    medicines,
    medicineLogs,
    pefLogs,
    checkIns,
    incidents,
    start,
    end,
    settings
);
```
