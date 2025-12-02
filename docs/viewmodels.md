# ViewModels Usage

## CheckInViewModel (R5)
```java
checkInViewModel.getCheckIn().observe(this, list -> adapter.submitList(list));
checkInViewModel.loadCheckInByUser(childId);
checkInViewModel.addCheckIn(childId, checkIn);
checkInViewModel.updateCheckIn(childId, checkInId, updates);
checkInViewModel.deleteCheckIn(childId, checkInId);
```

## MedicineViewModel (R3 inventory)
```java
medicineViewModel.getInventory().observe(this, inventory -> showInventory(inventory));
medicineViewModel.loadInventoryByUser(parentId);
medicineViewModel.addInventory(parentId, medicine);
medicineViewModel.updateInventory(parentId, inventoryId, updates);
medicineViewModel.deleteInventory(parentId, inventoryId);
```

## MedicineLogViewModel (R3 logs + streaks)
```java
medicineLogViewModel.getLog().observe(this, logs -> showLogs(logs));
medicineLogViewModel.loadLogByUser(childId);
medicineLogViewModel.addLog(childId, log);
medicineLogViewModel.updateInventory(childId, logId, updates);
medicineLogViewModel.deleteInventory(childId, logId);
```

## ControllerPlanViewModel (R3 dosing plan)
```java
controllerPlanViewModel.getPlans().observe(this, plans -> showPlans(plans));
controllerPlanViewModel.loadPlans(childId);
controllerPlanViewModel.addPlan(childId, plan);
controllerPlanViewModel.updatePlan(childId, planId, updates);
controllerPlanViewModel.deletePlan(childId, planId);
```

## PEFViewModel (R4 peak-flow)
```java
pefViewModel.addPEF(childId, pef);
pefViewModel.loadPEFByUser(childId);
```

## IncidentViewModel & TriageSessionViewModel (R4 safety)
```java
triageSessionViewModel.getSessions().observe(this, sessions -> ...);
triageSessionViewModel.addSession(childId, session);
triageSessionViewModel.updateSession(childId, sessionId, updates);
triageSessionViewModel.deleteSession(childId, sessionId);
incidentViewModel.addIncident(childId, incident);
```

## NotificationViewModel (Alerts)
```java
notificationViewModel.getNotification().observe(this, list -> showAlerts(list));
notificationViewModel.loadNotificationByUser(childId);
notificationViewModel.addNotification(childId, notification);
```

## ReportViewModel (R6 provider reports)
```java
reportViewModel.getReports().observe(this, reports -> showReports(reports));
reportViewModel.loadReportsByParent(parentId);

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
    shareSettings
);
```
