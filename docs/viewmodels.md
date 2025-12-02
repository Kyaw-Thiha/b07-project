# ViewModels Usage

Every ViewModel in `app/src/main/java/com/example/b07project/viewModel` exposes a `LiveData` stream plus the handful of commands you need to mutate Firebase. Use the groupings below to find the right one for your feature.

## Profiles & Session
- `UserViewModel`: CRUD wrapper around the base `users/profiles` branch. Handy right after Firebase Auth returns a UID.

```java
userViewModel.getUser().observe(this, profile -> bindProfile(profile));
userViewModel.loadUser(uid);
userViewModel.updateUser(uid, Collections.singletonMap("name", "New Name"));
```

- `ParentProfileViewModel` / `ProviderProfileViewModel`: same API (`getParent()/getProvider()`, `load...`, `create...`, `update...`) scoped to each role.
- `ChildProfileViewModel`: exposes both a single child (`getChild()`, `loadChild(uid)`) and `getChildren()` which is driven by `queryByParent` or `observeByParent`.

```java
childProfileViewModel.getChildren().observe(this, children -> adapter.submitList(children));
childProfileViewModel.observeChildrenForParent(parentUid);
```

- `ShareSettingsViewModel`: `observeSettings(parentId, childId)` auto-creates a default row and publishes it through `getSettings()`. Use `updateSetting(key, boolean)` or `updateSettings(Map<String,Object>)` to toggle what gets included in provider reports.
- `InviteViewModel`: manages provider invites.
  - `observeInvite(parentId)` keeps `getCurrentInvite()` up to date.
  - `generateInvite(parentId)` issues a new 8-character code, revoking the previous one.
  - `revokeInvite(parentId)` flags the current invite as revoked and removes the global code index.
  - `lookupInviteByCode(code)` is used on the provider side to validate a code.

## Logging & Tracking
Each of these ViewModels owns a `MutableLiveData<List<...>>`, exposes it via `LiveData`, and mirrors the repository methods (load/add/update/delete). They all call `load...` again after every mutation to keep the UI fresh.

- `MedicineViewModel`: parent inventory rows. `getInventory()`, `loadInventoryByUser(parentUid)`, `addInventory`, `updateInventory`, `deleteInventory`.
- `MedicineLogViewModel`: child rescue/controller logs. Adds an implicit call to `MotivationManager` so streaks update automatically when `addLog` or `addLogAndReturnId` succeeds.
- `ControllerPlanViewModel`: controller plans per child. Includes `observePlans(childId)` if you want a continuous stream instead of one-offs.
- `CheckInViewModel`: R5 symptom logging. API mirrors `loadCheckInByUser`, `addCheckIn`, `updateCheckIn`, `deleteCheckIn`.
- `PEFViewModel`: peak-flow logging.
- `IncidentViewModel`: triage incident logging; provides `addIncidentAndReturnId` so you can immediately join the generated Firebase key to related data.
- `TriageSessionViewModel`: broader triage wizard state; same CRUD surface plus `observeSessions(childId)`.
- `NotificationViewModel`: parent-facing notification inbox. Use `updateNotification` to mark read and `deleteNotification` for swipe-to-dismiss.
- `MotivationViewModel`: observes `motivation/{childId}` in real time so the child badge screen updates without polling. Exposes helper methods for updating a specific streak/badge if you need manual overrides.

Example inventory usage:

```java
medicineViewModel.getInventory().observe(this, this::bindInventory);
medicineViewModel.getLogError().observe(this, this::showToastIfNeeded);
medicineViewModel.loadInventoryByUser(parentUid);
```

## Reports & Charts
- `ReportViewModel`: orchestrates the provider report lifecycle.
  - `getReports()` publishes a list that you can populate via `loadReportsByParent`, `loadReportsByChild`, or `loadReportsByProvider`.
  - `createReport(...)` takes parent/child/provider profiles plus the raw data collections and `ShareSettings`. It builds the `Report.Summary` server-side and writes the report plus index entries.
  - `getSelectedReport()` + `loadReportById(reportId)` is used by `ProviderReportActivity`.
  - `exportReport(reportId)` pushes a request onto `reportExports` and sets `getExportMessage()` so the UI can confirm it.

```java
reportViewModel.getSelectedReport().observe(this, this::bindReport);
reportViewModel.getErrorMessage().observe(this, this::showError);
reportViewModel.loadReportById(reportId);

reportViewModel.exportReport(reportId);
reportViewModel.getExportMessage().observe(this, message -> {
    if (!TextUtils.isEmpty(message)) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
        reportViewModel.clearExportMessage();
    }
});
```

- `TrendViewModel`: used exclusively by chart fragments. Supply a list of `TrendStrategy` definitions via `setStrategies(...)`, feed raw data with `updateInput(TrendInput)`, and call `setRange(strategyId, DateRange)` when the user toggles the 7-day/30-day filter. The exposed `LiveData<Map<String, List<TrendPoint>>>` drives `TrendChartAdapter`.

```java
RescueUsageTrendFragment fragment =
        (RescueUsageTrendFragment) getSupportFragmentManager().findFragmentById(R.id.chartRescue);
if (fragment != null) {
    TrendInput input = new TrendInput(medicineLogs, pefLogs, checkIns);
    fragment.setTrendInput(input);
}
```

Remember: the Activities/Fragments should never instantiate repositories directly. Inject the ViewModel, observe its LiveData, and call the public methods above—everything else (Firebase references, listeners, threading) stays hidden in the ViewModel layer.
