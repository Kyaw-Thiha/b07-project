package com.example.b07project.view.child;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.b07project.R;

import com.example.b07project.view.common.BackButtonActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ChildSettingsActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void submitThreshold(View view){
        TextInputEditText rescueThreshold = findViewById(R.id.textInputEditText);
        TextInputEditText controllerThreshold = findViewById(R.id.editText);
        TextInputEditText techniqueThreshold = findViewById(R.id.editText2);

        //change threshold values in database to these values submitted by user
    }


}