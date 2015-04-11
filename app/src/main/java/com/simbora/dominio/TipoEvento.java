package com.simbora.dominio;

/**
 * Created by Demis e Lucas on 11/04/2015.
 */
public class TipoEvento {
    private String nome;
    private int icone;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcone() {
        return icone;
    }

    public void setIcone(int icone) {
        this.icone = icone;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
