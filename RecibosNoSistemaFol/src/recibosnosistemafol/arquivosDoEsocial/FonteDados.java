/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.arquivosDoEsocial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author jacks
 */
public class FonteDados {
    private String caminho;
    private File arquivoEventosTerceiraFase;

    public FonteDados() throws URISyntaxException {
        caminho = FonteDados.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);
        
        arquivoEventosTerceiraFase = new File (
      caminho + "\\Fontes de dados\\eventosTerceiraFase.txt");
    }
    
    public String geteventosTerceiraFase() throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(arquivoEventosTerceiraFase);
        BufferedReader br = new BufferedReader(fr);
        String textoArquivo = "";
        
        while (br.ready()) {
            textoArquivo += br.readLine();
        }

        br.close();
        fr.close();
        return textoArquivo;
    }
    
}
