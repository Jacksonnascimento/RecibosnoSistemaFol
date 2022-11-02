/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.bases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


/**
 *
 * @author Jackson
 */
public class ServidoresBases {

    private String descri;
    private String servidor;
    private String database;
    private String user;
    private String senha;
    private ArrayList<ServidoresBases> basesBanco = new ArrayList<>();
    private String caminhoDist;
    private File arquivo;
    public void addBase(String descri, String servidor, String database, String user, String senha) {
        this.descri = descri;
        this.servidor = servidor;
        this.database = database;
        this.user = user;
        this.senha = senha;
        
        
    }
    
    public void caminhoDosArquivos() throws URISyntaxException, IOException{
        
        caminhoDist = ServidoresBases.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
	caminhoDist = caminhoDist.substring(1, caminhoDist.lastIndexOf('/') + 1); 
        
        
        
        arquivo = new File( caminhoDist + "\\bases.txt");
        
        boolean existe = arquivo.exists();
        
        if(!existe){
            arquivo.createNewFile();
        }
            
            

    }
    

    public void addbasenoBanco(String descri, String servidor, String database, String user, String senha) throws IOException {
        String insert = String.format("%s, %s, "
                + "%s, %s, "
                + "%sfim\n", descri, servidor, database, user, senha);
        
        FileWriter fw = new FileWriter( arquivo, true );
        BufferedWriter bw = new BufferedWriter( fw );
        bw.write(insert);
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public void addBases(String descri, String servidor, String database, String user, String senha) {
        ServidoresBases base = new ServidoresBases();
        base.addBase(descri, servidor, database, user, senha);
        getBasesBanco().add(base);
        
    }
    
    public void buscarBasesbanco() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader( arquivo );
        BufferedReader br = new BufferedReader( fr );
        String textoArquivo  ="";
        while(br.ready()){
            textoArquivo += br.readLine();
        }
        
        br.close();
        fr.close();
        
        
        String [] linhas =  textoArquivo.split("fim");
        
        if(linhas != null){
            for(String linha : linhas){
            String [] colunas = linha.split(",");
            addBases(colunas[0], colunas[1], colunas[2], colunas[3], colunas[4]);
            
            }
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
