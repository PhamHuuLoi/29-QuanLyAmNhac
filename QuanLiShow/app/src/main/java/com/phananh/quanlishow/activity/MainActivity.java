package com.phananh.quanlishow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.phananh.quanlishow.R;

public class MainActivity extends AppCompatActivity {
    public Animation blink;
    public LinearLayout lnCamera;
    public LinearLayout lnGV;
    public LinearLayout lnMonHoc;
    public LinearLayout lnPhieuCham;
    public LinearLayout lnThongKe;
    public LinearLayout lnThongTin;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        setTitle("QUẢN LÝ ÂM NHẠC");



        init();
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .repeat(1)
                .playOn(lnCamera);
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .repeat(1)
                .playOn(lnGV);
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .repeat(1)
                .playOn(lnPhieuCham);
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .repeat(1)
                .playOn(lnThongTin);
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .repeat(1)
                .playOn(lnMonHoc);
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .repeat(1)
                .playOn(lnThongKe);
        this.lnCamera.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .repeat(3)
                        .playOn(lnCamera);
                MainActivity.this.startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
        this.lnGV.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CaSiActivity.class));
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .repeat(3)
                        .playOn(lnGV);
            }
        });
        this.lnPhieuCham.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, BaiHatActivity.class));
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .repeat(3)
                        .playOn(lnPhieuCham);
            }
        });
        this.lnThongTin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ShowActivity.class));
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .repeat(3)
                        .playOn(lnThongTin);
            }
        });
        this.lnMonHoc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, NhacSiActivity.class));
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .repeat(3)
                        .playOn(lnMonHoc);
            }
        });
        this.lnThongKe.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
//                ThongKeActivity.listTK.clear();
                MainActivity.this.startActivity(new Intent(MainActivity.this, ThongKeActivity.class));
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .repeat(3)
                        .playOn(lnThongKe);
            }
        });
    }

    private void init() {
        this.lnCamera = (LinearLayout) findViewById(R.id.lnCamera);
        this.lnGV = (LinearLayout) findViewById(R.id.lnGiaoVien);
        this.lnPhieuCham = (LinearLayout) findViewById(R.id.lnPhieuCham);
        this.lnThongTin = (LinearLayout) findViewById(R.id.lnThongTin);
        this.lnMonHoc = (LinearLayout) findViewById(R.id.lnMonHoc);
        this.lnThongKe = (LinearLayout) findViewById(R.id.lnThongKe);
        this.blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.faded);
    }

    public void onBackPressed() {
    }
}
