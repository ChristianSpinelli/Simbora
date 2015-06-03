package com.simbora.usuario.negocio;

import com.simbora.usuario.dominio.Usuario;
import com.simbora.usuario.persistencia.UsuarioDAO;
import com.simbora.util.dominio.Url;

/**
 * Created by Demis on 03/06/15.
 */
public class UsuarioService {

    UsuarioDAO usuarioDAO=new UsuarioDAO();
    public Usuario consultarUsuario(Usuario usuario){
        return usuarioDAO.consultar(usuario, Url.getUsuarios());
    }
    public Usuario validarLogin(){
        return null;
    }

}
