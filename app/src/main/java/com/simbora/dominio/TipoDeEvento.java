package com.simbora.dominio;

import com.simbora.R;

/**
 * Created by Krys on 11/05/2015.
 */
public enum TipoDeEvento {

    TODOS("Todos",R.mipmap.ic_launcher, 1),
    ROLANDO_AGORA("Rolando Agora", R.mipmap.ic_launcher,2),
    SHOW("Show", R.drawable.show_icone,3),
    TEATRO("Teatro",R.drawable.teatro_icone,4),
    CINEMA("Cinema",R.drawable.cinema_icone,5),
    FAMILIA("Família",R.mipmap.ic_launcher,6),
    RELIGIOSO("Religioso",R.mipmap.ic_launcher,7),
    EDUCACIONAL("Educacional",R.mipmap.ic_launcher,8),
    TECNOLOGIA("Tecnologia",R.mipmap.ic_launcher,9),
    GAMES("Games",R.mipmap.ic_launcher,10),
    ANIMES("Animes",R.mipmap.ic_launcher,11);;


    private String descricao;
    private int imagem;
    private int id;

    TipoDeEvento(String descricao, int imagem, int id){
        this.descricao=descricao;
        this.imagem=imagem;
        this.id=id;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getImagem() {
        return imagem;
    }

    public int getId() {
        return id;
    }
}
