package com.simbora.negocio;

import com.simbora.dominio.Evento;
import com.simbora.persistencia.EventoDAO;

import java.util.ArrayList;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoService {

    public ArrayList<Evento> retornarEventos(String url){
        EventoDAO eventoDAO=new EventoDAO();
        return eventoDAO.retornarEventos(url);
    }
}
