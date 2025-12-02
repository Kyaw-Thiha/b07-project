package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.PEF;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.PEFViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class PefEntryActivity extends BackButtonActivity {

    private TextInputEditText singleInput;
    private TextInputEditText preDoseInput;
    private TextInputEditText postDoseInput;
    private TextView singlePefBlock;
    private TextView bothPefBlock;
    private int choice =0;
    private PEFViewModel pefViewModel;
    private ChildProfileViewModel childProfileViewModel;
    private String childId;
    private Integer personalBest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pef_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        singlePefBlock = findViewById(R.id.textView53);
        bothPefBlock = findViewById(R.id.textView54);
        singleInput = findViewById(R.id.editText4);
        preDoseInput = findViewById(R.id.editText3);
        postDoseInput = findViewById(R.id.editText5);
        singlePefBlock.setVisibility(View.VISIBLE);
        bothPefBlock.setVisibility(View.VISIBLE);

        childId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
        if (childId == null) {
            Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        pefViewModel = new ViewModelProvider(this).get(PEFViewModel.class);
        childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
        childProfileViewModel.getChild().observe(this, child -> {
            if (child != null) {
                personalBest = child.getPersonalBest();
            }
        });
        childProfileViewModel.loadChild(childId);
    }

    public void singlePEF(View view){
        singlePefBlock.setVisibility(View.INVISIBLE);
        bothPefBlock.setVisibility(View.VISIBLE);
        choice = 1;
    }

    public void bothPEF(View view){
        bothPefBlock.setVisibility(View.INVISIBLE);
        singlePefBlock.setVisibility(View.VISIBLE);
        choice = 2;
    }

    public void pefSubmit(View view){
        if(choice !=0) {
            if(choice==1){
                String input = singleInput.getText() != null ? singleInput.getText().toString().trim() : "";
                if (TextUtils.isEmpty(input)) {
                    singleInput.setError(getString(R.string.child_pef_required));
                    return;
                }
                singleInput.setError(null);
                saveSinglePef(input);
            }
            else {
                String preInput = preDoseInput.getText() != null ? preDoseInput.getText().toString().trim() : "";
                String postInput = postDoseInput.getText() != null ? postDoseInput.getText().toString().trim() : "";
                if (TextUtils.isEmpty(preInput) && TextUtils.isEmpty(postInput)) {
                    preDoseInput.setError(getString(R.string.child_pef_required));
                    postDoseInput.setError(getString(R.string.child_pef_required));
                    return;
                }
                preDoseInput.setError(null);
                postDoseInput.setError(null);
                saveBothPef(preInput, postInput);
            }
            //go to dashboard
            Intent intent = new Intent(this, ChildDashboardActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            String msg = "Please choose and enter your PEF";
            Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        }
    }

    private void saveSinglePef(String value) {
        float pefValue = parseFloat(value);
        if (pefValue <= 0) {
            singleInput.setError(getString(R.string.child_pef_positive));
            return;
        }
        PEF pef = new PEF();
        pef.setUid(childId);
        pef.setTime(System.currentTimeMillis());
        pef.setPost_med(pefValue);
        pefViewModel.addPEF(childId, pef);
        updatePersonalBest(pefValue);
        Toast.makeText(this, R.string.child_pef_saved, Toast.LENGTH_LONG).show();
    }

    private void saveBothPef(String preValue, String postValue) {
        float pre = parseFloat(preValue);
        float post = parseFloat(postValue);
        PEF pef = new PEF();
        pef.setUid(childId);
        pef.setTime(System.currentTimeMillis());
        if (pre > 0) {
            pef.setPre_med(pre);
        }
        if (post > 0) {
            pef.setPost_med(post);
        }
        if (pef.getPre_med() == 0 && pef.getPost_med() == 0) {
            Toast.makeText(this, R.string.child_pef_positive, Toast.LENGTH_LONG).show();
            return;
        }
        pefViewModel.addPEF(childId, pef);
        float valueForZone = pef.getPost_med() > 0 ? pef.getPost_med() : pef.getPre_med();
        updatePersonalBest(valueForZone);
        Toast.makeText(this, R.string.child_pef_saved, Toast.LENGTH_LONG).show();
    }

    private void updatePersonalBest(float newValue) {
        int currentBest = personalBest != null ? personalBest : 0;
        int best = currentBest;
        if (newValue > currentBest) {
            best = (int) newValue;
        }
        String zone = computeZone(newValue, best);
        Map<String, Object> updates = new HashMap<>();
        if (best != currentBest) {
            updates.put("personalBest", best);
            personalBest = best;
        }
        updates.put("currentZone", zone);
        childProfileViewModel.updateChild(childId, updates);
    }

    private float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    private String computeZone(float value, int best) {
        if (best <= 0) {
            return "green";
        }
        double ratio = value / best;
        if (ratio >= 0.8) {
            return "green";
        } else if (ratio >= 0.5) {
            return "yellow";
        }
        return "red";
    }
}
