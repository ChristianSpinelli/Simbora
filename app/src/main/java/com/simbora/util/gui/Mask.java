package com.simbora.util.gui;

/**
 * Created by Demis e Lucas on 09/04/2015.
 */
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.Calendar;

//classe que tira os caracteres do cpf, data e hora
public abstract class Mask {

   static String cleanStr(CharSequence s){
       String clean = s.toString().replaceAll("[^\\d.]", "");
       return clean;
   }

    public static TextWatcher insert(final MaskType maskType, final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();



            private void mascararTelefone(CharSequence s){
                this.mascarar(s,MaskType.TELEFONE.getMask());
            }
            private void mascararHora(CharSequence s){
                this.mascarar(s,MaskType.HORA.getMask());
            }

            private void mascarar(CharSequence s,String mask){
                String str = Mask.cleanStr(s);
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());
            }

            private void mascararData(CharSequence s){
                if (!s.toString().equals(current)) {
                    String clean = Mask.cleanStr(s);
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

                        if ((mon -1)<cal.get(Calendar.MONTH)){
                            mon = cal.get(Calendar.MONTH)+1;

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
                    editText.setText(current);
                    editText.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            private void mascarar(CharSequence s){
                switch (maskType){
                    case DATA:
                         mascararData(s);
                        break;
                    case HORA:
                        mascararHora(s);
                        break;
                    case TELEFONE:
                        mascararTelefone(s);
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
