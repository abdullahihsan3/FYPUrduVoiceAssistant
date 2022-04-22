package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.fypurduvoiceassistant.LoginPage;
import com.code.fypurduvoiceassistant.MainScreenMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DELAY=2500;
    private ImageView imageView;
    ImageView limage;
    ImageView lname;
    FirebaseAuth mAuth;
    Animation top,bottom,mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        mid= AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        limage = findViewById(R.id.logo_image);
        lname = findViewById(R.id.lname);

        //set animations


        limage.setAnimation(mid);
        lname.setAnimation(bottom);

        getWindow().setBackgroundDrawable(null);
        initializeview();

        gotoMainActivity();


    }







    private void gotoMainActivity(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent newi=new Intent(SplashScreen.this, LoginPage.class);
                    startActivity(newi);
                    finish();
                }
            },SPLASH_DELAY);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(SplashScreen.this, MainScreenMessage.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_DELAY);

        }


    }

    private void initializeview(){
        imageView=findViewById(R.id.logo_image);

    }



}