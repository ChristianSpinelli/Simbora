package com.simbora.dominio;

/**
 * Created by Krys on 11/05/2015.
 */
public class Endereco {

    private String rua,numeroRua, bairro, cidade,pais,cep,
            nome;
        //nome é para facilitar o endereço, para colocarmos na tela
        //é mais fácil colocarmos na tela "Teatro Boa Vista" ou R. Dom Bosco, 551 - Boa Vista, Recife - PE, 50070-070, Brasil?
    public Endereco(){}

    public Endereco(String cidade, String bairro, String rua, String numero, String local){
        this.bairro = bairro;
        this.cidade = cidade;
        this.rua = rua;
        this.numeroRua = numero;
        this.nome = local;
        this.cep="";
        this.pais="";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumeroRua() {
        return numeroRua;
    }

    public void setNumeroRua(String numeroRua) {
        this.numeroRua = numeroRua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
