package com.simbora.gui;

import android.app.usage.UsageEvents;

import com.simbora.R;
import com.simbora.dominio.Evento;
import com.simbora.dominio.TipoEvento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Evento> ITEMS = new ArrayList<Evento>();
    public static List<TipoEvento> TIPO_ITEMS = new ArrayList<TipoEvento>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Evento> ITEM_MAP = new HashMap<String, Evento>();

    static {
        // Add 3 sample items.
        Evento evento1=new Evento();
        evento1.setNome("Caetano Veloso faz show no Teatro Guararapes");
        evento1.setHora("22h00");
        evento1.setEndereco("Teatro Guararapes");
        evento1.setPreco("de 90,00 a 1.300,00");
        evento1.setImage(R.mipmap.caetano_veloso);
        evento1.setId(1);
        addItem(evento1);

        Evento evento2=new Evento();
        evento2.setNome("Silvio Meira dá palestra sobre o SBC");
        evento2.setHora("16h00");
        evento2.setEndereco("Centro de Informática UFPE");
        evento2.setPreco("Gratuito");
        evento2.setImage(R.mipmap.palestra_silvio_meira);
        evento2.setId(2);

        addItem(evento2);

        Evento evento3=new Evento();
        evento3.setNome("Encontro de Arduino Recife");
        evento3.setHora("08h00 as 18h00");
        evento3.setEndereco("Fac. Maurício de Nassau");
        evento3.setPreco("15,00");
        evento3.setImage(R.mipmap.encontro_arduino);
        evento3.setId(3);
        addItem(evento3);

        Evento evento4=new Evento();
        evento4.setNome("2 Evangelizar é Preciso Recife");
        evento4.setHora("14h00");
        evento4.setEndereco("Praia do Pina");
        evento4.setPreco("Gratuito");
        evento4.setImage(R.mipmap.evangelizar_pe_reginaldo);
        evento4.setId(4);
        addItem(evento4);





        TipoEvento tipo1=new TipoEvento();
        tipo1.setNome("Todos");
        tipo1.setIcone(R.mipmap.ic_launcher);
        tipo1.setId("1");
        TipoEvento tipo2=new TipoEvento();
        tipo2.setNome("Rolando Agora");
        tipo2.setIcone(R.mipmap.ic_launcher);
        tipo2.setId("2");
        TipoEvento tipo3=new TipoEvento();
        tipo3.setNome("Shows");
        tipo3.setIcone(R.mipmap.ic_launcher);
        tipo3.setId("3");
        TipoEvento tipo4=new TipoEvento();
        tipo4.setNome("Exposições");
        tipo4.setIcone(R.mipmap.ic_launcher);
        tipo4.setId("4");
        TipoEvento tipo5=new TipoEvento();
        tipo5.setNome("Educacionais");
        tipo5.setIcone(R.mipmap.ic_launcher);
        tipo5.setId("5");
        TipoEvento tipo6=new TipoEvento();
        tipo6.setNome("Tecnologia");
        tipo6.setIcone(R.mipmap.ic_launcher);
        tipo6.setId("6");
        TipoEvento tipo7=new TipoEvento();
        tipo7.setNome("Jogos");
        tipo7.setIcone(R.mipmap.ic_launcher);
        tipo7.setId("7");
        TipoEvento tipo8=new TipoEvento();
        tipo8.setNome("Congressos");
        tipo8.setIcone(R.mipmap.ic_launcher);
        tipo8.setId("8");
        TipoEvento tipo9=new TipoEvento();
        tipo9.setNome("Esportes");
        tipo9.setIcone(R.mipmap.ic_launcher);
        tipo9.setId("9");
        TipoEvento tipo10=new TipoEvento();
        tipo10.setNome("Religiosos");
        tipo10.setIcone(R.mipmap.ic_launcher);
        tipo10.setId("10");

        TIPO_ITEMS.add(tipo1);
        TIPO_ITEMS.add(tipo2);
        TIPO_ITEMS.add(tipo3);
        TIPO_ITEMS.add(tipo4);
        TIPO_ITEMS.add(tipo5);
        TIPO_ITEMS.add(tipo6);
        TIPO_ITEMS.add(tipo7);
        TIPO_ITEMS.add(tipo8);
        TIPO_ITEMS.add(tipo9);
        TIPO_ITEMS.add(tipo10);

    }

    private static void addItem(Evento item) {
        ITEMS.add(item);
        ITEM_MAP.put(Integer.toString(item.getId()), item);
    }
}
