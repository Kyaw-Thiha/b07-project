package com.example.b07project.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;

import com.example.b07project.model.User.BaseUser;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.model.User.ProviderUser;
import com.example.b07project.model.User.UserType;
import com.example.b07project.view.provider.ProviderInstruction1Activity;
import com.example.b07project.viewModel.ParentProfileViewModel;
import com.example.b07project.viewModel.ProviderProfileViewModel;
import com.example.b07project.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

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
    private UserViewModel userViewModel;
    private ParentProfileViewModel parentProfileViewModel;
    private ProviderProfileViewModel providerProfileViewModel;

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
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        parentProfileViewModel = new ViewModelProvider(this).get(ParentProfileViewModel.class);
        providerProfileViewModel = new ViewModelProvider(this).get(ProviderProfileViewModel.class);

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
            Map<String, Boolean> roles = buildRoles(userType);
            BaseUser baseUser = new BaseUser(uid, name, email, roles);
            userViewModel.createUser(uid, baseUser);

            if (userType == UserType.PARENT){
                ParentUser parentUser = new ParentUser(uid, name, email, roles);
                parentUser.setUid(uid);
                parentProfileViewModel.createParent(uid, parentUser);
            }

            if (userType == UserType.PROVIDER){
                ProviderUser providerUser = new ProviderUser(uid, name, email, roles);
                providerProfileViewModel.createProvider(uid, providerUser);
                Intent intent = new Intent(SignupActivity.this, ProviderInstruction1Activity.class);
                startActivity(intent);
                finish();
            }

        });
    }

    private Map<String, Boolean> buildRoles(UserType type) {
        Map<String, Boolean> roles = new HashMap<>();

        if (type == null) {
            return roles;
        }

        if (type == UserType.PARENT) {
            roles.put("parent", true);
        } else if (type == UserType.PROVIDER) {
            roles.put("provider", true);
        } else if (type == UserType.CHILD) {
            roles.put("child", true);
        }

        return roles;
    }
}
