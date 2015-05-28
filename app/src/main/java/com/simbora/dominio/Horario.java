package com.simbora.dominio;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Demis e Lucas on 10/05/2015.
 */
public class Horario {

    private Date horaInicio;
    private Date horaTermino;
    private Date data;

    public Horario(String data,String horaInicio,String horaTermino){
        try {
            this.setHoraInicio(horaInicio);
            this.setHoraTermino(horaTermino);
            this.setData(data);
        } catch (Exception e) {
            Log.d("Erro inst 'horario' ",e.getMessage());
            e.printStackTrace();
        }
    }

    public Horario(){}

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setData(String data) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date hDate = dateFormat.parse(data);
        this.setData(hDate);
    }
    public void setHoraInicio(String horaInicio) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date hDate = dateFormat.parse(horaInicio);
        this.setHoraInicio(hDate);
    }
    public void setHoraTermino(String Termino) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date hDate = dateFormat.parse(Termino);
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
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


}
