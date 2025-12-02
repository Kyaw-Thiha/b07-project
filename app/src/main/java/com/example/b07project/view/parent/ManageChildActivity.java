package com.example.b07project.view.parent;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.ShareSettings;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ShareSettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class ManageChildActivity extends BackButtonActivity {

    private ShareSettingsViewModel shareSettingsViewModel;
    private ToggleButton toggleRescue;
    private ToggleButton toggleController;
    private ToggleButton toggleSymptoms;
    private ToggleButton toggleTriggers;
    private ToggleButton togglePeakFlow;
    private ToggleButton toggleIncidents;
    private ToggleButton toggleSummary;
    private ShareSettings currentSettings;
    private boolean suppressToggleEvents;
    private String parentId;
    private String childId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_manage_child);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        parentId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
        childId = getSharedPreferences("APP_DATA", MODE_PRIVATE)
                .getString("PARENT_SELECTED_CHILD", null);

        if (parentId == null || childId == null) {
            Toast.makeText(this, "Select a child before managing share settings.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initToggles();
        setupViewModel();
    }

    private void initToggles() {
        toggleRescue = findViewById(R.id.toggle_rescue_logs);
        toggleController = findViewById(R.id.toggle_controller_adherence);
        toggleSymptoms = findViewById(R.id.toggle_symptoms);
        toggleTriggers = findViewById(R.id.toggle_triggers);
        togglePeakFlow = findViewById(R.id.toggle_peak_flow);
        toggleIncidents = findViewById(R.id.toggle_triage_incidents);
        toggleSummary = findViewById(R.id.toggle_summary_charts);

        toggleRescue.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includeRescueLogs", isChecked));
        toggleController.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includeControllerSummary", isChecked));
        toggleSymptoms.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includeSymptoms", isChecked));
        toggleTriggers.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includeTriggers", isChecked));
        togglePeakFlow.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includePefLogs", isChecked));
        toggleIncidents.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includeIncidents", isChecked));
        toggleSummary.setOnCheckedChangeListener((buttonView, isChecked) ->
                onToggleChanged("includeSummaryCharts", isChecked));
    }

    private void setupViewModel() {
        shareSettingsViewModel = new ViewModelProvider(this).get(ShareSettingsViewModel.class);
        shareSettingsViewModel.getSettings().observe(this, this::bindSettings);
        shareSettingsViewModel.observeSettings(parentId, childId);
    }

    private void bindSettings(ShareSettings settings) {
        currentSettings = settings;
        if (settings == null) {
            Toast.makeText(this, "Unable to load share settings.", Toast.LENGTH_SHORT).show();
            return;
        }
        suppressToggleEvents = true;
        toggleRescue.setChecked(settings.isIncludeRescueLogs());
        toggleController.setChecked(settings.isIncludeControllerSummary());
        toggleSymptoms.setChecked(settings.isIncludeSymptoms());
        toggleTriggers.setChecked(settings.isIncludeTriggers());
        togglePeakFlow.setChecked(settings.isIncludePefLogs());
        toggleIncidents.setChecked(settings.isIncludeIncidents());
        toggleSummary.setChecked(settings.isIncludeSummaryCharts());
        suppressToggleEvents = false;
    }

    private void onToggleChanged(String key, boolean value) {
        if (suppressToggleEvents || currentSettings == null) {
            return;
        }
        Map<String, Object> updates = new HashMap<>();
        updates.put(key, value);
        shareSettingsViewModel.updateSettings(updates);
    }
}
