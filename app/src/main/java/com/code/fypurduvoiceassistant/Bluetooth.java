package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class Bluetooth extends AppCompatActivity {
    TextView text_to_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        text_to_view = findViewById(R.id.text_to_view);

        text_to_view.setText("Toggle Bluetooth");
        LabeledSwitch labeledSwitch = findViewById(R.id.switch4);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (Build.VERSION.SDK_INT <= 28) {
                    if (isOn) {
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (ActivityCompat.checkSelfPermission(Bluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (mBluetoothAdapter.disable()) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            mBluetoothAdapter.enable();
                            text_to_view.setText("Bluetooth Enabled");
                        }
                    } else {
                        BluetoothAdapter mBluetoothAdapter1 = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter1.isEnabled()) {
                            mBluetoothAdapter1.disable();
                            text_to_view.setText("Bluetooth Disabled");
                        }
                    }
                }
                else{
                    Toast.makeText(Bluetooth.this, "Bluetooth Permission Denied Above API 28", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}