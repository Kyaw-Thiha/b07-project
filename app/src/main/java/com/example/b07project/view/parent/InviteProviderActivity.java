package com.example.b07project.view.parent;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Invite;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.InviteViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class InviteProviderActivity extends BackButtonActivity {
    private TextInputEditText nameInput;
    private TextInputEditText lastNameInput;
    private EditText emailInput;
    private EditText phoneInput;
    private TextInputEditText codeInput;
    private TextView expirationText;
    private Button generateButton;
    private Button inviteButton;

    private InviteViewModel inviteViewModel;
    private String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_invite_provider);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        parentId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
        if (parentId == null) {
            Toast.makeText(this, "Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        bindViews();
        setupViewModel();
    }

    private void bindViews() {
        nameInput = findViewById(R.id.first_name);
        lastNameInput = findViewById(R.id.last_name);
        emailInput = findViewById(R.id.email_address);
        phoneInput = findViewById(R.id.phone_number);
        codeInput = findViewById(R.id.code_generator);
        expirationText = findViewById(R.id.text_code_expiration);
        generateButton = findViewById(R.id.button14);
        inviteButton = findViewById(R.id.invite_button);

        generateButton.setOnClickListener(v -> inviteViewModel.generateInvite(parentId));
        inviteButton.setOnClickListener(v -> shareInvite());
        inviteButton.setEnabled(false);
    }

    private void setupViewModel() {
        inviteViewModel = new ViewModelProvider(this).get(InviteViewModel.class);
        inviteViewModel.observeInvite(parentId);

        inviteViewModel.getCurrentInvite().observe(this, this::bindInvite);
        inviteViewModel.getActionSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(this, "Invite updated.", Toast.LENGTH_SHORT).show();
            }
        });
        inviteViewModel.getErrorMessage().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindInvite(Invite invite) {
        if (invite == null) {
            codeInput.setText("");
            expirationText.setText(R.string.invite_no_code);
            inviteButton.setEnabled(false);
            return;
        }
        codeInput.setText(invite.getCode());
        if (invite.getExpiresAt() != null) {
            String formatted = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT,
                    Locale.getDefault())
                    .format(new Date(invite.getExpiresAt()));
            expirationText.setText(getString(R.string.invite_code_expires, formatted));
        } else {
            expirationText.setText(R.string.invite_no_code);
        }
        inviteButton.setEnabled(true);
    }

    private void shareInvite() {
        String code = codeInput.getText() != null ? codeInput.getText().toString().trim() : "";
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "Generate a code first.", Toast.LENGTH_SHORT).show();
            return;
        }
        String providerName = nameInput.getText() != null ? nameInput.getText().toString().trim() : "";
        String providerLast = lastNameInput.getText() != null ? lastNameInput.getText().toString().trim() : "";
        String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";
        String phone = phoneInput.getText() != null ? phoneInput.getText().toString().trim() : "";

        String message = "Invite Code: " + code;
        if (!TextUtils.isEmpty(providerName)) {
            message += "\nFor: " + providerName + " " + providerLast;
        }
        if (!TextUtils.isEmpty(email)) {
            message += "\nEmail: " + email;
        }
        if (!TextUtils.isEmpty(phone)) {
            message += "\nPhone: " + phone;
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Invite Code", message));
            Toast.makeText(this, "Invite copied to clipboard.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
