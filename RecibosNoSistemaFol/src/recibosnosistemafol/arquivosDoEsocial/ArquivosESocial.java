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
    
    public void s2200(String matricula, String recibo, String servidor, String database, String user, String senha) {
        banco = new BancoDados(servidor, database, user, senha);
        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN FPG_REGISTROS_ESOCIAL_S_2200 S2 \n"
                + "ON S2.CHAVE = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2200'\n"
                + "\n"
                + "WHERE S2.matricula = %s", recibo, matricula);
        
      
        banco.update(update);
    }
    
    
    public void s2299(String matricula, String recibo, String servidor, String database, String user, String senha) {
       banco = new BancoDados(servidor, database, user, senha);
        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN FPG_REGISTROS_ESOCIAL_S_2299 S2 \n"
                + "ON S2.CHAVE = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2299'\n"
                + "\n"
                + "WHERE S2.matricula = %s", recibo, matricula);
        
        banco.update(update);
    }
    
    public void s1200(String cpf, String recibo, String perApur, String servidor, String database, String user, String senha) {
        banco = new BancoDados(servidor, database, user, senha);
        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM FPG_REGISTROS_ESOCIAL_S_1200 FE\n"
                + "INNER JOIN ESOCIAL_CONTROLA_ENVIO ECE\n"
                + "ON FE.PES_COD = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-1200'\n"
                + "WHERE FE.cpfTrab = '%s' AND FE.perApur = '%s'", recibo, perApur);
        banco.update(update);
    
    }
}
