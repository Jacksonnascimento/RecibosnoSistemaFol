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
    
    public void s2200(String matricula, String recibo) {
        banco = new BancoDados();
        String update = String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN FPG_REGISTROS_ESOCIAL_S_2200 S2 \n"
                + "ON S2.CHAVE = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2200'\n"
                + "\n"
                + "WHERE S2.matricula = %s", recibo, matricula);
        
        banco.update(update);
    }
}
