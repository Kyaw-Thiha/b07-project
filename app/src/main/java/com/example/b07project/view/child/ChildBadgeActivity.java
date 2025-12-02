package com.example.b07project.view.child;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.Motivation;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MotivationViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ChildBadgeActivity extends BackButtonActivity {

    private MotivationViewModel motivationViewModel;
    private String childId;

    private ImageView controllerBadge;
    private TextView controllerBadgeText;
    private ImageView controllerLock;
    private TextView controllerStatus;

    private ImageView techniqueBadge;
    private TextView techniqueBadgeText;
    private ImageView techniqueLock;
    private TextView techniqueStatus;

    private ImageView rescueBadge;
    private TextView rescueBadgeText;
    private ImageView rescueLock;
    private TextView rescueStatus;

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

        childId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
        if (childId == null) {
            Toast.makeText(this, R.string.child_dashboard_no_user, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        bindViews();
        motivationViewModel = new ViewModelProvider(this).get(MotivationViewModel.class);
        motivationViewModel.getMotivation().observe(this, this::updateBadges);
        motivationViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
        motivationViewModel.loadMotivation(childId);
    }

    private void bindViews() {
        controllerBadge = findViewById(R.id.imageView8);
        controllerBadgeText = findViewById(R.id.textView20);
        controllerLock = findViewById(R.id.imageView18);
        controllerStatus = findViewById(R.id.textView25);

        techniqueBadge = findViewById(R.id.imageView14);
        techniqueBadgeText = findViewById(R.id.textView28);
        techniqueLock = findViewById(R.id.imageView20);
        techniqueStatus = findViewById(R.id.textView24);

        rescueBadge = findViewById(R.id.imageView13);
        rescueBadgeText = findViewById(R.id.textView27);
        rescueLock = findViewById(R.id.imageView19);
        rescueStatus = findViewById(R.id.textView26);
    }

    private void updateBadges(Motivation motivation) {
        if (motivation == null || motivation.getBadges() == null) {
            return;
        }
        applyBadgeState(motivation.getBadges().get("controllerWeek"),
                controllerBadge, controllerBadgeText, controllerLock, controllerStatus);
        applyBadgeState(motivation.getBadges().get("techniqueSessions"),
                techniqueBadge, techniqueBadgeText, techniqueLock, techniqueStatus);
        applyBadgeState(motivation.getBadges().get("lowRescueMonth"),
                rescueBadge, rescueBadgeText, rescueLock, rescueStatus);
    }

    private void applyBadgeState(Motivation.Badge badge,
                                 ImageView badgeImage,
                                 TextView badgeEarnedText,
                                 ImageView lockImage,
                                 TextView statusText) {
        if (badge == null) {
            showLockedState(badgeImage, badgeEarnedText, lockImage, statusText,
                    getString(R.string.child_badge_progress_placeholder));
            return;
        }

        int target = badge.getTarget();
        int progress = badge.getProgress();
        String progressText = formatProgress(progress, target);
        statusText.setText(progressText);

        if (badge.isAchieved() || (target > 0 && progress >= target)) {
            showUnlockedState(badgeImage, badgeEarnedText, lockImage, statusText);
        } else {
            showLockedState(badgeImage, badgeEarnedText, lockImage, statusText, progressText);
        }
    }

    private String formatProgress(int progress, int target) {
        if (target <= 0) {
            return getString(R.string.child_badge_progress_no_target, progress);
        }
        return getString(R.string.child_badge_progress_template, progress, target);
    }

    private void showUnlockedState(ImageView badgeImage, TextView badgeText,
                                   ImageView lockImage, TextView statusText) {
        badgeImage.setVisibility(ImageView.VISIBLE);
        badgeText.setVisibility(TextView.VISIBLE);
        lockImage.setVisibility(ImageView.INVISIBLE);
        statusText.setVisibility(TextView.INVISIBLE);
    }

    private void showLockedState(ImageView badgeImage, TextView badgeText,
                                 ImageView lockImage, TextView statusText,
                                 String statusMessage) {
        badgeImage.setVisibility(ImageView.INVISIBLE);
        badgeText.setVisibility(TextView.INVISIBLE);
        lockImage.setVisibility(ImageView.VISIBLE);
        statusText.setVisibility(TextView.VISIBLE);
        statusText.setText(statusMessage);
    }
}
