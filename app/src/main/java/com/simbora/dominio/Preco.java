package com.simbora.dominio;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class Preco {
    //nomeEntrada é o nome dado ao tipo de Ingresso
    //ex: camarote, pista, geral, superior, etc...
    private String nomeEntrada;
    private double valor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNomeEntrada() {

        return nomeEntrada;
    }

    public Preco(){
        this.nomeEntrada="";
        this.valor=0.0;
    }
    public void setNomeEntrada(String nomeEntrada) {
        this.nomeEntrada = nomeEntrada;
    }
}
