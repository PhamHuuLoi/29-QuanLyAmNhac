package com.phananh.quanlishow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.phananh.quanlishow.R;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        setTitle("QUẢN LÝ ÂM NHẠC");
        YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(5)
                .playOn(logo);
        //3 giây màn hình tự chuyển
        Thread bamgio = new Thread() {


            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {

                } finally {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.bottom, R.anim.nothing);
                }
            }
        };
        bamgio.start();
    }

}
