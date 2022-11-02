
import java.io.IOException;
import java.net.URISyntaxException;
import recibosnosistemafol.bases.ServidoresBases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jacks
 */
public class Teste {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServidoresBases bases = new ServidoresBases();
        bases.caminhoDosArquivos();
        bases.addbasenoBanco("des", "servidor", "database", "user", "senha");
        bases.addbasenoBanco("des", "servidor", "database", "user", "senha");
        bases.addbasenoBanco("ola", "servidor", "database", "sa", "senha");
        bases.buscarBasesbanco();
        
        System.out.println(bases.getBaseDesc("olá").getDescri());
            
        
    }
}
