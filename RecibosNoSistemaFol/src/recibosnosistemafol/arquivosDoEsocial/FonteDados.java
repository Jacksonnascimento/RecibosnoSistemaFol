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
import java.io.PrintWriter;
import java.net.URISyntaxException;

/**
 *
 * @author Jackson
 */
public class FonteDados {

    private String caminho;
    private File arquivoEventosTerceiraFase;
    private File arquivoS2200;
    private File arquivoS2299;
    private File arquivoS3000;

    public FonteDados() throws URISyntaxException {
        caminho = FonteDados.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);

        iniciarCaminhodosEventos(false);
    }

    public void iniciarCaminhodosEventos(boolean insert) {

        if (insert) {
            arquivoEventosTerceiraFase = new File(
                    caminho + "\\Fontes de dados\\eventosTerceiraFase_insert.txt");

            arquivoS2200 = new File(caminho + "\\Fontes de dados\\eventoS2200_insert.txt");

            arquivoS2299 = new File(caminho + "\\Fontes de dados\\eventoS2299_insert.txt");

        } else {
            arquivoEventosTerceiraFase = new File(
                    caminho + "\\Fontes de dados\\eventosTerceiraFase.txt");

            arquivoS2200 = new File(caminho + "\\Fontes de dados\\eventoS2200.txt");

            arquivoS2299 = new File(caminho + "\\Fontes de dados\\eventoS2299.txt");

           
        }
         arquivoS3000 = new File(caminho + "\\Fontes de dados\\eventoS3000.txt");
    }

    public String getEventosTerceiraFase() throws FileNotFoundException, IOException {
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

    public void setEventosTerceiraFase(String fonte) throws IOException {
        arquivoEventosTerceiraFase.delete();
        FileWriter fw = new FileWriter(arquivoEventosTerceiraFase, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();

    }

    public String getEventoS2200() throws FileNotFoundException, IOException {
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

    public void setEventoS2200(String fonte) throws IOException {
        arquivoS2200.delete();
        FileWriter fw = new FileWriter(arquivoS2200, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();

    }

    public String getEventoS2299() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(arquivoS2299);
        BufferedReader br = new BufferedReader(fr);
        String textoArquivo = "";

        while (br.ready()) {
            textoArquivo += br.readLine();
        }

        br.close();
        fr.close();
        return textoArquivo;
    }

    public void setEventoS2299(String fonte) throws IOException {
        arquivoS2299.delete();
        FileWriter fw = new FileWriter(arquivoS2299, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();
    }

    public String getEventoS3000() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(arquivoS3000);
        BufferedReader br = new BufferedReader(fr);
        String textoArquivo = "";

        while (br.ready()) {
            textoArquivo += br.readLine();
        }

        br.close();
        fr.close();
        return textoArquivo;
    }

    public void setEventoS3000(String fonte) throws IOException {
        arquivoS3000.delete();
        FileWriter fw = new FileWriter(arquivoS3000, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();
    }
}
