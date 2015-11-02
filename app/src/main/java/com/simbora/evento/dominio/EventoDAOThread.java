package com.simbora.evento.dominio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.simbora.pessoa.dominio.Pessoa;
import com.simbora.pessoa.dominio.Simbora;
import com.simbora.pessoa.dominio.negocio.PessoaService;
import com.simbora.usuario.dominio.Usuario;
import com.simbora.usuario.persistencia.UsuarioDAO;
import com.simbora.util.dominio.Imagem;
import com.simbora.util.dominio.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by gprt on 11/1/15.
 */
public class EventoDAOThread implements Runnable{
    private JSONObject jsonObject;
    private Evento evento;

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public void run() {
        this.evento=converterParaObjeto(this.jsonObject);
    }

    public Evento converterParaObjeto(JSONObject jsonObject){
        Evento evento=new Evento();
        Endereco endereco=new Endereco();
        ArrayList<Horario> horarios=new ArrayList<Horario>();
        ArrayList<Preco> precos=new ArrayList<Preco>();
        ArrayList<TipoDeEvento> tiposDeEvento=new ArrayList<TipoDeEvento>();
        Simbora simbora=new Simbora();

        try {
            evento.setNome(jsonObject.getString("titulo"));
            evento.setDescricao(jsonObject.getString("descricao"));
            evento.setTelefone(jsonObject.getString("telefone"));

            //endereco
            JSONObject enderecoObject=jsonObject.getJSONArray("endereco").getJSONObject(0);
            endereco.setNome(enderecoObject.getString("nome"));
            endereco.setBairro(enderecoObject.getString("bairro"));
            endereco.setCep(enderecoObject.getString("cep"));
            endereco.setCidade(enderecoObject.getString("cidade"));
            endereco.setNumeroRua(enderecoObject.getString("numero"));
            endereco.setRua(enderecoObject.getString("rua"));

            //horarios
            JSONArray horariosObject=jsonObject.getJSONArray("horarios");
            for (int i=0;i<horariosObject.length();i++)   {
                Horario horario=new Horario(horariosObject.getJSONObject(i).getString("data"),horariosObject.getJSONObject(i).getString("horaInicio"),horariosObject.getJSONObject(i).getString("horaTermino"));
                horarios.add(horario);
            }

            JSONArray precosObject=jsonObject.getJSONArray("precos");
            for (int j=0;j<precosObject.length();j++){
                Preco preco=new Preco();
                preco.setNomeEntrada(precosObject.getJSONObject(j).getString("tipo"));
                preco.setValor(precosObject.getJSONObject(j).getDouble("preco"));
                precos.add(preco);
            }

            JSONArray tiposDeEventoObject=jsonObject.getJSONArray("tiposDeEvento");
            for (int j=0;j<tiposDeEventoObject.length();j++){
                for (TipoDeEvento t:TipoDeEvento.values()){
                    if(t.getDescricao().equals(tiposDeEventoObject.getJSONObject(j).getString("descricao"))){
                        tiposDeEvento.add(t);
                    }
                }
            }

            ArrayList<Pessoa> pessoasSimbora=new ArrayList<Pessoa>();
            //TODO: Tirar o comentário abaixo antes do commit
            /*PessoaService pessoaService=new PessoaService();
            try{
                JSONArray jsonArraySimbora=jsonObject.getJSONArray("simbora");
                for(int k=0; k<jsonArraySimbora.length();k++){
                    Pessoa pessoa=new Pessoa();
                    String idPessoa=jsonArraySimbora.getJSONObject(k).getString("idPessoa");
                    pessoa=pessoaService.consultarPessoa(Url.getPessoas()+"/"+idPessoa);

                    pessoasSimbora.add(pessoa);
                }

            }
            catch (Exception e){
                e.getMessage();
            }
            */simbora.setPessoas(pessoasSimbora);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario=null;
            String idUsuario=jsonObject.getString("idCriador");
            usuario=usuarioDAO.consultarPorId(idUsuario);

            Bitmap bitmap = null;
            byte[] bitmapdata=null;

            //pega a imagem do servidor e converte em um bitmap
            //TODO: tirar este comentário antes di commit
/*            try {
                InputStream in = new java.net.URL(Url.getIp()+"/"+jsonObject.getString("imagem")).openStream();
                bitmap = BitmapFactory.decodeStream(in);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 *//*ignored for PNG*//*, blob);
                bitmapdata = blob.toByteArray();

            } catch (Exception e) {
                Log.e("Erro na imagem", e.getMessage());
                e.printStackTrace();
            }*/
            //seta endereco
            evento.setEndereco(endereco);
            //seta horarios
            evento.setHorarios(horarios);
            //seta precos
            evento.setPrecos(precos);
            //seta tipos de evento
            evento.setTiposDeEvento(tiposDeEvento);
            //seta imagem
            evento.setImagem(new Imagem(jsonObject.getString("imagem"), null));
            evento.setSimbora(simbora);
            evento.setCriador(usuario);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Erro no evento", "erro no json do evento");
        }
        return evento;
    }
}
