package com.simbora.evento.negocio;

import com.simbora.evento.dominio.Evento;
import com.simbora.evento.dominio.TipoDeEvento;
import com.simbora.evento.persistencia.EventoDAO;
import com.simbora.util.dominio.Url;

import java.util.ArrayList;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoService {

    EventoDAO eventoDAO=new EventoDAO();

    public ArrayList<Evento> retornarEventos(TipoDeEvento tipoDeEvento){
        if(tipoDeEvento.equals(TipoDeEvento.ROLANDO_AGORA)){
            return eventoDAO.retornarEventosRolandoAgora(Url.getIp("eventos"));
        }
        if(tipoDeEvento.equals(TipoDeEvento.TODOS)){
            return eventoDAO.retornarEventos(Url.getIp("eventos"));
        }
        return eventoDAO.retornarEventos(Url.getIp("eventos"));
    }
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
