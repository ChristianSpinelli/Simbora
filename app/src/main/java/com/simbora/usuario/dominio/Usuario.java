package com.simbora.usuario.dominio;

import com.simbora.util.dominio.Imagem;

/**
 * Created by Demis e Lucas on 09/04/2015.
 */
//classe que contém todos os atributos de um usuário
public class Usuario {
    private String email;
    private String senha;
    private long mascates;
    private String nome;
    private Imagem imagem;


 public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario(){

    }

    public Usuario(String nome, String email, String senha){
        setEmail(email);
        setSenha(senha);
        setNome(nome);
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public long getMascates() {
        return mascates;
    }

    public void setMascates(long mascates) {
        this.mascates = mascates;
    }

    //métodos do usuário sem ser ser gets e sets

    /**
    Método para adicionar mascates ao usuário, recebendo o número de mascates a ser adicionado como parâmetro
     */
    public void creditarMascates(int mascates){

    }

    /**
     Método para remover mascates do usuário, recebendo o número de mascates a ser removido como parâmetro
     */
    public void debitarMascates(int mascates){

    }
}


