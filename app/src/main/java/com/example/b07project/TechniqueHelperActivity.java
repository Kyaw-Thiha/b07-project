package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.view.child.ChildDashboardActivity;
import com.example.b07project.view.common.BackButtonActivity;

public class TechniqueHelperActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_technique_helper);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView stepTwo = findViewById(R.id.textView61);
        TextView stepThree = findViewById(R.id.textView65);
        TextView stepTwoInhaler = findViewById(R.id.textView62);
        TextView stepTwoMask = findViewById(R.id.textView64);
        TextView stepThreeMask = findViewById(R.id.textView66);

        stepTwo.setVisibility(View.INVISIBLE);
        stepThree.setVisibility(View.INVISIBLE);
        stepTwoInhaler.setVisibility(View.INVISIBLE);
        stepTwoMask.setVisibility(View.INVISIBLE);
        stepThreeMask.setVisibility(View.INVISIBLE);
    }

    public void inhaler(View view){
        TextView stepTwo = findViewById(R.id.textView61);
        TextView stepThree = findViewById(R.id.textView65);
        TextView stepTwoInhaler = findViewById(R.id.textView62);
        TextView stepTwoMask = findViewById(R.id.textView64);
        TextView stepThreeMask = findViewById(R.id.textView66);
        stepTwo.setVisibility(View.VISIBLE);
        stepTwoInhaler.setVisibility(View.VISIBLE);
        stepThree.setVisibility(View.INVISIBLE);
        stepTwoMask.setVisibility(View.INVISIBLE);
        stepThreeMask.setVisibility(View.INVISIBLE);
    }
    public void inhalerMask(View view){
        TextView stepTwo = findViewById(R.id.textView61);
        TextView stepThree = findViewById(R.id.textView65);
        TextView stepTwoInhaler = findViewById(R.id.textView62);
        TextView stepTwoMask = findViewById(R.id.textView64);
        TextView stepThreeMask = findViewById(R.id.textView66);
        stepTwo.setVisibility(View.VISIBLE);
        stepTwoInhaler.setVisibility(View.INVISIBLE);
        stepThree.setVisibility(View.VISIBLE);
        stepTwoMask.setVisibility(View.VISIBLE);
        stepThreeMask.setVisibility(View.VISIBLE);
    }

    public void techniqueTwo(View view){
        Intent intent = new Intent(this, SecondTechniqueHelperActivity.class);
        startActivity(intent);
    }
}