/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.arquivosDoEsocial;

/**
 *
 * @author Jackson
 */
public class QueryArquivos {

    public String s2200(String matricula, String recibo) {
        return String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN GER_FUNCIONARIO GF \n"
                + "ON GF.FUN_MATRICULA = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2200'\n"
                + "\n"
                + "WHERE GF.FUN_MATRICULA = %s\n"
                + "AND ECE.ECO_RECIBO IS NULL\n", recibo, matricula);
    }

    public String s2299(String matricula, String recibo) {
        return String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN FPG_REGISTROS_ESOCIAL_S_2299 S2 \n"
                + "ON S2.CHAVE = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2299'\n"
                + "\n"
                + "WHERE S2.matricula = %s\n"
                + "AND ECE.ECO_RECIBO IS NULL\n", recibo, matricula);
    }

    public String eventosTerceiraFase(String etaCod, String cpf, String recibo, String perApur) {
        return String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s', ECO_SITUACAO = 'N'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECO\n"
                + "INNER JOIN GER_PESSOA_FISICA PF\n"
                + "ON ECO.ECO_CHAVE = PF.PES_COD \n"
                + "WHERE ECO.ETA_COD = '%s' AND PF.PFI_CPF = '%s'\n"
                + "AND ECO.ECO_ANO = SUBSTRING('%s', 1, 4) \n"
                + "AND ECO.ECO_MES = SUBSTRING('%s', 6, 2)\n"
                + "AND ECO.ECO_RECIBO IS NULL", recibo, etaCod, cpf, perApur, perApur);
    }

    public String s3000(String recibo) {
        return String.format("UPDATE ESOCIAL_CONTROLA_ENVIO\n"
                + "SET ECO_RECIBO = NULL, ECO_SITUACAO = 'I'\n"
                + "WHERE ECO_RECIBO = '%s'\n",
                recibo);
    }

}
