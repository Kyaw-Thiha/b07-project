package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MotivationViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ThirdTechniqueHelperActivity extends BackButtonActivity {

    private TechniqueMotivationLogger motivationLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third_technique_helper);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String childId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
        MotivationViewModel motivationViewModel = new ViewModelProvider(this).get(MotivationViewModel.class);
        if (childId != null) {
            motivationViewModel.loadMotivation(childId);
        }
        motivationLogger = new TechniqueMotivationLogger(childId, motivationViewModel);
    }

    public void finish(View view){
        motivationLogger.logSessionIfNeeded();
        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);
    }
}
