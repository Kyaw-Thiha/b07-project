package com.example.b07project.view.login;


import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.model.User.*;
import com.example.b07project.view.child.ChildDashboardActivity;
import com.example.b07project.view.parent.ParentDashboardActivity;
import com.example.b07project.view.provider.ProviderDashboardActivity;
import com.example.b07project.R;
import com.google.firebase.auth.FirebaseAuth;

import com.example.b07project.model.User.UserType;
import com.example.b07project.services.Service;
import com.example.b07project.view.common.BackButtonActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.b07project.viewModel.ParentProfileViewModel;
import com.example.b07project.viewModel.ProviderProfileViewModel;
import com.example.b07project.viewModel.ChildProfileViewModel;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BackButtonActivity {

    private TextView text;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button resetPasswordButton;

    private UserType userType;
    private FirebaseDatabase db;
    private final Service service = new Service();
    private FirebaseAuth mAuth;
    SharedPreferences prefs;
    private ParentProfileViewModel parentProfileViewModel;
    private ProviderProfileViewModel providerProfileViewModel;
    private ChildProfileViewModel childProfileViewModel;
    private UserType pendingNavigation;
    private String pendingUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("APP_DATA", MODE_PRIVATE);

        userType = UserType.valueOf(prefs.getString("USER_TYPE", null));
        db = FirebaseDatabase.getInstance();
        parentProfileViewModel = new ViewModelProvider(this).get(ParentProfileViewModel.class);
        providerProfileViewModel = new ViewModelProvider(this).get(ProviderProfileViewModel.class);
        childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);

        observeUserProfiles();

        text = findViewById(R.id.Text);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        if (userType == UserType.PROVIDER) {
            text.setText("Provider Login");
        } else if (userType == UserType.CHILD) {
            text.setText("Child Login");
        } else {
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

            String uid = mAuth.getCurrentUser().getUid();
            pendingUid = uid;

            pendingNavigation = userType;
            if (userType == UserType.PARENT) {
                parentProfileViewModel.loadParent(uid);
            } else if (userType == UserType.PROVIDER) {
                providerProfileViewModel.loadProvider(uid);
            } else if (userType == UserType.CHILD) {
                childProfileViewModel.loadChild(uid);
            }
        });
    }

    void resetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    private void observeUserProfiles() {
        parentProfileViewModel.getParent().observe(this, parent -> {
            if (pendingNavigation != UserType.PARENT || pendingUid == null) {
                return;
            }
            if (parent == null) {
                Toast.makeText(this, "Parent profile not found. Please contact support.", Toast.LENGTH_SHORT).show();
                pendingNavigation = null;
                pendingUid = null;
                return;
            }
            SessionManager.setUser(parent);
            pendingNavigation = null;
            String launchUid = pendingUid;
            pendingUid = null;
            getSharedPreferences("APP_DATA", MODE_PRIVATE)
                    .edit()
                    .putString("PARENT_UID", launchUid)
                    .apply();
            Intent intent = new Intent(LoginActivity.this, ParentDashboardActivity.class);
            intent.putExtra(ParentDashboardActivity.EXTRA_PARENT_UID, launchUid);
            startActivity(intent);
            finish();
        });

        providerProfileViewModel.getProvider().observe(this, provider -> {
            if (pendingNavigation != UserType.PROVIDER || pendingUid == null) {
                return;
            }
            if (provider == null) {
                Toast.makeText(this, "Provider profile not found. Please contact support.", Toast.LENGTH_SHORT).show();
                pendingNavigation = null;
                pendingUid = null;
                return;
            }
            SessionManager.setUser(provider);
            pendingNavigation = null;
            pendingUid = null;
            startActivity(new Intent(LoginActivity.this, ProviderDashboardActivity.class));
            finish();
        });

        childProfileViewModel.getChild().observe(this, child -> {
            if (pendingNavigation != UserType.CHILD || pendingUid == null) {
                return;
            }
            if (child == null) {
                Toast.makeText(this, "Child profile not found. Please contact support.", Toast.LENGTH_SHORT).show();
                pendingNavigation = null;
                pendingUid = null;
                return;
            }
            SessionManager.setUser(child);
            pendingNavigation = null;
            pendingUid = null;
            startActivity(new Intent(LoginActivity.this, ChildDashboardActivity.class));
            finish();
        });
    }
}
