# Screens & Layout Map

Quick reference linking every Activity ("page") to its layout XML so you can jump straight to UI code.

## Utility / Test
- `app/src/main/java/com/example/b07project/TestAPIActivity.java` → `app/src/main/res/layout/activity_test_api.xml`

## Login & Onboarding
- `view/login/MainActivity.java` → `res/layout/activity_main.xml` (role routing shell)
- `view/login/LoginActivity.java` → `res/layout/activity_login.xml`
- `view/login/SignupActivity.java` → `res/layout/activity_signup.xml`
- `view/login/ResetPasswordActivity.java` → `res/layout/activity_reset_password.xml`
- `view/login/AskLoginSignupActivity.java` → `res/layout/activity_ask_login_signup.xml`
- `view/login/AskUsertypeActivity.java` → `res/layout/ask_usertype_page.xml`
- `view/login/AskChildAgeActivity.java` → `res/layout/activity_ask_child_age.xml`
- Parent onboarding flow (`ParentOnboardingPage1–4Activity`) → `res/layout/activity_parent_onboarding_[1-4].xml`
- Child onboarding flow (`ChildOnboardingPage1–4Activity`) → `res/layout/activity_child_onboarding_page[1,3,4].xml` (page 1 layout reused for page 2)

## Parent Area
- `ParentDashboardActivity` → `activity_parent_dashboard.xml`
- `ManageChildActivity` → `activity_parent_manage_child.xml`
- `InventoryActivity` → `activity_parent_inventory.xml`
- `IncidentLogActivity` → `activity_parent_incident_log.xml`
- `InviteProviderActivity` → `activity_parent_invite_provider.xml`
- `NotificationsActivity` → `activity_parent_notifications.xml`
- `ChooseChildActivity` → `activity_log_child_rescue.xml` (shares child rescue form)

## Child Area
- `ChildDashboardActivity` → `activity_child_dashboard.xml`
- `ChildSettingsActivity` → `activity_child_settings.xml`
- `ChildBadgeActivity` → `activity_child_badge.xml`
- `ChildCheckinInputActivity` → `activity_child_checkin_input.xml`
- `LogChildMedicineActivity` → `activity_log_child_medicine.xml`
- `LogChildRescueActivity` → `activity_log_child_rescue.xml`
- `LogChildSymptomActivity` → `activity_log_child_symptom.xml`
- `ControllerMedicineInputActivity` → `activity_controller_medicine_input.xml`
- `PefEntryActivity` → `activity_pef_entry.xml`
- `DoseCheckActivity` → `activity_dose_check.xml`
- `RescueDecisionActivity` → `activity_rescue_decision.xml`
- Technique helpers: `TechniqueHelperActivity`, `SecondTechniqueHelperActivity`, `ThirdTechniqueHelperActivity` → respective `activity_*_technique_helper.xml`
- `MedicineLogExampleActivity` → `activity_medicine_log_example.xml`

## Provider Area
- `ProviderDashboardActivity` → `activity_provider_dashboard.xml`
- `ProviderInstruction1/2/3Activity` → `activity_provider_instrustion_1.xml`
- `ProviderReportActivity` → `activity_provider_report.xml`
- `ChildReportActivity` → `child_report_page.xml`

## Shared / Base
- `BackButtonActivity` and `OnboardingActivity` are base classes; they provide navigation helpers but no layouts.

### Notes
- If you use ViewBinding, the binding class name maps directly (e.g., `ActivityParentDashboardBinding` ↔ `activity_parent_dashboard.xml`).
- Some screens reuse layouts (e.g., provider dashboard reuses the child dashboard shell). Check the `setContentView` call in each Activity if you’re unsure.
- When adding a new page, place the Activity under the relevant package (`view/child`, `view/parent`, etc.) and follow the naming convention `activity_<feature>.xml` to keep this map predictable.
