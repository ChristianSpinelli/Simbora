package com.simbora.pessoa.dominio.persistencia;

import android.util.Log;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.util.dominio.Imagem;
import com.simbora.util.dominio.Url;
import com.simbora.util.persistencia.AbstractDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Krys on 02/06/2015.
 */
public class PessoaDAO extends AbstractDAO<Pessoa>{

    @Override
    public Pessoa consultar(Pessoa pessoa, String url) {
        String urlPessoa=retornarUrl(pessoa.getUsuario());
        if(urlPessoa!=null){
            Pessoa pessoaARetornar=consultarPessoa(urlPessoa);
            pessoaARetornar.setUsuario(pessoa.getUsuario());
            Log.d("Pessoa", pessoaARetornar.getDataNascimento().toString()+" "+pessoaARetornar.getUsuario().getNome());
            return pessoaARetornar;
        }
        return null;
    }

    @Override    public Pessoa remover(Pessoa pessoa, String url) {
        return null;
    }

    @Override
    public void atualizar(Pessoa pessoa, String url) {

    }

    @Override
    public boolean inserir(Pessoa pessoa, String url) {
        JSONObject inserirPessoa = converterParaJSON(pessoa);
        Boolean inseriu =post(inserirPessoa, url);
        return inseriu;
    }

    @Override
    public ArrayList<Pessoa> consultar(String url) {
        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
        Pessoa pessoa =new Pessoa();
        String urlPessoas = Url.getIp("pessoas");
        try {
            JSONObject jsonObject = new JSONObject(getJSON(urlPessoas));
            JSONArray jSonPessoas = jsonObject.getJSONArray("pessoas");
            for (int i=0;i<jSonPessoas.length();i++){
                JSONObject jsonPessoa = jSonPessoas.getJSONObject(i).getJSONArray("pessoa").getJSONObject(0);
                pessoa = converterParaObjeto(jsonPessoa);
                pessoas.add(pessoa);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pessoas;
    }

    @Override
    public Pessoa converterParaObjeto(JSONObject jsonObject) {
        Pessoa pessoa = new Pessoa();
        Imagem imagem = new Imagem();

        try {
            pessoa.setCpf(jsonObject.getString("cpf"));
            pessoa.setDataNascimento(jsonObject.getString("dataNascimento"));

            JSONObject generoObject = jsonObject.getJSONArray("genero").getJSONObject(0);
            pessoa.setGenero(generoObject.getString("descricao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pessoa;

    }

    @Override
    public JSONObject converterParaJSON(Pessoa pessoa) {
        return null;
    }

     public Pessoa consultarPessoa(String url){
        Pessoa pessoa = new Pessoa();
        JSONObject pessoaObect = null;
        try {
            pessoaObect = new JSONObject(getJSON(url));
            pessoa = converterParaObjeto(pessoaObect.getJSONObject("pessoa"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pessoa;

    }


    public String retornarUrl(String email){
        String url = null;
        String urlPessoas = "";
        urlPessoas = Url.getIp("pessoas");
        try {
            JSONObject jsonObject = new JSONObject(getJSON(urlPessoas));
            JSONArray pessoas = jsonObject.getJSONArray("pessoas");
            for (int i=0;i<pessoas.length();i++){
              JSONObject usuario =  pessoas.getJSONObject(i).getJSONArray("usuario").getJSONObject(0);
                if(usuario.getString("email").        equals(email)){
                   url = pessoas.getJSONObject(i).getString("uri");
                    return url;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String retornarUrl(Usuario usuario){
        String url = "";
        String urlPessoas = "";
        urlPessoas = Url.getIp("pessoas");
        try {
            JSONObject jsonObject = new JSONObject(getJSON(urlPessoas));
            JSONArray pessoas = jsonObject.getJSONArray("pessoas");
            for (int i=0;i<pessoas.length();i++){
                String idUsuario=pessoas.getJSONObject(i).getString("idUsuario");
                if(idUsuario.equals(usuario.getId())){
                    url = pessoas.getJSONObject(i).getString("uri");
                    return url;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}

