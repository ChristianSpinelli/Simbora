package com.simbora.persistencia;

import android.util.Log;

import com.simbora.dominio.Endereco;
import com.simbora.dominio.Evento;
import com.simbora.dominio.Horario;
import com.simbora.dominio.Preco;
import com.simbora.dominio.TipoDeEvento;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        Endereco endereco=new Endereco();
        ArrayList<Horario> horarios=new ArrayList<Horario>();
        ArrayList<Preco> precos=new ArrayList<Preco>();
        ArrayList<TipoDeEvento> tiposDeEvento=new ArrayList<TipoDeEvento>();

        try {
            evento.setNome(json.getString("titulo"));
            evento.setDescricao(json.getString("descricao"));
            evento.setTelefone(json.getJSONArray("telefone").getJSONObject(0).getString("numero"));

            //endereco

            JSONObject enderecoObject=json.getJSONArray("endereco").getJSONObject(0);
            endereco.setNome(enderecoObject.getString("nome"));
            endereco.setBairro(enderecoObject.getString("bairro"));
            endereco.setCep(enderecoObject.getString("cep"));
            endereco.setCidade(enderecoObject.getString("cidade"));
            endereco.setNumeroRua(enderecoObject.getString("numero"));
            endereco.setRua(enderecoObject.getString("rua"));

            //horarios
            JSONArray horariosObject=json.getJSONArray("horarios");
            for (int i=0;i<horariosObject.length();i++)   {
               Horario horario=new Horario();
                horario.setData(converterData(horariosObject.getJSONObject(i).getString("data")));
                horario.setHoraInicio(converterHora(horariosObject.getJSONObject(i).getString("horaInicio")));
                horario.setHoraInicio(converterHora(horariosObject.getJSONObject(i).getString("horaTermino")));
                horarios.add(horario);
            }

            JSONArray precosObject=json.getJSONArray("precos");
            for (int j=0;j<precosObject.length();j++){
                Preco preco=new Preco();
                preco.setNomeEntrada(precosObject.getJSONObject(j).getString("tipo"));
                preco.setValor(precosObject.getJSONObject(j).getDouble("preco"));
                precos.add(preco);
            }

            JSONArray tiposDeEventoObject=json.getJSONArray("tiposDeEvento");
            for (int j=0;j<tiposDeEventoObject.length();j++){
                for (TipoDeEvento t:TipoDeEvento.values()){
                    if(t.getDescricao().equals(tiposDeEventoObject.getJSONObject(j).getString("descricao"))){
                        tiposDeEvento.add(t);
                    }
                }
            }
            //seta endereco
            evento.setEndereco(endereco);
            //seta horarios
            evento.setHorarios(horarios);
            //seta precos
            evento.setPrecos(precos);
            //seta tipos de evento
            evento.setTiposDeEvento(tiposDeEvento);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro no json do evento");
        }
        return evento;
    }

    private Date converterData(String dataString){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date dataConvertida=null;
        try {
            dataConvertida=dateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataConvertida;
    }

    private Date converterHora(String dataString){
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
        Date horaConvertida=null;
        try {
            horaConvertida=dateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return horaConvertida;
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
