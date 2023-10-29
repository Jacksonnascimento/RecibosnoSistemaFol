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
import java.nio.file.Files;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;

/**
 *
 * @author Jackson
 */
public class CaminhoSalvoArquivos {
    private String caminhoArquivoDosCaminhos;
    private File arquivoDosCaminhos;
    private String [] caminhos;
    DosFileAttributeView attributes; 
    DosFileAttributes attrs; 
    

    public CaminhoSalvoArquivos() throws URISyntaxException, IOException {
        caminhoArquivoDosCaminhos = CaminhoSalvoArquivos.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminhoArquivoDosCaminhos = caminhoArquivoDosCaminhos.substring(1, caminhoArquivoDosCaminhos.lastIndexOf('/') + 1);
        
        arquivoDosCaminhos = new File(caminhoArquivoDosCaminhos + "\\Arquivo dos Caminhos.txt");
        
        
        boolean existe = arquivoDosCaminhos.exists();
        
        if (!existe) {
            arquivoDosCaminhos.createNewFile();
        } 
     
        buscarCaminhos();
        attributes = Files.getFileAttributeView(arquivoDosCaminhos.toPath(), DosFileAttributeView.class);
        attrs = attributes.readAttributes();
        attributes.setHidden(true);
    }
    
    
    public String getCaminhoSQL(){
        if (caminhos == null){
            return null;
        } else
        return caminhos[1];   
    }
            
    public void setCaminhos(String caminhoXML, String caminhoSQL) throws IOException{
       attributes.setHidden(false);
       String texto = caminhoXML + "," + caminhoSQL;
       FileWriter arquivoResultado = new FileWriter(arquivoDosCaminhos);
       PrintWriter gravarInfoAr = new PrintWriter(arquivoResultado);
       gravarInfoAr.printf(texto);
       arquivoResultado.close();
       attributes.setHidden(true);
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
