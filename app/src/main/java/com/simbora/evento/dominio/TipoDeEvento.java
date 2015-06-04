package com.simbora.evento.dominio;

import com.simbora.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Krys on 11/05/2015.
 */
public enum TipoDeEvento {

    TODOS("Todos", R.drawable.todos_icone, 1),
    ROLANDO_AGORA("Rolando Agora", R.drawable.rolando_agora_icone, 2),
    SHOW("Show", R.drawable.show_icone, 3),
    TEATRO("Teatro", R.drawable.teatro_icone, 4),
    CINEMA("Cinema", R.drawable.cinema_icone, 5),
    FAMILIA("Família", R.drawable.familia_icone, 6),
    RELIGIOSO("Religioso", R.drawable.religioso_icone, 7),
    EDUCACIONAL("Educacional", R.drawable.educacional_icone, 8),
    TECNOLOGIA("Tecnologia", R.drawable.tecnologia_icone, 9),
    GAMES("Games", R.drawable.games_icone, 10),
    ANIMES("Animes", R.drawable.animes_icone, 11);;


    private String descricao;
    private int imagem;
    private int id;

    TipoDeEvento(String descricao, int imagem, int id) {
        this.descricao = descricao;
        this.imagem = imagem;
        this.id = id;
    }

    public String getNome() {
        switch (this) {
            default:
                String str1 = this.name().substring(0, 1) + this.name().toLowerCase().substring(1);

                return str1;
        }
    }

    public static List<TipoDeEvento> strsToTipoDeEvento(List<String> strsTipoDeEvento) {
        if (strsTipoDeEvento != null) {
            List<TipoDeEvento> tiposDeEvento = new ArrayList<TipoDeEvento>();

                for (TipoDeEvento tipoDeEvento:values()){
                    if (!strsTipoDeEvento.contains(tipoDeEvento.getNome())){
                        tiposDeEvento.add(tipoDeEvento);

                    }
                }

            return tiposDeEvento;
        } else {
            return null;
        }
    }

    public static String[] getNomes() {
        List<String> nomes = new ArrayList<String>();

        for (TipoDeEvento tipoDeEvento : values()) {
            nomes.add(tipoDeEvento.getNome());
        }

        String[] nomesArray = new String[nomes.size()];
        return nomes.toArray(nomesArray);
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
