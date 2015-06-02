package com.simbora.evento.gui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.simbora.R;
import com.simbora.evento.dominio.Endereco;
import com.simbora.evento.dominio.Evento;
import com.simbora.evento.dominio.Horario;
import com.simbora.evento.dominio.Preco;
import com.simbora.evento.dominio.TipoDeEvento;
import com.simbora.evento.negocio.EventoService;
import com.simbora.util.dominio.Url;
import com.simbora.util.negocio.Mask;

import java.util.ArrayList;
import java.util.Calendar;

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
    private static int RESULT_LOAD_IMG = 1;
    String imagemString;
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

        //Mascara
        this.etData.addTextChangedListener(Mask.insert("##/##/####", this.etData));
        this.etTelefone.addTextChangedListener(Mask.insert("(##)#####-####", this.etTelefone));
        this.etHoraFim.addTextChangedListener(Mask.insert("##:##",this.etHoraFim));
        this.etHoraInicio.addTextChangedListener(Mask.insert("##:##",this.etHoraInicio));
        //FimMascara

        this.ibImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carregarImagemDaGaleria(v);
            }
        });

        this.bCadastrar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
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

                //tipodefault. tirar após configurarmos o cadastro
                tiposDeEvento.add(TipoDeEvento.CINEMA);

                Evento evento=new Evento();
                evento.setDescricao(etDescricao.getText().toString());
                evento.setTelefone(etTelefone.getText().toString());
                evento.setNome(etNomeEvento.getText().toString());
                evento.setEndereco(endereco);
                evento.setHorarios(horarios);
                evento.setPrecos(precos);
                evento.setTiposDeEvento(tiposDeEvento);

                new CadastrarAsyncTask().execute(evento);


            }
        });
    }

    public void carregarImagemDaGaleria(View view) {
        // Cria intent para ir a galeria
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //Caso uma imagem seja selecionada
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                //coleta os dados da imagem
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // move ao primeiro resultado
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagemString = cursor.getString(columnIndex);
                cursor.close();

                ibImagem.setImageBitmap(BitmapFactory
                        .decodeFile(imagemString));

                Log.d("IbImagem", imagemString);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            Log.d("Erro imagem", e.getMessage());
        }

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

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
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

    /*//MASCARA DO EditText data formato DD/MM/YYYY criado por Juan.

    TextWatcher textWatcher = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {



            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<2015)?2015:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                etData.setText(current);
                etData.setSelection(sel < current.length() ? sel : current.length());
            }


    }

        @Override
        public void afterTextChanged(Editable s) {

        }

        ;


    };
*/


}
