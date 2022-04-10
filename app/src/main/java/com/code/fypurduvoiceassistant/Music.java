package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Music extends AppCompatActivity {
    TextView musicbutton;

    private boolean isPackageInstalled(String packagename, PackageManager packageManager)
    {
        try
        {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        musicbutton=findViewById(R.id.musicbutton);

        musicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPackageInstalled("com.google.android.music", getPackageManager()))
                {
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.music");
                    startActivity(LaunchIntent);
                }
                else
                {
                    Toast.makeText(Music.this, "No Music Player Available", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}