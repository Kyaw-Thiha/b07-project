package com.example.b07project.view.parent;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Incident;
import com.example.b07project.model.ShareSettings;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.IncidentViewModel;
import com.example.b07project.viewModel.ShareSettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;

public class IncidentLogActivity extends BackButtonActivity {

    public static final String EXTRA_CHILD_ID = "incident_child_id";

    private IncidentViewModel incidentViewModel;
    private ShareSettingsViewModel shareSettingsViewModel;

    private ToggleButton toggleFirstQuestion;
    private ToggleButton toggleSecondQuestion;
    private ToggleButton toggleThirdQuestion;
    private ToggleButton toggleUserResponse;
    private ToggleButton toggleShareProvider;
    private TextView guidanceText;

    private Incident currentIncident;
    private boolean suppressShareListener;
    private String childId;
    private String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_incident_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        childId = getIntent().getStringExtra(EXTRA_CHILD_ID);
        if (TextUtils.isEmpty(childId)) {
            childId = getSharedPreferences("APP_DATA", MODE_PRIVATE)
                    .getString("PARENT_SELECTED_CHILD", null);
        }
        parentId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid() : null;

        if (childId == null || parentId == null) {
            Toast.makeText(this, "Select a child to view incident logs.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupViewModels();
    }

    private void initViews() {
        toggleFirstQuestion = findViewById(R.id.toggle_firstQuestion);
        toggleSecondQuestion = findViewById(R.id.toggle_secondQuestion);
        toggleThirdQuestion = findViewById(R.id.toggle_thirdQuestion);
        toggleUserResponse = findViewById(R.id.toggle_userResponse);
        toggleShareProvider = findViewById(R.id.toggle_shareProvider);
        guidanceText = findViewById(R.id.guidence_shown);

        toggleFirstQuestion.setEnabled(false);
        toggleSecondQuestion.setEnabled(false);
        toggleThirdQuestion.setEnabled(false);
        toggleUserResponse.setEnabled(false);

        toggleShareProvider.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (suppressShareListener) {
                return;
            }
            shareSettingsViewModel.updateSettings(
                    Collections.singletonMap("includeIncidents", isChecked));
        });
    }

    private void setupViewModels() {
        incidentViewModel = new ViewModelProvider(this).get(IncidentViewModel.class);
        shareSettingsViewModel = new ViewModelProvider(this).get(ShareSettingsViewModel.class);

        incidentViewModel.getInventory().observe(this, this::bindLatestIncident);
        incidentViewModel.getIncidentError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
        incidentViewModel.loadIncidentByUser(childId);

        shareSettingsViewModel.getSettings().observe(this, this::bindShareSettings);
        shareSettingsViewModel.observeSettings(parentId, childId);
    }

    private void bindLatestIncident(List<Incident> incidents) {
        if (incidents == null || incidents.isEmpty()) {
            currentIncident = null;
            guidanceText.setText(R.string.incident_guidance_placeholder);
            toggleFirstQuestion.setChecked(false);
            toggleSecondQuestion.setChecked(false);
            toggleThirdQuestion.setChecked(false);
            toggleUserResponse.setChecked(false);
            Toast.makeText(this, "No incident logs yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        Incident latest = incidents.get(0);
        for (Incident candidate : incidents) {
            if (candidate != null && (latest == null || candidate.getTime() > latest.getTime())) {
                latest = candidate;
            }
        }
        currentIncident = latest;
        Incident.Flags flags = latest != null ? latest.getFlags() : null;
        toggleFirstQuestion.setChecked(flags != null && flags.isCantSpeakFullSentences());
        toggleSecondQuestion.setChecked(flags != null && flags.isChestPulling());
        toggleThirdQuestion.setChecked(flags != null && flags.isBlueLips());
        boolean call911 = latest != null && "CALL_911".equalsIgnoreCase(latest.getDecision());
        toggleUserResponse.setChecked(call911);
        guidanceText.setText(latest != null && latest.getGuidance() != null ? latest.getGuidance() : "");
    }

    private void bindShareSettings(ShareSettings settings) {
        if (settings == null) {
            return;
        }
        suppressShareListener = true;
        toggleShareProvider.setChecked(settings.isIncludeIncidents());
        suppressShareListener = false;
    }
}
