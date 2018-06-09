package com.malekk.newdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends AppCompatActivity {

    ImageView imgSS ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        imgSS = (ImageView) findViewById(R.id.imgSS) ;

        startAnimation();

    }


    public void startAnimation () {
        Animation anim = AnimationUtils.loadAnimation(SplashScreen.this , R.anim.alpha) ;
        anim.reset();

        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay) ;
        l.clearAnimation();
        l.startAnimation(anim);


        anim = AnimationUtils.loadAnimation(SplashScreen.this , R.anim.translate) ;

        imgSS.clearAnimation();
        imgSS.startAnimation(anim);

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2700) {
                        sleep(100);
                        waited += 100;
                    }
//                    sleep(2000);
                    Intent intent = new Intent(SplashScreen.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                  //  SplashScreen.this.finish();
                }
            }
        };

        thread.start();

    }

}
