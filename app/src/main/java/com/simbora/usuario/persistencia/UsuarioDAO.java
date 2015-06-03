package com.simbora.usuario.persistencia;

import com.simbora.usuario.dominio.Usuario;
import com.simbora.util.persistencia.AbstractDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demis on 03/06/15.
 */
public class UsuarioDAO extends AbstractDAO<Usuario>{


    @Override
    public Usuario consultar(Usuario usuario, String url) {
        JSONObject jsonObject= null;
        Usuario usuarioARetornar=null;
        try {
            jsonObject = new JSONObject(getJSON(url));
            JSONArray jsonArrayUsuarios=jsonObject.getJSONArray("usuarios");

            for(int i=0;i<jsonArrayUsuarios.length();i++){
                String email =jsonArrayUsuarios.getJSONObject(i).getString("email");
                String senha=jsonArrayUsuarios.getJSONObject(i).getString("senha");

                if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)){
                    usuarioARetornar=converterParaObjeto(jsonArrayUsuarios.getJSONObject(i));
                }

                return usuarioARetornar;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Usuario remover(Usuario usuario, String url) {
        return null;
    }

    @Override
    public void atualizar(Usuario usuario, String url) {

    }

    @Override
    public boolean inserir(Usuario usuario, String url) {
        return false;
    }

    @Override
    public ArrayList<Usuario> consultar(String url) {
        return null;
    }

    @Override
    public Usuario converterParaObjeto(JSONObject jsonObject) {
        Usuario usuario=new Usuario();
        try {
            usuario.setEmail(jsonObject.getString("nome"));
            usuario.setMascates(jsonObject.getLong("mascates"));
            usuario.setNome(jsonObject.getString("nome"));
            usuario.setSenha(jsonObject.getString("senha"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public JSONObject converterParaJSON(Usuario usuario) {
        return null;
    }
}
