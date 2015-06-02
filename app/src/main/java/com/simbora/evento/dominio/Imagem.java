package com.simbora.evento.dominio;

/**
 * Created by Demis e Lucas on 29/05/2015.
 */
public class Imagem {

    private String caminho;
    private byte[] imagemByte;

    public byte[] getImagemByte() {
        return imagemByte;
    }

    public void setImagemByte(byte[] imagemByte) {
        this.imagemByte = imagemByte;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Imagem(){

    }
    public Imagem(String caminho, byte[] imagemByte){
        this.caminho=caminho;
        this.imagemByte=imagemByte;
    }
}
