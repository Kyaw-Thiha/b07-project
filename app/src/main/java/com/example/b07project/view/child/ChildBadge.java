package com.example.b07project.view.child;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class ChildBadge extends BackButtonActivity {

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
        getBadges();
    }

    public void getBadges(){
        //if controller streak>=7
            //then make badge with text visible & lock with text  invisible
        //if sessions taken>=10(configurable)
            //the make badge with text visible & lock with text  invisible
        //if <=4 rescues in the month
        //then make badge with text visible & lock with text  invisible
    }
}