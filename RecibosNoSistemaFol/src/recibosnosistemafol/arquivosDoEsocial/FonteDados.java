/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.arquivosDoEsocial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author jacks
 */
public class FonteDados {
    private String caminho;
    private File arquivoEventosTerceiraFase;
    private File arquivoS2200;

    public FonteDados() throws URISyntaxException {
        caminho = FonteDados.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);
        
        arquivoEventosTerceiraFase = new File (
      caminho + "\\Fontes de dados\\eventosTerceiraFase.txt");
        
        arquivoS2200 = new File(caminho + "\\Fontes de dados\\eventoS2200.txt");
    }
    
    public String getEventosTerceiraFase() throws FileNotFoundException, IOException{
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
    
    public void setEventosTerceiraFase(String fonte) throws IOException{
        arquivoEventosTerceiraFase.delete();
        FileWriter fw = new FileWriter(arquivoEventosTerceiraFase, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public String getEventoS2200() throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(arquivoS2200);
        BufferedReader br = new BufferedReader(fr);
        String textoArquivo = "";
        
        while (br.ready()) {
            textoArquivo += br.readLine();
        }

        br.close();
        fr.close();
        return textoArquivo;
    }
    
    public void setEventoS2200(String fonte) throws IOException{
        arquivoS2200.delete();
        FileWriter fw = new FileWriter(arquivoS2200, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();
    }
}