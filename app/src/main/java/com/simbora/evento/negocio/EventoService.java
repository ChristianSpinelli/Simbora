package com.simbora.evento.negocio;

import com.simbora.evento.dominio.Evento;
import com.simbora.evento.dominio.TipoDeEvento;
import com.simbora.evento.persistencia.EventoDAO;

import java.util.ArrayList;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoService {

    EventoDAO eventoDAO=new EventoDAO();

    public ArrayList<Evento> retornarEventos(String url){
        return eventoDAO.retornarEventos(url);
    }

    public ArrayList<Evento> retornarEventosPorTipo(String url, TipoDeEvento tipoDeEvento){
        return eventoDAO.retornarEventos(url, tipoDeEvento);
    }


    public boolean inserirEvento(Evento evento, String url){

        return eventoDAO.inserirEvento(evento, url);
    }



}
