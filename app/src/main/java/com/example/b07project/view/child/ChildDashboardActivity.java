package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.PefEntryActivity;
import com.example.b07project.R;
import com.example.b07project.TechniqueHelperActivity;
import com.example.b07project.view.login.AskLoginSignupActivity;
import com.example.b07project.view.login.AskUsertypeActivity;
import com.example.b07project.view.login.LoginActivity;
import com.example.b07project.view.common.BackButtonActivity;

public class ChildDashboardActivity extends BackButtonActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_child_dashboard);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    getCurrentZone();
    getCurrentPEF();
    getControllerCounter();
    getTechniqueCounter();
  }

  /**
   * Called when the user touches the log medicine button to go to log medicine
   * page
   */
  public void controllerMedicine(View view) {
    Intent intent = new Intent(this, LogChildMedicineActivity.class);
    startActivity(intent);
  }

  /**
   * Called when the user touches the log symptom button to go to check in page
   */
  public void symptomLog(View view) {
    Intent intent = new Intent(this, LogChildSymptomActivity.class);
    startActivity(intent);
  }

  /** Called when the user touches the rescue medicine button to start triage */
  public void rescueMedicine(View view) {
    Intent intent = new Intent(this, LogChildRescueActivity.class);
    startActivity(intent);
  }

  /**
   * Called when the user touches the settings button in child dashboard to go to
   * child settings
   */
  public void techniqueHelp(View view) {
    Intent intent = new Intent(this, TechniqueHelperActivity.class);
    startActivity(intent);
  }

  /**
   * Called when the user touches the settings button in child dashboard to go to
   * child settings
   */
  public void enterPEF(View view) {
    Intent intent = new Intent(this, PefEntryActivity.class);
    startActivity(intent);
  }

  /**
   * Called when the user touches the settings button in child dashboard to go to
   * child settings
   */
  public void childSettings(View view) {
    Intent intent = new Intent(this, ChildSettingsActivity.class);
    startActivity(intent);
  }

  /**
   * Called when the user touches the badge button in child dashboard to view
   * badges page
   */
  public void childBadge(View view) {
    Intent intent = new Intent(this, ChildBadgeActivity.class);
    startActivity(intent);
  }

  /**
   * Called when the user touches the sign out button in child dashboard to take
   * user back to askusertype activity and blocks them from going back
   */
  public void signOut(View view) {
    Intent intent = new Intent(this, AskUsertypeActivity.class);
    startActivity(intent);
  }

  /** gets the PB and PEF from firebase */
  public void getCurrentZone() {
    int personalBest = 5;// get PB from firebase
    int currentPEF = 7;// get PEF from firebase
    ImageView red, yellow, green;
    green = findViewById(R.id.imageView9);
    yellow = findViewById(R.id.imageView10);
    red = findViewById(R.id.imageView11);

    if (personalBest == 0) {
      // make personalBest = currentPEF
      // save this info to database
      // make green zone component visible
      green.setVisibility(View.VISIBLE);
      yellow.setVisibility(View.INVISIBLE);
      red.setVisibility(View.INVISIBLE);
    } else {
      double ratio = (double) currentPEF / (double) personalBest;
      if (ratio >= 0.8) {
        // make green zone image visible
        green.setVisibility(View.VISIBLE);
        yellow.setVisibility(View.INVISIBLE);
        red.setVisibility(View.INVISIBLE);
      } else if (ratio >= 0.5) {
        // make yellow zone image visible
        green.setVisibility(View.INVISIBLE);
        yellow.setVisibility(View.VISIBLE);
        red.setVisibility(View.INVISIBLE);
      } else {
        // make red zone image visible
        green.setVisibility(View.INVISIBLE);
        yellow.setVisibility(View.INVISIBLE);
        red.setVisibility(View.VISIBLE);
      }
    }
  }

  public void getCurrentPEF() {
    // pull pef from firebase set default to 0
    String pef = "2";
    // set textview component for PEF to this value
    TextView pefText = findViewById(R.id.textView32);
    pefText.setText(pef);
  }

  public void getControllerCounter() {
    // pull pef from firebase set default to 0
    String controller = "4";
    // set textview component for controller streaks to this value
    TextView controllerText = findViewById(R.id.textView33);
    controllerText.setText(controller);
  }

  public void getTechniqueCounter() {
    // pull pef from firebase set default to 0
    String technique = "6";
    // set textview component for technique streaks to this value
    TextView techniqueText = findViewById(R.id.textView38);
    techniqueText.setText(technique);
  }

}
