package com.simbora.util.gui;

/**
 * Created by root on 03/06/15.
 */
public enum MaskType {
    DATA,HORA,TELEFONE ;

public String getMask(){
    switch (this) {
        case TELEFONE:
            return "(##)#####-####";
        case HORA:
            return "##:##";
        case DATA:
            return "##/##/####";
        default:
            return null;
    }

}

}
