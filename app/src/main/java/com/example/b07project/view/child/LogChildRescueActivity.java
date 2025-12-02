package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.view.child.RescueDecisionActivity;
import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class LogChildRescueActivity extends BackButtonActivity {
  CheckBox speak, noSpeak, chestPulls, noChestPulls, blueLips, noBlueLips, recentRescue, noRescue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_log_child_rescue);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
  }

  public void rescueDecision(View view) {
      speak = findViewById(R.id.checkBox17);
      noSpeak = findViewById(R.id.checkBox18);
      chestPulls = findViewById(R.id.checkBox19);
      noChestPulls = findViewById(R.id.checkBox20);
      blueLips = findViewById(R.id.checkBox21);
      noBlueLips = findViewById(R.id.checkBox22);
      recentRescue = findViewById(R.id.checkBox23);
      noRescue = findViewById(R.id.checkBox24);
    // add info entered by child user into incident logs
      if(speak.isChecked()){
          //add to database that they can speak
      }
      if(noSpeak.isChecked()){
        //add to database that they cant speak
      }
      if(chestPulls.isChecked()){
          //add chestpulls
      }
      if(noChestPulls.isChecked()){
        //add no chestpulls
      }
      if(blueLips.isChecked()){

      }
      if(noBlueLips.isChecked()){

      }
      if(recentRescue.isChecked()){

      }
      if(noRescue.isChecked()){

      }
    Intent intent = new Intent(this, RescueDecisionActivity.class);
    startActivity(intent);
  }
}
