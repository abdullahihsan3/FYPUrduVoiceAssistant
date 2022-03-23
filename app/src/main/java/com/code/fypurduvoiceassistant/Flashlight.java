package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class Flashlight extends AppCompatActivity {
    TextView flashlightsettings;
    TextView text_to_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        flashlightsettings=findViewById(R.id.flashlightsettings);
        LabeledSwitch labeledSwitch = findViewById(R.id.switch4);
        text_to_view=findViewById(R.id.text_to_view);
        text_to_view.setText("Toggle FlashLight");
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {


                boolean hasFlash = Flashlight.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                if (hasFlash) {
                    if (isOn) {

                        CameraManager manager = (CameraManager) getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
                        try {
                            String cameraId;

                            if (manager != null) {
                                cameraId = manager.getCameraIdList()[0];
                                manager.setTorchMode(cameraId, true);
                                text_to_view.setText("FlashLight Turned On");
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {


                        String cameraId;
                        CameraManager manager = (CameraManager) getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
                        if (manager != null) {
                            try {
                                cameraId = manager.getCameraIdList()[0];
                                manager.setTorchMode(cameraId, false);
                                text_to_view.setText("FlashLight Turned Off");
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                } else {
                    Toast.makeText(Flashlight.this, "No Flashlight Found", Toast.LENGTH_LONG).show();
                }

            }
        });


        flashlightsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
            }
        });


    }
}