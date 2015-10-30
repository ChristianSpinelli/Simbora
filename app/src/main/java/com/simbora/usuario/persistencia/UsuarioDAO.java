package com.simbora.usuario.persistencia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.util.dominio.Imagem;
import com.simbora.util.dominio.Url;
import com.simbora.util.persistencia.AbstractDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Demis on 03/06/15.
 */
public class UsuarioDAO extends AbstractDAO<Usuario>{


    /** consulta por email e senha */
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


            }

            return usuarioARetornar;


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
    public boolean atualizar(Usuario usuario, String url) {
        return false;
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
            usuario.setEmail(jsonObject.getString("email"));
            usuario.setMascates(jsonObject.getLong("mascates"));
            usuario.setNome(jsonObject.getString("nome"));
            usuario.setSenha(jsonObject.getString("senha"));
            usuario.setId(jsonObject.getString("uri").split("/")[7]);

            //add imagem no usuario
            Bitmap bitmap;
            byte[] bitmapdata=null;
            try {
                InputStream in = new java.net.URL(Url.getIp()+"/"+jsonObject.getString("imagem")).openStream();
                bitmap = BitmapFactory.decodeStream(in);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
                bitmapdata = blob.toByteArray();

            } catch (Exception e) {
                Log.e("Erro na imagem", e.getMessage());
                e.printStackTrace();
            }

            usuario.setImagem(new Imagem(jsonObject.getString("imagem"), bitmapdata));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public JSONObject converterParaJSON(Usuario usuario) {
        return null;
    }

    public Usuario consultarPorId(String id){
        Usuario usuario=null;
        try {
            JSONObject jsonObjectUsuario=new JSONObject(getJSON(Url.getUsuarios()+"/"+id)).getJSONObject("usuario");
            usuario=converterParaObjeto(jsonObjectUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
