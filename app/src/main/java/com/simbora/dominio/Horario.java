package com.simbora.dominio;

import java.util.Date;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public class Horario {

    private Date horaInicio;
    private Date horaTermino;
    private Date data;

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
