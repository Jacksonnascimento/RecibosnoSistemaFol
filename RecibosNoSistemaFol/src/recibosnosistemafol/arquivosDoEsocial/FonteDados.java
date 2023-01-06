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

    public FonteDados() throws URISyntaxException {
        caminho = FonteDados.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);

    }

    public String getFonteEvento(String tipoEvento) throws FileNotFoundException, IOException {
        String caminhoDoEvento = caminho + "\\Fontes de dados\\";
        if (tipoEvento.equals("eventosTerceiraFase")) {
            caminhoDoEvento += "eventosTerceiraFase.txt";
        } else if (tipoEvento.equals("s2200")) {
            caminhoDoEvento += "eventoS2200.txt";
        } else if (tipoEvento.equals("s2299")) {
            caminhoDoEvento += "eventoS2299.txt";
        } else if (tipoEvento.equals("s3000")) {
            caminhoDoEvento += "eventoS3000.txt";
        }
        File arquivo = new File(caminhoDoEvento);

        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        String textoArquivo = "";

        while (br.ready()) {
            textoArquivo += br.readLine();
        }

        br.close();
        fr.close();
        return textoArquivo;
    }

    public void setFonteEvento(String tipoEvento, String fonte) throws IOException {
        String caminhoDoEvento = caminho + "\\Fontes de dados\\";
        if (tipoEvento.equals("eventosTerceiraFase")) {
            caminhoDoEvento += "eventosTerceiraFase.txt";
        } else if (tipoEvento.equals("s2200")) {
            caminhoDoEvento += "eventoS2200.txt";
        } else if (tipoEvento.equals("s2299")) {
            caminhoDoEvento += "eventoS2299.txt";
        } else if (tipoEvento.equals("s3000")) {
            caminhoDoEvento += "eventoS3000.txt";
        }
        File arquivo = new File(caminhoDoEvento);

        arquivo.delete();
        FileWriter fw = new FileWriter(arquivo, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fonte);
        bw.newLine();
        bw.close();
        fw.close();
    }

}
