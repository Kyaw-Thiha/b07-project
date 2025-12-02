package com.example.b07project.view.child;

import com.example.b07project.model.Motivation;
import com.example.b07project.viewModel.MotivationViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility class that updates motivation streak/badge when a technique session is completed.
 */
public class TechniqueMotivationLogger {

    private final String childId;
    private final MotivationViewModel viewModel;

    public TechniqueMotivationLogger(String childId, MotivationViewModel viewModel) {
        this.childId = childId;
        this.viewModel = viewModel;
    }

    public void logSessionIfNeeded() {
        if (childId == null || viewModel == null) {
            return;
        }
        Motivation motivation = viewModel.getMotivation().getValue();
        long today = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());

        Motivation.Streak streak = motivation != null && motivation.getStreaks() != null
                ? motivation.getStreaks().get("techniquePractice")
                : null;

        if (streak != null && streak.getUpdatedAt() == today) {
            return; // Already counted today.
        }

        long current = 1;
        long best = 1;

        if (streak != null) {
            current = streak.getCurrent();
            best = streak.getBest();
            long lastDay = streak.getUpdatedAt();

            if (lastDay >= 0 && today - lastDay == 1) {
                current += 1;
            } else {
                current = 1;
            }

            if (current > best) {
                best = current;
            }
        }

        Map<String, Object> streakUpdates = new HashMap<>();
        streakUpdates.put("current", current);
        streakUpdates.put("best", best);
        streakUpdates.put("updatedAt", today);
        viewModel.updateStreak(childId, "techniquePractice", streakUpdates);

        int progress = 0;
        Motivation.Badge badge = motivation != null && motivation.getBadges() != null
                ? motivation.getBadges().get("techniqueSessions")
                : null;
        if (badge != null) {
            progress = badge.getProgress();
        }
        Map<String, Object> badgeUpdates = new HashMap<>();
        badgeUpdates.put("progress", progress + 1);
        viewModel.updateBadge(childId, "techniqueSessions", badgeUpdates);
    }
}
