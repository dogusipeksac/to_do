package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    TextView title;
    TextView fromTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo=findViewById(R.id.logo);
        title=findViewById(R.id.appTittle);
        fromTxt=findViewById(R.id.fromText);


        startAnimation();



    }
    void startAnimation(){
        Thread logoAnimation=new Thread(){
            @Override
            public void run(){
                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_inro_logo);
                logo.startAnimation(animation);
            }
        };
        logoAnimation.start();

        Thread titleAnimation=new Thread(){
            @Override
            public void run(){

                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_inro_logo);
                title.startAnimation(animation);
            }
        };
        titleAnimation.start();

        Thread authorAnimation=new Thread(){
            @Override
            public void run(){
                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_intro_auth);
                fromTxt.startAnimation(animation);
            }
        };
        authorAnimation.start();
        Thread redirect=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3500);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        redirect.start();

    }

}