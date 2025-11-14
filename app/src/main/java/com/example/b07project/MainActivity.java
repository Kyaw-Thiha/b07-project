package com.example.b07project;

import com.example.b07project.model.Account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.example.b07project.model.Account;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button loginButton;


    private FirebaseDatabase database;
    private DatabaseReference accountsRef;
//    private FirebaseAuth mAuth;

    @Override
    /*
     * This methods takes the Bundle savedInstanceState, and handles the homepage
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // I don't really know what this is but it's just there --Simon
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance("https://b07-project-9e91e-default-rtdb.firebaseio.com/");
        accountsRef = database.getReference("accounts");

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> attemptLogin());
    }
    
    /*
     * This method handles the login part of the homepage
     */
    private void attemptLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        accountsRef.get().addOnSuccessListener(snapshot -> {

            if (!snapshot.exists()) {
                Toast.makeText(this, "No accounts found", Toast.LENGTH_SHORT).show();
                return;
            }

            String dbEmail = snapshot.child("email").getValue(String.class);
            String dbPassword = snapshot.child("password").getValue(String.class);


            if (dbEmail == null || dbPassword == null) {
                Toast.makeText(this, "corrupted data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.equals(dbEmail) &&
                    password.equals(dbPassword)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                //add your code here to connect to next page
            } else {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e ->
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}