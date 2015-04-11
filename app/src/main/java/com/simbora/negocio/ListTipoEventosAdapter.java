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

        // get a new View no matter recycling or ViewHolder FIXME
        convertView = inflater.inflate(resourceId, parent, false);

        //get all object from view
        TextView textView=(TextView) convertView.findViewById(R.id.textViewTipoEvento);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.imageViewTipoEvento);
        TipoEvento dI=getItem(position);

        textView.setText(dI.getNome());
        imageView.setBackgroundResource(dI.getIcone());
        return convertView;
    }



}
