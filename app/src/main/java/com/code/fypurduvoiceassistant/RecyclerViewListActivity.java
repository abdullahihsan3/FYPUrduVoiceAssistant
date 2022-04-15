package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewListActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerViewAdapter adapter;
    ArrayList<ImageClass> ls;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_list);
        rv=findViewById(R.id.rv);
        ls=new ArrayList<>();
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.flashlight),"FlashLight","Turn Flashlight On/Off","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.airplanemode),"AirplaneMode","Go To Airplane Mode Settings","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.screenshot),"Screenshot","Take a Screenshot","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.sound),"Sound","Control Sound","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.mobiledata),"MobileData","Go To MobileData Settings","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.alarm),"Alarm","Set an Alarm","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.reminder),"Reminder","Set a Reminder Notification","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.map),"Map","Open The Map","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.timeicon),"Time","See The Time","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.bluetooth),"Bluetooth","Turn Bluetooth On/Off","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.wifi_icon),"Wifi","Turn Wifi On/Off","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.youtube),"Youtube","Turn Youtube On/Off","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.facebook),"Facebook","Go To Facebook","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.browser),"Browser","Go To Browser","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.gmail),"Email","Go To Gmail","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.dateicon),"Date","Get The Current Date","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.music),"Music","Open Music Application","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.weather),"Weather","Check Weather","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.calculatoricon),"Calculation","Perform Calculation","-"));
        ls.add(new ImageClass(getResources().getDrawable(R.drawable.call),"CallContact","Perform Call","-"));




        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewAdapter(ls,this);
        rv.setAdapter(adapter);




    }
}