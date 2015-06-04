package com.simbora.empresa.persistencia;

import android.util.Log;

import com.simbora.empresa.dominio.Empresa;
import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.util.dominio.Url;
import com.simbora.util.persistencia.AbstractDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demis on 04/06/15.
 */
public class EmpresaDAO extends AbstractDAO<Empresa>{
    @Override
    public Empresa consultar(Empresa empresa, String url) {
        JSONObject jsonEmpresa=consultarUsuario(empresa.getUsuario());
        if(jsonEmpresa!=null){
            Empresa empresaARetornar=converterParaObjeto(jsonEmpresa);
            empresaARetornar.setUsuario(empresa.getUsuario());
            Log.d("Empresa",empresaARetornar.getCnpj()+ " " + empresaARetornar.getUsuario().getNome());
            return empresaARetornar;
        }
        return null;

    }

    @Override
    public Empresa remover(Empresa empresa, String url) {
        return null;
    }

    @Override
    public void atualizar(Empresa empresa, String url) {

    }

    @Override
    public boolean inserir(Empresa empresa, String url) {
        return false;
    }

    @Override
    public ArrayList<Empresa> consultar(String url) {
        return null;
    }

    @Override
    public Empresa converterParaObjeto(JSONObject jsonObject) {
        Empresa empresa=new Empresa();
        try {
            empresa.setCnpj(jsonObject.getString("cnpj"));
            return empresa;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JSONObject converterParaJSON(Empresa empresa) {
        return null;
    }

    /** Verifica se há um usuário na lista de empresas*/
    public JSONObject consultarUsuario(Usuario usuario){
       JSONObject jsonEmpresa=null;
        try {
            JSONObject jsonObject = new JSONObject(getJSON(Url.getIp("empresas")));
            JSONArray pessoas = jsonObject.getJSONArray("empresas");
            for (int i=0;i<pessoas.length();i++){
                String idUsuario=pessoas.getJSONObject(i).getString("idUsuario");
                if(idUsuario.equals(usuario.getId())){
                    jsonEmpresa = pessoas.getJSONObject(i);
                    return jsonEmpresa;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
