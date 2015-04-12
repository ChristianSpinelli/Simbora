package com.simbora;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import com.simbora.dominio.Evento;
import com.simbora.gui.DummyContent;


public class EventoActivity extends ActionBarActivity {
    int posicaoEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

            posicaoEvento= Evento.getIdEvento();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_evento, container, false);

            ImageView imageViewEvento=(ImageView) rootView.findViewById(R.id.imageViewEvento);
            TextView tituloEvento=(TextView) rootView.findViewById(R.id.textViewTituloEvento);
            TextView curtidas=(TextView) rootView.findViewById(R.id.textViewCurtidas);
            TextView descricao=(TextView) rootView.findViewById(R.id.textViewDescricao);

           // imageViewEvento.set
            //pega o id do evento selecionado
            Evento evento=DummyContent.ITEMS.get(Evento.getIdEvento());
            imageViewEvento.setBackgroundResource(evento.getImage());
            tituloEvento.setText(evento.getNome());
            descricao.setText(evento.getDescricao());

            return rootView;
        }
    }
}
