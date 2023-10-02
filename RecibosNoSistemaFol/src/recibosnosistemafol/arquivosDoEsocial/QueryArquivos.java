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
    
    public String s2200(String matricula, String recibo){
        return  String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN GER_FUNCIONARIO GF \n"
                + "ON GF.FUN_MATRICULA = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2200'\n"
                + "\n"
                + "WHERE GF.FUN_MATRICULA = %s\n"
                + "AND ECE.ECO_RECIBO IS NULL\n", recibo, matricula);
    }
    
    public String s2299(String matricula, String recibo){
        return  String.format("UPDATE ESOCIAL_CONTROLA_ENVIO \n"
                + "SET ECO_RECIBO = '%s'\n"
                + "FROM ESOCIAL_CONTROLA_ENVIO ECE INNER JOIN FPG_REGISTROS_ESOCIAL_S_2299 S2 \n"
                + "ON S2.CHAVE = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-2299'\n"
                + "\n"
                + "WHERE S2.matricula = %s\n"
                + "AND ECE.ECO_RECIBO IS NULL\n", recibo, matricula);
    }
    public String eventosTerceiraFase(String etaCod, String cpf, String recibo, String perApur){
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
    
    
    public String s3000(String recibo){
        return String.format("UPDATE ESOCIAL_CONTROLA_ENVIO\n"
                + "SET ECO_RECIBO = NULL, ECO_SITUACAO = 'I'\n"
                + "WHERE ECO_RECIBO = '%s'\n",
                recibo);
    }
    
    public String insertS1200(int ecoMes, int ecoAno, String ecoRecibo, int orgCod, String cpf, String perApure){
        return String.format("INSERT INTO ESOCIAL_CONTROLA_ENVIO\n"
                + "(ETA_COD, ECO_CHAVE, ECO_NOME_CHAVE, ECO_TIPO, ECO_SITUACAO, ECO_MES, \n"
                + "ECO_ANO, ECO_RECIBO, ENT_COD, ECO_AMBIENTE, ORG_COD, FOL_COD, PES_COD, \n"
                + "CEN_COD, FOL_TIPO, MES_COD, COM_ANO, FUN_COD, ETA_COD_EXCLUSAO, ECO_NIS, \n"
                + "ECO_CPF, ECO_PER_APUR)\n"
                + "VALUES('S-1200', (SELECT PES_COD FROM GER_PESSOA_FISICA WHERE PFI_CPF = '%s'), 'PES_COD', 'E',\n"
                + "'N', %s, %s, '%s', NULL, 1, \n"
                + "'%s', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '%s', '%s')",
                cpf, ecoMes, ecoAno, ecoRecibo, orgCod, cpf, perApure
                );
    }
    
}