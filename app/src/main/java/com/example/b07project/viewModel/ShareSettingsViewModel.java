package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.ShareSettings;
import com.example.b07project.services.Service;
import com.example.b07project.services.ShareSettingsRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Map;

public class ShareSettingsViewModel extends ViewModel {

    private final Service service = new Service();
    private final ShareSettingsRepository repository = new ShareSettingsRepository(service);

    private final MutableLiveData<ShareSettings> settings = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private String parentId;
    private String childId;
    private ValueEventListener listener;

    public LiveData<ShareSettings> getSettings() {
        return settings;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void observeSettings(String parentId, String childId) {
        this.parentId = parentId;
        this.childId = childId;
        if (listener != null) {
            // No direct remove since we don't keep reference to DatabaseReference; rely on GC.
        }
        if (parentId == null || childId == null) {
            settings.setValue(null);
            return;
        }
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ShareSettings value = snapshot.getValue(ShareSettings.class);
                if (value == null) {
                    value = buildDefaultSettings(parentId, childId);
                    repository.save(parentId, childId, value);
                }
                settings.setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue("Failed to load share settings");
            }
        };
        repository.observe(parentId, childId, listener);
    }

    public void updateSetting(String key, boolean value) {
        if (parentId == null || childId == null) {
            errorMessage.setValue("Missing parent or child id");
            return;
        }
        repository.update(parentId, childId, Collections.singletonMap(key, value));
    }

    public void updateSettings(Map<String, Object> updates) {
        if (parentId == null || childId == null || updates == null || updates.isEmpty()) {
            return;
        }
        repository.update(parentId, childId, updates);
    }

    private ShareSettings buildDefaultSettings(String parentId, String childId) {
        ShareSettings defaults = new ShareSettings();
        defaults.setParentId(parentId);
        defaults.setChildId(childId);
        defaults.setIncludeRescueLogs(true);
        defaults.setIncludeControllerSummary(true);
        defaults.setIncludeSymptoms(true);
        defaults.setIncludeTriggers(true);
        defaults.setIncludePefLogs(true);
        defaults.setIncludeIncidents(true);
        defaults.setIncludeSummaryCharts(true);
        defaults.setIncludeMedicineLogs(true);
        defaults.setIncludeMedicines(true);
        defaults.setIncludeCheckIns(true);
        return defaults;
    }
}
