package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.model.CheckIn;
import com.example.b07project.model.ControllerPlan;
import com.example.b07project.model.Incident;
import com.example.b07project.model.Medicine;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.model.Notification;
import com.example.b07project.model.PEF;
import com.example.b07project.model.TriageSession;
import com.example.b07project.model.ShareSettings;
import com.example.b07project.model.User.BaseUser;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.model.User.ProviderUser;
import com.example.b07project.model.User.UserType;
import com.example.b07project.viewModel.CheckInViewModel;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.ControllerPlanViewModel;
import com.example.b07project.viewModel.IncidentViewModel;
import com.example.b07project.viewModel.MedicineLogViewModel;
import com.example.b07project.viewModel.MedicineViewModel;
import com.example.b07project.viewModel.NotificationViewModel;
import com.example.b07project.viewModel.PEFViewModel;
import com.example.b07project.viewModel.ParentProfileViewModel;
import com.example.b07project.viewModel.ProviderProfileViewModel;
import com.example.b07project.viewModel.ReportViewModel;
import com.example.b07project.viewModel.TriageSessionViewModel;
import com.example.b07project.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Simple Activity with a button that pushes one sample record for every
 * repository
 * using the existing ViewModels. Useful to verify Firebase connectivity
 * quickly.
 */
public class TestAPIActivity extends AppCompatActivity {

  private static final String TAG = "TestAPIActivity";
  private static final int TREND_DAYS = 30;

  private static final String PARENT_UID = "debug-parent-01";
  private static final String CHILD_UID = "debug-child-01";
  private static final String PROVIDER_UID = "debug-provider-01";

  private static final String PARENT_EMAIL = "debug-parent@seed-users.example.com";
  private static final String CHILD_EMAIL = "debug-child@seed-users.example.com";
  private static final String PROVIDER_EMAIL = "debug-provider@seed-users.example.com";

  private static final String PARENT_PASSWORD = "ParentPass123!";
  private static final String CHILD_PASSWORD = "ChildPass123!";
  private static final String PROVIDER_PASSWORD = "ProviderPass123!";

  private TextView textStatus;
  private Button buttonCreateSampleData;

