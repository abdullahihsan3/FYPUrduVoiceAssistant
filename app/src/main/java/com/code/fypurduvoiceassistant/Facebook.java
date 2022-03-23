package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Facebook extends AppCompatActivity {
    TextView text_to_view;
    TextView facebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        text_to_view=findViewById(R.id.text_to_view);
        text_to_view.setText("Opening Facebook");
        facebook=findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent open=getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                startActivity(open);

            }
        });
    }
}