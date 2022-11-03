/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package add;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import recibosnosistemafol.ArquivoXML;
import recibosnosistemafol.arquivosDoEsocial.ArquivosESocial;
import recibosnosistemafol.tela.TelaAdicionarVariosRecibos;

/**
 *
 * @author jacks
 */
public class RecibosBanco {
    
    private  String servidor;
    private String database;
    private String user;
    private String senha;
    private String caminhoDist;
    private String caminhoarquivoResultado;
    
   public void caminhaDeDestino() throws URISyntaxException{
       String caminhoDist = RecibosBanco.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			caminhoDist = caminhoDist.substring(1, caminhoDist.lastIndexOf('/') + 1) 
                              +  "\\ArquivosXML";
   }
   
   public void setCaminhoDist(String caminho) {
       this.caminhoDist = caminho;
   }
   
   public void setcaminhoResultado(String caminho){
       this.caminhoarquivoResultado = caminho;
   }
   
   
   public void caminhoResultado() throws IOException{
       Date date = new Date();
             caminhoarquivoResultado
                    = String.format(caminhoDist + "\\ArquivoRe\\resultado%s.sql",
                            date.getTime() + date.getDay() + date.getYear()); 
            
   }

    public String getCaminhoDist() {
        return caminhoDist;
    }

    public String getCaminhoarquivoResultado() {
        return caminhoarquivoResultado;
    }
   
   
   
   public void ArquivoResultado(String arquivoUpdate, String caminhoarquivoResultado) throws IOException{
            FileWriter arquivoResultado = new FileWriter(caminhoarquivoResultado);
            PrintWriter gravarInfoAr = new PrintWriter(arquivoResultado);
           
            gravarInfoAr.printf(arquivoUpdate);

            arquivoResultado.close();
   }
 
 public void addRecibosBanco() throws IOException {

        String arquivoUpdate = "";
         
                        
       // caminho.setText(caminhoDist + "\\ArquivosXML");  
        
       // selecionarBase();

        String arquivosNaoAdd = "";
        String caminhoArquivo = "";//caminho.getText();

        int cont = 0;
        int i = 0;
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(caminhoArquivo))) {
            for (Path file : stream) {
                File arquivoFile = file.toFile();

                String[] tipo = arquivoFile.getName().split("-");
                ArquivoXML arquivoXML = new ArquivoXML();
                arquivoXML.infXML(arquivoFile, tipo[1]);

                ArquivosESocial esocial = new ArquivosESocial();

                if ("evtAdmissao".equals(arquivoXML.getTipoEvento())
                        || "evtDeslig".equals(arquivoXML.getTipoEvento())
                        || "evtRemun".equals(arquivoXML.getTipoEvento())
                        || "evtPgtos".equals(arquivoXML.getTipoEvento())
                        || "evtExclusao".equals(arquivoXML.getTipoEvento())) {

                /*    i = JOptionPane.showConfirmDialog(
                            null,
                            String.format("Deseja adicionar o arquivo de ID: %s?", arquivoXML.getId()),
                            "Continua",
                            JOptionPane.OK_CANCEL_OPTION
                    ); */ 
                    
                String tipoEvento = arquivoXML.getTipoEvento();
                    if (i == 0) {
                        cont++;
                        if ("evtAdmissao".equals(tipoEvento)) {
                            arquivoUpdate
                                    += esocial.s2200(arquivoXML.getMatricula(), arquivoXML.getRecibo(), servidor, database, user, senha) + "\n\n";
                        } else if ("evtDeslig".equals(tipoEvento)) {
                            arquivoUpdate
                                    += esocial.s2299(arquivoXML.getMatricula(), arquivoXML.getRecibo(), servidor, database, user, senha) + "\n\n";
                        } else if ("evtRemun".equals(tipoEvento)) {
                            arquivoUpdate
                                    += esocial.s1200(arquivoXML.getCpf(), arquivoXML.getRecibo(), arquivoXML.getPerApur(), servidor, database, user, senha) + "\n\n";
                        } else if ("evtPgtos".equals(tipoEvento)) {
                            arquivoUpdate
                                    += esocial.s1210(arquivoXML.getCpf(), arquivoXML.getRecibo(), arquivoXML.getPerApur(), servidor, database, user, senha) + "\n\n";
                        } else if ("evtExclusao".equals(tipoEvento)) {
                            arquivoUpdate
                                    += esocial.s3000(arquivoXML.getNrRecEvt(), servidor, database, user, senha) + "\n\n";
                        }

                    }
                } else {
                    arquivosNaoAdd += arquivoFile.getName() + "\n";
                }

            }
        } catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, cont + " arquivo (s) adicionado (s)");
        


        
    }
}
