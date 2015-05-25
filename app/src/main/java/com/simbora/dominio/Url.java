package com.simbora.dominio;

/**
 * Created by Demis e Lucas on 25/05/2015.
 */

//classe que detém o IP estático para conectarmos ao WebService
 //outras funcionalidades podem ser adicionadas futuramente
public class Url {
    public static String getIp() {
        return "http://"+ip;
    }

    public static void setIp(String ip) {
        Url.ip = ip;
    }

    private static String ip;
}
