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
import com.simbora.pessoa.dominio.Simbora;
import com.simbora.pessoa.dominio.negocio.PessoaService;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.usuario.persistencia.UsuarioDAO;
import com.simbora.util.dominio.Imagem;
import com.simbora.util.dominio.Url;
import com.simbora.util.persistencia.AbstractDAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoDAO extends AbstractDAO<Evento>{

    @Override
    public Evento consultar(Evento evento, String url) {
        return null;
    }

    @Override
    public Evento remover(Evento evento, String url) {
        return null;
    }

    private String retornarUrl(Evento evento, String url) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getJSON(url));
            JSONArray eventos = jsonObject.getJSONArray("eventos");

            //percorre a lista e adiciona os atributos no método retornarEvento
            for (int i = 0; i < eventos.length(); i++) {
                if (eventos.getJSONObject(i).getString("titulo").equals(evento.getNome())) {
                    return eventos.getJSONObject(i).getString("uri");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public boolean atualizar(Evento evento, String url) {
        String urlEvento=retornarUrl(evento, url);
        JSONObject jsonObject = converterParaJSON(evento);
        try {
            HttpResponse response;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 200);
            HttpConnectionParams.setSoTimeout(httpParameters, 200);
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPut putConnection = new HttpPut(urlEvento);
            putConnection.setHeader("json", jsonObject.toString());
            StringEntity se = new StringEntity(jsonObject.toString(), "UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            putConnection.setEntity(se);
            try {
                response = httpClient.execute(putConnection);
                String JSONString = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
                return true;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean inserir(Evento evento, String url) {
        JSONObject eventoAInserir = converterParaJSON(evento);
        postImagem(url, evento.getImagem().getCaminho());
        boolean inseriu=post(eventoAInserir,url);
        return inseriu;

    }

    @Override
    public ArrayList<Evento> consultar(String url) {
        ArrayList<Evento> listaEventos=new ArrayList<Evento>();
        try {
            //recebe um array de objetos JSON
            //o nome deste array é eventos
            JSONObject jsonObject=new JSONObject(getJSON(url));
            JSONArray eventos=jsonObject.getJSONArray("eventos");
            //percorre a lista e adiciona os atributos no método retornarEvento
            for (int i=0;i<eventos.length();i++)   {
                Evento evento=converterParaObjeto(eventos.getJSONObject(i));
                evento.setId(i+1);
                listaEventos.add(evento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro na lista de eventos");
        }
        return listaEventos;

    }

    @Override
    public Evento converterParaObjeto(JSONObject jsonObject) {
        Evento evento=new Evento();
        Endereco endereco=new Endereco();
        ArrayList<Horario> horarios=new ArrayList<Horario>();
        ArrayList<Preco> precos=new ArrayList<Preco>();
        ArrayList<TipoDeEvento> tiposDeEvento=new ArrayList<TipoDeEvento>();
        Simbora simbora=new Simbora();

        try {
            evento.setNome(jsonObject.getString("titulo"));
            evento.setDescricao(jsonObject.getString("descricao"));
            evento.setTelefone(jsonObject.getString("telefone"));

            //endereco
            JSONObject enderecoObject=jsonObject.getJSONArray("endereco").getJSONObject(0);
            endereco.setNome(enderecoObject.getString("nome"));
            endereco.setBairro(enderecoObject.getString("bairro"));
            endereco.setCep(enderecoObject.getString("cep"));
            endereco.setCidade(enderecoObject.getString("cidade"));
            endereco.setNumeroRua(enderecoObject.getString("numero"));
            endereco.setRua(enderecoObject.getString("rua"));

            //horarios
            JSONArray horariosObject=jsonObject.getJSONArray("horarios");
            for (int i=0;i<horariosObject.length();i++)   {
                Horario horario=new Horario(horariosObject.getJSONObject(i).getString("data"),horariosObject.getJSONObject(i).getString("horaInicio"),horariosObject.getJSONObject(i).getString("horaTermino"));
                horarios.add(horario);
            }

            JSONArray precosObject=jsonObject.getJSONArray("precos");
            for (int j=0;j<precosObject.length();j++){
                Preco preco=new Preco();
                preco.setNomeEntrada(precosObject.getJSONObject(j).getString("tipo"));
                preco.setValor(precosObject.getJSONObject(j).getDouble("preco"));
                precos.add(preco);
            }

            JSONArray tiposDeEventoObject=jsonObject.getJSONArray("tiposDeEvento");
            for (int j=0;j<tiposDeEventoObject.length();j++){
                for (TipoDeEvento t:TipoDeEvento.values()){
                    if(t.getDescricao().equals(tiposDeEventoObject.getJSONObject(j).getString("descricao"))){
                        tiposDeEvento.add(t);
                    }
                }
            }

            ArrayList<Pessoa> pessoasSimbora=new ArrayList<Pessoa>();

            PessoaService pessoaService=new PessoaService();
            try{
                JSONArray jsonArraySimbora=jsonObject.getJSONArray("simbora");
                for(int k=0; k<jsonArraySimbora.length();k++){
                    Pessoa pessoa=new Pessoa();
                    String idPessoa=jsonArraySimbora.getJSONObject(k).getString("idPessoa");
                    pessoa=pessoaService.consultarPessoa(Url.getPessoas()+"/"+idPessoa);

                    pessoasSimbora.add(pessoa);
                }

            }
            catch (Exception e){
                e.getMessage();
            }
            simbora.setPessoas(pessoasSimbora);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario=null;
            String idUsuario=jsonObject.getString("idCriador");
            usuario=usuarioDAO.consultarPorId(idUsuario);

            Bitmap bitmap = null;
            byte[] bitmapdata=null;

            //pega a imagem do servidor e converte em um bitmap
            try {
                InputStream in = new java.net.URL(Url.getIp()+":5000/"+jsonObject.getString("imagem")).openStream();
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
            evento.setImagem(new Imagem(jsonObject.getString("imagem"), bitmapdata));
            evento.setSimbora(simbora);
            evento.setCriador(usuario);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro no json do evento");
        }
        return evento;
    }

    @Override
    public JSONObject converterParaJSON(Evento evento) {
        JSONObject jsonObjectEvento=new JSONObject();
        JSONArray jsonArrayEndereco=new JSONArray();
        JSONArray jsonArrayHorarios=new JSONArray();
        JSONArray jsonArrayPrecos=new JSONArray();
        JSONArray jsonArrayTiposDeEvento=new JSONArray();
        JSONArray jsonArraySimbora=new JSONArray();

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

            jsonObjectEvento.put("idCriador",evento.getCriador().getId());

            if(evento.getSimbora().getPessoas()!=null){
                for (int i=0;i<evento.getSimbora().getPessoas().size();i++){
                    JSONObject jsonObjectIdUsuario=new JSONObject();
                    jsonObjectIdUsuario.put("idPessoa", evento.getSimbora().getPessoas().get(i).getId());
                    jsonArraySimbora.put(i,jsonObjectIdUsuario);
                }
            }
            jsonObjectEvento.put("imagem",nomeImagem);

            jsonObjectEvento.put("simbora", jsonArraySimbora);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObjectEvento;
    }

    public ArrayList<Evento> consultar(String url, TipoDeEvento tipo){
        try {
            JSONObject jsonObject=new JSONObject(getJSON(url));
            JSONArray eventos=jsonObject.getJSONArray("eventos");
            ArrayList<Evento> listaEventos=new ArrayList<Evento>();
            //criar um método para esta funcionalidade
            //verifica se um tipo est´no evento
             for (int i=0;i<eventos.length();i++){
                 JSONArray tiposDeEventoObject=eventos.getJSONObject(i).getJSONArray("tiposDeEvento");
                 for (int j=0;j<tiposDeEventoObject.length();j++) {
                     if (tipo.getDescricao().equals(tiposDeEventoObject.getJSONObject(j).getString("descricao"))) {
                         Evento evento = converterParaObjeto(eventos.getJSONObject(i));
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

    public ArrayList<Evento> consultarRolandoAgora(String url){
        ArrayList<Evento> listaEventos=new ArrayList<Evento>();
        ArrayList<Evento> listaEventosRolandoAgora=new ArrayList<Evento>();

        try {
            listaEventos=consultar(url);
            for(Evento evento: listaEventos){
                if(evento.isRolandoAgora()){
                    listaEventosRolandoAgora.add(evento);
                }
            }
            //corrigir índices
            for(int i=0;i<listaEventosRolandoAgora.size();i++){
                listaEventosRolandoAgora.get(i).setId(i+1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEventosRolandoAgora;
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
        String nomeSemEspaco=nome.replace(" ","_");
        Log.d("Retornar evento", nomeSemEspaco);
        return nomeSemEspaco;
    }


}
