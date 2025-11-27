package com.example.b07project.view.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

import java.util.GregorianCalendar;

public class LogChildSymptomActivity extends BackButtonActivity {

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
        //get most recent entry for check in and use that date
        header1();//make arguments to assign correct info to components in the first header
        header2();
        header3(); //third last entry and so on
        header4(); //second last entry
        header5(); //most recent entry

        CalendarView calendar =findViewById(R.id.calendarView);
        calendar.setVisibility(View.GONE);

    }

    //when add button for daily check in is clicked
    public void dailyCheckIn(View view){
        Intent intent = new Intent(this, ChildCheckinInputActivity.class);
        startActivity(intent);
    }

    public void header1() {
        TextView date = findViewById(R.id.textView72);
        TextView night = findViewById(R.id.textView71);
        TextView activity = findViewById(R.id.textView70);
        TextView cough = findViewById(R.id.textView69);
        TextView trigger = findViewById(R.id.textView68);

    }

    public void header2() {
        TextView date = findViewById(R.id.textView93);
        TextView night = findViewById(R.id.textView96);
        TextView activity = findViewById(R.id.textView94);
        TextView cough = findViewById(R.id.textView95);
        TextView trigger = findViewById(R.id.textView97);

    }

    public void header3() {
        TextView date = findViewById(R.id.textView98);
        TextView night = findViewById(R.id.textView101);
        TextView activity = findViewById(R.id.textView99);
        TextView cough = findViewById(R.id.textView100);
        TextView trigger = findViewById(R.id.textView102);

    }

    public void header4() {
        TextView date = findViewById(R.id.textView103);
        TextView night = findViewById(R.id.textView106);
        TextView activity = findViewById(R.id.textView104);
        TextView cough = findViewById(R.id.textView105);
        TextView trigger = findViewById(R.id.textView107);

    }

    public void header5() {
        TextView date = findViewById(R.id.textView108);
        TextView night = findViewById(R.id.textView111);
        TextView activity = findViewById(R.id.textView109);
        TextView cough = findViewById(R.id.textView110);
        TextView trigger = findViewById(R.id.textView112);

    }
    public void filter(View view){
        CalendarView calendar =findViewById(R.id.calendarView);
        calendar.setVisibility(View.VISIBLE);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                //get this date and the next 4 checkins after and call header function with each of these
            }
        });
    }

}