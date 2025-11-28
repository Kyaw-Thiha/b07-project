package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.view.child.ChildDashboardActivity;

import com.example.b07project.view.common.BackButtonActivity;

public class PefEntryActivity extends BackButtonActivity {

    //TextView pefText = findViewById(R.id.textView50);
    //TextView preDoseText = findViewById(R.id.textView51);
    //TextView postDoseText = findViewById(R.id.textView52);
    //TextInputLayout pefInputLayout = findViewById(R.id.inputLayout1);
    //TextInputLayout preDoseInputLayout = findViewById(R.id.inputLayout2);
    //TextInputLayout postDoseInputLayout = findViewById(R.id.inputLayout3);
    //TextInputEditText pefInput = findViewById(R.id.editText4);
    //TextInputEditText preDoseInput = findViewById(R.id.editText3);
    //TextInputEditText postDoseInput = findViewById(R.id.editText5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pef_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView singlePefBlock = findViewById(R.id.textView53);
        TextView bothPefBlock = findViewById(R.id.textView54);
        singlePefBlock.setVisibility(View.VISIBLE);
        bothPefBlock.setVisibility(View.VISIBLE);
    }

    public void singlePEF(View view){
        TextView singlePefBlock = findViewById(R.id.textView53);
        TextView bothPefBlock = findViewById(R.id.textView54);
        singlePefBlock.setVisibility(View.INVISIBLE);
        bothPefBlock.setVisibility(View.VISIBLE);
    }

    public void bothPEF(View view){
        TextView singlePefBlock = findViewById(R.id.textView53);
        TextView bothPefBlock = findViewById(R.id.textView54);
        bothPefBlock.setVisibility(View.INVISIBLE);
        singlePefBlock.setVisibility(View.VISIBLE);
    }

    public void pefSubmit(View view){
        //add pef to firebase through repository
        //go to dashboard
        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);
    }
}