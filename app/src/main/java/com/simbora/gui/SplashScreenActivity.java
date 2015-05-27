package com.simbora.gui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.simbora.R;
import com.simbora.dominio.Url;

import android.os.Handler;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


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
                POST();
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

    public void POST(){
        URL url = null;
        try {
            url = new URL(Url.getIp()+":5000/todo/api/v1.0/eventos");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        JSONObject jo = new JSONObject();
        try {
            jo.put("titulo", "json");
            jo.put("descricao", "teste");
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Prepare JSON to send by setting the entity
        try {
            httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

// Set up the header types needed to properly transfer JSON
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept-Encoding", "application/json");
        httpPost.setHeader("Accept-Language", "en-US");

// Execute POST
        try {
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
