package com.simbora.pessoa.dominio;

import android.util.Log;

import com.simbora.usuario.dominio.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public class Pessoa {
    private static Pessoa pessoaLogada;

    public static Pessoa getPessoaLogada() {
        return pessoaLogada;
    }

    public static void setPessoaLogada(Pessoa pessoaLogada) {
        Pessoa.pessoaLogada = pessoaLogada;
    }

    private String cpf;
    private Date dataNascimento;
    private Genero genero;
    private Usuario usuario;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setDataNascimento(String dataNascimento){
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date novaDataNascimento = dateFormat.parse(dataNascimento);
            setDataNascimento(novaDataNascimento);
        } catch (ParseException e) {
            Log.d("Erro na conversão"," colocando data padrão" );
            try {
                Date dataPadrao=dateFormat.parse("01/01/2000");
                setDataNascimento(dataPadrao);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

        }

    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public void setGenero(String genero){
        for(Genero g:Genero.values()) {
            if (g.getDescricao().equals(genero)) {
                setGenero(g);
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}


