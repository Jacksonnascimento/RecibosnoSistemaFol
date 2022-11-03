/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package recibosnosistemafol.tela;

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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import recibosnosistemafol.ArquivoXML;
import recibosnosistemafol.arquivosDoEsocial.ArquivosESocial;
import recibosnosistemafol.bases.ServidoresBases;

/**
 *
 * @author Jackson
 */
public class TelaAdicionarVariosRecibos extends javax.swing.JFrame {

    private DefaultListModel model = new DefaultListModel();
    private  String servidor;
    private String database;
    private String user;
    private String senha;
    private String caminhoDist;
    private ServidoresBases basesbanco;
    

    /**
     * Creates new form TelaAdicionarVariosRecibos
     */
    public TelaAdicionarVariosRecibos() throws URISyntaxException  {
        initComponents();
        addBase();
      //  caminho.setText("D:\\GitHub\\RecibosnoSistemaFol\\Recibos"); //apenas na minha máquina

         caminhoDist = TelaAdicionarVariosRecibos.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			caminhoDist = caminhoDist.substring(1, caminhoDist.lastIndexOf('/') + 1); 
                        
        caminho.setText(caminhoDist + "\\ArquivosXML");       
         
    }
    
     public void salvar() throws IOException, URISyntaxException{
        ServidoresBases bases = new ServidoresBases();
        bases.caminhoDosArquivos();
        bases.addbasenoBanco(descText.getText(), servidorText.getText(), databaseText.getText(), usrText.getText(), senhaText.getText());
        zerarLis();
        basesDoBanco();
        
    }

    public void addBase() {
        model = new DefaultListModel();
        model.addElement("Salvar arquivo.txt");
        bases.setModel(model);

    }
    
    public void zerarLis(){
        model = null;
        addBase();
    }

    public void selecionarBase() {
        if ("Salvar arquivo.txt".equals(bases.getSelectedValue())) {
            servidor = "txt";
        } else {
            ServidoresBases bas = basesbanco.getBaseDesc(bases.getSelectedValue());
            servidor = bas.getServidor();
            database = bas.getDatabase();
            user = bas.getUser();
            senha = bas.getSenha();

        }
    }

    public void basesDoBanco() throws IOException, URISyntaxException {
        basesbanco = new ServidoresBases();
        basesbanco.caminhoDosArquivos();
        basesbanco.buscarBasesbanco();
        for (ServidoresBases base : basesbanco.getBasesBanco()) {
            model.addElement(base.getDescri());
        }

        bases.setModel(model);
    }

    public void addRecibosBanco() throws IOException {

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
        

        if ("txt".equals(servidor)) {
            Date date = new Date();

            /*String caminhoarquivoResultado
                    = String.format("E:\\Jackson\\GitHub\\RecibosnoSistemaFol\\Arquivo\\resultado%s.sql",
                            date.getTime() + date.getDay() + date.getYear()); //apenas na minha máquina */

             String caminhoarquivoResultado
                    = String.format(caminhoDist + "\\ArquivoRe\\resultado%s.sql",
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
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bases = new javax.swing.JList<>();
        caminho = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        descText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        servidorText = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        databaseText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        usrText = new javax.swing.JTextField();
        senhaText = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bases.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(bases);

        jButton2.setText("Adicionar recibos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Banco de bases");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(48, 48, 48)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(59, 59, 59)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(195, 195, 195)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton2))
                    .addContainerGap(72, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Bases", jPanel4);

        jLabel2.setText("Descrição");

        jLabel3.setText("Servidor");

        jLabel6.setText("Database");

        jLabel4.setText("Login");

        senhaText.setText("jPasswordField1");

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(descText, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(servidorText))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(usrText, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(senhaText))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(databaseText, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(descText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(servidorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(databaseText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(usrText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(senhaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Adicionar bases", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 289, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Configurações", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            salvar();
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        zerarLis();
        try {
            basesDoBanco();
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
           
          
    }//GEN-LAST:event_jButton3ActionPerformed

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
                try {
                    new TelaAdicionarVariosRecibos().setVisible(true);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> bases;
    private javax.swing.JTextField caminho;
    private javax.swing.JTextField databaseText;
    private javax.swing.JTextField descText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPasswordField senhaText;
    private javax.swing.JTextField servidorText;
    private javax.swing.JTextField txServidor;
    private javax.swing.JTextField usrText;
    // End of variables declaration//GEN-END:variables
}
