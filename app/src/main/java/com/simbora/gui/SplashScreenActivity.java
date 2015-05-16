package com.simbora.gui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.simbora.R;

import android.os.Handler;


public class SplashScreenActivity extends ActionBarActivity {
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progress=(ProgressBar) findViewById(R.id.progressBar);
        gotoTelaInicial();

    }

    public void gotoTelaInicial(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this, EventoListActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }


}
