package com.example.b07project.view.parent;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.model.Medicine;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.MedicineViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class InventoryActivity extends BackButtonActivity {
    private TextInputEditText itemNameInput;
    private TextInputEditText purchaseDateInput;
    private TextInputEditText expiryDateInput;
    private TextInputEditText replacementReminderInput;
    private TextInputEditText canisterPuffsInput;
    private TextInputEditText typeInput;
    private Button addInventory;
    private MedicineViewModel medicineViewModel;
    private String parentUid;
    private boolean pendingSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        parentUid = getSharedPreferences("APP_DATA", MODE_PRIVATE).getString("PARENT_UID", null);
        if (parentUid == null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            parentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        initViews();
        setupViewModel();
    }

    private void initViews() {
        itemNameInput = findViewById(R.id.Item_name);
        purchaseDateInput = findViewById(R.id.purchase_date);
        expiryDateInput = findViewById(R.id.expiry_date);
        replacementReminderInput = findViewById(R.id.replacement_reminder_date);
        canisterPuffsInput = findViewById(R.id.canister_puffs);
        typeInput = findViewById(R.id.medicine_type);
        addInventory = findViewById(R.id.add_inventory);
        addInventory.setOnClickListener(v -> saveInventory());
    }

    private void setupViewModel() {
        medicineViewModel = new ViewModelProvider(this).get(MedicineViewModel.class);
        medicineViewModel.getInventory().observe(this, inventory -> {
            if (pendingSave) {
                pendingSave = false;
                clearForm();
                Toast.makeText(this, "Inventory added.", Toast.LENGTH_SHORT).show();
            }
        });
        medicineViewModel.getLogError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                pendingSave = false;
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
        if (parentUid != null) {
            medicineViewModel.loadInventoryByUser(parentUid);
        }
    }

    private void saveInventory() {
        if (parentUid == null) {
            Toast.makeText(this, "Unable to determine parent account.", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = itemNameInput.getText() != null ? itemNameInput.getText().toString().trim() : "";
        String purchase = purchaseDateInput.getText() != null ? purchaseDateInput.getText().toString().trim() : "";
        String expiry = expiryDateInput.getText() != null ? expiryDateInput.getText().toString().trim() : "";
        String reminder = replacementReminderInput.getText() != null ? replacementReminderInput.getText().toString().trim() : "";
        String puffsText = canisterPuffsInput.getText() != null ? canisterPuffsInput.getText().toString().trim() : "";
        String type = typeInput.getText() != null ? typeInput.getText().toString().trim() : "rescue";

        if (TextUtils.isEmpty(name)) {
            itemNameInput.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(purchase)) {
            purchaseDateInput.setError("Purchase date required");
            return;
        }
        if (TextUtils.isEmpty(expiry)) {
            expiryDateInput.setError("Expiry date required");
            return;
        }
        if (TextUtils.isEmpty(puffsText)) {
            canisterPuffsInput.setError("Enter canister puffs");
            return;
        }
        int puffs;
        try {
            puffs = Integer.parseInt(puffsText);
        } catch (NumberFormatException e) {
            canisterPuffsInput.setError("Enter a valid number");
            return;
        }

        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setPurchase_date(purchase);
        medicine.setExpiry_date(expiry);
        medicine.setReplacement_reminder(reminder);
        medicine.setCanister_puffs(puffs);
        medicine.setInitialCanisterPuffs(puffs);
        medicine.setType(type);
        medicine.setUid(parentUid);
        pendingSave = true;
        medicineViewModel.addInventory(parentUid, medicine);
    }

    private void clearForm() {
        itemNameInput.setText(null);
        purchaseDateInput.setText(null);
        expiryDateInput.setText(null);
        replacementReminderInput.setText(null);
        canisterPuffsInput.setText(null);
        typeInput.setText(null);
    }
}
