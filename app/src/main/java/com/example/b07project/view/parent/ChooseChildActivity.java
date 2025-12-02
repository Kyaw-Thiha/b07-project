package com.example.b07project.view.parent;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Incident;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.view.child.RescueDecisionActivity;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.IncidentViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChooseChildActivity extends BackButtonActivity {

    private ChildProfileViewModel childViewModel;
    private IncidentViewModel incidentViewModel;
    private Spinner childSpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private final List<ChildUser> children = new ArrayList<>();
    private String selectedChildId;

    private CheckBox speakYes, speakNo, chestYes, chestNo, blueYes, blueNo, rescueYes, rescueNo;
    private TextInputEditText pefInput;

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

        TextView selectLabel = findViewById(R.id.textSelectChild);
        selectLabel.setVisibility(View.VISIBLE);

        childSpinner = findViewById(R.id.spinnerChildSelector);
        childSpinner.setVisibility(View.VISIBLE);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        childSpinner.setAdapter(spinnerAdapter);
        childSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < children.size()) {
                    selectedChildId = children.get(position).getUid();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedChildId = null;
            }
        });

        bindForm();
        bindViewModels();

        findViewById(R.id.buttonSubmit).setOnClickListener(v -> submitIncident());
    }

    private void bindForm() {
        speakYes = findViewById(R.id.checkBox17);
        speakNo = findViewById(R.id.checkBox18);
        chestYes = findViewById(R.id.checkBox19);
        chestNo = findViewById(R.id.checkBox20);
        blueYes = findViewById(R.id.checkBox21);
        blueNo = findViewById(R.id.checkBox22);
        rescueYes = findViewById(R.id.checkBox23);
        rescueNo = findViewById(R.id.checkBox24);
        pefInput = findViewById(R.id.inputPef);
    }

    private void bindViewModels() {
        childViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
        incidentViewModel = new ViewModelProvider(this).get(IncidentViewModel.class);

        String parentId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        if (parentId == null) {
            Toast.makeText(this, "Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        childViewModel.getChildren().observe(this, list -> {
            children.clear();
            if (list != null) {
                children.addAll(list);
            }
            List<String> names = new ArrayList<>();
            for (ChildUser child : children) {
                names.add(child.getName());
            }
            spinnerAdapter.clear();
            spinnerAdapter.addAll(names);
            spinnerAdapter.notifyDataSetChanged();
            if (!children.isEmpty()) {
                childSpinner.setSelection(0);
                selectedChildId = children.get(0).getUid();
            } else {
                selectedChildId = null;
                Toast.makeText(this, "No children available.", Toast.LENGTH_SHORT).show();
            }
        });

        childViewModel.observeChildrenForParent(parentId);
    }

    private void submitIncident() {
        if (TextUtils.isEmpty(selectedChildId)) {
            Toast.makeText(this, "Select a child first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Incident.Flags flags = new Incident.Flags(
                speakYes.isChecked(),
                chestYes.isChecked(),
                blueYes.isChecked(),
                false,
                false);

        Incident incident = new Incident();
        incident.setFlags(flags);
        incident.setTime(System.currentTimeMillis());
        incident.setRescueAttempts(rescueYes.isChecked() ? 1 : 0);
        incident.setUid(selectedChildId);
        String pefValue = pefInput.getText() != null ? pefInput.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(pefValue)) {
            try {
                incident.setPefNumber(Integer.parseInt(pefValue));
            } catch (NumberFormatException ignored) {
            }
        }
        incident.setGuidance("Parent submitted triage responses.");

        incidentViewModel.addIncident(selectedChildId, incident);

        Intent data = new Intent();
        data.putExtra("selectedChildId", selectedChildId);
        setResult(RESULT_OK, data);

        startActivity(new Intent(this, RescueDecisionActivity.class));
        finish();
    }
}
