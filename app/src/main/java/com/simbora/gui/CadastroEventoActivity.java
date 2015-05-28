package com.simbora.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.simbora.R;
import com.simbora.dominio.Endereco;
import com.simbora.dominio.Evento;
import com.simbora.dominio.Horario;
import com.simbora.dominio.Preco;
import com.simbora.dominio.TipoDeEvento;
import com.simbora.dominio.Url;
import com.simbora.negocio.EventoService;
import com.simbora.negocio.ListaPrincipalEventosAdapter;

import java.util.ArrayList;

public class CadastroEventoActivity extends ActionBarActivity {
    private Button bCadastrar;
    private EditText etNomeEvento;
    private EditText etTelefone;
    private EditText etRua;
    private EditText etLocal;
    private EditText etDescricao;
    private EditText etCidade;
    private EditText etBairro;
    private EditText etNumero;
    private ImageButton ibImagem;
    private EditText etData;
    private EditText etHoraInicio;
    private EditText etHoraFim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        this.bCadastrar = (Button) findViewById(R.id.bCadastrar);
        this.ibImagem = (ImageButton) findViewById(R.id.ibImagem);
        this.etBairro = (EditText) findViewById(R.id.etBairro);
        this.etCidade = (EditText) findViewById(R.id.etCidade);
        this.etData = (EditText) findViewById(R.id.etData);
        this.etDescricao = (EditText) findViewById(R.id.etDescricao);
        this.etLocal = (EditText) findViewById(R.id.etLocal);
        this.etHoraFim = (EditText) findViewById(R.id.etHoraFim);
        this.etHoraInicio = (EditText) findViewById(R.id.etHoraInicio);
        this.etNomeEvento = (EditText) findViewById(R.id.etNomeEvento);
        this.etNumero = (EditText) findViewById(R.id.etNumero);
        this.etRua = (EditText) findViewById(R.id.etRua);
        this.etTelefone = (EditText) findViewById(R.id.etTelefone);

        this.bCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Endereco endereco = new Endereco(etCidade.getText().toString(),etBairro.getText().toString(),etRua.getText().toString(),etNumero.getText().toString(),etLocal.getText().toString());
                Horario horario = new Horario(etData.getText().toString(),etHoraInicio.getText().toString(),etHoraFim.getText().toString());
                Preco preco = new Preco();
                ArrayList<Horario> horarios=new ArrayList<Horario>();
                ArrayList<Preco> precos=new ArrayList<Preco>();
                ArrayList<TipoDeEvento> tiposDeEvento=new ArrayList<TipoDeEvento>();

                horarios.add(horario);
                precos.add(preco);
                tiposDeEvento.add(TipoDeEvento.CINEMA);

                Evento evento=new Evento();
                evento.setDescricao(etDescricao.getText().toString());
                evento.setTelefone(etTelefone.getText().toString());
                evento.setEndereco(endereco);
                evento.setHorarios(horarios);
                evento.setPrecos(precos);

                new CadastrarAsyncTask().execute(evento);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_evento, menu);
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

    private class CadastrarAsyncTask extends AsyncTask<Evento, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(CadastroEventoActivity.this, " Cadastrando...", Toast.LENGTH_LONG).show();
        }


        @Override
        protected Boolean doInBackground(Evento... evento) {

            EventoService eventoService = new EventoService();
            return eventoService.inserirEvento(evento[0], Url.getIp() + ":5000/todo/api/v1.0/eventos");

        }

        // o onPostExecute é executado após o resultado da Thread ser coletado
        //ou seja, quando a Thread é finalizada
        @Override
        protected void onPostExecute(Boolean result) {
            //se cadastrou, então executa este if
            if (result) {
                Toast.makeText(getBaseContext(), "Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CadastroEventoActivity.this, EventoListActivity.class);
                startActivity(intent);
                finish();
            }
            else{

                Toast.makeText(getBaseContext(), "Falha ao inserir evento", Toast.LENGTH_LONG).show();
            }

        }
    }
}
