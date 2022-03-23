package com.code.fypurduvoiceassistant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;

public class Date extends AppCompatActivity {
    TextView textView;
    TextView date_to_view;
    TextView date_settings;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        textView=findViewById(R.id.text_to_view);
        date_settings=findViewById(R.id.date_settings);
        textView.setText("Getting Date");
        date_to_view=findViewById(R.id.date_to_view);

        LocalDate myObj = LocalDate.now();
        date_to_view.setText("The Date Is "+myObj.toString());


        date_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
            }
        });



    }
}