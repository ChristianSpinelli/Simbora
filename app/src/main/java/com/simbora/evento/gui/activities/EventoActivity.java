package com.simbora.evento.gui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simbora.R;
import com.simbora.evento.dominio.Evento;
import com.simbora.evento.negocio.EventoService;
import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.usuario.negocio.UsuarioService;
import com.simbora.util.dominio.Url;


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

    //fragmento que contém os detalhes do evento
    public static class PlaceholderFragment extends Fragment {
        Evento evento;
        Button buttonSimbora;
        public PlaceholderFragment() {
        }

        //
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_evento, container, false);

            ImageView imageViewEvento=(ImageView) rootView.findViewById(R.id.imageViewEvento);
            TextView tituloEvento=(TextView) rootView.findViewById(R.id.textViewTituloEvento);
            TextView curtidas=(TextView) rootView.findViewById(R.id.textViewCurtidas);
            TextView descricao=(TextView) rootView.findViewById(R.id.textViewDescricao);
            buttonSimbora= (Button) rootView.findViewById(R.id.buttonSimbora);

           // imageViewEvento.set
            //pega o id do evento selecionado
            //seta os atributos em cada campo da tela
            evento=Evento.getListaEventosPorTipo().get(Evento.getIdEvento());
            //converte a imagem de um array de bytes para um bitmap
            Bitmap imagemBitmap = BitmapFactory.decodeByteArray(evento.getImagem().getImagemByte(), 0, evento.getImagem().getImagemByte().length);
            imageViewEvento.setImageBitmap(imagemBitmap);
            tituloEvento.setText(evento.getNome());
            descricao.setText(evento.getDescricao());
            curtidas.setText("Simboras: " + evento.getSimbora().getPessoas().size());
            if(evento.getSimbora().deuSimbora(Pessoa.getPessoaLogada())){
                changeButtonSimbora();
             }
            else{
                changeButtonDesistir();
            }
            buttonSimbora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!evento.getSimbora().deuSimbora(Pessoa.getPessoaLogada())) {
                        evento.getSimbora().darSimbora(Pessoa.getPessoaLogada());
                        Toast.makeText(getActivity().getBaseContext(), "Simbora dado com sucesso!", Toast.LENGTH_LONG).show();
                        changeButtonSimbora();
                    } else {
                        evento.getSimbora().desistir(Pessoa.getPessoaLogada());
                        Toast.makeText(getActivity().getBaseContext(), "Você desistiu do evento", Toast.LENGTH_LONG).show();
                        changeButtonDesistir();
                    }
                    Log.d("Tamanho da lista:", Integer.toString(evento.getSimbora().getPessoas().size()));
                    new AtualizarAsyncTask().execute(evento);
                }
            });
            return rootView;
        }
        public void changeButtonSimbora(){
            buttonSimbora.setText("DESISTIR");
            buttonSimbora.setTextColor(Color.WHITE);
        }

        public void changeButtonDesistir(){
            buttonSimbora.setText("SIMBORA");
            buttonSimbora.setTextColor(Color.BLACK);
        }

        private class AtualizarAsyncTask extends AsyncTask<Evento, Void, Boolean>{

            @Override
            protected Boolean doInBackground(Evento... params) {
                EventoService eventoService=new EventoService();
                Boolean resultado=eventoService.atualizar(params[0], Url.getEventos());
                return resultado;
            }

            @Override
            protected void onPostExecute(Boolean b) {
                Intent intent=new Intent(getActivity(), EventoListActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }

    }


}
