package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class DoseCheckActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dose_check);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String previous = getIntent().getStringExtra("previous_activity");

        if ("LogChildMedicineActivity".equals(previous)) {
            //make pre-dose text visible
            TextView preDose=findViewById(R.id.textView36);
            TextView postDose=findViewById(R.id.textView37);
            preDose.setVisibility(View.VISIBLE);
            postDose.setVisibility(View.INVISIBLE);
            //make pre dose button visible and clickable
            Button preDoseButton=findViewById(R.id.button12);
            Button postDoseButton=findViewById(R.id.invite_button);
            preDoseButton.setVisibility(View.VISIBLE);
            postDoseButton.setVisibility(View.GONE);
        } else if ("ControllerMedicineInputActivity".equals(previous)) {
            //make post-dose text visible
            TextView preDose=findViewById(R.id.textView36);
            TextView postDose=findViewById(R.id.textView37);
            preDose.setVisibility(View.INVISIBLE);
            postDose.setVisibility(View.VISIBLE);
            //make post dose button visible and clickable
            Button preDoseButton=findViewById(R.id.button12);
            Button postDoseButton=findViewById(R.id.button13);
            preDoseButton.setVisibility(View.GONE);
            postDoseButton.setVisibility(View.VISIBLE);
        }
    }

    public void betterDose(View view){
        TextView preDose=findViewById(R.id.textView36);
        int preDoseVisible = preDose.getVisibility();
        String msg = "Its great that you feel better!";
        //add pre dose check in if pre-dose button visible
        if(preDoseVisible==View.VISIBLE){
                //add  that the child reported better predose

        }
        else{
            //add post dose check in if postdose button visible
            //add the child reported better postdose

        }
        //make a toast saying predose check
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    public void normalDose(View view){
        TextView preDose=findViewById(R.id.textView36);
        int preDoseVisible = preDose.getVisibility();
        String msg = "It's okay to feel normal!";
        //add pre dose check in if pre-dose button visible
        if(preDoseVisible==View.VISIBLE){
            //add  that the child reported better predose

        }
        else{
            //add post dose check in if postdose button visible
            //add the child reported better postdose

        }
        //make a toast saying predose check
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    public void worseDose(View view){
        TextView preDose=findViewById(R.id.textView36);
        int preDoseVisible = preDose.getVisibility();
        String msg;
        //add pre dose check in if pre-dose button visible
        if(preDoseVisible==View.VISIBLE){
            //add  that the child reported better predose
            msg = "Let's see you feel better after!";
        }
        else{
            //add post dose check in if postdose button visible
            //add the child reported better postdose
            msg = "Your parent has been reported of worse condition!";
        }
        //make a toast saying predose check
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    public void preDoseCheck(View view){
        //go to controller medicine input
        Intent intent = new Intent(this, ControllerMedicineInputActivity.class);
        startActivity(intent);
    }

    public void postDoseCheck(View view){
        //go back to dashboard after saving
        Intent intent = new Intent(this, ChildDashboardActivity.class);
        startActivity(intent);
    }
}