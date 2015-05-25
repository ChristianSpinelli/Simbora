package com.simbora.persistencia;

import android.util.Log;

import com.simbora.dominio.Evento;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    //,étodo que retorna Eventos dado uma Url
    public ArrayList<Evento> retornarEventos(String url){
        ArrayList<Evento> listaEventos=new ArrayList<Evento>();
        try {
            //recebe um array de objetos JSON
            //o nome deste array é eventos
            JSONObject jsonObject=new JSONObject(getJSON(url));
            JSONArray eventos=jsonObject.getJSONArray("eventos");
            //percorre a lista e adiciona os atributos no método retornarEvento
            for (int i=0;i<eventos.length();i++)   {
                Evento evento=retornarEvento(eventos.getJSONObject(i));
                evento.setId(i+1);
                listaEventos.add(evento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro na lista de eventos");
        }
        return listaEventos;
    }

    private Evento retornarEvento(JSONObject json){
        Evento evento=new Evento();
        try {
            evento.setNome(json.getString("titulo"));
            evento.setDescricao(json.getString("descricao"));
            evento.setTelefone(json.getJSONArray("telefone").getJSONObject(0).getString("numero"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro no json do evento");
        }
        return evento;
    }


    //método que retorna a String do JSON
    private  String getJSON(String url){
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
