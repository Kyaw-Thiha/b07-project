package com.example.b07project.view.parent;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AddActionPlan extends BackButtonActivity {
    TextInputEditText green_zone,yellow_zone,red_zone;
    Button add_action_plan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_add_action_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        green_zone = findViewById(R.id.green_zone);
        yellow_zone = findViewById(R.id.yellow_zone);
        red_zone = findViewById(R.id.red_zone);
        add_action_plan = findViewById(R.id.action_plan_button);
        // updates action plan when the button is clicked
        add_action_plan.setOnClickListener(v->updateActionPlan());
    }

    // saves action plan
    private void updateActionPlan(){
        String green_zone_instructions = green_zone.getText().toString().trim();
        String yellow_zone_instructions = yellow_zone.getText().toString().trim();
        String red_zone_instructions = red_zone.getText().toString().trim();

        if(green_zone_instructions.isEmpty()){
            green_zone.setError("Instructions are required");
        }
        if(yellow_zone_instructions.isEmpty()){
            yellow_zone.setError("Instructions are required");
        }
        if(red_zone_instructions.isEmpty()) {
            red_zone.setError("Instructions are required");
        }

        // Firebase stuff TODO
    }
}
