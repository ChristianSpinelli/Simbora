package com.simbora.pessoa.dominio.negocio;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.pessoa.dominio.persistencia.PessoaDAO;
import com.simbora.util.dominio.Url;

/**
 * Created by Demis on 03/06/15.
 */
public class PessoaService {
    PessoaDAO pessoaDAO=new PessoaDAO();
    public Pessoa consultarPessoa(Pessoa pessoa){
        return pessoaDAO.consultar(pessoa, Url.getUsuarios());
    }

    public Pessoa consultarPessoa(String url){
        return pessoaDAO.consultarPessoa(url);
    }
}
