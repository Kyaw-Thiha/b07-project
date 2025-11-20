package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.view.auth.LoginActivity;
import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class ChildDashboard extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getCurrentZone();
    }
    /** Called when the user touches the log medicine button*/
    public void controllerMedicine(View view)
    {
        // Do something in response to button click
        //startActivity(new Intent(getApplicationContext(), LogChildMedicine.class));
        Intent intent = new Intent(this, LogChildMedicine.class);
        startActivity(intent);
    }

    /** Called when the user touches the log symptom button*/
    public void symptomLog(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, LogChildSymptom.class);
        startActivity(intent);
    }

    /** Called when the user touches the rescue medicine button*/
    public void rescueMedicine(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, LogChildRescue.class);
        startActivity(intent);
    }

    /** Called when the user touches the settings button in child dashboard*/
    public void childSettings(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, ChildSettings.class);
        startActivity(intent);
    }

    /** Called when the user touches the badge button in child dashboard*/
    public void childBadge(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, ChildBadge.class);
        startActivity(intent);
    }

    public void signOut(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void getCurrentZone() {
        int personalBest = 5;
        int currentPEF = 7;
        if(personalBest == 0) {
            //make personalBest = currentEF
            //save this info to database
        }
        else {
            double ratio = (double)currentPEF / personalBest;
            if (ratio >= 0.8) {
                //make green zone visible
            } else if (ratio >= 0.5) {
                //make yellow zone visible
            } else {
                //make red zone visible
            }
        }
    }

}