package com.example.b07project.util;

import android.content.Context;
import android.content.SharedPreferences;

public final class OnboardingPrefs {

    private static final String PREFS_NAME = "onboarding";
    private static final String KEY_PROVIDER_FLOW = "provider_onboarding_completed";

    private OnboardingPrefs() {
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isProviderOnboardingCompleted(Context context) {
        return prefs(context).getBoolean(KEY_PROVIDER_FLOW, false);
    }

    public static void setProviderOnboardingCompleted(Context context, boolean completed) {
        prefs(context).edit().putBoolean(KEY_PROVIDER_FLOW, completed).apply();
    }
}
