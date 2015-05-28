package com.simbora.negocio;

import com.simbora.dominio.Evento;
import com.simbora.persistencia.EventoDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Demis e Lucas on 24/05/2015.
 */
public class EventoService {

    EventoDAO eventoDAO=new EventoDAO();

    public ArrayList<Evento> retornarEventos(String url){
        return eventoDAO.retornarEventos(url);
    }

    public boolean inserirEvento(Evento evento, String url){

        return eventoDAO.inserirEvento(evento, url);
    }


}
