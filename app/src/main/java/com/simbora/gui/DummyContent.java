package com.simbora.gui;

import android.app.usage.UsageEvents;

import com.simbora.R;
import com.simbora.dominio.Endereco;
import com.simbora.dominio.Evento;

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

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Evento> ITEM_MAP = new HashMap<String, Evento>();

    static {
        Endereco endereco=new Endereco();
        //ADD EVENTOS
        Evento evento1=new Evento();
        evento1.setNome("Caetano Veloso faz show no Teatro Guararapes");
        evento1.setHora("22h00");
        endereco.setNome("Teatro Guararapes");
        evento1.setEndereco(endereco);
        evento1.setPreco("de 90,00 a 1.300,00");
        evento1.setDescricao("Caetano Veloso aparece mais uma vez no Teatro Guararapes.\n O cantor apresentará o show de turnê “Abraçaço”. O evento será realizado em conjunto com cantora Vanessa da Mata, dona de uma belíssima voz, e ganhadora do Grammy Latino.");
        evento1.setImage(R.mipmap.caetano_veloso);
        evento1.setData("19/04/2015");
        evento1.setId(1);
        addItem(evento1);

        Evento evento2=new Evento();
        evento2.setNome("Silvio Meira dá palestra sobre o SBC");
        evento2.setHora("16h00");
        endereco.setNome("Centro de Informática UFPE");
        evento2.setEndereco(endereco);
        evento2.setPreco("Gratuito");
        evento2.setDescricao("Silvio Meira tratará sobre inovação e empreendedorismo, temas relacionados a empreendimentos. O pesquisador vai discorrer também sobre educação, porque, segundo ele, toda boa empresa é uma boa escola. Segundo ele, todo colaborador de um negócio qualquer deveria, pelo menos uma vez por semana, se perguntar o que aprendeu nos últimos sete dias.Autor de centenas de artigos científicos e textos sobre tecnologias da informação e seu impacto na economia, sociedade e pessoas, Silvio Meira é autor do livro Novos Negócios Inovadores de Crescimento Empreendedor no Brasil e, em 2007, foi eleito pela revista Época um dos 100 brasileiros mais influentes. Em 2011, foi escolhido por O Globo como personalidade do ano da economia brasileira.");
        evento2.setImage(R.mipmap.palestra_silvio_meira);
        evento2.setId(2);
        evento2.setData("21/04/2015");

        addItem(evento2);

        Evento evento3=new Evento();
        evento3.setNome("Encontro de Arduino Recife");
        evento3.setHora("08h00 as 18h00");
        endereco.setNome("Fac. Maurício de Nassau");
        evento3.setEndereco(endereco);
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
        endereco.setNome("Praia do Pina");
        evento4.setEndereco(endereco);
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

    }

    private static void addItem(Evento item) {
        ITEMS.add(item);
        ITEM_MAP.put(Integer.toString(item.getId()), item);
    }
}
