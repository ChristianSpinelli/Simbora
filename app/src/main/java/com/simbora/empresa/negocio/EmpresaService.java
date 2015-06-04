package com.simbora.empresa.negocio;

import com.simbora.empresa.dominio.Empresa;
import com.simbora.empresa.persistencia.EmpresaDAO;
import com.simbora.util.dominio.Url;

/**
 * Created by Demis on 04/06/15.
 */
public class EmpresaService {
    EmpresaDAO empresaDAO = new EmpresaDAO();

    public Empresa consultarEmpresa(Empresa empresa){
        return empresaDAO.consultar(empresa, Url.getIp("empresas"));
    }
}
