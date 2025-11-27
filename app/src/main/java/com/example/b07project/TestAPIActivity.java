package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.model.CheckIn;
import com.example.b07project.model.Incident;
import com.example.b07project.model.Medicine;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.model.Notification;
import com.example.b07project.model.PEF;
import com.example.b07project.model.Report;
import com.example.b07project.model.User.BaseUser;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.model.User.ProviderUser;
import com.example.b07project.model.User.UserType;
import com.example.b07project.viewModel.CheckInViewModel;
import com.example.b07project.viewModel.IncidentViewModel;
import com.example.b07project.viewModel.MedicineViewModel;
import com.example.b07project.viewModel.MedicineLogViewModel;
import com.example.b07project.viewModel.NotificationViewModel;
import com.example.b07project.viewModel.PEFViewModel;
import com.example.b07project.viewModel.ParentProfileViewModel;
import com.example.b07project.viewModel.ProviderProfileViewModel;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.UserViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Simple Activity with a button that pushes one sample record for every
 * repository
 * using the existing ViewModels. Useful to verify Firebase connectivity
 * quickly.
 */
public class TestAPIActivity extends AppCompatActivity {

  private static final String TAG = "TestAPIActivity";

  private TextView textStatus;
  private Button buttonCreateSampleData;

  private CheckInViewModel checkInViewModel;
  private IncidentViewModel incidentViewModel;
  private MedicineViewModel medicineViewModel;
  private MedicineLogViewModel medicineLogViewModel;
  private NotificationViewModel notificationViewModel;
  private PEFViewModel pefViewModel;
  private ParentProfileViewModel parentProfileViewModel;
  private ProviderProfileViewModel providerProfileViewModel;
  private ChildProfileViewModel childProfileViewModel;
  private UserViewModel userViewModel;

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
    pefViewModel = provider.get(PEFViewModel.class);
    parentProfileViewModel = provider.get(ParentProfileViewModel.class);
    providerProfileViewModel = provider.get(ProviderProfileViewModel.class);
    childProfileViewModel = provider.get(ChildProfileViewModel.class);
    userViewModel = provider.get(UserViewModel.class);

    buttonCreateSampleData.setOnClickListener(v -> createSampleData());
  }

  private void createSampleData() {
    String parentUid = generateFakeUid("parent");
    String childUid = generateFakeUid("child");
    String providerUid = generateFakeUid("provider");

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
      ChildUser childUser = new ChildUser(childUid, childBase.getName(), childBase.getEmail(), childRole, true,
          parentUid);
      ProviderUser providerUser = new ProviderUser(providerUid, providerBase.getName(), providerBase.getEmail(),
          providerRole);

      userViewModel.createUser(parentUid, parentBase);
      userViewModel.createUser(childUid, childBase);
      userViewModel.createUser(providerUid, providerBase);

      parentProfileViewModel.createParent(parentUid, parentUser);
      childProfileViewModel.createChild(childUid, childUser);
      providerProfileViewModel.createProvider(providerUid, providerUser);

      // Build sample payloads
      CheckIn.Triggers triggers = new CheckIn.Triggers(true, true, false, true, false, false);
      CheckIn.NightWalking nightWalking = new CheckIn.NightWalking(false, triggers, "nothing");
      CheckIn checkIn = new CheckIn(now, nightWalking, null, null, childUid);

      Medicine med = new Medicine("Penicillin", "2025-11-20", "2026-01-26", 200, "2026-01-26", parentUid);
      MedicineLog medicineLog = new MedicineLog(now, 2, "worse", "better", childUid);
      PEF pef = new PEF(now, 250, 300, childUid);

      Incident.Flags flags = new Incident.Flags(true, false, false, true, false);
      Incident incident = new Incident(now + 100000, flags, "guidance text", 3, childUid);
      Notification notification = new Notification(
          "Firebase smoke test",
          "Generated at " + new Date(now).toString());

      // Fire the writes through each ViewModel
      checkInViewModel.addCheckIn(childUid, checkIn);
      medicineViewModel.addInventory(parentUid, med);
      medicineLogViewModel.addLog(childUid, medicineLog);
      pefViewModel.addPEF(childUid, pef);
      incidentViewModel.addIncident(childUid, incident);
      notificationViewModel.addNotification(childUid, notification);

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

  private String generateFakeUid(String type) {
    return "test-" + type + "-" + UUID.randomUUID();
  }
}
