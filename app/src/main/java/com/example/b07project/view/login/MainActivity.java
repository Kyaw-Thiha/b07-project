package com.example.b07project.view.login;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
//import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.getStartedPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //connect UIs
        getStartedButton = findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(v->getStarted());
    }

    void getStarted() {
        Intent intent = new Intent(MainActivity.this, AskUsertypeActivity.class);
        startActivity(intent);
    }


//    private EditText inputEmail;
//    private EditText inputPassword;
//    private Button loginButton;
//    private Button signupButton;
//
//
//    private FirebaseDatabase database;
//    private DatabaseReference accountsRef;
//    private FirebaseAuth mAuth;
//
//    @Override
//    /*
//     * This methods takes the Bundle savedInstanceState, and handles the homepage
//     */
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.login_page);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // firebase stuffs
//        database = FirebaseDatabase.getInstance("https://b07-project-9e91e-default-rtdb.firebaseio.com/");
//        accountsRef = database.getReference("accounts");
//        mAuth = FirebaseAuth.getInstance();
//
//        // ui stuffs
//        inputEmail = findViewById(R.id.inputEmail);
//        inputPassword = findViewById(R.id.inputPassword);
//        loginButton = findViewById(R.id.buttonLogin);
//        signupButton = findViewById(R.id.buttonSignup);
//
//        // ui functionality stuffs
//        loginButton.setOnClickListener(v -> attemptLogin());
//        signupButton.setOnClickListener(v->signup());
//    }
//void getStarted() {
//        Intent intent = new Intent(MainActivity.this, AskLoginSignupActivity.class);
//        startActivity(intent);
//    }

//    /*
//     * This method handles the login part of the homepage
//     */
//    private void attemptLogin() {
//        String email = inputEmail.getText().toString();
//        String password = inputPassword.getText().toString();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        boolean loggedIn = false;
//
//        accountsRef.get().addOnSuccessListener(snapshot -> {
//
//            for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
//
//                String dbEmail = accountSnapshot.child("email").getValue(String.class);
//                String dbPassword = accountSnapshot.child("password").getValue(String.class);
//
//                if (dbEmail == null || dbPassword == null) {
//                    continue;
//                }
//
//                if (email.equals(dbEmail) && password.equals(dbPassword)) {
//                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
//                    // TODO: go to next page
//                    return;
//                }
//            }
//
//            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
//
//        }).addOnFailureListener(e ->
//                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }
//
//    /*
//     * This method handles stuffs after login
//     */
//    private void signup() {
//
//
//    }
}