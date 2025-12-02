# Models Reference

The app follows a straight-forward MVVM + Repository pattern. Everything under `app/src/main/java/com/example/b07project/model` is a POJO that mirrors a Firebase path, so these classes are simple data holders with getters/setters. Use the sections below to quickly remember what each model represents and how it is typically used.

## Users & Identity
- `User` / `BaseUser` / `UserType`: shared fields (`uid`, `name`, `email`, `roles`) for any authenticated account. `SessionManager` caches the active `User` instance so dashboards can grab a UID before Firebase Auth finishes restoring state.
- `ParentUser`: adds `Map<String, ChildUser> children`, `providers`, and `inventory` references so the parent dashboard can fan out queries.
- `ChildUser`: adds child-specific metadata such as `ageBelow9`, `dateOfBirth`, `parentId`, `personalBest`, and `zoneHistory` (used to show current zone on dashboards).
- `ProviderUser`: tracks `organization` plus the base fields above.
- `ProviderChild`: lightweight projection used by the provider dashboard list (child name, age, parent name, `todayZone`, and trend snippets such as `rescue7d`).

## Inventory & Plans
- `Medicine`: represents a single controller/rescue canister. Repositories populate `inventoryId`, `initialCanisterPuffs`, `lastUpdated`, and default the `type` to `rescue` if you omit it.

```java
Medicine controller = new Medicine();
controller.setName("Flovent HFA");
controller.setPurchase_date("2025-03-01");
controller.setExpiry_date("2026-03-01");
controller.setCanister_puffs(200);
controller.setInitialCanisterPuffs(200);
controller.setType("controller");
medicineViewModel.addInventory(parentUid, controller);
```

- `MedicineInventory`: legacy DTO with `Date` fields; new code should prefer `Medicine`.
- `ControllerPlan`: dosing schedule for a child. The repository assigns `planId`, fills `childId`, and stamps `createdAt/updatedAt`.

```java
ControllerPlan plan = new ControllerPlan();
plan.setChildId(childUid);
plan.setPlanName("Morning/evening controller");
plan.setMedicineId(controller.getInventoryId());
plan.setDosesPerDay(2);
plan.setTimesOfDay(Arrays.asList("07:00", "19:00"));
controllerPlanViewModel.addPlan(childUid, plan);
```

## Logging & Clinical Data
- `CheckIn`: records daily symptoms, triggers, author, and whether the entry was parent-entered.

```java
CheckIn.Triggers triggers = new CheckIn.Triggers(true, false, true, false, false, false, false);
CheckIn.NightWalking night = new CheckIn.NightWalking(false, triggers, "Slept through the night");
CheckIn checkIn = new CheckIn(System.currentTimeMillis(), night, "Gym OK", "No cough", childUid);
checkIn.setAuthorId(parentUid);
checkIn.setEnteredByParent(true);
checkInViewModel.addCheckIn(childUid, checkIn);
```

- `PEF`: peak-flow entry (`pre_med`, `post_med`, `personalBestAtEntry`, `zone`). Used by the child logging flow and the report builder.
- `MedicineLog`: dose size + before/after symptoms + references to the controller plan and medicine ids. `MedicineLogViewModel` also triggers `MotivationManager` to update streaks when a new log is saved.

```java
MedicineLog log = new MedicineLog(System.currentTimeMillis(), 2, "Tight", "Better", childUid);
log.setMedicineType("controller");
log.setMedicineId(controller.getInventoryId());
log.setControllerPlanId(plan.getPlanId());
medicineLogViewModel.addLog(childUid, log);
```

- `Incident`: rescue/triage record with `Incident.Flags` (cant speak, chest pulling, etc.), `pefNumber`, and links back to `TriageSession`.
- `TriageSession`: broader triage decision tree state (`startedAt`, `resolvedAt`, `decision`, `parentAlertSent`, etc.). `TriageSessionRepository` can return the generated `sessionId` so you can join it to an `Incident`.

```java
TriageSession session = new TriageSession();
session.setChildId(childUid);
session.setFlags(new Incident.Flags(true, false, false, true, false));
session.setRescueAttempts(1);
String sessionId = triageSessionViewModel.addSessionAndReturnId(childUid, session);

Incident incident = new Incident(System.currentTimeMillis(), session.getFlags(),
        "Parent submitted triage responses.", 260, childUid);
incident.setTriageSessionId(sessionId);
incident.setDecision(session.getDecision());
incidentViewModel.addIncident(childUid, incident);
```

## Motivation & Notifications
- `Motivation`: stores `streaks` (per log type) and `badges` (controller week, technique sessions, low rescue month). `MotivationViewModel` observes this branch and drives `ChildBadgeActivity`.
- `MotivationManager`: helper that recomputes streak counts whenever a controller log is added; invoked automatically by `MedicineLogViewModel`.
- `Notification`: push-style alert rows (title/text/`type`, `createdAt`, `deliveredAt`, `status`, optional metadata blob). Parents can mark rows as read or delete them.

```java
Notification notification = new Notification("Rapid rescue alert", "3 uses within 3 hours");
notification.setType("rapid_rescue");
notification.setChildId(childUid);
notificationViewModel.addNotification(childUid, notification);
```

## Sharing & Reports
- `ShareSettings`: parent-managed toggles that decide what goes into a provider report (`includeRescueLogs`, `includeMedicines`, `includeSummaryCharts`, etc.). `ShareSettingsViewModel` auto-creates a default row per (parent, child) pair.

```java
ShareSettings settings = new ShareSettings();
settings.setParentId(parentUid);
settings.setChildId(childUid);
settings.setIncludeIncidents(true);
settings.setIncludeSummaryCharts(true);
shareSettingsViewModel.observeSettings(parentUid, childUid);
```

- `Report`: provider-ready export that bundles metadata (`parentName`, `childName`, `providerName`), the selected raw datasets, and a computed `Summary` (rescue count, controller adherence percent, symptom burden, zone distribution, categorical slices, etc.).

```java
long end = System.currentTimeMillis();
long start = end - TimeUnit.DAYS.toMillis(30);
reportViewModel.createReport(parentUser, childUser, providerUser,
        medicines, medicineLogs, pefLogs, checkIns, incidents,
        start, end, shareSettings);
```

- `Invite`: parent-generated provider invite codes (`code`, `issuedAt`, `expiresAt`, `status`, `childIds`). `InviteViewModel` handles generate/revoke/redeem flows and keeps the `invites/<code>` index in sync.

```java
inviteViewModel.generateInvite(parentUid);
inviteViewModel.getCurrentInvite().observe(this, invite -> {
    if (invite != null && invite.getExpiresAt() > System.currentTimeMillis()) {
        clipboard.setPrimaryClip(ClipData.newPlainText(\"Invite Code\", invite.getCode()));
    }
});
```

These POJOs intentionally stay thin—business logic lives in the repositories and ViewModels, keeping serialization/deserialization simple and predictable.
