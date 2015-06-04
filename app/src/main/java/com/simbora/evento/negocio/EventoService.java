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
            return eventoDAO.consultarRolandoAgora(Url.getIp("eventos"));
        }
        if(tipoDeEvento.equals(TipoDeEvento.TODOS)){
            return eventoDAO.consultar(Url.getIp("eventos"));
        }
        return eventoDAO.consultar(Url.getIp("eventos"),tipoDeEvento);
    }
    public ArrayList<Evento> retornarEventos(String url){
        return eventoDAO.consultar(url);
    }


    public boolean inserirEvento(Evento evento, String url){
        //se nada for selecionado no Spinenr do tipon de eventos, é configurado como todos
        if(evento.getTiposDeEvento()==null){
            ArrayList<TipoDeEvento> tiposDeEvento=new ArrayList<TipoDeEvento>();
            tiposDeEvento.add(TipoDeEvento.TODOS);
            evento.setTiposDeEvento(tiposDeEvento);
        }
        return eventoDAO.inserir(evento, url);
    }
    public void atualizar(Evento evento, String url){
        eventoDAO.atualizar(evento,url);
    }



}
