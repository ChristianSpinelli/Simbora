package com.simbora.evento.dominio;

import com.simbora.evento.negocio.EventoService;
import com.simbora.pessoa.dominio.Simbora;
import com.simbora.util.dominio.Url;

import java.util.ArrayList;

//classe Evento
public class Evento {

    private static int idEvento;
    private int id;
    private byte[] image;
    private String nome;
    private String descricao;
    private String telefone;
    private ArrayList<Preco> precos;
    private Endereco endereco;
    private ArrayList<Horario> horarios;
    private ArrayList<TipoDeEvento> tiposDeEvento;
    private Simbora simbora;

    private static ArrayList<Evento> listaEventosPorTipo;

    public static ArrayList<Evento> getListaEventosPorTipo() {
        return listaEventosPorTipo;
    }

    public static void setListaEventosPorTipo(ArrayList<Evento> listaEventosPorTipo) {
        Evento.listaEventosPorTipo = listaEventosPorTipo;
    }

    public Evento() {

    }

    public Evento(String nome, ArrayList<Horario> horarios, byte[] image, String descricao, String telefone, int simboras, ArrayList<Preco> precos, Endereco endereco) {
        this.horarios = horarios;
        this.nome = nome;
        this.image = image;
        this.telefone = telefone;
        this.precos = precos;
        this.endereco=endereco;
        this.descricao = descricao;
    }

    public ArrayList<TipoDeEvento> getTiposDeEvento() {
        return tiposDeEvento;
    }

    public void setTiposDeEvento(ArrayList<TipoDeEvento> tiposDeEvento) {
        this.tiposDeEvento=tiposDeEvento;
    }

    public Simbora getSimbora() {
        return simbora;
    }

    public void setSimbora(Simbora simbora) {
        this.simbora = simbora;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public static int getIdEvento() {
        return idEvento;
    }

    public static void setIdEvento(int idEvento) {
        Evento.idEvento = idEvento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }

    public static ArrayList<String> getListaTitulosEventos(){

           ArrayList<String> titulosEventos=new ArrayList<String>(10);

           for (Evento e : getListaEventosPorTipo()){

                  titulosEventos.add(e.getNome());

           }

             return titulosEventos;



    }

    //retorna eventos por tipo
    public static ArrayList<Evento> getEventos(String tipo){
        return null;
    }

    //retorna todos os eventos
    public static ArrayList<Evento> getEventos(){
        EventoService eventoService=new EventoService();

        setListaEventosPorTipo(eventoService.retornarEventos(Url.getIp()+":5000/todo/api/v1.0/eventos"));
        return getListaEventosPorTipo();
    }

    public ArrayList<Preco> getPrecos() {
        return precos;
    }

    public void setPrecos(ArrayList<Preco> precos) {
        this.precos = precos;
    }
}