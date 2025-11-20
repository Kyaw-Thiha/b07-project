package com.example.b07project;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.b07project.BackButtonActivity;
import android.os.Bundle;

public class ChildReportActivity extends BackButtonActivity{
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create child_report_page.xml for showing the report of each child
        //then this code can work
        //setContentView(R.layout.child_report_page);

        // initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser == null) {
            finish();
            return;
        }

        String providerId = mUser.getUid();
        String childId = getIntent().getStringExtra("childId");
        if (childId == null) {
            finish();
            return;
        }
        // TODO: here to use providerId & childId access data from firebase
    }
}
