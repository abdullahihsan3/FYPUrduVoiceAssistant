package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Reminder extends AppCompatActivity {
    TextView textView;
    TextView notes_application;
    String CHANNEL_ID="CHANNEL_1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        textView=findViewById(R.id.text_to_view);
        notes_application=findViewById(R.id.notesbutton);

        notes_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newintent=new Intent(getApplicationContext(),Reminder.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,newintent,PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Reminder Alert")
                        .setContentText(textView.getText().toString())
                        .setSmallIcon(R.drawable.notification);

                textView.setText("");
                Toast.makeText(Reminder.this,"Reminder Notification Is Set",Toast.LENGTH_LONG).show();

                NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"This Is My First Notification",NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(notificationChannel);
                notificationManager.notify(0,builder.build());
            }
        });





    }
}