package com.simbora.dominio;

import java.util.ArrayList;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public class Simbora {

    private int qtdSimboras;
    private ArrayList<Pessoa> pessoas;

    public int getQtdSimboras() {
        return qtdSimboras;
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public void darSimbora (Pessoa pessoa){
        this.pessoas.add(pessoa);
        qtdSimboras +=1;

    }
}
