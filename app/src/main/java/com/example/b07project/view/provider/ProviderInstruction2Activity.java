package com.example.b07project.view.provider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.User.ProviderUser;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.view.login.LoginActivity;
import com.example.b07project.viewModel.ProviderProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProviderInstruction2Activity extends BackButtonActivity {
    private ProviderProfileViewModel viewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_instruction_2);

        auth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(ProviderProfileViewModel.class);

        TextView subtitle = findViewById(R.id.textSubtitle);
        viewModel.getProvider().observe(this, provider -> applyProfileCopy(provider, subtitle));

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            viewModel.loadProvider(user.getUid());
        }

        ImageButton buttonNext = findViewById(R.id.buttonNext);
        TextView buttonSkip = findViewById(R.id.buttonSkip);

        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProviderInstruction2Activity.this,
                    ProviderInstruction3Activity.class
            );
            startActivity(intent);
        });

        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProviderInstruction2Activity.this,
                    LoginActivity.class
            );
            startActivity(intent);
            finish();
        });
    }

    private void applyProfileCopy(ProviderUser provider, TextView subtitle) {
        if (provider == null) {
            return;
        }
        if (provider.getName() != null) {
            subtitle.setText(getString(R.string.provider_onboarding_data_source,
                    provider.getName()));
        }
    }
}
