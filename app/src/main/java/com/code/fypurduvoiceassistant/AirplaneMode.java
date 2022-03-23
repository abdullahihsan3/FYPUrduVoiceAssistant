package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class AirplaneMode extends AppCompatActivity {
    TextView airplanemode;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane_mode);
        LabeledSwitch labeledSwitch = findViewById(R.id.switch4);
        textView=findViewById(R.id.text_to_view);
        textView.setText("Toggle Airplane Mode");
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(Build.VERSION.SDK_INT<16) {
                    if (isOn) {
                        boolean isEnabled = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                        Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
                        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                        intent.putExtra("state", !isEnabled);
                        sendBroadcast(intent);
                    }
                }
                else{
                    Toast.makeText(AirplaneMode.this, "Permission Denied Above API 16", Toast.LENGTH_SHORT).show();
                }

            }
        });


        airplanemode=findViewById(R.id.airplanemode);
        airplanemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS );
                startActivity(intent);
            }
        });



    }
}