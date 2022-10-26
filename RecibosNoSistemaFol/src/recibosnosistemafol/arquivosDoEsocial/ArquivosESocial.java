/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.arquivosDoEsocial;

import recibosnosistemafol.banco.BancoDados;

/**
 *
 * @author jacks
 */
public class ArquivosESocial {

    BancoDados banco;
    QueryArquivos query;

    public String s2200(String matricula, String recibo, String servidor, String database, String user, String senha) {
        query = new QueryArquivos();
        String update = query.s2200(matricula, recibo);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;
    }

    public String s2299(String matricula, String recibo, String servidor, String database, String user, String senha) {
        query = new QueryArquivos();       
        String update = query.s2299(matricula, recibo);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }

    public String s1200(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) {
        query = new QueryArquivos();
        String update = query.s1200(cpf, recibo, perApur);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }

    public String s1210(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) {
       query = new QueryArquivos();
        String update = query.s1210(cpf, recibo, perApur);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }
    
    public String s3000(String recibo, String servidor, String database, String user, String senha) {
        query = new QueryArquivos();
        String update = query.s3000(recibo);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }
}
