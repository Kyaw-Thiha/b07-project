package com.example.b07project.view.child;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07project.R;
import com.example.b07project.view.common.BackButtonActivity;

public class SecondTechniqueHelperActivity extends BackButtonActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_technique_helper);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        VideoView videoView1 = findViewById(R.id.videoView2);
        String videoPath1 = "android.resource://" + getPackageName() + "/" + R.raw.inhaler_breath_in;
        Uri uri1 = Uri.parse(videoPath1);
        videoView1.setVideoURI(uri1);

        videoView1.start();

        VideoView videoView2 = findViewById(R.id.videoView3);
        String videoPath2 = "android.resource://" + getPackageName() + "/" + R.raw.inhaler_breath_out;
        Uri uri2 = Uri.parse(videoPath2);
        videoView2.setVideoURI(uri2);

        videoView2.start();
    }

    public void techniqueThree(View view){
        Intent intent = new Intent(this, ThirdTechniqueHelperActivity.class);
        startActivity(intent);
    }
}