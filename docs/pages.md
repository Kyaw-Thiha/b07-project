# Screens & Layout Map

This reference ties each Activity class to the layout XML that defines its UI. Paths are relative to `app/src/main`, so you can jump straight to the code that matters.

## Utility / Seed Data
| Activity | Layout XML | Notes |
|---|---|---|
| `TestAPIActivity` | `res/layout/activity_test_api.xml` | Seeds Firebase Auth/DB with deterministic demo users and log data so every QA session starts with the same fixtures. |

## Login & Entry Points
| Activity | Layout XML | Notes |
|---|---|---|
| `view/login/MainActivity` | `res/layout/activity_main.xml` | Launcher entry shell that picks the correct flow (onboarding vs dashboard) based on the cached role. |
| `view/login/AskLoginSignupActivity` | `res/layout/activity_ask_login_signup.xml` | First decision point that lets users choose “Log in” vs “Sign up”. |
| `view/login/AskUsertypeActivity` | `res/layout/ask_usertype_page.xml` | Captures whether the user is a parent, child, or provider before continuing. |
| `view/login/AskChildAgeActivity` | `res/layout/activity_ask_child_age.xml` | Parent helper that records whether the child is under 9, which alters later UX copy. |
| `view/login/LoginActivity` | `res/layout/activity_login.xml` | Email/password auth and `SessionManager` hydration. |
| `view/login/SignupActivity` | `res/layout/activity_signup.xml` | Creates the Firebase Auth account, then writes the initial profile via `UserViewModel`. |
| `view/login/ResetPasswordActivity` | `res/layout/activity_reset_password.xml` | Firebase password-reset email trigger. |

### Parent Onboarding
| Activity | Layout XML | Notes |
|---|---|---|
| `view/parent/ParentOnboardingPage1Activity` | `res/layout/activity_parent_onboarding_1.xml` | Intro slide for parents (extends `BackButtonActivity`). |
| `view/parent/ParentOnboardingPage2Activity` | `res/layout/activity_parent_onboarding_2.xml` | Highlights controller adherence goals. |
| `view/parent/ParentOnboardingPage3Activity` | `res/layout/activity_parent_onboarding_3.xml` | Educates parents about triggers/check-ins. |
| `view/parent/ParentOnboardingPage4Activity` | `res/layout/activity_parent_onboarding_4.xml` | Final CTA that routes back to `LoginActivity`. |

### Child Onboarding
| Activity | Layout XML | Notes |
|---|---|---|
| `view/child/ChildOnboardingPage1Activity` | `res/layout/activity_child_onboarding_page1.xml` | First child-facing slide. |
| `view/child/ChildOnboardingPage2Activity` | `res/layout/activity_child_onboarding_page1.xml` | Shares the page-1 layout while swapping copy in code. |
| `view/child/ChildOnboardingPage3Activity` | `res/layout/activity_child_onboarding_page3.xml` | Focuses on controller technique. |
| `view/child/ChildOnboardingPage4Activity` | `res/layout/activity_child_onboarding_page4.xml` | Wraps the flow and navigates back to login. |

## Parent Area
| Activity | Layout XML | Notes |
|---|---|---|
| `view/parent/ParentDashboardActivity` | `res/layout/activity_parent_dashboard.xml` | Main hub: child selector, rescue trend sparkline, notification badge, and entry points into logging/reporting. |
| `view/parent/InventoryListActivity` | `res/layout/activity_parent_inventory_list.xml` | Recycler list of medicines with “Add inventory” CTA that launches `InventoryActivity`. |
| `view/parent/InventoryActivity` | `res/layout/activity_parent_inventory.xml` | Full-screen form for creating/editing controller/rescue canisters. |
| `view/parent/AddActionPlan` | `res/layout/activity_parent_add_action_plan.xml` | Lets parents type green/yellow/red-zone instructions (pending persistence). |
| `view/parent/IncidentLogActivity` | `res/layout/activity_parent_incident_log.xml` | Shows logged incidents and links to the rescue form. |
| `view/parent/ChooseChildActivity` | `res/layout/activity_log_child_rescue.xml` | Parent version of the rescue form with an injected child spinner. |
| `view/parent/SelectChildActivity` | `res/layout/activity_select_child.xml` | Child picker launched via `ActivityResultLauncher`; feeds the dashboard and inventory flows. |
| `view/parent/ManageChildActivity` | `res/layout/activity_parent_manage_child.xml` | Share-settings toggles wired to `ShareSettingsViewModel`. |
| `view/parent/InviteProviderActivity` | `res/layout/activity_parent_invite_provider.xml` | Generates and revokes provider invite codes, copies them to the clipboard. |
| `view/parent/NotificationsActivity` | `res/layout/activity_parent_notifcations.xml` | Lists rule-based alerts with swipe actions (`NotificationsAdapter`). |

