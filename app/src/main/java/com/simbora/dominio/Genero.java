package com.simbora.dominio;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public enum Genero {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");

    private String descricao;
    public String getDescricao(){
        return descricao;
    }

    Genero(String descricao){
        this.descricao=descricao;
    }
}
