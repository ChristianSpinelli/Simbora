package com.simbora.gui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.simbora.R;
import com.simbora.dominio.Evento;
import com.simbora.dominio.Url;
import com.simbora.negocio.EventoService;
import com.simbora.negocio.ListaPrincipalEventosAdapter;
import com.simbora.negocio.SearchAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a single Evento detail screen.
 * This fragment is either contained in a {@link EventoListActivity}
 * in two-pane mode (on tablets) or a {@link EventoDetailActivity}
 * on handsets.
 */

//ESSA CLASSE MOSTRA A LISTA DE EVENTOS SEPARADAS POR TIPO
//FOI GERADA A PARTIR DE UM TEMPLATE DO ANDROID
public class EventoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */

    private Evento evento;
    private MatrixCursor cursorAtual;
    private ListView lv;
    private Button bCriarEvento;
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
        }
    }

    //MÉTODO QUE MOSTRA A LISTA DE EVENTOS
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evento_detail, container, false);
        //executa a Thread assíncrona para carregar a lista de eventos do Web Service
        new HttpAsyncTask().execute(Url.getIp()+":5000/todo/api/v1.0/eventos");
        //seta o listview com o layout do xml
        lv = (ListView) rootView.findViewById(R.id.listView);
        this.bCriarEvento = (Button) rootView.findViewById(R.id.bCriarEvento);
        this.bCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CadastroEventoActivity.class);
                startActivity(intent);

            }
        });
        final SearchView search = (SearchView) rootView.findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //cria as sugestões a partir dos eventos que tem a string s
                cursorAtual=loadHistory(s, search);
                return true;
            }
        });

        search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                //método que pega o evento clicado e manda a tela do evento
                search.setQuery(cursorAtual.getString(1),true);
                String tituloEvento=search.getQuery().toString();

                for (Evento e : Evento.getListaEventosPorTipo()) {
                    if (e.getNome().equals(tituloEvento)) {
                        //se clicou no evento, prepara o intent e passa ao evento escolhido
                        Intent intent = new Intent(getActivity(), EventoActivity.class);
                        Evento.setIdEvento(e.getId()-1);
                        //indica a ListActivity para caso ela seja chamada, vá direto a tela de eventos
                        EventoListActivity.setGoToTodos(true);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

         return rootView;
    }

    public MatrixCursor loadHistory(String s, SearchView search)
    {
        //método que gera as sugestões para o usuário
        // Cursor
        String[] columns = new String[] { "_id", "text" };
        Object[] temp = new Object[] { 0, "default" };

        MatrixCursor cursor = new MatrixCursor(columns);
        //procura os eventos que tem aquela string e joga na lista para mostrar ao usuário
        ArrayList<String> eventos=Evento.getListaTitulosEventos();
        for(int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).toLowerCase().contains(s.toLowerCase())) {
                temp[0] = i;
                temp[1] = eventos.get(i);
                cursor.addRow(temp);
            }
        }
        search.setSuggestionsAdapter(new SearchAdapter(getActivity(), cursor, eventos));
        return cursor;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<Evento>> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity().getBaseContext(), " Carregando...", Toast.LENGTH_LONG).show();
        }

        //doInBackground realiza a operação com as outras funcionalidades do sistema rodando
        //está retornando os eventos a partir da url
        @Override
        protected ArrayList<Evento> doInBackground(String... urls) {

            EventoService eventoService=new EventoService();
            return eventoService.retornarEventos(urls[0]);
        }

        // o onPostExecute é executado após o resultado da Thread ser coletado
        //ou seja, quando a Thread é finalizada
        @Override
        protected void onPostExecute(ArrayList<Evento> result) {

            Toast.makeText(getActivity().getBaseContext(), "Carregada com Sucesso!", Toast.LENGTH_LONG).show();
            //seta a lista de eventos por tipo com os eventos do tipo escolhido
            Evento.setListaEventosPorTipo(result);
            //ESTA LISTA DE EVENTOS É INSERIDA NA CLASSE LISTAPRINCIPALEVENTOSADAPTER()
            //QUE JOGA CADA EVENTO NUM LAYOUT CHAMADO LIST_PRINCIPAL_EVENTOS
            //A LIST VIEW CRIADA RECEBE O ADAPTER PARA MONTAR A LISTVIEW DO JEITO QUE INDICAMOS EM SEU LAYOUT
            ArrayAdapter ad = new ListaPrincipalEventosAdapter(getActivity(), R.layout.list_principal_eventos, Evento.getListaEventosPorTipo());

            lv.setAdapter(ad);

            //O OnItemClickListener permite que a listView receba cliques e responda a eles;
            //Neste caso, há um Intent que manda abrir a tela que mostra o Evento clickado
            //seta o Id do evento com a posição da lista pra que a EventoActivity abra o evento correspondente
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Evento.setIdEvento(i);
                    Intent intent=new Intent(getActivity(), EventoActivity.class);
                    EventoListActivity.setGoToTodos(true);
                    startActivity(intent);

                }
            });
        }
    }


}
