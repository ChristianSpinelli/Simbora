package com.simbora.gui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.simbora.R;
import com.simbora.dominio.Endereco;
import com.simbora.dominio.Evento;
import com.simbora.dominio.Horario;
import com.simbora.dominio.Preco;

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

                horarios.add(horario);
                precos.add(preco);

                Evento evento=new Evento();
                evento.setDescricao(etDescricao.getText().toString());
                evento.setTelefone(etTelefone.getText().toString());
                evento.setEndereco(endereco);
                evento.setHorarios(horarios);
                evento.setPrecos(precos);

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
}
