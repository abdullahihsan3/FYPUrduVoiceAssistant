package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Alarm extends AppCompatActivity {
    TextView text_to_view;
    TextView SetAlarm;
    EditText Hours;
    EditText Mins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        SetAlarm=findViewById(R.id.setalarm);
        Hours=findViewById(R.id.Hours);
        Mins=findViewById(R.id.Minutes);
        text_to_view=findViewById(R.id.text_to_view);
        text_to_view.setText("Set Alarm");
        SetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hours=Hours.getText().toString();
                int i_hours= Integer.parseInt(String.valueOf(hours));

                String mins=Mins.getText().toString();
                int i_mins=Integer.parseInt(String.valueOf(mins));

                Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR,i_hours);
                intent.putExtra(AlarmClock.EXTRA_MINUTES,i_mins);
                startActivity(intent);

            }
        });



    }
}