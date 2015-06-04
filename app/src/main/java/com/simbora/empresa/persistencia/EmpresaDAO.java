package com.simbora.empresa.persistencia;

import com.simbora.util.persistencia.AbstractDAO;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Demis on 04/06/15.
 */
public class EmpresaDAO extends AbstractDAO{
    @Override
    public Object consultar(Object o, String url) {
        return null;
    }

    @Override
    public Object remover(Object o, String url) {
        return null;
    }

    @Override
    public void atualizar(Object o, String url) {

    }

    @Override
    public boolean inserir(Object o, String url) {
        return false;
    }

    @Override
    public ArrayList consultar(String url) {
        return null;
    }

    @Override
    public Object converterParaObjeto(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject converterParaJSON(Object o) {
        return null;
    }
}
