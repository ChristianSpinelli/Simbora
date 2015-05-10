package com.simbora.dominio;

/**
 * Created by Demis e Lucas on 11/04/2015.
 */
public class TipoEvento {

    //TODO:
    //mudar o TipoEvento para um Enum e jogá-lo numa lista na classe Evento
    private String nome;
    private int icone;
    private String id;

    private static final String EVENTO_SHOW="Show";
    private static final String EVENTO_TEATRO="Teatro";
    private static final String EVENTO_ESPORTES="Esporte";
    private static final String EVENTO_OUTROS="Outros";
    private static final String EVENTO_FAMILIA="Familia";
    private static final String EVENTO_PALESTRA="Palestra";
    private static final String EVENTO_ENCONTRO="Encontro";
    private static final String EVENTO_NIGHT="Night";

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
