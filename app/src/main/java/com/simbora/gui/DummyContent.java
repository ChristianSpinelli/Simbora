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
 */
    //CLASSE QUE INICIALIZA OS EVENTOS E OS SEUS TIPOS
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    //LISTA DE EVENTOS
    public static List<Evento> ITEMS = new ArrayList<Evento>();
    //LISTA DE TIPOS DE EVENTOS
    public static List<TipoEvento> TIPO_ITEMS = new ArrayList<TipoEvento>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Evento> ITEM_MAP = new HashMap<String, Evento>();

    static {
        //ADD EVENTOS E TIPOS DE EVENTOS
        Evento evento1=new Evento();
        evento1.setNome("Caetano Veloso faz show no Teatro Guararapes");
        evento1.setHora("22h00");
        evento1.setEndereco("Teatro Guararapes");
        evento1.setPreco("de 90,00 a 1.300,00");
        evento1.setDescricao("Caetano Veloso aparece mais uma vez no Teatro Guararapes.\n O cantor apresentará o show de turnê “Abraçaço”. O evento será realizado em conjunto com cantora Vanessa da Mata, dona de uma belíssima voz, e ganhadora do Grammy Latino.");
        evento1.setImage(R.mipmap.caetano_veloso);
        evento1.setData("19/04/2015");
        evento1.setId(1);
        addItem(evento1);

        Evento evento2=new Evento();
        evento2.setNome("Silvio Meira dá palestra sobre o SBC");
        evento2.setHora("16h00");
        evento2.setEndereco("Centro de Informática UFPE");
        evento2.setPreco("Gratuito");
        evento2.setDescricao("Silvio Meira tratará sobre inovação e empreendedorismo, temas relacionados a empreendimentos. O pesquisador vai discorrer também sobre educação, porque, segundo ele, toda boa empresa é uma boa escola. Segundo ele, todo colaborador de um negócio qualquer deveria, pelo menos uma vez por semana, se perguntar o que aprendeu nos últimos sete dias.Autor de centenas de artigos científicos e textos sobre tecnologias da informação e seu impacto na economia, sociedade e pessoas, Silvio Meira é autor do livro Novos Negócios Inovadores de Crescimento Empreendedor no Brasil e, em 2007, foi eleito pela revista Época um dos 100 brasileiros mais influentes. Em 2011, foi escolhido por O Globo como personalidade do ano da economia brasileira.");
        evento2.setImage(R.mipmap.palestra_silvio_meira);
        evento2.setId(2);
        evento2.setData("21/04/2015");

        addItem(evento2);

        Evento evento3=new Evento();
        evento3.setNome("Encontro de Arduino Recife");
        evento3.setHora("08h00 as 18h00");
        evento3.setEndereco("Fac. Maurício de Nassau");
        evento3.setPreco("15,00");
        evento3.setDescricao("O Encontro será uma grande oportunidade para fazer networking, trocar experiência com a comunidade, aprender mais sobre automação, robótica e áreas afins, usando Arduino ou qualquer outra plataforma (Raspberry Pi, Intel Galileo...). O encontro contará com palestras, apresentação de projetos e workshop.\n" +
                "\n" +
                "Se você quer aprender mais sobre as plataformas não deixe de se inscrever para as palestras e o workshop. E se você já conhece algumas delas traga um projeto seu para apresentar no 3º Encontro Arduino Recife, além de divulgar para comunidade você pode concorrer a prêmios.");
        evento3.setImage(R.mipmap.encontro_arduino);
        evento3.setId(3);
        evento3.setData("28/02/2015");
        addItem(evento3);

        Evento evento4=new Evento();
        evento4.setNome("2 Evangelizar é Preciso Recife");
        evento4.setHora("14h00");
        evento4.setEndereco("Praia do Pina");
        evento4.setPreco("Gratuito");
        evento4.setDescricao("No próximo dia 9 de maio, a partir das 15h, fiéis de toda a Arquidiocese de Olinda e Recife se encontrarão para celebrar a segunda edição do “Evangelizar é preciso Recife – Show da Esperança”. O evento, que tem o objetivo de arrecadar recursos para a construção da Fazenda da Esperança Padre Antônio Henrique, terá como uma das atrações o padre Reginaldo Manzotti.\n" +
                "\n" +
                "O arcebispo dom Fernando Saburido presidirá a Celebração Eucarística que vai abrir o 2º Evangelizar é preciso Recife – Show da Esperança. Em seguida, haverá shows do Dj Ângelus, padre João Carlos, frei Damião Silva e do padre Reginaldo Manzotti.\n" +
                "\n" +
                "O acesso ao evento será gratuito, porém quem quiser contribuir com a construção da primeira Fazenda da Esperança da arquidiocese, que será erguida em Jaboatão dos Guararapes, poderá adquiri as camisas do evento ao preço de R$ 20. As vendas terão início na próxima semana, em todas as 117 paróquias da arquidiocese.\n" +
                "\n" +
                "Além das igrejas, as camisas também estarão disponíveis em lojas de artigos católicos e em estandes montados nos principais shoppings da Região Metropolitana do Recife.");
        evento4.setImage(R.mipmap.evangelizar_pe_reginaldo);
        evento4.setId(4);
        evento4.setData("09/05/2015");
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
