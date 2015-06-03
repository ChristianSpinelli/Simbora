package com.simbora.evento.dominio;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public class Horario {

    private Date horaInicio;
    private Date horaTermino;


    public Horario(String data,String horaInicio,String horaTermino){
        try {
            this.setHoraInicio(data,horaInicio);
            this.setHoraTermino(data,horaTermino);

        } catch (Exception e) {
            Log.d("Erro inst 'horario' ",e.getMessage());
            e.printStackTrace();
        }
    }

    public Horario(){}

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setData(String data,String horaInicio) throws Exception {
        this.setHoraInicio(data,horaInicio);
    }
    public void setHoraInicio(String data, String horaInicio) throws Exception {
        String diaHoraInicio = data+" "+horaInicio;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date hDate = dateFormat.parse(diaHoraInicio);
        this.setData(hDate);
    }
    public void setHoraTermino(String data,String termino) throws Exception {
        String diaHoraFim = data+" "+termino;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date hDate = dateFormat.parse(diaHoraFim);
        this.setHoraTermino(hDate);
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
        return this.getHoraInicio();
    }

    public void setData(Date data) {
        this.setHoraInicio(data);
    }


}