  private CheckInViewModel checkInViewModel;
  private IncidentViewModel incidentViewModel;
  private MedicineViewModel medicineViewModel;
  private MedicineLogViewModel medicineLogViewModel;
  private NotificationViewModel notificationViewModel;
  private PEFViewModel pefViewModel;
  private ControllerPlanViewModel controllerPlanViewModel;
  private TriageSessionViewModel triageSessionViewModel;
  private ParentProfileViewModel parentProfileViewModel;
  private ProviderProfileViewModel providerProfileViewModel;
  private ChildProfileViewModel childProfileViewModel;
  private UserViewModel userViewModel;
  private ReportViewModel reportViewModel;
  private FirebaseAuth firebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_test_api);

    textStatus = findViewById(R.id.textStatus);
    buttonCreateSampleData = findViewById(R.id.buttonCreateSampleData);

    ViewModelProvider provider = new ViewModelProvider(this);
    checkInViewModel = provider.get(CheckInViewModel.class);
    incidentViewModel = provider.get(IncidentViewModel.class);
    medicineViewModel = provider.get(MedicineViewModel.class);
    medicineLogViewModel = provider.get(MedicineLogViewModel.class);
    notificationViewModel = provider.get(NotificationViewModel.class);
    controllerPlanViewModel = provider.get(ControllerPlanViewModel.class);
    triageSessionViewModel = provider.get(TriageSessionViewModel.class);
    pefViewModel = provider.get(PEFViewModel.class);
    parentProfileViewModel = provider.get(ParentProfileViewModel.class);
    providerProfileViewModel = provider.get(ProviderProfileViewModel.class);
    childProfileViewModel = provider.get(ChildProfileViewModel.class);
    userViewModel = provider.get(UserViewModel.class);
    reportViewModel = provider.get(ReportViewModel.class);
    firebaseAuth = FirebaseAuth.getInstance();

    buttonCreateSampleData.setOnClickListener(v -> createSampleData());
  }

  private void createSampleData() {
    String parentUid = PARENT_UID;
    String childUid = CHILD_UID;
    String providerUid = PROVIDER_UID;

    ensureAuthUser(PARENT_EMAIL, PARENT_PASSWORD, "Test Parent", UserType.PARENT);
    ensureAuthUser(CHILD_EMAIL, CHILD_PASSWORD, "Test Child", UserType.CHILD);
    ensureAuthUser(PROVIDER_EMAIL, PROVIDER_PASSWORD, "Test Provider", UserType.PROVIDER);

    buttonCreateSampleData.setEnabled(false);
    textStatus
        .setText("Creating data for parent " + parentUid + ", child " + childUid + ", provider " + providerUid + "...");

    long now = System.currentTimeMillis();

    try {
      HashMap<String, Boolean> parentRole = new HashMap<>();
      parentRole.put("parent", true);
      HashMap<String, Boolean> childRole = new HashMap<>();
      childRole.put("child", true);
      HashMap<String, Boolean> providerRole = new HashMap<>();
      providerRole.put("provider", true);

      BaseUser parentBase = new BaseUser(parentUid, "Test Parent", "parent+" + parentUid + "@example.com", parentRole);
      BaseUser childBase = new BaseUser(childUid, "Test Child", "child+" + childUid + "@example.com", childRole);
      BaseUser providerBase = new BaseUser(providerUid, "Test Provider", "provider+" + providerUid + "@example.com",
          providerRole);

      ParentUser parentUser = new ParentUser(parentUid, parentBase.getName(), parentBase.getEmail(), parentRole);
      int personalBest = 350;
      ChildUser childUser = new ChildUser(childUid, childBase.getName(), childBase.getEmail(), childRole, true,
          "2016-01-01", "Sample note", parentUid, personalBest, "Green", null);
      ProviderUser providerUser = new ProviderUser(providerUid, providerBase.getName(), providerBase.getEmail(),
          providerRole);

      userViewModel.createUser(parentUid, parentBase);
      userViewModel.createUser(childUid, childBase);
      userViewModel.createUser(providerUid, providerBase);

      parentProfileViewModel.createParent(parentUid, parentUser);
      childProfileViewModel.createChild(childUid, childUser);
      providerProfileViewModel.createProvider(providerUid, providerUser);


      // Build sample payloads
      CheckIn.Triggers triggers = new CheckIn.Triggers(true, true, false, true, false, false, false);
      CheckIn.NightWalking nightWalking = new CheckIn.NightWalking(false, triggers, "nothing");
      CheckIn checkIn = new CheckIn(now, nightWalking, null, null, childUid);
      checkIn.setAuthorId(parentUid);
      checkIn.setEnteredByParent(true);

      Medicine med = new Medicine("Penicillin", "2025-11-20", "2026-01-26", 200, "2026-01-26", parentUid);
      med.setType("controller");
      med.setInitialCanisterPuffs(200);
      med.setLastUpdated(now);
      medicineViewModel.addInventory(parentUid, med);

      ControllerPlan controllerPlan = new ControllerPlan();
      controllerPlan.setPlanName("Controller plan");
      controllerPlan.setChildId(childUid);
      controllerPlan.setMedicineId(med.getInventoryId());
      controllerPlan.setDosesPerDay(2);
      controllerPlan.setTimesOfDay(Arrays.asList("08:00", "20:00"));
      controllerPlan.setStartDate("2025-11-01");
      controllerPlan.setNotes("Sample plan");
      controllerPlan.setCreatedAt(now);
      controllerPlan.setUpdatedAt(now);
      controllerPlanViewModel.addPlan(childUid, controllerPlan);

      List<MedicineLog> medicineLogs = buildMedicineLogs(childUid, med.getInventoryId(),
          controllerPlan.getPlanId(), now);
      List<PEF> pefLogs = buildPefLogs(childUid, personalBest, now);
      List<CheckIn> checkIns = buildCheckIns(childUid, parentUid, now);
      List<Incident> incidents = buildIncidents(childUid, now);

      Notification notification = new Notification(
          "Firebase smoke test",
          "Generated at " + new Date(now).toString());
      notification.setType("triage_escalation");
      notification.setChildId(childUid);
      notification.setStatus("pending");

      TriageSession triageSession = new TriageSession();
      triageSession.setStartedAt(now);
      triageSession.setFlags(new Incident.Flags(true, true, false, false, false));
      triageSession.setRescueAttempts(1);
      triageSession.setPefNumber(260);
      triageSession.setDecision("HOME_STEPS");
      triageSession.setStatus("RESOLVED");
      triageSession.setParentAlertSent(true);
      triageSession.setParentAlertSentAt(now);
      triageSession.setGuidanceShown("Use rescue inhaler and start home steps.");
      triageSession.setUserResponse("Following guidance");

      // Fire the writes through each ViewModel
      for (CheckIn entry : checkIns) {
        checkInViewModel.addCheckIn(childUid, entry);
      }
      for (MedicineLog log : medicineLogs) {
        medicineLogViewModel.addLog(childUid, log);
      }
      for (PEF entry : pefLogs) {
        pefViewModel.addPEF(childUid, entry);
      }
      for (Incident incident : incidents) {
        incidentViewModel.addIncident(childUid, incident);
      }
      notificationViewModel.addNotification(childUid, notification);
      triageSessionViewModel.addSession(childUid, triageSession);
      ShareSettings shareSettings = new ShareSettings();
      shareSettings.setIncludeRescueLogs(true);
      shareSettings.setIncludeControllerSummary(true);
      shareSettings.setIncludeSymptoms(true);
      shareSettings.setIncludeTriggers(true);
      shareSettings.setIncludeSummaryCharts(true);
      shareSettings.setIncludePefLogs(true);
      shareSettings.setIncludeIncidents(true);

      long endDate = now;
      long startDate = now - TimeUnit.DAYS.toMillis(TREND_DAYS);

      reportViewModel.createReport(
          parentUser,
          childUser,
          providerUser,
          Collections.singletonList(med),
          medicineLogs,
          pefLogs,
          checkIns,
          incidents,
          startDate,
          endDate,
          shareSettings);

      Toast
          .makeText(this, "Created parent " + parentUid + ", child " + childUid + ", provider " + providerUid,
              Toast.LENGTH_LONG)
          .show();
      textStatus.setText("Last run at " + new Date(now) + "\nParent UID: " + parentUid + "\nChild UID: " + childUid
          + "\nProvider UID: " + providerUid);
    } catch (Exception ex) {
      Log.e(TAG, "Failed to create test data", ex);
      Toast.makeText(this, "Failed to create test data: " + ex.getMessage(), Toast.LENGTH_LONG)
          .show();
      textStatus.setText("Failed: " + ex.getMessage());
    } finally {
      buttonCreateSampleData.setEnabled(true);
    }
  }

  private List<MedicineLog> buildMedicineLogs(String childId, String medicineId, String planId, long now) {
    List<MedicineLog> logs = new ArrayList<>();
    for (int i = 0; i < TREND_DAYS; i++) {
      long day = now - TimeUnit.DAYS.toMillis(i);
      MedicineLog controller = new MedicineLog(day, 2, "same", "better", childId);
      controller.setMedicineId(medicineId);
      controller.setControllerPlanId(planId);
      controller.setMedicineType("controller");
      logs.add(controller);

      if (i % 4 == 0) {
        MedicineLog rescue = new MedicineLog(day + TimeUnit.HOURS.toMillis(6), 1, "worse", "better", childId);
        rescue.setMedicineType("rescue");
        logs.add(rescue);
      }
    }
    return logs;
  }

  private List<PEF> buildPefLogs(String childId, int personalBest, long now) {
    List<PEF> logs = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < TREND_DAYS; i++) {
      long day = now - TimeUnit.DAYS.toMillis(i);
      int offset = random.nextInt(80);
      PEF pef = new PEF(day, personalBest - offset - 20, personalBest - offset, childId);
      pef.setPersonalBestAtEntry(personalBest);
      pef.setZone(i % 10 == 0 ? "Yellow" : "Green");
      logs.add(pef);
    }
    return logs;
  }

  private List<CheckIn> buildCheckIns(String childId, String authorId, long now) {
    List<CheckIn> entries = new ArrayList<>();
    for (int i = 0; i < TREND_DAYS; i++) {
      long day = now - TimeUnit.DAYS.toMillis(i);
      CheckIn.Triggers triggers = new CheckIn.Triggers(i % 3 == 0, i % 5 == 0, i % 4 == 0,
          false, i % 7 == 0, i % 6 == 0, false);
      CheckIn.NightWalking nightWalking = new CheckIn.NightWalking(i % 2 == 0, triggers, "note " + i);
      CheckIn checkIn = new CheckIn(day, nightWalking, i % 2 == 0 ? "limited" : null,
          i % 3 == 0 ? "cough" : null, childId);
      checkIn.setAuthorId(authorId);
      checkIn.setEnteredByParent(true);
      entries.add(checkIn);
    }
    return entries;
  }

  private List<Incident> buildIncidents(String childId, long now) {
    List<Incident> incidents = new ArrayList<>();
    Incident.Flags severeFlags = new Incident.Flags(true, true, false, true, false);
    Incident incident1 = new Incident(now - TimeUnit.DAYS.toMillis(5), severeFlags,
        "Escalated guidance", 2, childId);
    incident1.setDecision("CALL_EMERGENCY");
    incidents.add(incident1);

    Incident.Flags moderateFlags = new Incident.Flags(false, false, false, true, false);
    Incident incident2 = new Incident(now - TimeUnit.DAYS.toMillis(12), moderateFlags,
        "Started home steps", 1, childId);
    incident2.setDecision("HOME_STEPS");
    incidents.add(incident2);
    return incidents;
  }

  private void ensureAuthUser(String email, String password, String displayName, UserType type) {
    firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
      if (!task.isSuccessful()) {
        Log.w(TAG, "Failed to check auth user " + email, task.getException());
        return;
      }
      boolean exists = task.getResult() != null
          && task.getResult().getSignInMethods() != null
          && !task.getResult().getSignInMethods().isEmpty();
      if (exists) {
        Log.d(TAG, "Auth user already exists for " + email);
        return;
      }

      firebaseAuth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener(createTask -> {
            if (!createTask.isSuccessful()) {
              Log.e(TAG, "Failed to create auth user " + email, createTask.getException());
              return;
            }
            FirebaseUser user = createTask.getResult().getUser();
            if (user != null) {
              user.updateProfile(new UserProfileChangeRequest.Builder()
                  .setDisplayName(displayName + " (" + type.name() + ")")
                  .build());
              Log.d(TAG, "Created auth user " + email);
            }
          });
    });
  }
}
