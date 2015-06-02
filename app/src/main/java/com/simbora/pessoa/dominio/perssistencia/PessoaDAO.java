package com.simbora.pessoa.dominio.perssistencia;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.pessoa.dominio.Usuario;
import com.simbora.util.dominio.Imagem;

import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject pessoaObect = new JSONObject();
        try {
            pessoaObect.get(url);
            pessoa = retornarPessoa(pessoaObect);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pessoa;

    }

}

