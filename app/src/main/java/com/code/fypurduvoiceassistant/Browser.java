package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Browser extends AppCompatActivity {
    TextView text_to_view;
    TextView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        text_to_view=findViewById(R.id.text_to_view);
        text_to_view.setText("Opening Chrome");
        browser=findViewById(R.id.browser);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")));


            }
        });

    }
}