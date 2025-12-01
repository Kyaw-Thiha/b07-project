package com.example.b07project.view.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class ParentOnboardingPage3Activity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_onboarding_3);

        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ParentOnboardingPage3Activity.this,
                        ParentOnboardingPage4Activity.class
                );
                startActivity(intent);
            }
        });
    }
}

