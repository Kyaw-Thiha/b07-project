package com.example.b07project.view.auth;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class AskLoginSignupActivity extends BackButtonActivity {
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ask_login_signup_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.askLoginSignupPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton = findViewById(R.id.LoginButton);
        signupButton = findViewById(R.id.SignupButton);

        loginButton.setOnClickListener(v->toLogin());
        signupButton.setOnClickListener(v->toSignup());
    }

    void toLogin() {
        Intent intent = new Intent(AskLoginSignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    void toSignup() {
        Intent intent = new Intent(AskLoginSignupActivity.this, AskUsertypeActivity.class);
        startActivity(intent);
    }
}