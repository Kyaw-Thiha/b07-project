package com.example.b07project.view.login;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.model.UserType;


public class AskUsertypeActivity extends BackButtonActivity {
    private Button parentButton;
    private Button childButton;
    private Button providerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ask_usertype_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.whoAreYouPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        parentButton = findViewById(R.id.ParentButton);
        childButton = findViewById(R.id.ChildButton);
        providerButton = findViewById(R.id.ProviderButton);

        parentButton.setOnClickListener(v->toPage(parentButton));
        childButton.setOnClickListener(v->toPage(childButton));
        providerButton.setOnClickListener(v->toPage(providerButton));
    }

    void toPage(Button b) {
        Intent intent;
        SharedPreferences prefs = getSharedPreferences("APP_DATA", MODE_PRIVATE);

        if(b == childButton) {
            prefs.edit().putString("USER_TYPE", UserType.CHILD.name()).apply();
        }
        else if(b == parentButton) {
            prefs.edit().putString("USER_TYPE", UserType.PARENT.name()).apply();
        }
        else {
            prefs.edit().putString("USER_TYPE", UserType.PROVIDER.name()).apply();
        }

        intent = new Intent(AskUsertypeActivity.this, AskLoginSignupActivity.class);
        startActivity(intent);
    }
}