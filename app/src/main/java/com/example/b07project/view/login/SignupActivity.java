package com.example.b07project.view.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;

import com.example.b07project.model.User.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.b07project.view.common.BackButtonActivity;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends BackButtonActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private UserType userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signupButton = findViewById(R.id.SignupButton);

        mAuth = FirebaseAuth.getInstance();
        userType = UserType.valueOf(getSharedPreferences("APP_DATA",MODE_PRIVATE).getString("USER_TYPE", null));

        signupButton.setOnClickListener(v->signupAccount());
    }

    void signupAccount() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }
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

        if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "Please enter the same password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(result-> {
            if(!result.isSuccessful()) {
                Toast.makeText(this, result.getException().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            String uid = result.getResult().getUser().getUid();

            DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference()
                    .child("users");

            if (userType == UserType.PARENT){
                DatabaseReference ref = baseRef
                        .child("parents")
                        .child(uid);
                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("email", email);
                data.put("providers", null);
                data.put("inventory", null);
                data.put("badges", null);
                ref.setValue(data);
            }
            if (userType == UserType.PROVIDER){
                DatabaseReference ref = baseRef
                        .child("providers")
                        .child(uid);
                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("email", email);
                ref.setValue(data);
            }

            Toast.makeText(this, "Sign up successful! Head to the sign in page to proceed", Toast.LENGTH_SHORT).show();
        });
    }
}