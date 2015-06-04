package com.simbora.util.gui;

/**
 * Created by Demis e Lucas on 09/04/2015.
 */
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.simbora.util.dominio.Mascara;

import java.util.Calendar;
import java.util.Date;

//classe que tira os caracteres do cpf, data e hora
public abstract class Mask {

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll("[:]", "");
    }

    public static TextWatcher insert(final Mascara mascara, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            private Calendar calendario = Calendar.getInstance();

            

            private void mascararData(CharSequence s){
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //Validação da data

                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        /*if(mon > 12) mon = 12;
                        Date data = new Date();*/

                        Log.d("Ano_atual",""+cal.get(Calendar.YEAR));
                        Log.d("Mês_atual",""+(cal.get(Calendar.MONTH)+1));
                        Log.d("Dia_atual",""+cal.get(Calendar.DAY_OF_MONTH));

                        if (year<cal.get(Calendar.YEAR) ){
                            year = cal.get(Calendar.YEAR);


                        }else if (year>cal.getActualMaximum(Calendar.YEAR)){
                            year = cal.getActualMaximum(Calendar.YEAR);
                        }
                        cal.set(Calendar.YEAR, year);

                        if ((mon -1)<calendario.get(Calendar.MONTH)){
                            mon = calendario.get(Calendar.MONTH)+1;

                        }else if ((mon-1)>cal.getActualMaximum(Calendar.MONTH)){
                            mon = cal.get(Calendar.MONTH)+1;
                        }

                        cal.set(Calendar.MONTH, mon-1);

                        if (day<cal.get(Calendar.DAY_OF_MONTH)){
                            day = cal.get(Calendar.DAY_OF_MONTH);
                        }else if (day>cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
                            day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                        }

                        cal.set(Calendar.DAY_OF_MONTH,day);

                        Log.d("Ano_checked",""+cal.get(Calendar.YEAR));
                        Log.d("Mês_checked",""+(cal.get(Calendar.MONTH)+1));
                        Log.d("Dia_checked",""+cal.get(Calendar.DAY_OF_MONTH));
                        //day = (day > calendario.getActualMaximum(Calendar.DATE))? calendario.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    ediTxt.setText(current);
                    ediTxt.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            private void mascarar(CharSequence s){
                switch (mascara){
                    case DATA:
                         mascararData(s);
                        break;
                    case HORA:
                        break;
                    case TELEFONE:
                        break;
                    default:
                }

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                        this.mascarar(s);

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

}
