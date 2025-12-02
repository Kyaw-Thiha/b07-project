package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.b07project.model.CheckIn;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.CheckInViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChildCheckinInputActivity extends BackButtonActivity {
    CheckBox nightY, nightN, hardAct, lowAct, noAct, cough, coughSome, coughNone, smoke, illness, pets, exercise, coldAir, dust, odor, noTrigger;
    private EditText dateInput;

    private CheckInViewModel checkInViewModel;
    private String childId;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_checkin_input);
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

        nightN=findViewById(R.id.checkBox);
        nightY=findViewById(R.id.checkBox2);
        hardAct=findViewById(R.id.checkBox4);
        lowAct=findViewById(R.id.checkBox3);
        noAct=findViewById(R.id.checkBox5);
        cough=findViewById(R.id.checkBox7);
        coughSome=findViewById(R.id.checkBox6);
        coughNone=findViewById(R.id.checkBox8);
        smoke=findViewById(R.id.checkBox10);
        illness=findViewById(R.id.checkBox12);
        pets=findViewById(R.id.checkBox14);
        exercise=findViewById(R.id.checkBox9);
        coldAir=findViewById(R.id.checkBox11);
        dust=findViewById(R.id.checkBox13);
        odor=findViewById(R.id.checkBox15);
        noTrigger=findViewById(R.id.checkBox16);
        dateInput = findViewById(R.id.dateInput);

        setupMutualExclusions();

        checkInViewModel = new ViewModelProvider(this).get(CheckInViewModel.class);
    }

    public void submit(View view){
        if (childId == null) {
            Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
            return;
        }

        Long timestamp = parseDateInput();
        if (timestamp == null) {
            dateInput.setError(getString(R.string.child_checkin_date_required));
            return;
        }
        dateInput.setError(null);

        String nightStatus = resolveNightStatus();
        String activityStatus = resolveActivityStatus();
        String coughStatus = resolveCoughStatus();
        boolean hasTriggersSelection = hasTriggerSelection();

        if (nightStatus == null && activityStatus == null && coughStatus == null && !hasTriggersSelection) {
            Toast.makeText(this, R.string.child_checkin_selection_required, Toast.LENGTH_LONG).show();
            return;
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setUid(childId);
        checkIn.setTime(timestamp);
        checkIn.setActivity_limits(activityStatus);
        checkIn.setCough(coughStatus);
        checkIn.setNight_walking(buildNightWalking(nightStatus));

        checkInViewModel.addCheckIn(childId, checkIn);
        Toast.makeText(this, R.string.child_checkin_success, Toast.LENGTH_LONG).show();
        clearSelections();

        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);

    }

    private void setupMutualExclusions() {
        setupPairExclusion(nightY, nightN);
        setupPairExclusion(nightN, nightY);

        setupTripleExclusion(hardAct, lowAct, noAct);
        setupTripleExclusion(lowAct, hardAct, noAct);
        setupTripleExclusion(noAct, hardAct, lowAct);

        setupTripleExclusion(cough, coughSome, coughNone);
        setupTripleExclusion(coughSome, cough, coughNone);
        setupTripleExclusion(coughNone, cough, coughSome);

        noTrigger.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                smoke.setChecked(false);
                illness.setChecked(false);
                pets.setChecked(false);
                exercise.setChecked(false);
                coldAir.setChecked(false);
                dust.setChecked(false);
                odor.setChecked(false);
            }
        });
    }

    private void setupPairExclusion(CheckBox primary, CheckBox secondary) {
        primary.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                secondary.setChecked(false);
            }
        });
    }

    private void setupTripleExclusion(CheckBox primary, CheckBox secondary, CheckBox tertiary) {
        primary.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                secondary.setChecked(false);
                tertiary.setChecked(false);
            }
        });
    }

    private Long parseDateInput() {
        if (dateInput == null) {
            return System.currentTimeMillis();
        }
        String value = dateInput.getText() != null ? dateInput.getText().toString().trim() : "";
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        try {
            Date parsed = dateFormat.parse(value);
            if (parsed != null) {
                return parsed.getTime();
            }
        } catch (ParseException e) {
            return null;
        }
        return null;
    }

    private String resolveNightStatus() {
        if (nightY.isChecked()) {
            return "issues";
        }
        if (nightN.isChecked()) {
            return "no_issues";
        }
        return null;
    }

    private String resolveActivityStatus() {
        if (hardAct.isChecked()) {
            return "hard";
        }
        if (lowAct.isChecked()) {
            return "light";
        }
        if (noAct.isChecked()) {
            return "none";
        }
        return null;
    }

    private String resolveCoughStatus() {
        if (cough.isChecked()) {
            return "heavy";
        }
        if (coughSome.isChecked()) {
            return "some";
        }
        if (coughNone.isChecked()) {
            return "none";
        }
        return null;
    }

    private CheckIn.NightWalking buildNightWalking(String nightStatus) {
        CheckIn.NightWalking nightWalking = new CheckIn.NightWalking();
        nightWalking.setEntry_by_parent(false);
        nightWalking.setNote(nightStatus);
        nightWalking.setTriggers(buildTriggers());
        return nightWalking;
    }

    private CheckIn.Triggers buildTriggers() {
        CheckIn.Triggers triggers = new CheckIn.Triggers();
        triggers.setSmoke(smoke.isChecked());
        triggers.setIllness(illness.isChecked());
        triggers.setPets(pets.isChecked());
        triggers.setExercise(exercise.isChecked());
        triggers.setCold_air(coldAir.isChecked());
        triggers.setDust(dust.isChecked());
        triggers.setPerfume_odors(odor.isChecked());
        return triggers;
    }

    private boolean hasTriggerSelection() {
        return smoke.isChecked() || illness.isChecked() || pets.isChecked() || exercise.isChecked()
                || coldAir.isChecked() || dust.isChecked() || odor.isChecked() || noTrigger.isChecked();
    }

    private void clearSelections() {
        nightN.setChecked(false);
        nightY.setChecked(false);
        hardAct.setChecked(false);
        lowAct.setChecked(false);
        noAct.setChecked(false);
        cough.setChecked(false);
        coughSome.setChecked(false);
        coughNone.setChecked(false);
        smoke.setChecked(false);
        illness.setChecked(false);
        pets.setChecked(false);
        exercise.setChecked(false);
        coldAir.setChecked(false);
        dust.setChecked(false);
        odor.setChecked(false);
        noTrigger.setChecked(false);
        if (dateInput != null) {
            dateInput.setText("");
        }
    }
}
