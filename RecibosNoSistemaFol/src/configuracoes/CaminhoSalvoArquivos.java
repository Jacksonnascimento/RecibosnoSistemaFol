/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracoes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

/**
 *
 * @author Jackson
 */
public class CaminhoSalvoArquivos {
    private String caminhoArquivoDosCaminhos;
    private File arquivoDosCaminhos;
    private String [] caminhos;
    

    public CaminhoSalvoArquivos() throws URISyntaxException, IOException {
        caminhoArquivoDosCaminhos = CaminhoSalvoArquivos.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminhoArquivoDosCaminhos = caminhoArquivoDosCaminhos.substring(1, caminhoArquivoDosCaminhos.lastIndexOf('/') + 1);
        
        arquivoDosCaminhos = new File(caminhoArquivoDosCaminhos + "\\Arquivo dos Caminhos.txt");
        
        boolean existe = arquivoDosCaminhos.exists();
        
        if (!existe) {
            arquivoDosCaminhos.createNewFile();
        }
     
        buscarCaminhos();
    }
    
    public String getCaminhoXML(){
        if (caminhos == null){
            return null;
        } else
         return caminhos[0];  
    }
    
    public String getCaminhoSQL(){
        if (caminhos == null){
            return null;
        } else
        return caminhos[1];   
    }
            
    public void setCaminhos(String caminhoXML, String caminhoSQL) throws IOException{
       String texto = caminhoXML + "," + caminhoSQL;
       FileWriter arquivoResultado = new FileWriter(arquivoDosCaminhos);
       PrintWriter gravarInfoAr = new PrintWriter(arquivoResultado);
       gravarInfoAr.print(texto);
       arquivoResultado.close();
    }
    
    public void buscarCaminhos() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(arquivoDosCaminhos);
        BufferedReader br = new BufferedReader(fr);
        String textoArquivo = "";
        while (br.ready()) {
            textoArquivo += br.readLine();
        }
       

        br.close();
        fr.close();

        if (textoArquivo != "") {
            caminhos = textoArquivo.split(",");
        }
     
    }
    
    
    
}
