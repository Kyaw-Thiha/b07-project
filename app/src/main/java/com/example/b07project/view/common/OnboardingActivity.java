package com.example.b07project.view.common;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.b07project.R;

public abstract class OnboardingActivity extends BackButtonActivity{
    protected abstract String getOnboardingPrefKey();
    protected abstract void showOnboarding();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void runOnboardingIfFirstTime() {
        SharedPreferences prefs = getSharedPreferences("onboarding", MODE_PRIVATE);
        String key = getOnboardingPrefKey();
        boolean firstTime = prefs.getBoolean(key, true);

        if (firstTime) {
            showOnboarding();
            prefs.edit().putBoolean(key, false).apply();
        }
    }
    protected void startBreathing(View v) {
        Animation breathing = AnimationUtils.loadAnimation(this, R.anim.breathing);
        v.startAnimation(breathing);
    }
    protected void showTooltip(View anchor, String text) {
        View tooltipView = getLayoutInflater().inflate(R.layout.view_tooltip, null);
        TextView textView = tooltipView.findViewById(R.id.textTooltip);
        textView.setText(text);

        final PopupWindow popup = new PopupWindow(
                tooltipView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

        popup.setBackgroundDrawable(getDrawable(android.R.color.transparent));
        popup.setOutsideTouchable(true);

        tooltipView.post(() -> {
            int xOffset = anchor.getWidth();
            int yOffset = -anchor.getHeight() / 2;
            popup.showAsDropDown(anchor, xOffset, yOffset);
        });

        tooltipView.postDelayed(popup::dismiss, 1800);
    }
}
