package com.simbora.dominio;

/**
 * Created by Krys on 11/05/2015.
 */
public enum TipoDeEvento {

    SHOW("Show"),
    TEATRO("Teatro"),
    CINEMA("Cinema"),
    FAMILIA("Família"),
    NIGTH("Nigth");

    private String descricao;

    TipoDeEvento(String descricao){
        this.descricao=descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
