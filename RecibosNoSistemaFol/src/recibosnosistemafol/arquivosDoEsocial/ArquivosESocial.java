/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.arquivosDoEsocial;

import java.io.IOException;
import java.net.URISyntaxException;
import recibosnosistemafol.banco.BancoDados;

/**
 *
 * @author jacks
 */
public class ArquivosESocial {

    BancoDados banco;
    QueryArquivos query;
    FonteDados fonte;

    public ArquivosESocial(boolean insert) throws URISyntaxException {
         fonte = new FonteDados();
         fonte.iniciarCaminhodosEventos(insert);
    }
    
    
   

    public String s2200(String matricula, String recibo, String servidor, String database, String user, String senha) throws IOException {
       // query = new QueryArquivos();
        String update = String.format(fonte.getEventoS2200(), recibo, matricula);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;
    }

    public String s2299(String matricula, String recibo, String servidor, String database, String user, String senha) throws IOException {
       // query = new QueryArquivos();       
        String update = String.format(fonte.getEventoS2299(), recibo, matricula);
    

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }

    public String s1200(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) throws IOException {
      //  query = new QueryArquivos();
     //   String update = query.eventosTerceiraFase("S-1200", cpf, recibo, perApur);
        String update = String.format(fonte.getEventosTerceiraFase(), recibo, "S-1200", cpf, perApur, perApur);
     
        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }

    public String s1210(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) throws IOException {
       //query = new QueryArquivos();
        String update = String.format(fonte.getEventosTerceiraFase(), recibo, "S-1210", cpf, perApur, perApur);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }
    
    public String s3000(String recibo, String servidor, String database, String user, String senha) throws IOException {
        //query = new QueryArquivos();
        String update = String.format(fonte.getEventoS3000(), recibo);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }
    
    public String insertS1200(int ecoMes, int ecoAno, String ecoRecibo, int orgCod, String cpf, String perApure,
                              String servidor, String database, String user, String senha)
    {
        query = new QueryArquivos();
        String insert = query.insertS1200(ecoMes, ecoAno, ecoRecibo, orgCod, cpf, perApure);
        
        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(insert);
        }
        
        return insert;
    }
}
