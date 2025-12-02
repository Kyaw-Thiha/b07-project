package com.example.b07project.view.provider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.ProviderChild;
import com.example.b07project.view.common.OnboardingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProviderDashboardActivity extends OnboardingActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RecyclerView recyclerView;
    private ProviderChildAdapter adapter;
    private final List<ProviderChild> children = new ArrayList<>();
    private ProgressBar loading;

    @Override
    protected String getOnboardingPrefKey() {
        return "provider_dashboard";
    }

    @Override
    protected void showOnboarding() {
        recyclerView.post(() -> {
            RecyclerView.ViewHolder vh =
                    recyclerView.findViewHolderForAdapterPosition(0);
            if (vh == null) return;

            View card = vh.itemView;
            View name = card.findViewById(R.id.textChildName);

            addStep(card, "This card shows the basic information of a child shared with you.");
            addStep(name, "Tap the name to view this child's detailed report.");

            startOnboardingSequence();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用 provider 的布局
        setContentView(R.layout.activity_provider_dashboard);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser == null) {
            Toast.makeText(this, "Error: no logged-in provider.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.childrenRecycler);
        loading = findViewById(R.id.loading);

        if (loading != null) {
            loading.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProviderChildAdapter(this, children);
        recyclerView.setAdapter(adapter);

        loadChildrenForProvider();
    }

    /**
     * 从 users/providers/{providerId}/reports → reports/{reportId} → users/children/{childId}
     * 组装 ProviderChild，填充到 RecyclerView.
     */
    private void loadChildrenForProvider() {
        if (loading != null) {
            loading.setVisibility(View.VISIBLE);
        }

        String providerId = mUser.getUid();

        // 1. 先从 provider 下面查所有 reportId
        DatabaseReference providerReportsRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child("providers")
                .child(providerId)
                .child("reports");

        providerReportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                children.clear();

                if (!snapshot.hasChildren()) {
                    if (loading != null) loading.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ProviderDashboardActivity.this,
                            "No children have shared reports with you yet.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 对每个 reportId 逐个加载详情
                for (DataSnapshot reportRefSnap : snapshot.getChildren()) {
                    String reportId = reportRefSnap.getKey();
                    if (reportId == null) continue;

                    loadChildFromReport(reportId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (loading != null) {
                    loading.setVisibility(View.GONE);
                }

                Toast.makeText(ProviderDashboardActivity.this,
                        "Failed to load children: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 根据 reportId 从 /reports 和 /users/children 组装一个 ProviderChild.
     */
    private void loadChildFromReport(String reportId) {
        DatabaseReference reportRef = FirebaseDatabase.getInstance()
                .getReference("reports")
                .child(reportId);

        reportRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // 没这个 report，直接跳过
                    return;
                }

                String childId = snapshot.child("childId").getValue(String.class);
                String childName = snapshot.child("childName").getValue(String.class);
                String parentName = snapshot.child("parentName").getValue(String.class);

                // summary 里的字段
                Long rescueCount = snapshot.child("summary/rescueCount").getValue(Long.class);
                Long lastRescueTime = snapshot.child("summary/lastRescueTime").getValue(Long.class);
                Long taken = snapshot.child("summary/controllerTakenDoses").getValue(Long.class);
                Long scheduled = snapshot.child("summary/controllerScheduledDoses").getValue(Long.class);
                Long adherencePercentLong = snapshot.child("summary/controllerAdherencePercent")
                        .getValue(Long.class);

                int rescue7d = rescueCount != null ? rescueCount.intValue() : 0;

                // adherencePercent 先算出来，存到 final 变量里
                final int adherenceP;
                if (adherencePercentLong != null) {
                    adherenceP = adherencePercentLong.intValue();
                } else if (taken != null && scheduled != null && scheduled > 0) {
                    adherenceP = (int) (taken * 100 / scheduled);
                } else {
                    adherenceP = 0;
                }


                // 把 lastRescueTime 变成人类可读日期
                final String lastUpdatedStr;
                if (lastRescueTime != null && lastRescueTime > 0) {
                    Date date = new Date(lastRescueTime);
                    SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    lastUpdatedStr = outFormat.format(date);
                } else {
                    lastUpdatedStr = "";
                }

                if (childId == null) {
                    // 没有 childId 就没法继续
                    return;
                }

                // 2. 再去 child 节点拿 currentZone + dateOfBirth 算年龄
                DatabaseReference childRef = FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child("children")
                        .child(childId);

                childRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot childSnap) {
                        String todayZone = childSnap.child("currentZone").getValue(String.class);
                        String dobStr = childSnap.child("dateOfBirth").getValue(String.class);

                        int age = calculateAgeFromDob(dobStr);

                        // 用 setter 方式填充，避免构造函数参数不匹配
                        ProviderChild child = new ProviderChild();
                        child.setId(childId);
                        child.setName(childName);
                        child.setAge(age);
                        child.setParentName(parentName);
                        child.setTodayZone(todayZone);
                        child.setRescue7d(rescue7d);
                        child.setControllerAdherence(adherenceP);
                        child.setLastUpdated(lastUpdatedStr);

                        children.add(child);
                        adapter.notifyDataSetChanged();

                        if (loading != null) {
                            loading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        if (loading != null) {
                            loading.setVisibility(View.GONE);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (loading != null) {
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 根据 "yyyy-MM-dd" 的生日字符串算年龄，算错就返回 0.
     */
    private int calculateAgeFromDob(String dobStr) {
        if (dobStr == null || dobStr.isEmpty()) return 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date dobDate = sdf.parse(dobStr);
            if (dobDate == null) return 0;

            Calendar dob = Calendar.getInstance();
            dob.setTime(dobDate);

            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        } catch (ParseException e) {
            return 0;
        }
    }
}
