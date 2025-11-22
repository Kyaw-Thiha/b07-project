package com.example.b07project.view.login;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.model.UserType;

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
        UserType userType = UserType.valueOf(getSharedPreferences("APP_DATA", MODE_PRIVATE).getString("USER_TYPE", null));

        Intent intent = new Intent();
        switch (userType) {
            case CHILD:
                intent = new Intent(AskLoginSignupActivity.this, AskChildAgeActivity.class);
                break;
            case PARENT:
                intent = new Intent(AskLoginSignupActivity.this, SignupActivity.class);
                break;
            case PROVIDER:
                intent = new Intent(AskLoginSignupActivity.this, SignupActivity.class);
            default:
                break;
        }
        startActivity(intent);
    }
}