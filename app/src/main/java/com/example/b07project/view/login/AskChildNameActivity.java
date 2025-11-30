package com.example.b07project.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class AskChildNameActivity extends BackButtonActivity {

    private TextView nameInput;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ask_child_name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.nameInput);
        button = findViewById(R.id.proceedButton);

        button.setOnClickListener(v->proceed());
    }

    void proceed() {
        String name = nameInput.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent previous = getIntent();
        Intent intent = new Intent(AskChildNameActivity.this, LoginActivity.class);
        intent.putExtra("child-user-age-below-9", previous.getBooleanExtra("child-user-age-below-9", false));
        intent.putExtra("child name", name);
        startActivity(intent);
    }
}