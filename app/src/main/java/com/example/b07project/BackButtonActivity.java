package com.example.b07project;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BackButtonActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupBackButton();  // ALWAYS runs after layout is inflated
    }

    protected void setupBackButton() {
        Button back = findViewById(R.id.buttonBack);
        if (back != null) {
            back.setOnClickListener(v -> finish());
        }
    }
}