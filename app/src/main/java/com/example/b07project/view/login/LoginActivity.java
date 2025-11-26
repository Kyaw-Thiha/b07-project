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
import com.example.b07project.model.FirebaseManager;
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

            String uid = mAuth.getCurrentUser().getUid();
            pendingUid = uid;

            if (userType == UserType.CHILD) {
                Intent intent = getIntent();
                if (intent.hasExtra("child-user-age-below-9")) {
                    Boolean age_below_9 = intent.getBooleanExtra("child-user-age-below-9", false);
                    String child_uid = db.getReference("children").push().getKey();
                    ChildUser user = new ChildUser(child_uid, "placeholder name", "", null, age_below_9);
                    SessionManager.setUser(user);

                    DatabaseReference childRef = FirebaseManager.getRefParent().child(uid)
                            .child("children")
                            .child(user.getUid());
                    Map<String, Object> data = new HashMap();
                    data.put("name", user.getName());
                    data.put("ageBelow9", user.isAgeBelow9());
                    data.put("optionalNote", "none");
                    data.put("medicineLog", null);
                    data.put("incidentLog", null);
                    data.put("PEFLog", null);
                    data.put("symptomsLog", null);
                    childRef.setValue(data);
                }
                startActivity(new Intent(LoginActivity.this, ChildDashboardActivity.class));
                return;
            }

            pendingNavigation = userType;
            if (userType == UserType.PARENT) {
                parentProfileViewModel.loadParent(uid);
            } else if (userType == UserType.PROVIDER) {
                providerProfileViewModel.loadProvider(uid);
            }
        });
    }

    void resetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    private void observeUserProfiles() {
        parentProfileViewModel.getParent().observe(this, parent -> {
            if (parent != null && pendingNavigation == UserType.PARENT && pendingUid != null) {
                SessionManager.setUser(parent);
                pendingNavigation = null;
                pendingUid = null;
                startActivity(new Intent(LoginActivity.this, ParentDashboardActivity.class));
            }
        });

        providerProfileViewModel.getProvider().observe(this, provider -> {
            if (provider != null && pendingNavigation == UserType.PROVIDER && pendingUid != null) {
                SessionManager.setUser(provider);
                pendingNavigation = null;
                pendingUid = null;
                startActivity(new Intent(LoginActivity.this, ProviderDashboardActivity.class));
            }
        });

        childProfileViewModel.getChild().observe(this, child -> {
            if (child != null && pendingNavigation == UserType.CHILD && pendingUid != null) {
                SessionManager.setUser(child);
                pendingNavigation = null;
                pendingUid = null;
                startActivity(new Intent(LoginActivity.this, ChildDashboardActivity.class));
            }
        });
    }
}
