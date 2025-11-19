package com.example.b07project;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import com.example.b07project.model.BackButtonActivity;

public class LoginActivity extends BackButtonActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button resetPasswordButton;

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

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

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
            //TODO CONNECT TO THE NEXT PAGE
        });
    }

    void resetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}