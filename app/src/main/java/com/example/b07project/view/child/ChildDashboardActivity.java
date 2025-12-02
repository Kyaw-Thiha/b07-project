package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.Motivation;
import com.example.b07project.model.PEF;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.view.login.AskUsertypeActivity;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.example.b07project.viewModel.MotivationViewModel;
import com.example.b07project.viewModel.PEFViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildDashboardActivity extends BackButtonActivity {
  private ChildProfileViewModel childProfileViewModel;
  private PEFViewModel pefViewModel;
  private MotivationViewModel motivationViewModel;

  private ImageView greenZoneView;
  private ImageView yellowZoneView;
  private ImageView redZoneView;
  private TextView pefTextView;
  private TextView controllerStreakView;
  private TextView techniqueStreakView;
  private ProgressBar loadingIndicator;

  private String childId;
  private Integer personalBest;
  private String currentZone;
  private boolean childLoaded;
  private boolean pefLoaded;
  private boolean motivationLoaded;

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

    childId = FirebaseAuth.getInstance().getCurrentUser() != null
        ? FirebaseAuth.getInstance().getCurrentUser().getUid()
        : null;
    if (childId == null) {
      Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    initViews();
    initViewModels();
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

  private void initViews() {
    greenZoneView = findViewById(R.id.imageView9);
    yellowZoneView = findViewById(R.id.imageView10);
    redZoneView = findViewById(R.id.imageView11);
    pefTextView = findViewById(R.id.textView32);
    controllerStreakView = findViewById(R.id.textView33);
    techniqueStreakView = findViewById(R.id.textView38);
    loadingIndicator = findViewById(R.id.dashboardProgress);
    showLoading(true);
  }

  private void initViewModels() {
    childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
    pefViewModel = new ViewModelProvider(this).get(PEFViewModel.class);
    motivationViewModel = new ViewModelProvider(this).get(MotivationViewModel.class);

    childProfileViewModel.getChild().observe(this, this::handleChildProfile);
    childProfileViewModel.getErrorMessage().observe(this, error -> {
      if (error != null && !error.isEmpty()) {
        handleFirstLoadFlag(LoadFlag.CHILD);
        showToast(error);
      }
    });
    childProfileViewModel.loadChild(childId);

    pefViewModel.getPEF().observe(this, this::handlePefEntries);
    pefViewModel.getPEFError().observe(this, error -> {
      if (error != null && !error.isEmpty()) {
        handleFirstLoadFlag(LoadFlag.PEF);
        showToast(error);
      }
    });
    pefViewModel.loadPEFByUser(childId);

    motivationViewModel.getMotivation().observe(this, this::handleMotivation);
    motivationViewModel.getErrorMessage().observe(this, error -> {
      if (error != null && !error.isEmpty()) {
        handleFirstLoadFlag(LoadFlag.MOTIVATION);
        showToast(error);
      }
    });
    motivationViewModel.loadMotivation(childId);
  }

  private void handleChildProfile(ChildUser child) {
    if (child == null) {
      showToast(getString(R.string.child_dashboard_no_profile));
    } else {
      personalBest = child.getPersonalBest();
      currentZone = child.getCurrentZone();
      applyZone(currentZone);
    }
    handleFirstLoadFlag(LoadFlag.CHILD);
  }

  private void handlePefEntries(List<PEF> entries) {
    if (entries == null || entries.isEmpty()) {
      pefTextView.setText(getString(R.string.child_dashboard_pef_placeholder));
      applyZone(currentZone);
      handleFirstLoadFlag(LoadFlag.PEF);
      return;
    }
    PEF latest = Collections.max(entries, Comparator.comparingLong(PEF::getTime));
    float value = pickDisplayValue(latest);
    if (value <= 0f) {
      pefTextView.setText(getString(R.string.child_dashboard_pef_placeholder));
      handleFirstLoadFlag(LoadFlag.PEF);
      return;
    }
    pefTextView.setText(String.valueOf(Math.round(value)));
    evaluateZoneFromPef(Math.round(value));
    handleFirstLoadFlag(LoadFlag.PEF);
  }

  private void handleMotivation(Motivation motivation) {
    long controllerStreak = extractStreak(motivation, "controller", "medicineLog");
    long techniqueStreak = extractStreak(motivation, "technique", "techniquePractice");
    controllerStreakView.setText(String.valueOf(controllerStreak));
    techniqueStreakView.setText(String.valueOf(techniqueStreak));
    handleFirstLoadFlag(LoadFlag.MOTIVATION);
  }

  private void evaluateZoneFromPef(int latestValue) {
    boolean pbUpdated = false;
    int pb = personalBest != null ? personalBest : 0;
    if (pb <= 0 || latestValue > pb) {
      pb = latestValue;
      personalBest = latestValue;
      pbUpdated = true;
    }

    String newZone = computeZone(latestValue, pb);
    boolean zoneChanged = newZone != null && !newZone.equals(currentZone);
    applyZone(newZone);

    if ((pbUpdated || zoneChanged) && childProfileViewModel != null) {
      Map<String, Object> updates = new HashMap<>();
      if (pbUpdated) {
        updates.put("personalBest", pb);
      }
      if (zoneChanged) {
        updates.put("currentZone", newZone);
        currentZone = newZone;
      }
      childProfileViewModel.updateChild(childId, updates);
    }
  }

  private float pickDisplayValue(PEF pef) {
    if (pef == null) {
      return 0f;
    }
    if (pef.getPost_med() > 0) {
      return pef.getPost_med();
    }
    if (pef.getPre_med() > 0) {
      return pef.getPre_med();
    }
    return 0f;
  }

  private String computeZone(float currentValue, int pb) {
    if (pb <= 0) {
      return "green";
    }
    double ratio = currentValue / pb;
    if (ratio >= 0.8) {
      return "green";
    } else if (ratio >= 0.5) {
      return "yellow";
    }
    return "red";
  }

  private void applyZone(String zone) {
    if (greenZoneView == null || yellowZoneView == null || redZoneView == null) {
      return;
    }
    greenZoneView.setVisibility(View.INVISIBLE);
    yellowZoneView.setVisibility(View.INVISIBLE);
    redZoneView.setVisibility(View.INVISIBLE);

    if (zone == null) {
      return;
    }
    switch (zone) {
      case "green":
        greenZoneView.setVisibility(View.VISIBLE);
        break;
      case "yellow":
        yellowZoneView.setVisibility(View.VISIBLE);
        break;
      case "red":
        redZoneView.setVisibility(View.VISIBLE);
        break;
      default:
        break;
    }
  }

  private long extractStreak(Motivation motivation, String... keys) {
    if (motivation == null || motivation.getStreaks() == null) {
      return 0;
    }
    for (String key : keys) {
      Motivation.Streak streak = motivation.getStreaks().get(key);
      if (streak != null) {
        return streak.getCurrent();
      }
    }
    return 0;
  }

  private void showToast(String message) {
    if (message == null || message.isEmpty()) {
      return;
    }
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  private void showLoading(boolean show) {
    if (loadingIndicator != null) {
      loadingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }
  }

  private void handleFirstLoadFlag(LoadFlag flag) {
    switch (flag) {
      case CHILD:
        if (!childLoaded) {
          childLoaded = true;
          dismissLoadingIfReady();
        }
        break;
      case PEF:
        if (!pefLoaded) {
          pefLoaded = true;
          dismissLoadingIfReady();
        }
        break;
      case MOTIVATION:
        if (!motivationLoaded) {
          motivationLoaded = true;
          dismissLoadingIfReady();
        }
        break;
      default:
        break;
    }
  }

  private void dismissLoadingIfReady() {
    if (childLoaded && pefLoaded && motivationLoaded) {
      showLoading(false);
    }
  }

  private enum LoadFlag {
    CHILD,
    PEF,
    MOTIVATION
  }

}
