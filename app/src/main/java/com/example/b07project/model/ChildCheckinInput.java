package com.example.b07project.model;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;

public class ChildCheckinInput extends BackButtonActivity {
    CheckBox nightY, nightN, hardAct, lowAct, noAct, cough, coughSome, coughNone, smoke, illness, pets, exercise, coldAir, dust, odor, noTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_checkin_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nightN=findViewById(R.id.checkBox);
        nightY=findViewById(R.id.checkBox2);
        hardAct=findViewById(R.id.checkBox3);
        lowAct=findViewById(R.id.checkBox4);
        noAct=findViewById(R.id.checkBox4);
        cough=findViewById(R.id.checkBox4);
        coughSome=findViewById(R.id.checkBox4);
        coughNone=findViewById(R.id.checkBox4);
        smoke=findViewById(R.id.checkBox4);
        illness=findViewById(R.id.checkBox4);
        pets=findViewById(R.id.checkBox4);
        exercise=findViewById(R.id.checkBox4);
        coldAir=findViewById(R.id.checkBox4);
        dust=findViewById(R.id.checkBox4);
        odor=findViewById(R.id.checkBox4);
        noTrigger=findViewById(R.id.checkBox4);
    }

    public void check(View v){
        String msg="Thank you for submitting!";

        // Checking the selection
        if(nightN.isChecked()){
            // if issues at night
        }
        else if(nightY.isChecked()){
            //if no issues at night
        }
        if(hardAct.isChecked()){
            //if hard activity
        }
        else if(lowAct.isChecked()){
            //if low activity
        }
        else if(noAct.isChecked()){
            //if no activity
        }
        if(cough.isChecked()){
            //if heavy cough
        }
        else if(coughSome.isChecked()){
            //if little cough
        }
        else if(coughNone.isChecked()){
            //if no cough
        }
        if(smoke.isChecked()){
            //if smoke is a trigger
        }
        if(illness.isChecked()){
            //if illness is a trigger
        }
        if(pets.isChecked()){
            //if pets is a trigger
        }
        if(exercise.isChecked()){
            //if exercise is a trigger
        }
        if(coldAir.isChecked()){
            //if cold air is a trigger
        }
        if(dust.isChecked()){
            //if dust is a trigger
        }
        if(odor.isChecked()){
            //if odors is a trigger
        }
        if(noTrigger.isChecked()){
            //if no trigger
        }


        // Executing the Toast
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();

        // Clearing all the selection
        nightN.setChecked(false);
        nightY.setChecked(false);
        hardAct.setChecked(false);
        lowAct.setChecked(false);
        noAct.setChecked(false);
        cough.setChecked(false);
        coughSome.setChecked(false);
        coughNone.setChecked(false);
        smoke.setChecked(false);
        illness.setChecked(false);
        pets.setChecked(false);
        exercise.setChecked(false);
        coldAir.setChecked(false);
        dust.setChecked(false);
        odor.setChecked(false);
        noTrigger.setChecked(false);

    }
}