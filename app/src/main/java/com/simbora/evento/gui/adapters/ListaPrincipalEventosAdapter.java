package com.simbora.evento.gui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simbora.R;
import com.simbora.evento.dominio.Evento;
import com.simbora.util.dominio.Url;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Demis e Lucas on 11/04/2015.
 */
//classe que monta a lista dos eventos
public class ListaPrincipalEventosAdapter extends ArrayAdapter<Evento>{
        private RelativeLayout relativeLayoutLista;
        private LayoutInflater inflater;
        private int resourceId;
        private Evento evento;
        public ListaPrincipalEventosAdapter(Context context, int resource, List<Evento> objects){
            super(context, resource, objects);
            this.inflater=LayoutInflater.from(context);
            this.resourceId=resource;
        }


        public View getView(int position, View convertView, ViewGroup parent){
             //infla o layout em um objeto do tipo View para que possa ser manipulado
            convertView=inflater.inflate(this.resourceId,parent,false);
            //converte os objetos do xml em objetos de java para que possamos manipulá-los
            TextView tituloEvento= (TextView) convertView.findViewById(R.id.textViewTituloEventoLista);
            TextView localEvento= (TextView) convertView.findViewById(R.id.textViewLocalEventoLista);
            TextView horarioEvento= (TextView) convertView.findViewById(R.id.textViewHorarioEventoLista);
            TextView precoEvento= (TextView) convertView.findViewById(R.id.textViewPrecoEventoLista);
            TextView dataEvento = (TextView) convertView.findViewById(R.id.textViewDataEventoLista);
            relativeLayoutLista= (RelativeLayout) convertView.findViewById(R.id.relativeLayoutPrincipalEventos);
            //seta os atributo
            evento=getItem(position);
            if(evento.getImagem().getImagemByte()==null){
                new ImagemTask().execute(evento.getImagem().getCaminho());

            }
            else{
                evento.getImagem().setImagemByte(evento.getImagem().getImagemByte());
                Bitmap imagemBitmap=decodeFile(evento.getImagem().getImagemByte());
                BitmapDrawable background = new BitmapDrawable(imagemBitmap);
                relativeLayoutLista.setBackgroundDrawable(background);

            }

            //byte [] bitmap=imagemTask.doInBackground(evento);
            //imagemTask.onPostExecute(bitmap);
            //evento.getImagem().setImagemByte(bitmap);
            //new ImagemTask().execute(evento.getImagem().getCaminho());
            tituloEvento.setText(evento.getNome());
            if(evento.getEndereco()==null){
                localEvento.setText("");
            }
            else{
                localEvento.setText(evento.getEndereco().getNome());

            }
            horarioEvento.setText(evento.getHorarios().get(0).getHora(evento.getHorarios().get(0).getHoraInicio()));

            //se o evento tiver preço 0,00, ele é setado como gratuito
            if(evento.getPrecos().get(0).getValor()==0.0){
                precoEvento.setText("Entrada Gratuita ");
            }
            else{
                precoEvento.setText("A partir de R$: " + evento.getPrecos().get(0).getValor() + "0");
            }
            dataEvento.setText(evento.getHorarios().get(0).getData(evento.getHorarios().get(0).getHoraInicio()));

            return convertView;
        }
    // Decodifica a imagem para evitar erros de memória
    private Bitmap decodeFile(byte[] byteArray) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, o);



            // novo tamanho a ser escalado
            final int REQUIRED_SIZE=70;

            // Achar o valor de escala correto, deve ser uma potência de dois
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decodifica com a escala correta
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, o2);
        }catch (Exception e){
            Log.d("Exceção na conversão", e.getMessage());
        }
        return null;
    }
    public byte[] getImagem(String nomeImagem){
        Bitmap bitmap = null;
        byte[] bitmapdata=null;

        //pega a imagem do servidor e converte em um bitmap
        try {
            Log.d("Nome da Imagem", nomeImagem);
            InputStream in = new java.net.URL(Url.getIp()+"/"+nomeImagem).openStream();
            bitmap = BitmapFactory.decodeStream(in);

            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
            bitmapdata = blob.toByteArray();

            return bitmapdata;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapdata;
    }

    private class ImagemTask extends AsyncTask<String, Integer, byte[]> {

        @Override
        protected byte[] doInBackground(String... strings) {
            return getImagem(strings[0]);

        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            //if(evento.getImagem()!=null){
                evento.getImagem().setImagemByte(bytes);
                Bitmap imagemBitmap=decodeFile(evento.getImagem().getImagemByte());
                BitmapDrawable background = new BitmapDrawable(imagemBitmap);
                relativeLayoutLista.setBackgroundDrawable(background);

            Log.d("onPostExecute", evento.getNome());

        }
    }

}

