package com.example.b07project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.model.User;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.b07project.model.BackButtonActivity;

public class SignupActivity extends BackButtonActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signupButton = findViewById(R.id.SignupButton);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signupButton.setOnClickListener(v->signupAccount());
    }

    void signupAccount() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please confirm password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(result-> {
            if(!result.isSuccessful()) {
                Toast.makeText(this, result.getException().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            String uid = mUser.getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);
            ref.setValue(new User(email));


            Toast.makeText(this, "Sign up successful! Head to the sign in page to proceed", Toast.LENGTH_SHORT).show();
        });
    }
}