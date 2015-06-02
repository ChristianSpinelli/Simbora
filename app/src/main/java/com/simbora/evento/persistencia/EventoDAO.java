package com.simbora.evento.persistencia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.simbora.evento.dominio.Endereco;
import com.simbora.evento.dominio.Evento;
import com.simbora.evento.dominio.Horario;
import com.simbora.evento.dominio.Preco;
import com.simbora.evento.dominio.TipoDeEvento;
import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.pessoa.dominio.perssistencia.PessoaDAO;
import com.simbora.util.dominio.Imagem;
import com.simbora.util.dominio.Url;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoDAO {


    public ArrayList<Evento> retornarEventos(String url, TipoDeEvento tipo){
        try {
            JSONObject jsonObject=new JSONObject(getJSON(url));
            JSONArray eventos=jsonObject.getJSONArray("eventos");
            ArrayList<Evento> listaEventos=new ArrayList<Evento>();

            //criar um método para esta funcionalidade
            //verifica se um tipo est´no evento
             for (int i=0;i<eventos.length();i++){
                 Log.d("print i", Integer.toString(i));
                 JSONArray tiposDeEventoObject=eventos.getJSONObject(i).getJSONArray("tiposDeEvento");
                 for (int j=0;j<tiposDeEventoObject.length();j++) {
                     Log.d("tipo do objto no json", tiposDeEventoObject.getJSONObject(j).getString("descricao"));
                     if (tipo.getDescricao().equals(tiposDeEventoObject.getJSONObject(j).getString("descricao"))) {
                         Evento evento = retornarEvento(eventos.getJSONObject(i));
                         listaEventos.add(evento);
                     }
                 }
             }
            return listaEventos;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    //,étodo que retorna Eventos dado uma Url
    public ArrayList<Evento> retornarEventos(String url){
        PessoaDAO p=new PessoaDAO();
        String urlPessoas=p.retornarUrl("simbora@simbora.org");
        Pessoa pessoa=p.retornarPessoa(urlPessoas);
        ArrayList<Evento> listaEventos=new ArrayList<Evento>();
        Log.d("Pessoa", pessoa.getUsuario().getNome());
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
            evento.setTelefone(json.getString("telefone"));

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



            Bitmap bitmap = null;
            byte[] bitmapdata=null;
            //pega a imagem do servidor e converte em um bitmap
            try {
                InputStream in = new java.net.URL(Url.getIp()+":5000/"+json.getString("imagem")).openStream();
                bitmap = BitmapFactory.decodeStream(in);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
                bitmapdata = blob.toByteArray();

            } catch (Exception e) {
                Log.e("Erro na imagem", e.getMessage());
                e.printStackTrace();
            }
            //seta endereco
            evento.setEndereco(endereco);
            //seta horarios
            evento.setHorarios(horarios);
            //seta precos
            evento.setPrecos(precos);
            //seta tipos de evento
            evento.setTiposDeEvento(tiposDeEvento);
            //seta imagem
            evento.setImagem(new Imagem(json.getString("imagem"), bitmapdata));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro no json do evento");
        }
        return evento;
    }

    public boolean inserirEvento(Evento evento, String url){

        JSONObject eventoAInserir = converterEventoJSON(evento);
        postImagem(url, evento.getImagem().getCaminho());
        boolean inseriu=post(eventoAInserir,url);
        return inseriu;
    }

    private JSONObject converterEventoJSON(Evento evento){

        JSONObject jsonObjectEvento=new JSONObject();
        JSONArray jsonArrayEndereco=new JSONArray();
        JSONArray jsonArrayHorarios=new JSONArray();
        JSONArray jsonArrayPrecos=new JSONArray();
        JSONArray jsonArrayTiposDeEvento=new JSONArray();

        try {
            JSONObject jsonObjectEndereco=new JSONObject();
            jsonObjectEndereco.put("bairro", evento.getEndereco().getBairro());
            jsonObjectEndereco.put("cep", evento.getEndereco().getCep());
            jsonObjectEndereco.put("cidade", evento.getEndereco().getCidade());
            jsonObjectEndereco.put("nome", evento.getEndereco().getNome());
            jsonObjectEndereco.put("numero", evento.getEndereco().getNumeroRua());
            jsonObjectEndereco.put("rua",evento.getEndereco().getRua());

            jsonArrayEndereco.put(0, jsonObjectEndereco);

            for (int i=0;i< evento.getHorarios().size();i++){
                JSONObject jsonObjectHorario=new JSONObject();
                jsonObjectHorario.put("data", converterData(evento.getHorarios().get(i).getData()));
                jsonObjectHorario.put("horaInicio", converterHora(evento.getHorarios().get(i).getHoraInicio()));
                jsonObjectHorario.put("horaTermino", converterHora(evento.getHorarios().get(i).getHoraTermino()));

                jsonArrayHorarios.put(i,jsonObjectHorario);
            }

            for (int i=0;i< evento.getPrecos().size();i++){
                JSONObject jsonObjectPreco=new JSONObject();
                jsonObjectPreco.put("preco", evento.getPrecos().get(i).getValor());
                jsonObjectPreco.put("tipo",evento.getPrecos().get(i).getNomeEntrada());

                jsonArrayPrecos.put(i,jsonObjectPreco);
            }

            for (int i=0;i< evento.getTiposDeEvento().size();i++){
                JSONObject jsonObjectTipoDeEvento=new JSONObject();
                jsonObjectTipoDeEvento.put("descricao", evento.getTiposDeEvento().get(i).getDescricao());

                jsonArrayTiposDeEvento.put(i,jsonObjectTipoDeEvento);
            }


            jsonObjectEvento.put("titulo", evento.getNome());
            jsonObjectEvento.put("descricao",evento.getDescricao());
            jsonObjectEvento.put("endereco",jsonArrayEndereco);
            jsonObjectEvento.put("horarios", jsonArrayHorarios);
            jsonObjectEvento.put("precos", jsonArrayPrecos);
            jsonObjectEvento.put("tiposDeEvento", jsonArrayTiposDeEvento);
            jsonObjectEvento.put("telefone", evento.getTelefone());

            //default caso não tenha imagem no evento
            String nomeImagem="logo_simbora_nome.png";

            if (evento.getImagem().getCaminho()!=null){
                nomeImagem=retornarNomeImagem(evento.getImagem().getCaminho());
            }

            jsonObjectEvento.put("imagem",nomeImagem);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSON Object", jsonObjectEvento.toString());
        return jsonObjectEvento;
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

    private boolean post(JSONObject jo, String urlWebService){
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

        // Configura o cabeçcalho do post informando que é um JSON
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

    public void postImagem(String url, String caminho){
        RequestParams params = new RequestParams();
        if (caminho!=null){
            try {
                params.put("file",new File(caminho));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Url.getIp()+":5000/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    Log.w("async", "success!!!!");
                }
            });
        }

    }


    public String converterHora(Date date){
        return converterDatas(date, "HH:mm");
    }

    public String converterData(Date date) {
        return converterDatas(date, "dd/MM/yyyy");
    }


    public String converterDatas(Date date, String formato){
        DateFormat dateFormat = new SimpleDateFormat(formato);
        String data=dateFormat.format(date);
        return data;
    }

    private String retornarNomeImagem(String caminho){
        String[] nomes=caminho.split("/");
        String nome=nomes[nomes.length-1];
        Log.d("Retornar evento", nome);
        return nome;
    }


}
