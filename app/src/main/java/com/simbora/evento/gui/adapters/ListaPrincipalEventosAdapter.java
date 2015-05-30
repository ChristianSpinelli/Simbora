package com.simbora.evento.gui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simbora.R;
import com.simbora.evento.dominio.Evento;

import java.util.List;

/**
 * Created by Demis e Lucas on 11/04/2015.
 */
//classe que monta a lista dos eventos
public class ListaPrincipalEventosAdapter extends ArrayAdapter<Evento>{

        private LayoutInflater inflater;
        private int resourceId;
        public ListaPrincipalEventosAdapter(Context context, int resource, List<Evento> objects){
            super(context, resource, objects);
            this.inflater=LayoutInflater.from(context);
            this.resourceId=resource;
        }

        public View getView(int position, View convertView, ViewGroup parent){
             //infla o layout em um objeto do tipo View para que possa ser manipulado
            convertView=inflater.inflate(this.resourceId,parent,false);
            //converte os objetos do xml em objetos de java para que possamos manipulá-los
            ImageView imagemEvento=(ImageView) convertView.findViewById(R.id.imageViewEventoLista);
            TextView tituloEvento= (TextView) convertView.findViewById(R.id.textViewTituloEventoLista);
            TextView localEvento= (TextView) convertView.findViewById(R.id.textViewLocalEventoLista);
            TextView horarioEvento= (TextView) convertView.findViewById(R.id.textViewHorarioEventoLista);
            TextView precoEvento= (TextView) convertView.findViewById(R.id.textViewPrecoEventoLista);
            TextView dataEvento = (TextView) convertView.findViewById(R.id.textViewDataEventoLista);

            //seta os atributos
            Evento evento=getItem(position);
            //converte bytes em bitmap
            if(evento.getImagem()!=null){
                Bitmap imagemBitmap = BitmapFactory.decodeByteArray(evento.getImagem().getImagemByte(), 0, evento.getImagem().getImagemByte().length);
                imagemEvento.setImageBitmap(imagemBitmap);

            }
             tituloEvento.setText(evento.getNome());
            localEvento.setText(evento.getEndereco().getNome());
            horarioEvento.setText(evento.getHorarios().get(0).getHoraInicio().toString());
            precoEvento.setText("A partir de R$: "+evento.getPrecos().get(0).getValor()+"0");
           dataEvento.setText(evento.getHorarios().get(0).getData().toString());
          return convertView;
        }

}

