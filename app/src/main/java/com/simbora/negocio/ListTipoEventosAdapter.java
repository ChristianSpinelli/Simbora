package com.simbora.negocio;

/**
 * Created by Demis e Lucas on 11/04/2015.
 */
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.simbora.R;
import com.simbora.dominio.Evento;
import com.simbora.dominio.TipoEvento;

//classe que monta A lista dos tipos de eventos
public class ListTipoEventosAdapter extends ArrayAdapter<TipoEvento>{

    private final LayoutInflater inflater;
    private int resourceId;

    public ListTipoEventosAdapter(Context context, int resource, List<TipoEvento> objects) {
        super(context, resource, objects);
        this.inflater=LayoutInflater.from(context);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //recebe o view da lista de eventos, que nesse caso é o xml list_tipo_eventos
        convertView = inflater.inflate(resourceId, parent, false);

        //relaciona os campos com os do xml
        TextView textView=(TextView) convertView.findViewById(R.id.textViewTipoEvento);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.imageViewTipoEvento);
        TipoEvento dI=getItem(position);

        //seta os campos
        textView.setText(dI.getNome());
        imageView.setBackgroundResource(dI.getIcone());
        return convertView;
    }



}
