package com.example.b07project.view.common;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.b07project.R;

import java.util.ArrayList;
import java.util.List;

public abstract class OnboardingActivity extends BackButtonActivity {

    // use to ensure each page is first time opened
    protected abstract String getOnboardingPrefKey();

    // call addStep(...)，then startOnboardingSequence()
    protected abstract void showOnboarding();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * only run showOnboarding() when they first time log in
     */
    protected void runOnboardingIfFirstTime() {
        SharedPreferences prefs = getSharedPreferences("onboarding", MODE_PRIVATE);
        String key = getOnboardingPrefKey();
        if (key == null) return;

        boolean firstTime = prefs.getBoolean(key, true);

        if (firstTime) {
            steps.clear();
            showOnboarding();
            startOnboardingSequence();
            prefs.edit().putBoolean(key, false).apply();
        }
    }

    // ======================
    // breathing + Tooltip
    // ======================

    protected void startBreathing(View v) {
        if (v == null) return;
        Animation breathing = AnimationUtils.loadAnimation(this, R.anim.breathing);
        v.startAnimation(breathing);
    }

    protected void showTooltip(View anchor, String text) {
        if (anchor == null) return;

        View tooltipView = getLayoutInflater().inflate(R.layout.view_tooltip, null);
        TextView textView = tooltipView.findViewById(R.id.textTooltip);
        textView.setText(text);

        final PopupWindow popup = new PopupWindow(
                tooltipView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

        popup.setBackgroundDrawable(
                ContextCompat.getDrawable(this, android.R.color.transparent)
        );
        popup.setOutsideTouchable(true);

        tooltipView.post(() -> {
            int xOffset = anchor.getWidth();
            int yOffset = -anchor.getHeight() / 2;
            popup.showAsDropDown(anchor, xOffset, yOffset);
        });

        tooltipView.postDelayed(popup::dismiss, 1800);
    }

    // ======================
    // 多步骤 Onboarding 系统
    // ======================

    private static class OnboardingStep {
        final View anchor;
        final String message;

        OnboardingStep(View anchor, String message) {
            this.anchor = anchor;
            this.message = message;
        }
    }

    private final List<OnboardingStep> steps = new ArrayList<>();
    private int currentStep = 0;

    /**
     * 子类调用：addStep(view, "说明文案");
     */
    protected void addStep(View anchor, String message) {
        if (anchor != null) {
            steps.add(new OnboardingStep(anchor, message));
        }
    }

    /**
     * 子类一般不用 override，直接在 showOnboarding() 里最后调用即可
     */
    protected void startOnboardingSequence() {
        if (steps.isEmpty()) return;
        currentStep = 0;
        playStep(currentStep);
    }

    private void playStep(int index) {
        if (index >= steps.size()) return;

        OnboardingStep step = steps.get(index);

        // 动画 + Tooltip
        startBreathing(step.anchor);
        showTooltip(step.anchor, step.message);

        // 下一步
        step.anchor.postDelayed(() -> {
            currentStep++;
            if (currentStep < steps.size()) {
                playStep(currentStep);
            }
        }, 2000);
    }
}
