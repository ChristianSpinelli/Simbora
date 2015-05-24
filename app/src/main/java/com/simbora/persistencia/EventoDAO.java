package com.simbora.persistencia;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.simbora.dominio.Evento;
import com.simbora.gui.DummyContent;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoDAO {


    public ArrayList<Evento> retornarEventos(){
           return null;
    }
    public ArrayList<Evento> retornarEventos(String url){
         System.out.println(getUrl(url));
        return (ArrayList<Evento>) DummyContent.ITEMS;
    }


    private  String getUrl(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
