package com.simbora.util.dominio;

/**
 * Created by Demis e Lucas on 25/05/2015.
 */

/**classe que detém o IP estático e as URLs para conectarmos ao WebService*/

public class Url {

    private static String ip;
    public static final String EVENTOS=":5000/todo/api/v1.0/eventos";
    public static final String USUARIOS=":5000/todo/api/v1.0/usuarios";
    public static final String PESSOAS=":5000/todo/api/v1.0/pessoas";

    public static String getEventos() {
        return getIp()+EVENTOS;
    }

    public static String getUsuarios() {
        return getIp()+USUARIOS;
    }

    public static String getPessoas() {
        return getIp()+PESSOAS;
    }

    public static String getIp() {
        return "http://"+ip;
    }

    public static String getIp(String pagina){
        return "http://"+ip+":5000/todo/api/v1.0/"+pagina;
    }

    public static void setIp(String ip) {
        Url.ip = ip;
    }
}
