package com.simbora.empresa.dominio;

import com.simbora.usuario.dominio.Usuario;

/**
 * Created by Demis on 04/06/15.
 */
public class Empresa {
    public static Empresa getEmpresaLogada() {
        return empresaLogada;
    }

    public static void setEmpresaLogada(Empresa empresaLogada) {
        Empresa.empresaLogada = empresaLogada;
    }

    private static Empresa empresaLogada;
    private String cnpj;
    private Usuario usuario;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
