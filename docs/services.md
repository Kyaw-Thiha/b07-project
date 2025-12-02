# Services (Repositories)

These wrap Firebase references. Call them indirectly via ViewModels.

## CheckInRepository
- `add(childId, checkIn)`
- `update(childId, checkInId, updates)`
- `delete(childId, checkInId)`
- `getAll(childId, listener)`

## MedicineRepository
- `add(parentId, medicine)` – auto-sets `inventoryId`, initial puffs, timestamps.
- `update(parentId, inventoryId, updates)`
- `delete(parentId, inventoryId)`
- `observeAll(parentId, listener)`

## MedicineLogRepository
- `add(childId, log)` – used by `MedicineLogViewModel`.
- `update/delete/get/getAll`

## ControllerPlanRepository
- `add(childId, plan)` – sets `planId`, timestamps.
- `update(childId, planId, updates)`
- `delete(...)`, `observeAll(...)`

## PEF / Incident / Notification repositories
Each follows the same add/update/delete + observe pattern exposed via corresponding ViewModels.

## TriageSessionRepository
- `add(childId, session)` – saves triage flow state.
- `update(childId, sessionId, updates)`
- `delete(...)`, `observeAll(...)`

## ReportRepository
- `createReport(report, completion)` – persists provider-ready summary + raw data.
- `observeReportsByParent/Child/Provider(id, listener)` – feeds dashboards per role.
