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
import com.example.b07project.viewModel.CheckInViewModel;
import com.example.b07project.viewModel.IncidentViewModel;
import com.example.b07project.viewModel.MedicineViewModel;
import com.example.b07project.viewModel.MedicineLogViewModel;
import com.example.b07project.viewModel.NotificationViewModel;
import com.example.b07project.viewModel.PEFViewModel;

import java.util.Date;
import java.util.UUID;

/**
 * Simple Activity with a button that pushes one sample record for every repository
 * using the existing ViewModels. Useful to verify Firebase connectivity quickly.
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

        buttonCreateSampleData.setOnClickListener(v -> createSampleData());
    }

    private void createSampleData() {
        String uid = generateFakeUid();

        buttonCreateSampleData.setEnabled(false);
        textStatus.setText("Creating data for " + uid + "...");

        long now = System.currentTimeMillis();

        try {
            // Build sample payloads
            CheckIn.Triggers triggers = new CheckIn.Triggers(true, true, false, true, false, false);
            CheckIn.NightWalking nightWalking = new CheckIn.NightWalking(false, triggers, "nothing");
            CheckIn checkIn = new CheckIn(now, nightWalking, null, null, uid);

            Medicine med = new Medicine("11/20/25", "01/26/26", 0.63, "stuffs", uid);
            MedicineLog medicineLog = new MedicineLog(now, 2, "worse", "better", uid);
            PEF pef = new PEF(now, 250, 300, uid);

            Incident.Flags flags = new Incident.Flags(true, false, false, true, false);
            Incident incident = new Incident(now + 100000, flags, "guidance text", 3, uid);
            Notification notification = new Notification(
                    "Firebase smoke test",
                    "Generated at " + new Date(now).toString()
            );

            // Fire the writes through each ViewModel
            checkInViewModel.addCheckIn(uid, checkIn);
            medicineViewModel.addInventory(uid, med);
            medicineLogViewModel.addLog(uid, medicineLog);
            pefViewModel.addPEF(uid, pef);
            incidentViewModel.addIncident(uid, incident);
            notificationViewModel.addNotification(uid, notification);

            Toast.makeText(this, "Creation requested for UID " + uid, Toast.LENGTH_LONG)
                    .show();
            textStatus.setText("Last run at " + new Date(now) + "\nUID: " + uid);
        } catch (Exception ex) {
            Log.e(TAG, "Failed to create test data", ex);
            Toast.makeText(this, "Failed to create test data: " + ex.getMessage(), Toast.LENGTH_LONG)
                    .show();
            textStatus.setText("Failed: " + ex.getMessage());
        } finally {
            buttonCreateSampleData.setEnabled(true);
        }
    }

    private String generateFakeUid() {
        return "test-" + UUID.randomUUID();
    }
}
