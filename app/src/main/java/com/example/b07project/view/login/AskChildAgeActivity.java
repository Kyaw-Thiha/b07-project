package com.example.b07project.view.login;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ask_child_age);
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
        Boolean user_age_below9 = false;
        if (b == below9Button){
            user_age_below9 = true;
        }

        Intent intent = new Intent(AskChildAgeActivity.this, AskChildNameActivity.class);
        intent.putExtra("child-user-age-below-9", user_age_below9);
        startActivity(intent);
    }
}