package com.simbora.evento.gui.fragments;

import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.simbora.R;
import com.simbora.evento.dominio.Evento;
import com.simbora.evento.dominio.TipoDeEvento;
import com.simbora.evento.gui.activities.CadastroEventoActivity;
import com.simbora.evento.gui.activities.EventoActivity;
import com.simbora.evento.gui.activities.EventoDetailActivity;
import com.simbora.evento.gui.activities.EventoListActivity;
import com.simbora.evento.gui.adapters.ListaPrincipalEventosAdapter;
import com.simbora.evento.gui.adapters.SearchAdapter;
import com.simbora.evento.negocio.EventoService;
import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.util.dominio.Url;

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

    private TipoDeEvento tipoDeEvento;
    private MatrixCursor cursorAtual;
    private ListView lv;
    private Button bCriarEvento;
    private ProgressBar progressBarEventos;
    private ImageView imageViewUsuario;
    private TextView textViewUsuario;
    private Button meusEventos;

    public EventoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            for (TipoDeEvento tE:TipoDeEvento.values()){
                if (tE.getId()== Integer.parseInt(getArguments().getString(ARG_ITEM_ID))){
                    tipoDeEvento=tE;
                }
            }
        }
    }

    //MÉTODO QUE MOSTRA A LISTA DE EVENTOS
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evento_detail, container, false);
        //executa a Thread assíncrona para carregar a lista de eventos do Web Service
        progressBarEventos=(ProgressBar) rootView.findViewById(R.id.progressBarLoadingEventos);
        textViewUsuario= (TextView) rootView.findViewById(R.id.textViewNomeUsuario);
        imageViewUsuario= (ImageView) rootView.findViewById(R.id.imageViewUsuario);
        meusEventos=(Button) rootView.findViewById(R.id.buttonMeusEventos);
        meusEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MeusEventosAsyncTask().execute(Url.getEventos());
            }
        });


        if (Usuario.getUsuarioLogado()!=null){
            imageViewUsuario.setImageBitmap(decodeFile(Usuario.getUsuarioLogado().getImagem().getImagemByte()));
            textViewUsuario.setText(Usuario.getUsuarioLogado().getNome());
        }

                 new HttpAsyncTask().execute(Url.getIp("eventos"));

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
                search.setQuery(cursorAtual.getString(1), true);
                String tituloEvento = search.getQuery().toString();

                for (Evento e : Evento.getListaEventosPorTipo()) {
                    if (e.getNome().equals(tituloEvento)) {
                        //se clicou no evento, prepara o intent e passa ao evento escolhido
                        Intent intent = new Intent(getActivity(), EventoActivity.class);
                        Evento.setIdEvento(e.getId() - 1);
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

    //Thread que executa as operações assincronamente
    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<Evento>> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity().getBaseContext(), " Carregando...", Toast.LENGTH_LONG).show();

                progressBarEventos.setVisibility(View.VISIBLE);

        }

        //doInBackground realiza a operação com as outras funcionalidades do sistema rodando
        //está retornando os eventos a partir da url
        @Override
        protected ArrayList<Evento> doInBackground(String... urls) {
            Log.d("tipo do evento", tipoDeEvento.getDescricao());
            EventoService eventoService = new EventoService();
            //mostra  a barra de progresso se a API for maior que a 20, para evitar erros de permissão de Thread

            return eventoService.retornarEventos(tipoDeEvento);
        }

        // o onPostExecute é executado após o resultado da Thread ser coletado
        //ou seja, quando a Thread é finalizada
        @Override
        protected void onPostExecute(ArrayList<Evento> result) {
            //if(Build.VERSION.SDK_INT >20){
                progressBarEventos.setVisibility(View.GONE);
            //}
            if(result.size()!=0){
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
                        Intent intent = new Intent(getActivity(), EventoActivity.class);
                        EventoListActivity.setGoToTodos(true);
                        startActivity(intent);

                    }
                });

            }
            else{
                Toast.makeText(getActivity().getBaseContext(), "Erro na comunicação com o servidor.", Toast.LENGTH_LONG).show();
            }



        }

    }

    //Thread que executa as operações assincronamente
    private class MeusEventosAsyncTask extends AsyncTask<String, Void, ArrayList<Evento>> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity().getBaseContext(), " Carregando...", Toast.LENGTH_LONG).show();
        }

        //doInBackground realiza a operação com as outras funcionalidades do sistema rodando
        //está retornando os eventos a partir da url
        @Override
        protected ArrayList<Evento> doInBackground(String... urls) {

            EventoService eventoService = new EventoService();
            return eventoService.retornarMeusEventos(urls[0]);


        }

        // o onPostExecute é executado após o resultado da Thread ser coletado
        //ou seja, quando a Thread é finalizada
        @Override
        protected void onPostExecute(ArrayList<Evento> result) {
            if(result.size()!=0){
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
                        Intent intent = new Intent(getActivity(), EventoActivity.class);
                        EventoListActivity.setGoToTodos(true);
                        startActivity(intent);

                    }
                });

            }
            else{
                Toast.makeText(getActivity().getBaseContext(), "Erro na comunicação com o servidor.", Toast.LENGTH_LONG).show();
            }



        }

    }

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



}
