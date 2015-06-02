package com.simbora.pessoa.dominio.perssistencia;

import android.util.Log;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.pessoa.dominio.Usuario;
import com.simbora.util.dominio.Imagem;
import com.simbora.util.dominio.Url;

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

/**
 * Created by Krys on 02/06/2015.
 */
public class PessoaDAO {

    private Pessoa retornarPessoa(JSONObject json) {

        Pessoa pessoa = new Pessoa();
        Usuario usuario = new Usuario();
        Imagem imagem = new Imagem();
        JSONObject generoObject = new JSONObject();
        JSONObject usuarioObject = new JSONObject();

        try {
            pessoa.setCpf(json.getString("cpf"));
            pessoa.setDataNascimento(json.getString("dataNascimento"));

            generoObject = json.getJSONArray("genero").getJSONObject(0);
            pessoa.setGenero(generoObject.getString("descricao"));

            usuarioObject = json.getJSONArray("usuario").getJSONObject(0);
            usuario.setEmail(usuarioObject.getString("email"));
            usuario.setMascates(usuarioObject.getLong("mascates"));
            usuario.setNome(usuarioObject.getString("nome"));
            usuario.setSenha(usuarioObject.getString("senha"));
            imagem.setCaminho(usuarioObject.getString("imagem"));
            usuario.setImagem(imagem);

            pessoa.setUsuario(usuario);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pessoa;


    }

    public Pessoa retornarPessoa(String url){
        Pessoa pessoa = new Pessoa();
        JSONObject pessoaObect = null;
        try {
            pessoaObect = new JSONObject(getJSON(url));
            pessoa = retornarPessoa(pessoaObect.getJSONObject("pessoa"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pessoa;

    }

    public String retornarUrl(String email){
        String url = "";
        String urlPessoas = "";
        Url urlPagina = new Url();
        urlPessoas = urlPagina.getIp("pessoas");
        Log.d("Teste", urlPessoas);

        try {
            JSONObject jsonObject = new JSONObject(getJSON(urlPessoas));
            JSONArray pessoas = jsonObject.getJSONArray("pessoas");
            JSONObject usuario = new JSONObject();
            for (int i=0;i<=pessoas.length();i++){
               usuario =  pessoas.getJSONObject(i).getJSONArray("usuario").getJSONObject(0);
                Log.d("Teste",usuario.getString("email"));
                if(usuario.getString("email").equals(email)){
                   url = pessoas.getJSONObject(i).getString("uri");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
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

}