## Child Area
| Activity | Layout XML | Notes |
|---|---|---|
| `view/child/ChildDashboardActivity` | `res/layout/activity_child_dashboard.xml` | Child home view with streak summary and shortcuts into each logging flow. |
| `view/child/ChildSettingsActivity` | `res/layout/activity_child_settings.xml` | Profile tweaks plus logout for the child role. |
| `view/child/ChildBadgeActivity` | `res/layout/activity_child_badge.xml` | Shows earned badges from `MotivationViewModel`. |
| `view/child/ChildCheckinInputActivity` | `res/layout/activity_child_checkin_input.xml` | R5 symptom logging form (triggers, cough, notes). |
| `view/child/LogChildMedicineActivity` | `res/layout/activity_log_child_medicine.xml` | Rescue/controller logging UI that hits `MedicineLogViewModel`. |
| `view/child/LogChildRescueActivity` | `res/layout/activity_log_child_rescue.xml` | Triage-style rescue entry without the parent-only spinner. |
| `view/child/LogChildSymptomActivity` | `res/layout/activity_log_child_symptom.xml` | Simpler symptom logger used in younger-child mode. |
| `view/child/MedicineLogExampleActivity` | `res/layout/activity_medicine_log_example.xml` | Static teaching page shown in onboarding. |
| `view/child/ControllerMedicineInputActivity` | `res/layout/activity_controller_medicine_input.xml` | Captures controller plan details before handing off to parents/providers. |
| `view/child/PefEntryActivity` | `res/layout/activity_pef_entry.xml` | Peak-flow entry with zone calculation hooks. |
| `view/child/DoseCheckActivity` | `res/layout/activity_dose_check.xml` | Dose confirmation screen before launching the helpers. |
| `view/child/RescueDecisionActivity` | `res/layout/activity_rescue_decision.xml` | Decision-tree summary (stay home vs escalate). |
| `view/child/TechniqueHelperActivity` | `res/layout/activity_technique_helper.xml` | First technique walkthrough. |
| `view/child/SecondTechniqueHelperActivity` | `res/layout/activity_second_technique_helper.xml` | Continuation of the helper content. |
| `view/child/ThirdTechniqueHelperActivity` | `res/layout/activity_third_technique_helper.xml` | Final helper screen that loops back into badges/logging. |

## Provider Area
| Activity | Layout XML | Notes |
|---|---|---|
| `view/provider/ProviderDashboardActivity` | `res/layout/activity_provider_dashboard.xml` | Provider landing page listing `ProviderChild` summaries. |
| `view/provider/ProviderInstruction1Activity` | `res/layout/activity_provider_instrustion_1.xml` | First onboarding slide (file name preserves earlier typo). |
| `view/provider/ProviderInstruction2Activity` | `res/layout/activity_provider_instruction_2.xml` | Personalized onboarding copy using `ProviderProfileViewModel`. |
| `view/provider/ProviderInstruction3Activity` | `res/layout/activity_provider_instruction_3.xml` | Wraps onboarding and directs back to login. |
| `view/provider/ChildReportActivity` | `res/layout/child_report_page.xml` | Snapshot view for a single child's latest report. |
| `view/provider/ProviderReportActivity` | `res/layout/activity_provider_report.xml` (via ViewBinding) | Full report detail with embedded chart fragments (`FragmentContainerView` IDs `chart_rescue_usage`, `chart_controller_adherence`, `chart_zone_distribution`, `chart_pef_trend`). |

## Shared & Base Classes
- `view/common/BackButtonActivity`: abstract base that wires the toolbar back arrow plus Edge-to-Edge window insets. Most Activities extend it.
- `view/common/OnboardingActivity`: optional helper invoked by dashboards to show breathing animations + tooltips (`res/layout/view_tooltip.xml`).
- `SessionManager`: singleton cache for the current `User`, so dashboards can grab the UID before Firebase Auth finishes restoring state.
- `view/charts/fragments/*`: chart fragments are embedded inside other layouts; see `docs/charts.md` for usage.

When adding a new page, follow the package structure (`view/login`, `view/parent`, `view/child`, `view/provider`, etc.) and keep the `activity_<feature>.xml` naming convention so this index stays predictable.
