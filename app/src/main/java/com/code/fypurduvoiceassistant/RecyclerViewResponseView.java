package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerViewResponseView extends AppCompatActivity {
    RecyclerView rv;
    RecyclerViewListActivityAdapter adapter;
    ArrayList<ResponseClass> ls;
    Intent get_intent;

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_response_view);
        get_intent = getIntent();

        int response_count = get_intent.getIntExtra("count", 0);
        if (response_count == 0) {
            ls = new ArrayList<>();
        }
        String response = get_intent.getStringExtra("response");
        Toast.makeText(RecyclerViewResponseView.this, response, Toast.LENGTH_LONG).show();
        rv = findViewById(R.id.rv_response);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        if (response.toLowerCase(Locale.ROOT).contains("flashlight") && response.toLowerCase(Locale.ROOT).contains("on")) {
            Toast.makeText(this, "Bluetooth Turned On", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime, getResources().getDrawable(R.drawable.flashlight)));

            CameraManager manager = (CameraManager) getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
            try {
                String cameraId;

                if (manager != null) {
                    cameraId = manager.getCameraIdList()[0];
                    manager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else if ((response.toLowerCase(Locale.ROOT).contains("flashlight") && response.toLowerCase(Locale.ROOT).contains("off")) || (response.toLowerCase(Locale.ROOT).contains("light") && response.toLowerCase(Locale.ROOT).contains("stop"))) {
            Toast.makeText(this, "Bluetooth Turned On", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime, getResources().getDrawable(R.drawable.flashlight)));
            CameraManager manager = (CameraManager) getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
            try {
                String cameraId;
                if (manager != null) {
                    cameraId = manager.getCameraIdList()[0];
                    manager.setTorchMode(cameraId, false);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else if (response.toLowerCase(Locale.ROOT).contains("bluetooth") && response.toLowerCase(Locale.ROOT).contains("on")) {
            Toast.makeText(this, "Bluetooth Turned On", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime, getResources().getDrawable(R.drawable.bluetooth)));

            if (Build.VERSION.SDK_INT <= 28) {

                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (ActivityCompat.checkSelfPermission(RecyclerViewResponseView.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (mBluetoothAdapter.disable()) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mBluetoothAdapter.enable();
                    }

                mBluetoothAdapter.enable();
            }
            else{
                Toast.makeText(RecyclerViewResponseView.this, "Bluetooth Permission Denied Above API 28", Toast.LENGTH_SHORT).show();
            }



        }
        else if(response.toLowerCase(Locale.ROOT).contains("bluetooth") && response.toLowerCase(Locale.ROOT).contains("off") ){

            Toast.makeText(this, "Bluetooth Turned Off", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.bluetooth) ));

            if (Build.VERSION.SDK_INT <= 28) {

                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (ActivityCompat.checkSelfPermission(RecyclerViewResponseView.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (mBluetoothAdapter.enable()) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mBluetoothAdapter.disable();
                }

                mBluetoothAdapter.disable();
            }
            else{
                Toast.makeText(RecyclerViewResponseView.this, "Bluetooth Permission Denied Above API 28", Toast.LENGTH_SHORT).show();
            }



        }
        else if((response.toLowerCase(Locale.ROOT).contains("wifi") && response.toLowerCase(Locale.ROOT).contains("on")) || response.contains("1") || (response.toLowerCase(Locale.ROOT).contains("wifi") && response.toLowerCase(Locale.ROOT).contains("online")) ){
            Toast.makeText(this, "Wifi Turned Off", Toast.LENGTH_SHORT).show();
            WifiManager wifiManager = (WifiManager)RecyclerViewResponseView.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if(Build.VERSION.SDK_INT<=28) {
                wifiManager.setWifiEnabled(true);
            }
            else{
                Toast.makeText(RecyclerViewResponseView.this,"WiFi Cannot Be Accessed Above API 28",Toast.LENGTH_LONG).show();
            }
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.wifi_icon) ));

        }
        else if(response.toLowerCase(Locale.ROOT).contains("wifi") && response.toLowerCase(Locale.ROOT).contains("off") ){
            Toast.makeText(this, "Wifi Turned Off", Toast.LENGTH_SHORT).show();
            WifiManager wifiManager = (WifiManager)RecyclerViewResponseView.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.wifi_icon) ));

        }
        else if(response.toLowerCase(Locale.ROOT).contains("aeroplane") && response.toLowerCase(Locale.ROOT).contains("on") ){
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.airplanemode) ));

            if(Build.VERSION.SDK_INT<16) {
                Toast.makeText(RecyclerViewResponseView.this,"Airplane Mode Turned On",Toast.LENGTH_LONG).show();
                boolean isEnabled = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                    Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
                    Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                    intent.putExtra("state", !isEnabled);
                    sendBroadcast(intent);
            }
            else{
                Toast.makeText(RecyclerViewResponseView.this, "Permission Denied Above API 16", Toast.LENGTH_SHORT).show();
            }



        }
        else if(response.toLowerCase(Locale.ROOT).contains("calculator")){
            Toast.makeText(this, "Opening Calculator", Toast.LENGTH_SHORT).show();

            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.calculatoricon) ));
            Intent open=getPackageManager().getLaunchIntentForPackage("com.google.android.calculator");
            startActivity(open);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("browser") ){
            Toast.makeText(this, "Browser Opened", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.browser) ));

        }
        else if(response.toLowerCase(Locale.ROOT).contains("brightness") || response.toLowerCase(Locale.ROOT).contains("luminous")){
            Toast.makeText(this, "Brightness Lowered", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.brightness) ));

            WindowManager.LayoutParams layoutParams = getWindow().getAttributes(); // Get Params
            layoutParams.screenBrightness = 20; // Set Value
            getWindow().setAttributes(layoutParams); // Set params

        }
        else if(response.toLowerCase(Locale.ROOT).contains("map") ){
            Toast.makeText(this, "Map Opened", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.map) ));
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=an+address+city"));
            startActivity(intent);


        }
        else if(response.toLowerCase(Locale.ROOT).contains("alarm")){
            int val = 0;
            if(response.toLowerCase(Locale.ROOT).contains("one")){
                val=1;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("two")){
                val=2;
            }

            else if(response.toLowerCase(Locale.ROOT).contains("three")){
                val=3;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("four")){
                val=4;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("five")){
                val=5;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("six")){
                val=6;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("seven")){
                val=7;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("eight")){
                val=8;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("nine")){
                val=9;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("ten")){
                val=10;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("eleven")){
                val=11;
            }
            else if(response.toLowerCase(Locale.ROOT).contains("twelve")){
                val=12;
            }
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.alarm) ));
            Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR,val);
            intent.putExtra(AlarmClock.EXTRA_MINUTES,00);
            startActivity(intent);




        }
        else if(response.toLowerCase(Locale.ROOT).contains("name") ){
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.ic_baseline_person_add_24) ));
            Toast.makeText(RecyclerViewResponseView.this,"Mera Naam Urdu Assistant FYP Hai",Toast.LENGTH_LONG).show();

        }
        else if(response.toLowerCase(Locale.ROOT).contains("time") ){
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.timeicon) ));
            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            Toast.makeText(this,"The Current Time Is "+time,Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RecyclerViewResponseView.this,MainScreenMessage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("date") ){
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.dateicon) ));
            LocalDate myObj = LocalDate.now();
            Toast.makeText(this,"The Current Date Is "+myObj.toString(),Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RecyclerViewResponseView.this,MainScreenMessage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }


        else if(response.toLowerCase(Locale.ROOT).contains("data") ){
            Toast.makeText(this, "Mobile Data Settings Opened", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.mobiledata)));
            Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            startActivity(intent);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("facebook") && response.toLowerCase(Locale.ROOT).contains("open") ){
            Toast.makeText(this, "Open Facebook", Toast.LENGTH_SHORT).show();

            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.fb) ));
            Intent open=getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
            startActivity(open);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("youtube") && response.contains("open") ){
            Toast.makeText(this, "Open Youtube", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.youtube) ));
            Intent openYoutube=getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
            startActivity(openYoutube);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("reduce") && response.contains("sound") || (response.toLowerCase(Locale.ROOT).contains("slow") && response.contains("sound")) ){
            Toast.makeText(this, "Open Youtube", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.sound) ));
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER,AudioManager.FLAG_PLAY_SOUND);
        }
        else if(response.toLowerCase(Locale.ROOT).contains("more") && response.contains("sound") ){
            Toast.makeText(this, "Open Youtube", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.sound) ));
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
        }
        else if((response.toLowerCase(Locale.ROOT).contains("gmail") && response.toLowerCase(Locale.ROOT).contains("open"))){

            Toast.makeText(this, "Open Gmail", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.gmail) ));
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            final PackageManager pm = getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") ||
                        info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
            if (best != null)
                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            startActivity(intent);


        }
        else if(response.toLowerCase(Locale.ROOT).contains("weather") && response.contains("today") ){
            Toast.makeText(this, "Weather App Opened", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.weather) ));
            Intent openYoutube=getPackageManager().getLaunchIntentForPackage("com.graph.weather.forecast.channel");
            startActivity(openYoutube);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("reminder")){
            Toast.makeText(this, "Set a Reminder", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.reminder) ));
            Intent intent=new Intent(RecyclerViewResponseView.this,Reminder.class);
            startActivity(intent);

        }
        else if(response.toLowerCase(Locale.ROOT).contains("music")){
            Toast.makeText(this, "Music App Opened", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.music) ));
            Intent openYoutube=getPackageManager().getLaunchIntentForPackage("com.shaiban.audioplayer.mplayer");
            startActivity(openYoutube);


        }
        else if(response.toLowerCase(Locale.ROOT).contains("profile")){
            Toast.makeText(this, "Profile Shown", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.ic_baseline_person_add_24) ));
            Intent intent=new Intent(RecyclerViewResponseView.this,ProfilePicActivity.class);
            startActivity(intent);
        }
        else if((response.toLowerCase(Locale.ROOT).contains("screenshot")) || response.toLowerCase(Locale.ROOT).contains("screen") || response.toLowerCase(Locale.ROOT).contains("shot") ){
            Toast.makeText(this, "Screenshot Taken", Toast.LENGTH_SHORT).show();
            ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.screenshot) ));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                    Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(),rootView.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    rootView.draw(canvas);
                    String pathofBmp= MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                            bitmap,"Title", null);
                    Uri uri = Uri.parse(pathofBmp);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Urdu Assistant App");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(shareIntent, "ScreenShot Title"));

                }
            }, 1000);


        }


        else{
            Toast.makeText(this,"Command Not Understood",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RecyclerViewResponseView.this,MainScreenMessage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }





        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewListActivityAdapter(ls, this);
        rv.setAdapter(adapter);
    }
}