package com.ngopidev.project.androidlatihan6_networking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ngopidev.project.androidlatihan6_networking.allViews.TampilData;


/**
 * created by Irfan Assidiq on 2019-05-28
 * email : assidiq.irfan@gmail.com
 **/
public class Splash_Act extends AppCompatActivity {
    private Context ctx;
    private static final int lamaWaktu = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ctx = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ctx, TampilData.class);
                startActivity(i);
                finish();
            }
        }, lamaWaktu);
    }
}
