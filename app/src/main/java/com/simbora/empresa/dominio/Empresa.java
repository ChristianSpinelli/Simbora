package com.simbora.empresa.dominio;

import com.simbora.usuario.dominio.Usuario;

/**
 * Created by Demis on 04/06/15.
 */
public class Empresa {
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
