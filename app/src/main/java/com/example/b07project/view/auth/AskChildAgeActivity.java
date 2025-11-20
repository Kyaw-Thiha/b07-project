package com.example.b07project.view.auth;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class AskChildAgeActivity extends BackButtonActivity {
    private Button below9Button;
    private Button above10Button;
    boolean isBelow9 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ask_child_age_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.askChildAgePage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        below9Button = findViewById(R.id.below9Button);
        above10Button = findViewById(R.id.above10Button);

        below9Button.setOnClickListener(v->toPage(below9Button));
        above10Button.setOnClickListener(v->toPage(above10Button));
    }

    private void toPage(Button b) {
        if (b == below9Button){
            isBelow9 = true;
        }

        //TODO complete this method
        Intent intent = new Intent(AskChildAgeActivity.this, LoginActivity.class);

        startActivity(intent);
    }
}