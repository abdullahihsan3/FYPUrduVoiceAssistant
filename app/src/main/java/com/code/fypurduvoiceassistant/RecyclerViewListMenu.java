package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class RecyclerViewListMenu extends AppCompatActivity {
    RecyclerView rv;
    RecyclerViewAdapter adapter;
    ArrayList<ImageClass> ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_list_menu);
        ls=new ArrayList<>();
        rv=findViewById(R.id.rv);
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.flashlight, null),"FlashLight","Change Flashlight Mode","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.airplanemode, null),"AirplaneMode","Change Airplane Mode","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.screenshot, null),"Screenshot","Take a Screenshot","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.sound, null),"Sound","Change Sound Level","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.mobiledata, null),"MobileData","Change MobileData Mode","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.alarm, null),"Alarm","Set An Alarm","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.reminder, null),"Reminder","Set A Reminder","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.map, null),"Map","Open Map","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.timeicon, null),"Time","Show The Time","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.bluetooth, null),"Bluetooth","Change Bluetooth Mode","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.wifi_icon, null),"Wifi","Change Wifi Mode","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.youtube, null),"Youtube","Open Youtube","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.facebook, null),"Facebook","Open Facebook","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.browser, null),"Browser","Open Browser","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.gmail, null),"Email","Open Gmail App","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.dateicon, null),"Date","Show Date","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.music, null),"Music","Open Music","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.weather, null),"Weather","Show Weather","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.calculatoricon, null),"Calculation","Show Calculator","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.call, null),"CallContact","Call A Contact","1"));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.brightness, null),"Brightness","Change Brightness Settings","1"));


        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(rv.getLayoutParams());
        marginLayoutParams.setMargins(0, 20, 0, 60);
        rv.setLayoutParams(marginLayoutParams);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewAdapter(ls, this);
        rv.setAdapter(adapter);

    }
}