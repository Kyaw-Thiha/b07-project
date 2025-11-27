package com.example.b07project.view.parent;

import android.os.Bundle;
import android.widget.ToggleButton;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

import android.widget.ToggleButton;

public class ManageChildActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_manage_child);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Checks if the toggle for rescue log is on
        ToggleButton toggle_rescue_logs = findViewById(R.id.toggle_rescue_logs);
        toggle_rescue_logs.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                // Show provider the rescue log information
            }
            else{
                //Show provider nothing
            }

        });
        // Checks if the toggle for controller adherence is on
        ToggleButton toggle_controller_adherence = findViewById(R.id.toggle_controller_adherence);
        toggle_controller_adherence.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                // Show provider the controller adherence
            }
            else{
                //Show provider nothing
            }

        });
        // Checks if the toggle for symptoms is on
        ToggleButton toggle_symptoms = findViewById(R.id.toggle_symptoms);
        toggle_symptoms.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                // Show provider the toggle symptoms
            }
            else{
                //Show provider nothing
            }

        });
        // Checks if the toggle for peak flow is on
        ToggleButton toggle_peak_flow = findViewById(R.id.toggle_peak_flow);
        toggle_peak_flow.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                // Show provider the peak flow
            }
            else{
                //Show provider nothing
            }

        });
        // Checks if the toggle for peak flow is on
        ToggleButton toggle_triage_incidents = findViewById(R.id.toggle_triage_incidents);
        toggle_triage_incidents.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked) {
                // Show provider the triange incidents
            }
            else{
                //Show provider nothing
            }

        });
        // Checks if the toggle for summary charts is on
        ToggleButton toggle_summary_charts= findViewById(R.id.toggle_summary_charts);
        toggle_summary_charts.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked) {
                // Show provider the triange incidents
            }
            else{
                    //Show provider nothing
                }
        });
    }
}