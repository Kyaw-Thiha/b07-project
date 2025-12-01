package com.example.b07project.view.provider;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.model.ProviderChild;
import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.view.common.OnboardingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            Toast.makeText(this, "error!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        recyclerView = findViewById(R.id.childrenRecycler);
        loading = findViewById(R.id.loading);
        if (loading != null) loading.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProviderChildAdapter(this, children);
        recyclerView.setAdapter(adapter);

//        loadChildrenForProvider();
    }
    private void loadChildrenForProvider() {

        if (loading != null) {
            loading.setVisibility(View.VISIBLE);
        }

        String providerId = mUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("providersChildren")
                .child(providerId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                children.clear();



                for (DataSnapshot childSnap : snapshot.getChildren()) {
                    // childId is the key under providerId
                    String childId = childSnap.getKey();

                    ProviderChild child = childSnap.getValue(ProviderChild.class);
                    if (child == null) continue;

                    child.setId(childId);    // make sure id is set on the model
                    children.add(child);
                }

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

                Toast.makeText(ProviderDashboardActivity.this,
                        "Failed to load children: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}

