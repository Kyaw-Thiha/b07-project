package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.model.CheckIn;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.CheckInViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogChildSymptomActivity extends BackButtonActivity {

    private CheckInViewModel checkInViewModel;
    private String childId;
    private CalendarView calendarView;
    private TextView calendarMessage;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
    private final SimpleDateFormat dateFilterFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private List<CheckIn> cachedCheckIns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_child_symptom);
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

        calendarView = findViewById(R.id.calendarView);
        calendarMessage = findViewById(R.id.textView114);
        calendarView.setVisibility(View.GONE);
        calendarMessage.setVisibility(View.GONE);

        checkInViewModel = new ViewModelProvider(this).get(CheckInViewModel.class);
        checkInViewModel.getCheckIn().observe(this, this::populateHeaders);
        checkInViewModel.getLogError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
        checkInViewModel.loadCheckInByUser(childId);

    }

    //when add button for daily check in is clicked
    public void dailyCheckIn(View view){
        Intent intent = new Intent(this, ChildCheckinInputActivity.class);
        startActivity(intent);
    }

    public void header1(String date, String night, String activity, String cough, int triggerCount) {
        TextView dateText = findViewById(R.id.textView72);
        TextView nightText = findViewById(R.id.textView71);
        TextView activityText = findViewById(R.id.textView70);
        TextView coughText = findViewById(R.id.textView69);
        TextView trigger = findViewById(R.id.textView68);
        applyHeader(dateText, nightText, activityText, coughText, trigger,
                date, night, activity, cough, triggerCount);

    }

    public void header2(String date, String night, String activity, String cough, int triggerCount) {
        TextView dateText = findViewById(R.id.textView93);
        TextView nightText = findViewById(R.id.textView96);
        TextView activityText = findViewById(R.id.textView94);
        TextView coughText = findViewById(R.id.textView95);
        TextView trigger = findViewById(R.id.textView97);
        applyHeader(dateText, nightText, activityText, coughText, trigger,
                date, night, activity, cough, triggerCount);

    }

    public void header3(String date, String night, String activity, String cough, int triggerCount) {
        TextView dateText = findViewById(R.id.textView98);
        TextView nightText = findViewById(R.id.textView101);
        TextView activityText = findViewById(R.id.textView99);
        TextView coughText = findViewById(R.id.textView100);
        TextView trigger = findViewById(R.id.textView102);
        applyHeader(dateText, nightText, activityText, coughText, trigger,
                date, night, activity, cough, triggerCount);

    }

    public void header4(String date, String night, String activity, String cough, int triggerCount) {
        TextView dateText = findViewById(R.id.textView103);
        TextView nightText = findViewById(R.id.textView106);
        TextView activityText = findViewById(R.id.textView104);
        TextView coughText = findViewById(R.id.textView105);
        TextView trigger = findViewById(R.id.textView107);
        applyHeader(dateText, nightText, activityText, coughText, trigger,
                date, night, activity, cough, triggerCount);

    }

    public void header5(String date, String night, String activity, String cough, int triggerCount) {
        TextView dateText = findViewById(R.id.textView108);
        TextView nightText = findViewById(R.id.textView111);
        TextView activityText = findViewById(R.id.textView109);
        TextView coughText = findViewById(R.id.textView110);
        TextView trigger = findViewById(R.id.textView112);
        applyHeader(dateText, nightText, activityText, coughText, trigger,
                date, night, activity, cough, triggerCount);

    }
    public void filter(View view){
        calendarView.setVisibility(View.VISIBLE);
        calendarMessage.setVisibility(View.VISIBLE);
        calendarView.setOnDateChangeListener((cal, year, month, dayOfMonth) -> {
            String key = String.format(Locale.getDefault(), "%04d%02d%02d", year, month + 1, dayOfMonth);
            populateHeaders(filterByDateKey(key));
        });
    }

    private void populateHeaders(List<CheckIn> checkIns) {
        cachedCheckIns = checkIns != null ? new ArrayList<>(checkIns) : new ArrayList<>();
        List<CheckIn> displayList = new ArrayList<>(cachedCheckIns);
        displayList.sort((a, b) -> Long.compare(b.getTime(), a.getTime()));
        updateHeaderSlot(0, displayList.size() > 0 ? displayList.get(0) : null);
        updateHeaderSlot(1, displayList.size() > 1 ? displayList.get(1) : null);
        updateHeaderSlot(2, displayList.size() > 2 ? displayList.get(2) : null);
        updateHeaderSlot(3, displayList.size() > 3 ? displayList.get(3) : null);
        updateHeaderSlot(4, displayList.size() > 4 ? displayList.get(4) : null);
    }

    private void updateHeaderSlot(int index, CheckIn checkIn) {
        String dateText = checkIn != null ? dateFormat.format(new Date(checkIn.getTime()))
                : getString(R.string.child_symptom_no_data);
        String night = checkIn != null && checkIn.getNight_walking() != null
                ? checkIn.getNight_walking().getNote()
                : getString(R.string.child_symptom_no_data);
        String activity = checkIn != null ? safeText(checkIn.getActivity_limits())
                : getString(R.string.child_symptom_no_data);
        String cough = checkIn != null ? safeText(checkIn.getCough())
                : getString(R.string.child_symptom_no_data);
        int triggerCount = checkIn != null && checkIn.getNight_walking() != null
                && checkIn.getNight_walking().getTriggers() != null
                ? countTriggers(checkIn.getNight_walking().getTriggers())
                : 0;

        switch (index) {
            case 0:
                header1(dateText, night, activity, cough, triggerCount);
                break;
            case 1:
                header2(dateText, night, activity, cough, triggerCount);
                break;
            case 2:
                header3(dateText, night, activity, cough, triggerCount);
                break;
            case 3:
                header4(dateText, night, activity, cough, triggerCount);
                break;
            case 4:
                header5(dateText, night, activity, cough, triggerCount);
                break;
            default:
                break;
        }
    }

    private void applyHeader(TextView dateText, TextView nightText, TextView activityText,
                             TextView coughText, TextView triggerText,
                             String date, String night, String activity, String cough, int triggerCount) {
        dateText.setText(date);
        nightText.setText(night);
        activityText.setText(activity);
        coughText.setText(cough);
        triggerText.setText(getString(R.string.child_symptom_trigger_template, triggerCount));
    }

    private int countTriggers(CheckIn.Triggers triggers) {
        int count = 0;
        if (triggers.isExercise()) count++;
        if (triggers.isCold_air()) count++;
        if (triggers.isDust()) count++;
        if (triggers.isPets()) count++;
        if (triggers.isSmoke()) count++;
        if (triggers.isIllness()) count++;
        if (triggers.isPerfume_odors()) count++;
        return count;
    }

    private String safeText(String value) {
        return value == null || value.isEmpty() ? getString(R.string.child_symptom_no_data) : value;
    }

    private List<CheckIn> filterByDateKey(String key) {
        if (cachedCheckIns == null || cachedCheckIns.isEmpty()) {
            return new ArrayList<>();
        }
        List<CheckIn> filtered = new ArrayList<>();
        for (CheckIn checkIn : cachedCheckIns) {
            String entryKey = dateFilterFormat.format(new Date(checkIn.getTime()));
            if (entryKey.equals(key)) {
                filtered.add(checkIn);
            }
        }
        if (filtered.isEmpty()) {
            Toast.makeText(this, R.string.child_symptom_no_matches, Toast.LENGTH_LONG).show();
        }
        return filtered;
    }

}
