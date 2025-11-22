package com.example.b07project.view.login;


import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
//import android.content.SharedPreferences;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.view.child.ChildDashboardActivity;
import com.example.b07project.view.parent.ParentDashboardActivity;
import com.example.b07project.R;
import com.google.firebase.auth.FirebaseAuth;

import com.example.b07project.model.UserType;
import com.example.b07project.view.common.BackButtonActivity;

public class LoginActivity extends BackButtonActivity {

    private TextView text;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button resetPasswordButton;

    UserType userType;
    private FirebaseAuth mAuth;
//    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        userType = UserType.valueOf(getSharedPreferences("APP_DATA", MODE_PRIVATE).getString("USER_TYPE", null));

        text = findViewById(R.id.Text);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        if (userType == UserType.PROVIDER) {
            text.setText("Provider Login");
        }
        else {
            text.setText("Parent Login");
        }

        loginButton.setOnClickListener(v->checkLogin());
        resetPasswordButton.setOnClickListener(v->resetPassword());
    }

    void checkLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(result-> {
            if(!result.isSuccessful()) {
                Toast.makeText(this, result.getException().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            switch (userType) {
                case CHILD:
                    intent = new Intent(LoginActivity.this, ChildDashboardActivity.class);
                    break;
                case PARENT:
                    intent = new Intent(LoginActivity.this, ParentDashboardActivity.class);
                    break;
                case PROVIDER:
                    //add stuffs later
                    return;
                default:
                    break;
            }
            startActivity(intent);

        });
    }

    void resetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}