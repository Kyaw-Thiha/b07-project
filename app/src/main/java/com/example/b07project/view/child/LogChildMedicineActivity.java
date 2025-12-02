package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MedicineLogViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogChildMedicineActivity extends BackButtonActivity {

    private MedicineLogViewModel viewModel;
    private String childId;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    private TextView controllerPlaceholder;
    private TextView rescuePlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_child_medicine);
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

        controllerPlaceholder = findViewById(R.id.textControllerPlaceholder);
        rescuePlaceholder = findViewById(R.id.textRescuePlaceholder);

        viewModel = new ViewModelProvider(this).get(MedicineLogViewModel.class);
        viewModel.getLog().observe(this, this::populateLogs);
        viewModel.getLogError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.loadLogByUser(childId);
    }

    public void addDose(View view){
        Intent intent = new Intent(this, DoseCheckActivity.class);
        //start dose check activity remembering the previous state
        intent.putExtra(DoseCheckActivity.EXTRA_PREVIOUS, "LogChildMedicineActivity");
        intent.putExtra(DoseCheckActivity.EXTRA_MEDICINE_TYPE, "controller");
        startActivity(intent);

    }

    private void populateLogs(List<MedicineLog> logs) {
        List<MedicineLog> controllerLogs = new ArrayList<>();
        List<MedicineLog> rescueLogs = new ArrayList<>();

        if (logs != null) {
            for (MedicineLog log : logs) {
                if (log == null || log.getMedicineType() == null) {
                    continue;
                }
                if ("controller".equalsIgnoreCase(log.getMedicineType())) {
                    controllerLogs.add(log);
                } else if ("rescue".equalsIgnoreCase(log.getMedicineType())) {
                    rescueLogs.add(log);
                }
            }
        }

        controllerLogs.sort((a, b) -> Long.compare(b.getTime(), a.getTime()));
        rescueLogs.sort((a, b) -> Long.compare(b.getTime(), a.getTime()));

        bindControllerLogs(controllerLogs);
        bindRescueLogs(rescueLogs);
    }

    private void bindControllerLogs(List<MedicineLog> logs) {
        List<TextView[]> slots = new ArrayList<>();
        slots.add(new TextView[]{findViewById(R.id.textView116), findViewById(R.id.textView92)});
        slots.add(new TextView[]{findViewById(R.id.textView90), findViewById(R.id.textView115)});
        slots.add(new TextView[]{findViewById(R.id.textView117), findViewById(R.id.textView91)});

        if (logs == null || logs.isEmpty()) {
            showPlaceholder(controllerPlaceholder, slots);
            return;
        }

        hidePlaceholder(controllerPlaceholder);
        for (int i = 0; i < slots.size(); i++) {
            if (i < logs.size()) {
                MedicineLog log = logs.get(i);
                controllerDose(slots.get(i)[0], slots.get(i)[1], log);
            } else {
                clearSlot(slots.get(i)[0], slots.get(i)[1]);
            }
        }
    }

    private void bindRescueLogs(List<MedicineLog> logs) {
        List<TextView[]> slots = new ArrayList<>();
        slots.add(new TextView[]{findViewById(R.id.textView83), findViewById(R.id.textView82)});
        slots.add(new TextView[]{findViewById(R.id.textView85), findViewById(R.id.textView84)});
        slots.add(new TextView[]{findViewById(R.id.textView86), findViewById(R.id.textView89)});

        if (logs == null || logs.isEmpty()) {
            showPlaceholder(rescuePlaceholder, slots);
            return;
        }

        hidePlaceholder(rescuePlaceholder);
        for (int i = 0; i < slots.size(); i++) {
            if (i < logs.size()) {
                MedicineLog log = logs.get(i);
                rescueDose(slots.get(i)[0], slots.get(i)[1], log);
            } else {
                clearSlot(slots.get(i)[0], slots.get(i)[1]);
            }
        }
    }

    private void controllerDose(TextView doseView, TextView timeView, MedicineLog log) {
        if (log == null) {
            clearSlot(doseView, timeView);
            return;
        }
        doseView.setText(getString(R.string.child_medicine_dose_template, log.getDose()));
        timeView.setText(timeFormat.format(new Date(log.getTime())));
    }

    private void rescueDose(TextView doseView, TextView timeView, MedicineLog log) {
        if (log == null) {
            clearSlot(doseView, timeView);
            return;
        }
        doseView.setText(getString(R.string.child_medicine_dose_template, log.getDose()));
        timeView.setText(timeFormat.format(new Date(log.getTime())));
    }

    private void clearSlot(TextView doseView, TextView timeView) {
        doseView.setText("--");
        timeView.setText("--");
    }

    private void showPlaceholder(TextView placeholder, List<TextView[]> slots) {
        if (placeholder != null) {
            placeholder.setVisibility(View.VISIBLE);
        }
        for (TextView[] slot : slots) {
            clearSlot(slot[0], slot[1]);
        }
    }

    private void hidePlaceholder(TextView placeholder) {
        if (placeholder != null) {
            placeholder.setVisibility(View.GONE);
        }
    }
}
