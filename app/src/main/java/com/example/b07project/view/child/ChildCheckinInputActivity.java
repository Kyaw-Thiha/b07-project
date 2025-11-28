package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class ChildCheckinInputActivity extends BackButtonActivity {
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
        hardAct=findViewById(R.id.checkBox4);
        lowAct=findViewById(R.id.checkBox3);
        noAct=findViewById(R.id.checkBox5);
        cough=findViewById(R.id.checkBox7);
        coughSome=findViewById(R.id.checkBox6);
        coughNone=findViewById(R.id.checkBox8);
        smoke=findViewById(R.id.checkBox10);
        illness=findViewById(R.id.checkBox12);
        pets=findViewById(R.id.checkBox14);
        exercise=findViewById(R.id.checkBox9);
        coldAir=findViewById(R.id.checkBox11);
        dust=findViewById(R.id.checkBox13);
        odor=findViewById(R.id.checkBox15);
        noTrigger=findViewById(R.id.checkBox16);
    }

    public void submit(View view){
        String msg="Thank you for checking in!";

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

        // Clearing all the selections made by user
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

        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);

    }
}