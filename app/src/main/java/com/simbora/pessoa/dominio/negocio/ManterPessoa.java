package com.simbora.pessoa.dominio.negocio;

import com.simbora.pessoa.dominio.perssistencia.PessoaDAO;

/**
 * Created by Krys on 03/06/2015.
 */
public class ManterPessoa {

    private PessoaDAO pessoaDAO = new PessoaDAO();

    public String retornarUrl(String email){
        String url = pessoaDAO.retornarUrl(email);
        return url;
    }
}
