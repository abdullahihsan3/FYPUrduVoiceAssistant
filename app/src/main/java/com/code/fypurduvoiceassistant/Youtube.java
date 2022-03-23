package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Youtube extends AppCompatActivity {
    TextView text_to_view;
    TextView youtube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        text_to_view=findViewById(R.id.text_to_view);
        text_to_view.setText("Opening Youtube");
        youtube=findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openYoutube=getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                startActivity(openYoutube);

            }
        });

    }
}