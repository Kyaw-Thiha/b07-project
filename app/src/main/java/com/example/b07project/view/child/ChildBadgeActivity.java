package com.example.b07project.view.child;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class ChildBadgeActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_badge);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getController();
        getTechnique();
        getRescue();
    }

    public void getController(){
        //get controller streak from firebase
        String controllerStreak="8";

        //get configured threshold numbers from database
        int controllerThreshold= 10;

        //get image view and text view components
        ImageView badge=findViewById(R.id.imageView8);
        ImageView badgeText=findViewById(R.id.textView20);
        TextView badgeLock=findViewById(R.id.imageView18);
        TextView noBadgeText=findViewById(R.id.textView25);

        if(Integer.parseInt(controllerStreak)>=controllerThreshold) {
            //then make badge with text visible & lock with text  invisible
            badge.setVisibility(View.VISIBLE);
            badgeText.setVisibility(View.VISIBLE);
            badgeLock.setVisibility(View.INVISIBLE);
            noBadgeText.setVisibility(View.INVISIBLE);
        }
        else{
            badge.setVisibility(View.INVISIBLE);
            badgeText.setVisibility(View.INVISIBLE);
            badgeLock.setVisibility(View.VISIBLE);
            noBadgeText.setVisibility(View.VISIBLE);
        }

    }

    public void getTechnique(){
        String techniqueStreak="20";//get technique streak from database
        int techniqueThreshold= 15; // get threshold from data base

        //get image view and text view components
        ImageView badge=findViewById(R.id.imageView14);
        ImageView badgeText=findViewById(R.id.textView28);
        TextView badgeLock=findViewById(R.id.imageView20);
        TextView noBadgeText=findViewById(R.id.textView24);

        if(Integer.parseInt(techniqueStreak)>=techniqueThreshold) {
            //then make badge with text visible & lock with text  invisible
            badge.setVisibility(View.VISIBLE);
            badgeText.setVisibility(View.VISIBLE);
            badgeLock.setVisibility(View.INVISIBLE);
            noBadgeText.setVisibility(View.INVISIBLE);
        }
        else {
            badge.setVisibility(View.INVISIBLE);
            badgeText.setVisibility(View.INVISIBLE);
            badgeLock.setVisibility(View.VISIBLE);
            noBadgeText.setVisibility(View.VISIBLE);
        }

    }

    public void getRescue(){
            String rescueCount = "3";//get rescue count from database
            int rescueThreshold = 4;// get threshold from data base

            //get image view and text view components
            ImageView badge=findViewById(R.id.imageView13);
            ImageView badgeText=findViewById(R.id.textView27);
            TextView badgeLock=findViewById(R.id.imageView19);
            TextView noBadgeText=findViewById(R.id.textView26);

            if(Integer.parseInt(rescueCount)>=rescueThreshold) {
                //then make badge with text visible & lock with text  invisible
                badge.setVisibility(View.VISIBLE);
                badgeText.setVisibility(View.VISIBLE);
                badgeLock.setVisibility(View.INVISIBLE);
                noBadgeText.setVisibility(View.INVISIBLE);
            }
            else{
                badge.setVisibility(View.INVISIBLE);
                badgeText.setVisibility(View.INVISIBLE);
                badgeLock.setVisibility(View.VISIBLE);
                noBadgeText.setVisibility(View.VISIBLE);
            }
    }
}