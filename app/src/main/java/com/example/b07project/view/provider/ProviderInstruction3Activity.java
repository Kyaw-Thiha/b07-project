package com.example.b07project.view.provider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.util.OnboardingPrefs;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.view.login.LoginActivity;
import com.example.b07project.viewModel.ProviderProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class ProviderInstruction3Activity extends BackButtonActivity {
    private ProviderProfileViewModel viewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_instruction_3);

        auth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(ProviderProfileViewModel.class);

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            viewModel.loadProvider(user.getUid());
        }

        ImageButton buttonNext = findViewById(R.id.buttonNext);
        TextView buttonSkip = findViewById(R.id.buttonSkip);

        buttonNext.setOnClickListener(v -> finishOnboarding());
        buttonSkip.setOnClickListener(v -> finishOnboarding());
    }

    private void finishOnboarding() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            viewModel.updateProvider(user.getUid(),
                    Collections.singletonMap("onboardingCompleted", true));
        }
        OnboardingPrefs.setProviderOnboardingCompleted(this, true);
        Intent intent = new Intent(
                ProviderInstruction3Activity.this,
                LoginActivity.class
        );
        startActivity(intent);
        finish();
    }
}
