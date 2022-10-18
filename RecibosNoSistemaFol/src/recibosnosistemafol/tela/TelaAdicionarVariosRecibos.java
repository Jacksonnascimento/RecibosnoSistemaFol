/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package recibosnosistemafol.tela;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import recibosnosistemafol.ArquivoXML;
import recibosnosistemafol.arquivosDoEsocial.ArquivosESocial;

/**
 *
 * @author Jackson
 */
public class TelaAdicionarVariosRecibos extends javax.swing.JFrame {
        DefaultListModel model = new DefaultListModel();
        String servidor;
        String database;
        String user;
        String senha;
    /**
     * Creates new form TelaAdicionarVariosRecibos
     */
    public TelaAdicionarVariosRecibos() {
        initComponents();
        addbases();
        txServidor.setText("localhost");
        caminho.setText("D:\\GitHub\\RecibosnoSistemaFol\\Recibos");
        
    }
    
    public void addbases(){
        model = new DefaultListModel();
        model.addElement("Salvar arquivo.txt");
        model.addElement("PM Itapetinga - PC Jack");
        model.addElement("PM Cocos - PC Jack");
             
         
        bases.setModel(model);
    }
    
    public void selecionarBase(){
        
        if("PM Itapetinga - PC Jack".equals(bases.getSelectedValue())){
            servidor = "localhost";
            database = "FPG_WEB_PM_ITAPETINGA";
            user = "sa";
            senha = "87519023";
        } else if("PM Cocos - PC Jack".equals(bases.getSelectedValue())){
            servidor = "localhost";
            database = "FPG_WEB_CM_COCOS";
            user = "sa";
            senha = "87519023";
        } else if ("Salvar arquivo.txt".equals(bases.getSelectedValue())){
            servidor = "txt";
        }
    }
    
    public void addRecibosBanco() throws IOException{
        
        String arquivoUpdate = "";
        
        selecionarBase();
        
        String arquivosNaoAdd = "";
        String caminhoArquivo = caminho.getText();

        
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
                   ) 
                {

                   

                    i = JOptionPane.showConfirmDialog(
                            null,
                            String.format("Deseja adicionar o arquivo de ID: %s?", arquivoXML.getId()),
                            "Continua",
                            JOptionPane.OK_CANCEL_OPTION
                    );
                    
                    if (i == 0) {
                        cont++;
                        if ("evtAdmissao".equals(arquivoXML.getTipoEvento())) {
                            arquivoUpdate += 
                                 esocial.s2200(arquivoXML.getMatricula(), arquivoXML.getRecibo(), servidor, database, user, senha) + "\n\n" ;
                        } else if ("evtDeslig".equals(arquivoXML.getTipoEvento())) {
                            arquivoUpdate += 
                                esocial.s2299(arquivoXML.getMatricula(), arquivoXML.getRecibo(), servidor, database, user, senha) + "\n\n" ;
                        } else if ("evtRemun".equals(arquivoXML.getTipoEvento())){
                            arquivoUpdate += 
                                esocial.s1200(arquivoXML.getCpf(), arquivoXML.getRecibo(), arquivoXML.getPerApur(), servidor, database, user, senha) + "\n\n" ;
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
        if(!"".equals(arquivosNaoAdd)){
            JOptionPane.showMessageDialog(null, "Arquivos que não foram enviados: " + arquivosNaoAdd);
        }
        
        if ("txt".equals(servidor)) {
            Date date = new Date();
            
            String caminhoarquivoResultado = 
                    String.format("D:\\GitHub\\RecibosnoSistemaFol\\Arquivo\\resultado%s.sql", 
                    date.getTime() + date.getDay() + date.getYear());
            
            FileWriter arquivoResultado = new FileWriter(caminhoarquivoResultado);
            PrintWriter gravarInfoAr = new PrintWriter(arquivoResultado);
            
            gravarInfoAr.printf(arquivoUpdate);
            
            arquivoResultado.close();

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txServidor = new javax.swing.JTextField();
        caminho = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bases = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setText("Adicionar recibos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Bases");

        bases.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(bases);

        jButton1.setText("Adicionar base");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caminho)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            try {
                addRecibosBanco();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAdicionarVariosRecibos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> bases;
    private javax.swing.JTextField caminho;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txServidor;
    // End of variables declaration//GEN-END:variables
}
