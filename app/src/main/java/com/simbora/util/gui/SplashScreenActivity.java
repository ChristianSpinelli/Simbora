package com.simbora.util.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simbora.R;
import com.simbora.usuario.gui.LoginActivity;
import com.simbora.util.dominio.Url;


public class SplashScreenActivity extends Activity {
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

        Url.setIp("myflaskapp-simbora.rhcloud.com");
        gotoTelaInicial();

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
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }




}
