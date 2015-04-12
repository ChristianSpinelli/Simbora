package com.simbora.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.simbora.EventoActivity;
import com.simbora.R;
import com.simbora.dominio.Evento;
import com.simbora.negocio.ListaPrincipalEventosAdapter;

/**
 * A fragment representing a single Evento detail screen.
 * This fragment is either contained in a {@link EventoListActivity}
 * in two-pane mode (on tablets) or a {@link EventoDetailActivity}
 * on handsets.
 */
public class EventoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Evento mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evento_detail, container, false);

        // Show the dummy content as text in a TextView.
        //if (mItem != null) {
            ArrayAdapter ad = new ListaPrincipalEventosAdapter(getActivity(), R.layout.list_principal_eventos, DummyContent.ITEMS);
            ListView lv = (ListView) rootView.findViewById(R.id.listView);
            lv.setAdapter(ad);
           System.out.println("Estou na lista");
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("Estou no onClick");
                    Evento.setIdEvento(i);
                    Intent intent=new Intent(getActivity(), EventoActivity.class);
                    startActivity(intent);
                }
            });
       // }

        return rootView;
    }
}
