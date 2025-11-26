package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.view.child.ChildDashboardActivity;
import com.example.b07project.view.common.BackButtonActivity;

public class RescueDecisionActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rescue_decision);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void rescueEmergency(View view){
        //send alert to parent
        //show toast for alerting parent
        String msg = "Your parent has been alerted of emergency!";
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        //go back to dashboard
        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);
    }

    public void rescueActionPlan(View view){
        //go to technique helper activity
        Intent intent = new Intent(this, TechniqueHelperActivity.class);
        startActivity(intent);
    }
}