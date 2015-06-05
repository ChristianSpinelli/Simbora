package com.simbora.pessoa.dominio;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public class Simbora {

    private int qtdSimboras;

    public void setQtdSimboras(int qtdSimboras) {
        this.qtdSimboras = qtdSimboras;
    }

    public void setPessoas(ArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

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

    public boolean deuSimbora(Pessoa pessoa){
       for(Pessoa p: pessoas){
           if(p.getCpf().equals(pessoa.getCpf())){
               return true;
           }

       }
           return false;
    }

    public void desistir(Pessoa pessoa) {
        for (Pessoa p : pessoas) {
            if (p.getCpf().equals(pessoa.getCpf())) {
                pessoas.remove(p);
            }
        }
    }
}
