package com.simbora.dominio;


import com.simbora.gui.DummyContent;

import java.util.ArrayList;

//classe Evento
public class Evento {

    //TODO:
    //apagar o atributo hora, deixando só horários. Não apaguei ainda pois o código não funciona sem este atributo
    //na tela de eventos, ele é usado. Tempos que dar um jeito de pegar só o primeiro horário da lista de horários para que a app continue funcionando

    //apagar a data
    //talvez tornar endereço uma lasse tbm
    //tonar imagem um array de bytes
    //tornar preco float
    private static int idEvento;
    private int id;
    private int image;
    private String nome;
    private String data;
    private String hora;
    private String descricao;
    private String telefone;
    private String preco;
    private String endereco;
    private ArrayList<Horario> horarios;

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int idEvento) {
        this.id = idEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public static int getIdEvento() {
        return idEvento;
    }

    public static void setIdEvento(int idEvento) {
        Evento.idEvento = idEvento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }
    public Evento() {

    }

    public Evento(String nome, String data, String hora, int image, String descricao, String telefone, int simboras, String preco, String endereco) {
        this.data = data;
        this.hora = hora;
        this.nome = nome;
        this.image = image;
        this.telefone = telefone;
        this.preco = preco;
        this.setEndereco(endereco);
        this.descricao = descricao;
    }


    public static ArrayList<String> getListaTitulosEventos(){

           ArrayList<String> titulosEventos=new ArrayList<String>(10);

           for (Evento e : DummyContent.ITEMS){

                  titulosEventos.add(e.getNome());

           }

             return titulosEventos;



    }

}