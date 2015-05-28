package com.simbora.util.gui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.simbora.R;
import com.simbora.util.dominio.Url;
import com.simbora.evento.gui.activities.EventoListActivity;

import android.os.Handler;
import android.widget.TextView;


public class SplashScreenActivity extends ActionBarActivity {
    ProgressBar progress;
    TextView textViewCarregando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progress=(ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
        textViewCarregando=(TextView) findViewById(R.id.textViewCarregando);
        textViewCarregando.setVisibility(View.INVISIBLE);
        //configuramos um IP padrão
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        final EditText textoIP=(EditText) findViewById(R.id.editText);
        Button btnEntrar = (Button) findViewById(R.id.button);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Url.setIp(textoIP.getText().toString());
                gotoTelaInicial();

            }
        });

    }

    //método que espera 2 segundos e ai a tela principal
    public void gotoTelaInicial(){
        progress.setVisibility(View.VISIBLE);
        textViewCarregando.setVisibility(View.VISIBLE);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, EventoListActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }




}
