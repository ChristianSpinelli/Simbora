package com.simbora.util.persistencia;

import android.util.Log;

import com.simbora.pessoa.dominio.Pessoa;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Demis on 03/06/15.
 */
public abstract class AbstractDAO<E> {

    public abstract E consultar(E e, String url);
    public abstract E remover(E e, String url);
    public abstract boolean atualizar(E e, String url);
    public abstract boolean inserir(E e, String url);

    /** consultar uma lista de objetos */
    public abstract ArrayList<E> consultar(String url);


    public abstract E converterParaObjeto(JSONObject jsonObject);
    public abstract JSONObject converterParaJSON(E e);

    protected boolean post(JSONObject jo, String urlWebService){
        boolean inseriu=false;
        URL url = null;
        try {
            url = new URL(urlWebService);
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
        // Seta o corpo do Post
        try {
            httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Configura o cabeçalho do post informando que é um JSON
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept-Encoding", "application/json");
        httpPost.setHeader("Accept-Language", "en-US");
        // Executa o POST
        try {
            HttpResponse response = httpClient.execute(httpPost);
            inseriu=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inseriu;
    }

    //método que retorna a String do JSON
    protected  String getJSON(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // cria HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // dá o GET
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // recebe resposta no inputStream
            inputStream = httpResponse.getEntity().getContent();
            // converte inputStream para string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Não funcionou!";

        } catch (Exception e) {
            Log.d("InputStream", e.getMessage());
        }

        return result;
    }

    protected static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
