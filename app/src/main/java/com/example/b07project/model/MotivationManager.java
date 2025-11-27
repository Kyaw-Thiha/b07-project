package com.example.b07project.model;

import androidx.annotation.NonNull;

import com.example.b07project.services.MotivationRepository;
import com.example.b07project.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MotivationManager {
    private final MotivationRepository motivationRepository;

    public MotivationManager(Service service) {
        this.motivationRepository = new MotivationRepository(service);
    }

    public void onMedicineLogAdded(String childId, MedicineLog log) {
        motivationRepository.get(childId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Motivation motivation = snapshot.getValue(Motivation.class);
                Map<String, Object> updates = computeMedicineLogStreak(motivation, log);
                if (updates != null) {
                    motivationRepository.updateStreak(childId, "medicineLog", updates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // no-op
            }
        });
    }

    private Map<String, Object> computeMedicineLogStreak(Motivation motivation, MedicineLog log) {
        long dayIndex = TimeUnit.MILLISECONDS.toDays(log.getTime());
        Motivation.Streak streak = null;

        if (motivation != null && motivation.getStreaks() != null) {
            streak = motivation.getStreaks().get("medicineLog");
        }

        long current = 1;
        long best = 1;
        long lastUpdatedDay = -1;

        if (streak != null) {
            current = streak.getCurrent();
            best = streak.getBest();
            lastUpdatedDay = streak.getUpdatedAt();

            if (lastUpdatedDay == dayIndex) {
                return null; // already counted today
            }

            if (lastUpdatedDay >= 0 && dayIndex - lastUpdatedDay == 1) {
                current += 1;
            } else {
                current = 1;
            }

            if (current > best) {
                best = current;
            }
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("current", current);
        updates.put("best", best);
        updates.put("updatedAt", dayIndex);
        return updates;
    }
}
