package com.simbora.evento.dominio;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.pessoa.dominio.Simbora;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.util.dominio.Imagem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//classe Evento
public class Evento {

    private static int idEvento;
    private int id;
    private Imagem imagem;
    private String nome;
    private String descricao;
    private String telefone;
    private ArrayList<Preco> precos;
    private Endereco endereco;
    private ArrayList<Horario> horarios;
    private List<TipoDeEvento> tiposDeEvento;
    private Simbora simbora;
    private Usuario criador;
    private static ArrayList<Evento> listaEventosPorTipo;



    public Evento() {

    }

    public Evento(String nome, ArrayList<Horario> horarios, Imagem image, String descricao, String telefone, int simboras, ArrayList<Preco> precos, Endereco endereco,List<TipoDeEvento> tiposDeEvento) {
        this.horarios = horarios;
        this.nome = nome;
        this.imagem = image;
        this.telefone = telefone;
        this.precos = precos;
        this.endereco=endereco;
        this.descricao = descricao;
        this.tiposDeEvento = tiposDeEvento;
    }

    public static ArrayList<Evento> getListaEventosPorTipo() {
        return listaEventosPorTipo;
    }

    public static void setListaEventosPorTipo(ArrayList<Evento> listaEventosPorTipo) {
        Evento.listaEventosPorTipo = listaEventosPorTipo;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }


    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public List<TipoDeEvento> getTiposDeEvento() {
        return tiposDeEvento;
    }

    public void setTiposDeEvento(List<TipoDeEvento> tiposDeEvento) {
        this.tiposDeEvento = tiposDeEvento;
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

     public ArrayList<Preco> getPrecos() {
        return precos;
    }

    public void setPrecos(ArrayList<Preco> precos) {
        this.precos = precos;
    }


    public boolean isRolandoAgora(){
        Date dateAtual=new Date();
        for(Horario horario: this.horarios){
            if(dateAtual.after(horario.getHoraInicio()) && (horario.getHoraTermino().after(dateAtual))){
                return true;
            }
        }
        return false;
    }

}