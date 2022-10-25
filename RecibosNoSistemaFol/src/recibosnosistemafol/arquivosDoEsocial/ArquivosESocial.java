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

    public String s2200(String matricula, String recibo, String servidor, String database, String user, String senha) {

        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN GER_FUNCIONARIO GF \n"
                + "ON GF.FUN_MATRICULA = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2200'\n"
                + "\n"
                + "WHERE GF.FUN_MATRICULA = %s", recibo, matricula);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;
    }

    public String s2299(String matricula, String recibo, String servidor, String database, String user, String senha) {

        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN FPG_REGISTROS_ESOCIAL_S_2299 S2 \n"
                + "ON S2.CHAVE = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2299'\n"
                + "\n"
                + "WHERE S2.matricula = %s", recibo, matricula);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }

    public String s1200(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) {

        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s', ECO_SITUACAO = 'N'\n"
                + "FROM FPG_REGISTROS_ESOCIAL_S_1200 FE\n"
                + "INNER JOIN ESOCIAL_CONTROLA_ENVIO ECE\n"
                + "ON FE.PES_COD = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-1200'\n"
                + "AND ECE.ECO_ANO = FE.COM_ANO AND  ECE.ECO_MES = FE.MES_COD\n"
                + "WHERE FE.cpfTrab = '%s' AND FE.perApur = '%s'", recibo, cpf, perApur);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }

    public String s1210(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) {
       
        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s', ECO_SITUACAO = 'N'\n"
                + "FROM FPG_REGISTROS_ESOCIAL_S_1210 FE\n"
                + "INNER JOIN ESOCIAL_CONTROLA_ENVIO ECE\n"
                + "ON FE.PES_COD = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-1210'\n"
                + "AND ECE.ECO_ANO = FE.COM_ANO AND  ECE.ECO_MES = FE.MES_COD\n"
                + "WHERE FE.cpfBenef = '%s' AND FE.perApur = '%s'\n", 
                recibo, cpf, perApur);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }
    
    public String s3000(String recibo, String servidor, String database, String user, String senha) {

        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO\n"
                + "SET ECO_RECIBO = NULL, ECO_SITUACAO = 'I'\n"
                + "WHERE ECO_RECIBO = '%s'",
                recibo);

        if (!"txt".equals(servidor)) {
            banco = new BancoDados(servidor, database, user, senha);
            banco.update(update);
        }

        return update;

    }
}
