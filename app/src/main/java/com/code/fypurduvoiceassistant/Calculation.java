package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class Calculation extends AppCompatActivity {
    LabeledSwitch labeledSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        labeledSwitch=findViewById(R.id.switch4);

        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn)
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    toggleableView.setEnabled(false);
                }
                else{
                   toggleableView.setEnabled(true);
                }
            }
        });
    }
}