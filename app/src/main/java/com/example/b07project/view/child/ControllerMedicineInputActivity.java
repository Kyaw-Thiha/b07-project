package com.example.b07project.view.child;

import android.content.Intent;
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
import com.example.b07project.model.ControllerPlan;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ControllerPlanViewModel;
import com.example.b07project.viewModel.MedicineLogViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ControllerMedicineInputActivity extends BackButtonActivity {

    private TextInputEditText doseInput;
    private TextInputEditText timeInput;
    private MedicineLogViewModel medicineLogViewModel;
    private ControllerPlanViewModel controllerPlanViewModel;
    private String childId;
    private String medicineType;
    private String preFeeling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_controller_medicine_input);
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

        medicineType = getIntent().getStringExtra(DoseCheckActivity.EXTRA_MEDICINE_TYPE);
        if (medicineType == null) {
            medicineType = "controller";
        }
        preFeeling = getIntent().getStringExtra(DoseCheckActivity.EXTRA_FEELING);

        doseInput = findViewById(R.id.textInputDose);
        timeInput = findViewById(R.id.textInputTime);

        medicineLogViewModel = new ViewModelProvider(this).get(MedicineLogViewModel.class);
        controllerPlanViewModel = new ViewModelProvider(this).get(ControllerPlanViewModel.class);
        controllerPlanViewModel.loadPlans(childId);
    }

    public void submitDose(View view){
        String doseValue = doseInput.getText() != null ? doseInput.getText().toString().trim() : "";
        String timeValue = timeInput.getText() != null ? timeInput.getText().toString().trim() : "";
        if (TextUtils.isEmpty(doseValue) || TextUtils.isEmpty(timeValue)) {
            Toast.makeText(this, R.string.child_medicine_missing_fields, Toast.LENGTH_LONG).show();
            return;
        }

        long timestamp = System.currentTimeMillis();
        MedicineLog log = new MedicineLog();
        log.setUid(childId);
        log.setDose(parseIntSafe(doseValue));
        log.setTime(timestamp);
        log.setBefore(preFeeling);
        log.setMedicineType(medicineType);

        List<ControllerPlan> plans = controllerPlanViewModel.getPlans().getValue();
        if (plans != null && !plans.isEmpty()) {
            ControllerPlan plan = plans.get(0);
            log.setControllerPlanId(plan.getPlanId());
            log.setMedicineId(plan.getMedicineId());
        }

        String logId = medicineLogViewModel.addLogAndReturnId(childId, log);

        Intent intent = new Intent(this, DoseCheckActivity.class);
        intent.putExtra(DoseCheckActivity.EXTRA_PREVIOUS, "ControllerMedicineInputActivity");
        intent.putExtra(DoseCheckActivity.EXTRA_LOG_ID, logId);
        intent.putExtra(DoseCheckActivity.EXTRA_MEDICINE_TYPE, medicineType);
        intent.putExtra(DoseCheckActivity.EXTRA_FEELING, preFeeling);
        startActivity(intent);
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
