package com.example.b07project.view.provider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.view.login.LoginActivity;

public class ProviderInstruction1Activity extends BackButtonActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_instruction_1);

        // right-down Next button
        ImageButton buttonNext = findViewById(R.id.buttonNext);
        // left-down corner Skip textview
        TextView buttonSkip = findViewById(R.id.buttonSkip);

        // click Next → jump to Provider's Instruction Page 2
        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProviderInstruction1Activity.this,
                    ProviderInstruction2Activity.class
            );
            startActivity(intent);
        });

        // click Skip → back to log in
        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProviderInstruction1Activity.this,
                    LoginActivity.class
            );
            startActivity(intent);
            finish();
        });
    }
}