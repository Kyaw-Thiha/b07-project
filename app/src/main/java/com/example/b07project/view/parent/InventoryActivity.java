package com.example.b07project.view.parent;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.google.android.material.textfield.TextInputEditText;

public class InventoryActivity extends BackButtonActivity {
    TextInputEditText Item_name,purchase_date,expiry_date,replacement_reminder_date;
    Button addInventory;
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
        Item_name = findViewById(R.id.Item_name);
        purchase_date = findViewById(R.id.purchase_date);
        expiry_date = findViewById(R.id.expiry_date);
        replacement_reminder_date = findViewById(R.id.replacement_reminder_date);
        addInventory= findViewById(R.id.add_inventory);
    }
    // Firebase stuff TODO
}
