package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Brightness extends AppCompatActivity {
    TextView text_to_view;
    TextView brightness_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);
        askPermission(this);
        text_to_view=findViewById(R.id.text_to_view);
        brightness_button=findViewById(R.id.brigtnessbutton);
        SeekBar seekBar=findViewById(R.id.seekbar);
        Drawable progressDrawable = seekBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        seekBar.setProgressDrawable(progressDrawable);
        text_to_view.setText("Changing Brightness");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,i);
                text_to_view.setText("Brightness Changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        brightness_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });

    }
    public void askPermission(Context c){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(Settings.System.canWrite(c)){

            }
            else{
                Intent i=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                c.startActivity(i);
            }
        }
    }
}