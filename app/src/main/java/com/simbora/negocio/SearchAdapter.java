package com.simbora.negocio;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.simbora.R;

import java.util.List;

/**
 * Created by Demis e Lucas on 21/04/2015.
 */
public class SearchAdapter extends CursorAdapter {

    private List<String> items;

    private TextView text;

    public SearchAdapter(Context context, Cursor cursor, List<String> items) {

        super(context, cursor, false);

        this.items = items;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        text.setText(cursor.getString(1));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.search, parent, false);

        text = (TextView) view.findViewById(R.id.textViewSearch);

        System.out.println("Criou a lista de sugestões");

        return view;

    }

}
