
import configuracoes.CaminhoSalvoArquivos;
import java.io.IOException;
import java.net.URISyntaxException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jackson
 */
public class Testes {
    public static void main(String[] args) throws URISyntaxException, IOException {
        CaminhoSalvoArquivos caminhosSalvo = new CaminhoSalvoArquivos();
       
        System.out.println(caminhosSalvo.getCaminhoXML());
        System.out.println(caminhosSalvo.getCaminhoSQL());
        
    }
}
