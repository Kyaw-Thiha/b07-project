package com.example.b07project.view.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.child.LogChildMedicineActivity;
import com.example.b07project.view.child.PefEntryActivity;
import com.example.b07project.view.common.BackButtonActivity;

public class ParentDashboardActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Checks if the toggle on dashboard is pressed
        ToggleButton togglebutton = findViewById(R.id.toggleButton);
        togglebutton.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                // Show provider sees the 30 day chart
            }
            else{
                //Show provider sees teh 7 day chart(default)
            }

        });
    }
    public void manage_child(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, ManageChildActivity.class);
        startActivity(intent);
    }

    public void Inventory(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }
    public void medicine_log(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, LogChildMedicineActivity.class);
        startActivity(intent);
    }
    public void notifications(View view)
    {
        // Do something in response to button click
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }
    public void peak_flow(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this,PefEntryActivity.class);
        startActivity(intent);
    }

    public void incident_log(View view){
        Intent intent = new Intent(this, IncidentLogActivity.class);
        startActivity(intent);
    }

    public void provider(View view){
        Intent intent = new Intent(this, InviteProviderActivity.class);
        startActivity(intent);
    }

    public void toggle_chart_clicked(View view)
    {
        ToggleButton toggle_chart = (ToggleButton) view;
        if (toggle_chart.isChecked()){
            // show chart(30 days)
        }
        else{
          // show chart(7days)
        }

    }
    public void add_action_plan(View view)
    { Intent intent = new Intent(this,AddActionPlan.class);
        startActivity(intent);
    }

    Button chooseChild = findViewById(R.id.chooseChild);
    // TODO


    }