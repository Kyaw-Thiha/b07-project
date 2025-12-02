package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MedicineLogViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;

public class DoseCheckActivity extends BackButtonActivity {

    public static final String EXTRA_PREVIOUS = "previous_activity";
    public static final String EXTRA_MEDICINE_TYPE = "medicine_type";
    public static final String EXTRA_FEELING = "feeling";
    public static final String EXTRA_LOG_ID = "log_id";

    private MedicineLogViewModel medicineLogViewModel;
    private String childId;
    private String medicineType;
    private String feeling;
    private String logId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dose_check);
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

        medicineLogViewModel = new ViewModelProvider(this).get(MedicineLogViewModel.class);

        String previous = getIntent().getStringExtra(EXTRA_PREVIOUS);
        medicineType = getIntent().getStringExtra(EXTRA_MEDICINE_TYPE);
        if (medicineType == null) {
            medicineType = "controller";
        }
        feeling = getIntent().getStringExtra(EXTRA_FEELING);
        logId = getIntent().getStringExtra(EXTRA_LOG_ID);

        if ("LogChildMedicineActivity".equals(previous)) {
            //make pre-dose text visible
            TextView preDose=findViewById(R.id.textView36);
            TextView postDose=findViewById(R.id.textView37);
            preDose.setVisibility(View.VISIBLE);
            postDose.setVisibility(View.INVISIBLE);
            //make pre dose button visible and clickable
            Button preDoseButton=findViewById(R.id.button12);
            Button postDoseButton=findViewById(R.id.invite_button);
            preDoseButton.setVisibility(View.VISIBLE);
            postDoseButton.setVisibility(View.GONE);
        } else if ("ControllerMedicineInputActivity".equals(previous)) {
            //make post-dose text visible
            TextView preDose=findViewById(R.id.textView36);
            TextView postDose=findViewById(R.id.textView37);
            preDose.setVisibility(View.INVISIBLE);
            postDose.setVisibility(View.VISIBLE);
            //make post dose button visible and clickable
            Button preDoseButton=findViewById(R.id.button12);
            Button postDoseButton=findViewById(R.id.button13);
            preDoseButton.setVisibility(View.GONE);
            postDoseButton.setVisibility(View.VISIBLE);
        }
    }

    public void betterDose(View view){
        handleDoseSelection(getString(R.string.child_dose_better));
    }

    public void normalDose(View view){
        handleDoseSelection(getString(R.string.child_dose_normal));
    }

    public void worseDose(View view){
        handleDoseSelection(getString(R.string.child_dose_worse));
    }

    public void preDoseCheck(View view){
        Intent intent = new Intent(this, ControllerMedicineInputActivity.class);
        intent.putExtra(EXTRA_PREVIOUS, "DoseCheckActivity");
        intent.putExtra(EXTRA_MEDICINE_TYPE, medicineType != null ? medicineType : "controller");
        intent.putExtra(EXTRA_FEELING, feeling);
        startActivity(intent);
    }

    public void postDoseCheck(View view){
        //go back to dashboard after saving
        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleDoseSelection(String selection) {
        TextView preDose=findViewById(R.id.textView36);
        int preDoseVisible = preDose.getVisibility();
        if (preDoseVisible == View.VISIBLE) {
            feeling = selection;
            Intent intent = new Intent(this, ControllerMedicineInputActivity.class);
            intent.putExtra(EXTRA_PREVIOUS, "DoseCheckActivity");
            intent.putExtra(EXTRA_MEDICINE_TYPE, medicineType != null ? medicineType : "controller");
            intent.putExtra(EXTRA_FEELING, feeling);
            startActivity(intent);
            return;
        }

        if (logId == null) {
            Toast.makeText(this, R.string.child_medicine_missing_log, Toast.LENGTH_LONG).show();
            return;
        }

        medicineLogViewModel.updateInventory(childId, logId,
                Collections.singletonMap("after", selection));
        Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
        postDoseCheck(null);
    }
}
