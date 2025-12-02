package com.example.b07project.view.child;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.b07project.R;

import com.example.b07project.model.Motivation;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MotivationViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class ChildSettingsActivity extends BackButtonActivity {
    private TextInputEditText controllerInput;
    private TextInputEditText rescueInput;
    private TextInputEditText techniqueInput;

    private MotivationViewModel motivationViewModel;
    private String childId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_settings);
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

        controllerInput = findViewById(R.id.textInputEditText);
        rescueInput = findViewById(R.id.code_generator);
        techniqueInput = findViewById(R.id.editText2);

        motivationViewModel = new ViewModelProvider(this).get(MotivationViewModel.class);
        motivationViewModel.getMotivation().observe(this, this::populateThresholds);
        motivationViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
        motivationViewModel.loadMotivation(childId);
    }

    public void submitThreshold(View view){
        if (childId == null) {
            Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
            return;
        }

        Integer controller = parseValue(controllerInput);
        Integer rescue = parseValue(rescueInput);
        Integer technique = parseValue(techniqueInput);

        if (controller == null || rescue == null || technique == null) {
            Toast.makeText(this, R.string.child_settings_invalid_input, Toast.LENGTH_LONG).show();
            return;
        }

        updateBadgeTarget("controllerWeek", controller);
        updateBadgeTarget("lowRescueMonth", rescue);
        updateBadgeTarget("techniqueSessions", technique);

        Toast.makeText(this, R.string.child_settings_thresholds_updated, Toast.LENGTH_LONG).show();
    }

    private void populateThresholds(Motivation motivation) {
        if (motivation == null || motivation.getBadges() == null) {
            return;
        }
        setTextIfPresent(controllerInput, motivation.getBadges().get("controllerWeek"));
        setTextIfPresent(rescueInput, motivation.getBadges().get("lowRescueMonth"));
        setTextIfPresent(techniqueInput, motivation.getBadges().get("techniqueSessions"));
    }

    private void setTextIfPresent(TextInputEditText input, Motivation.Badge badge) {
        if (input == null || badge == null) {
            return;
        }
        if (badge.getTarget() > 0) {
            input.setText(String.valueOf(badge.getTarget()));
        }
    }

    private Integer parseValue(TextInputEditText input) {
        if (input == null) {
            return null;
        }
        CharSequence text = input.getText();
        if (TextUtils.isEmpty(text)) {
            input.setError(getString(R.string.child_settings_required));
            return null;
        }
        try {
            int value = Integer.parseInt(text.toString().trim());
            if (value <= 0) {
                input.setError(getString(R.string.child_settings_positive_number));
                return null;
            }
            input.setError(null);
            return value;
        } catch (NumberFormatException e) {
            input.setError(getString(R.string.child_settings_positive_number));
            return null;
        }
    }

    private void updateBadgeTarget(String badgeKey, int target) {
        if (motivationViewModel == null || childId == null) {
            return;
        }
        Map<String, Object> updates = new HashMap<>();
        updates.put("target", target);
        motivationViewModel.updateBadge(childId, badgeKey, updates);
    }
}
