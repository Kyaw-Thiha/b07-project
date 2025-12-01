package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;

import com.example.b07project.view.common.BackButtonActivity;

public class LogChildMedicineActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_child_medicine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get three most recent doses and times
        //get the database
        controllerDose1(5, "2:30");
        controllerDose2(5, "2:30");
        controllerDose3(5, "2:30");
        rescueDose1(5, "2:30");
        rescueDose2(5, "2:30");
        rescueDose3(5, "2:30");
    }

    public void addDose(View view){
        Intent intent = new Intent(this, DoseCheckActivity.class);
        //start dose check activity remembering the previous state
        intent.putExtra("previous_activity", "LogChildMedicineActivity");
        startActivity(intent);

    }

    public void controllerDose1(int dose, String time){
        TextView firstDose = findViewById(R.id.textView116);
        TextView firstTime = findViewById(R.id.textView92);
    }

    public void controllerDose2(int dose, String time){
        TextView secondDose = findViewById(R.id.textView90);
        TextView secondTime = findViewById(R.id.textView115);
    }

    public void controllerDose3(int dose, String time){
        TextView thirdDose = findViewById(R.id.textView117);
        TextView thirdTime = findViewById(R.id.textView91);
    }

    public void rescueDose1(int dose, String time){
        TextView firstDose = findViewById(R.id.textView83);
        TextView firstTime = findViewById(R.id.textView82);
    }

    public void rescueDose2(int dose, String time){
        TextView secondDose = findViewById(R.id.textView85);
        TextView secondTime = findViewById(R.id.textView84);
    }

    public void rescueDose3(int dose, String time){
        TextView thirdDose = findViewById(R.id.textView86);
        TextView thirdTime = findViewById(R.id.textView89);
    }
}
