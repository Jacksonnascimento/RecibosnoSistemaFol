/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.bases;

import java.util.ArrayList;
import recibosnosistemafol.banco.BancoDados;

/**
 *
 * @author Jackson
 */
public class ServidoresBases {

    private int id;
    private String descri;
    private String servidor;
    private String database;
    private String user;
    private String senha;
    private ArrayList<ServidoresBases> basesBanco = new ArrayList<>();
    
    public void addBase(int id, String descri, String servidor, String database, String user, String senha) {
        this.id = id;
        this.descri = descri;
        this.servidor = servidor;
        this.database = database;
        this.user = user;
        this.senha = senha;
    }

    public void addbasenoBanco(int id, String descri, String servidor, String database, String user, String senha) {
        String insert = String.format("INSERT INTO BASES VALUES"
                + "('%s', '%s', "
                + "'%s', '%s', "
                + "'%s')", id, descri, servidor, database, user, senha);
    }
    
    public void addBases(int id, String descri, String servidor, String database, String user, String senha) {
        ServidoresBases base = new ServidoresBases();
        base.addBase(id, descri, servidor, database, user, senha);
        getBasesBanco().add(base);
        
    }
    
    public void buscarBasesbanco() {
        BancoDados banco = new BancoDados("191.233.29.0", "RECIBOS_BASES", "sa", "@jn87519023");
        String select = "SELECT * FROM BASES";
        select = banco.select(select, 6);
        String[] linhas = select.split("\n");
        
        for (String linha : linhas) {
            String[] colunas = linha.split(",");
            addBases(Integer.parseInt(colunas[0]),
                     colunas[1], colunas[2], colunas[3], colunas[4], colunas[5]);
        }
    }
    
    public ServidoresBases getBaseDesc(String desc) {
        for (ServidoresBases base : basesBanco) {
            if (desc.equals(base.getDescri())) {
                return base;
            }
        }
        
        return null;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the descri
     */
    public String getDescri() {
        return descri;
    }

    /**
     * @return the servidor
     */
    public String getServidor() {
        return servidor;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @return the basesBanco
     */
    public ArrayList<ServidoresBases> getBasesBanco() {
        return basesBanco;
    }
    
}
