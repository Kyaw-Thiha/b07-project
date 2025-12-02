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

public class ProviderInstruction1Activity extends BackButtonActivity {
    private ProviderProfileViewModel viewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_instruction_1);

        auth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(ProviderProfileViewModel.class);

        TextView title = findViewById(R.id.textTitle);
        TextView subtitle = findViewById(R.id.textSubtitle);

        viewModel.getProvider().observe(this, provider -> applyProfileCopy(provider, title, subtitle));

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            viewModel.loadProvider(user.getUid());
        }

        ImageButton buttonNext = findViewById(R.id.buttonNext);
        TextView buttonSkip = findViewById(R.id.buttonSkip);

        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProviderInstruction1Activity.this,
                    ProviderInstruction2Activity.class
            );
            startActivity(intent);
        });

        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProviderInstruction1Activity.this,
                    LoginActivity.class
            );
            startActivity(intent);
            finish();
        });
    }

    private void applyProfileCopy(ProviderUser provider, TextView title, TextView subtitle) {
        if (provider == null) {
            return;
        }
        if (provider.getName() != null) {
            title.setText(getString(R.string.provider_onboarding_welcome, provider.getName()));
        }
        if (provider.getOrganization() != null && !provider.getOrganization().isEmpty()) {
            subtitle.setText(getString(R.string.provider_onboarding_subtitle_with_org,
                    provider.getOrganization()));
        }
    }
}
