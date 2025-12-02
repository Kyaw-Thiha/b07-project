package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.Incident;
import com.example.b07project.model.TriageSession;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.IncidentViewModel;
import com.example.b07project.viewModel.TriageSessionViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LogChildRescueActivity extends BackButtonActivity {
  private CheckBox speak, noSpeak, chestPulls, noChestPulls, blueLips, noBlueLips, recentRescue, noRescue;
  private EditText pefInput;
  private String childId;
  private TriageSessionViewModel triageSessionViewModel;
  private IncidentViewModel incidentViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_log_child_rescue);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    childId = FirebaseAuth.getInstance().getCurrentUser() != null
            ? FirebaseAuth.getInstance().getCurrentUser().getUid()
            : null;
    if (childId == null) {
      Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    speak = findViewById(R.id.checkBox17);
    noSpeak = findViewById(R.id.checkBox18);
    chestPulls = findViewById(R.id.checkBox19);
    noChestPulls = findViewById(R.id.checkBox20);
    blueLips = findViewById(R.id.checkBox21);
    noBlueLips = findViewById(R.id.checkBox22);
    recentRescue = findViewById(R.id.checkBox23);
    noRescue = findViewById(R.id.checkBox24);
    pefInput = findViewById(R.id.inputPef);

    triageSessionViewModel = new ViewModelProvider(this).get(TriageSessionViewModel.class);
    incidentViewModel = new ViewModelProvider(this).get(IncidentViewModel.class);
  }

  public void rescueDecision(View view) {
    TriageSession session = new TriageSession();
    session.setChildId(childId);
    session.setStartedAt(System.currentTimeMillis());
    session.setFlags(null);
    session.setCantSpeak(noSpeak.isChecked());
    session.setChestPulling(chestPulls.isChecked());
    session.setBlueLips(blueLips.isChecked());
    session.setRecentRescue(recentRescue.isChecked());
    int pefNumber = parseIntSafe(pefInput.getText() != null ? pefInput.getText().toString().trim() : "");
    session.setPefNumber(pefNumber);
    session.setStatus("in_progress");
    String sessionId = triageSessionViewModel.addSessionAndReturnId(childId, session);

    Incident incident = new Incident();
    incident.setUid(childId);
    incident.setTime(System.currentTimeMillis());
    incident.setFlags(buildIncidentFlags());
    incident.setPefNumber(pefNumber);
    incident.setTriageSessionId(sessionId);
    incident.setDecision("pending");
    String incidentId = incidentViewModel.addIncidentAndReturnId(childId, incident);
    triageSessionViewModel.updateSession(childId, sessionId,
        java.util.Collections.singletonMap("incidentId", incidentId));

    Intent intent = new Intent(this, RescueDecisionActivity.class);
    intent.putExtra(RescueDecisionActivity.EXTRA_SESSION_ID, sessionId);
    intent.putExtra(RescueDecisionActivity.EXTRA_CHILD_ID, childId);
    intent.putExtra(RescueDecisionActivity.EXTRA_INCIDENT_ID, incidentId);
    startActivity(intent);
  }

  private Incident.Flags buildIncidentFlags() {
    Incident.Flags flags = new Incident.Flags();
    flags.setCantSpeakFullSentences(noSpeak.isChecked());
    flags.setChestPulling(chestPulls.isChecked());
    flags.setBlueLips(blueLips.isChecked());
    flags.setSevereWheeze(false);
    flags.setUnableToLieFlat(false);
    return flags;
  }

  private int parseIntSafe(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
