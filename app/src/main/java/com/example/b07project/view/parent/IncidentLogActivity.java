package com.example.b07project.view.parent;

import android.os.Bundle;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class IncidentLogActivity extends BackButtonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_incident_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;

                });
        // Checks if the toggle for rescue log is on
        ToggleButton toggle_firstQuestion = findViewById(R.id.toggle_firstQuestion);
        /*child said yes*/
       /* if(){
            toggle_firstQuestion.setChecked(true);
        }
        else{
            toggle_firstQuestion.setChecked(false);
        }


        ToggleButton toggle_secondQuestion = findViewById(R.id.toggle_secondQuestion);
        /*child said yes*/
        /*if(){
            toggle_secondQuestion.setChecked(true);
        }
        else{
            toggle_secondQuestion.setChecked(false);
        }

        ToggleButton toggle_thirdQuestion = findViewById(R.id.toggle_userResponse);
        /*child said yes*/
        /*if(){
            toggle_thirdQuestion.setChecked(true);
        }
        else{
            toggle_thirdQuestion.setChecked(false);
        }

        ToggleButton toggle_userResponse = findViewById(R.id.toggle_userResponse);
        /*the child calls 911*/
        /*if(){
            toggle_userResponse.setChecked(true);
        }
        else{
            toggle_thirdQuestion.setChecked(false);
        }
        ToggleButton toggle_shareProvider = findViewById(R.id.toggle_shareProvider);
        toggle_shareProvider.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Share information with provider
            } else {
                //Show provider nothing
            }
        });*/
        // TODO other information


    }
}
