package com.example.b07project.view.login;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends BackButtonActivity {

    private EditText emailInput;
    private Button sendButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_reset_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInput);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> attemptSend());
    }
    @SuppressWarnings("deprecation")
    private void attemptSend() {
        String email = emailInput.getText().toString().trim();

        // 1) Empty check
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2) Format check
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3) Check if this email exists in Firebase Auth
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this,
                        "Error: " + task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!(task.getResult() != null &&
                    task.getResult().getSignInMethods() != null &&
                    !task.getResult().getSignInMethods().isEmpty())) {
                // No account with this email
                Toast.makeText(this, "No account found with this email.", Toast.LENGTH_SHORT).show();
                return;
            }


            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(sendTask -> {
                if (!sendTask.isSuccessful()) {
                    Toast.makeText(this, "Failed to send reset email: " + sendTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Reset link sent. Please check your email.", Toast.LENGTH_LONG).show();

            });
        });
    }
}