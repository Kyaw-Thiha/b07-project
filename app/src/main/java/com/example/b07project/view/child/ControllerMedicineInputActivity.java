package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ControllerMedicineInputActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_controller_medicine_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void submitDose(View view){
        //save the info inputted in the text view input bar into database then go to the dose check after dose
        TextInputEditText dose = findViewById(R.id.textInputDose);
        TextInputEditText time = findViewById(R.id.textInputTime);
        //save the dosage and time to firebase
        Intent intent = new Intent(this, DoseCheckActivity.class);
        //start dose check activity remembering the previous state
        intent.putExtra("previous_activity", "ControllerMedicineInputActivity");
        startActivity(intent);
    }
}