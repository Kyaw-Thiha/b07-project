package com.example.b07project.view.parent;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.Medicine;
import com.example.b07project.model.User.ParentUser;
import com.example.b07project.model.User.SessionManager;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MedicineViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class InventoryListActivity extends BackButtonActivity {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private ProgressBar loadingView;
    private Button addButton;
    private ParentInventoryAdapter adapter;
    private MedicineViewModel medicineViewModel;
    private String parentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_inventory_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        parentUid = resolveParentUid();
        if (TextUtils.isEmpty(parentUid)) {
            Toast.makeText(this, R.string.inventory_parent_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupRecycler();
        setupViewModel();
        loadingView.setVisibility(View.VISIBLE);
        medicineViewModel.loadInventoryByUser(parentUid);
    }

    private String resolveParentUid() {
        String uid = getIntent().getStringExtra(InventoryActivity.EXTRA_PARENT_UID);
        if (!TextUtils.isEmpty(uid)) {
            return uid;
        }
        uid = getSharedPreferences("APP_DATA", MODE_PRIVATE).getString("PARENT_UID", null);
        if (!TextUtils.isEmpty(uid)) {
            return uid;
        }
        if (SessionManager.getUser() instanceof ParentUser) {
            return ((ParentUser) SessionManager.getUser()).getUid();
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return null;
    }

    private void initViews() {
        recyclerView = findViewById(R.id.inventory_list);
        emptyView = findViewById(R.id.inventory_empty);
        loadingView = findViewById(R.id.inventory_loading);
        addButton = findViewById(R.id.button_add_inventory);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, InventoryActivity.class);
            intent.putExtra(InventoryActivity.EXTRA_PARENT_UID, parentUid);
            startActivity(intent);
        });
    }

    private void setupRecycler() {
        adapter = new ParentInventoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        medicineViewModel = new ViewModelProvider(this).get(MedicineViewModel.class);
        medicineViewModel.getInventory().observe(this, this::bindInventory);
        medicineViewModel.getLogError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindInventory(List<Medicine> inventory) {
        loadingView.setVisibility(View.GONE);
        List<Medicine> items = inventory != null ? inventory : new ArrayList<>();
        adapter.submitList(items);
        boolean showEmpty = items.isEmpty();
        emptyView.setVisibility(showEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(showEmpty ? View.GONE : View.VISIBLE);
    }
}
