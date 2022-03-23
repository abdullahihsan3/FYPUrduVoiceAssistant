package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class WifiActivity extends AppCompatActivity {
    TextView wifisettings;
    TextView text_to_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        text_to_view=findViewById(R.id.text_to_view1);
        text_to_view.setText("Toggle Wifi Settings");
        wifisettings=findViewById(R.id.wifisettings);
        LabeledSwitch labeledSwitch = findViewById(R.id.switch4);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {



                WifiManager wifiManager = (WifiManager)WifiActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                if(isOn){
                    if(Build.VERSION.SDK_INT<=28) {
                        wifiManager.setWifiEnabled(true);
                    }
                    else{
                        Toast.makeText(WifiActivity.this,"WiFi Cannot Be Accessed Above API 28",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    text_to_view.setText("Wifi Turned Off");
                    wifiManager.setWifiEnabled(false);
                }
            }
        });

        wifisettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


    }
}