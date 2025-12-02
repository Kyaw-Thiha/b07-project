# Services (Repositories)

All repositories live under `app/src/main/java/com/example/b07project/services` and wrap a `FirebaseDatabase` subtree. They never expose Firebase APIs directly; Activities and Fragments talk to ViewModels, ViewModels talk to these repositories.

## Core Service Helper
- `Service`: tiny wrapper around `FirebaseDatabase.getInstance()` with convenience accessors for every branch (`users/parents`, `users/children`, `reports`, `motivation/<childId>`, `reportExports`, invite indexes, etc.). Every repository receives one instance via composition, keeping paths consistent.

## Profiles, Sharing, and Invites
- `UserRepository`: CRUD + observe for the base `users/profiles` branch (used when creating accounts right after Firebase Auth registration).
- `ParentProfileRepository`, `ChildProfileRepository`, `ProviderProfileRepository`: per-role profile stores. The child repository also exposes `queryByParent` / `observeByParent` so dashboards can react to child lists in real time.
- `ShareSettingsRepository`: scoped under `users/parents/{parentId}/shareSettings/{childId}`. `observe()` keeps a LiveData subscription alive, `save()` writes defaults, and `update()` patches individual toggles.
- `InviteRepository`: persists invites under both `users/parents/{parentId}/invite` and the global `invites/{code}` index. Handles code revocation (removes from both locations) and lookups by invite code.

## Logging & Tracking Repositories
Each of these follows the same shape: `add(...)`, `update(...)`, `delete(...)`, plus `getAll(...)` and `observeAll(...)`. Use the ViewModels to handle threading/UI updates.

- `MedicineRepository`: inventory rows per parent. The `add` method auto-assigns `inventoryId`, sets defaults, and stamps `lastUpdated`.
- `MedicineLogRepository`: controller/rescue logs per child. Provides `addAndReturnId` so triage flows can join logs to streaks.
- `ControllerPlanRepository`: dosing schedules per child. `add` stamps timestamps and childId; `update` injects the current `updatedAt` so `Report.Summary` can reason about recency.
- `CheckInRepository`: daily symptom logging per child.
- `PEFRepository`: peak-flow logs per child, used by reports and charts.
- `IncidentRepository`: triage/incidents per child. `addAndReturnId` returns the generated Firebase key so `TriageSession` can point back.
- `TriageSessionRepository`: stores the broader triage wizard state; also exposes `addAndReturnId`.
- `NotificationRepository`: alerts per child. `add` fills `notificationId`, defaults `status` to `pending`, and stamps `createdAt`.

## Motivation & Alerts
- `MotivationRepository`: wrapper around `motivation/{childId}`. Provides `get`/`observe` plus `updateStreak(childId, key, updates)` and `updateBadge(...)` so streaks/badges can be patched atomically.
- `NotificationRepository`: see above; doubles as the parent inbox data source.

## Profiles & Reports
- `ChildProfileRepository` / `ParentProfileRepository` / `ProviderProfileRepository`: covered above but worth noting they all support `observe(...)` for live dashboards.
- `ReportRepository`: writes provider reports under `reports/{firebaseKey}` and maintains per-role indexes (`users/parents/{parentId}/reports/{reportId}`, etc.). Also exposes:
  - `deleteReport(reportId, completion)` which cleans up every index.
  - `observeReportsByParent/Child/Provider(...)` for each dashboard view.
  - `exportReport(reportId, completion)` which pushes a work item into `reportExports`, letting Cloud Functions or other workers pick it up.

When you add a new flow, copy the existing pattern: keep Firebase access inside a repository, return results through a `ValueEventListener`, and let the ViewModel own the LiveData transformation. That keeps Activities free of network code and makes it easy to mock repositories in tests.
