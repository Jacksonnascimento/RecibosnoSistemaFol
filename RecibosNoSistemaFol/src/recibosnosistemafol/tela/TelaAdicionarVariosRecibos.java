/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package recibosnosistemafol.tela;

import configuracoes.CaminhoSalvoArquivos;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import recibosnosistemafol.ArquivoXML;
import recibosnosistemafol.arquivosDoEsocial.ArquivosESocial;
import recibosnosistemafol.arquivosDoEsocial.FonteDados;
import recibosnosistemafol.bases.ServidoresBases;

/**
 *
 * @author Jackson
 */
public class TelaAdicionarVariosRecibos extends javax.swing.JFrame {

    private DefaultListModel model = new DefaultListModel();
    private String servidor;
    private String database;
    private String user;
    private String senha;
    private String caminhoDist;
    private ServidoresBases basesbanco;
    private boolean insert;
    private FonteDados fonteDadosArquivos = new FonteDados();
    private String fonteTipo = null;
    private CaminhoSalvoArquivos caminhosSalvos;
    private String caminhoAbrir;
    private int contagemNomeArquivo = 0;
    private String arquivoUpdate;
    private String arquivosNaoAdd;
    private int cont;
    int quantidadeArquivo;
    

    /**
     * Creates new form TelaAdicionarVariosRecibos
     */
    public TelaAdicionarVariosRecibos() throws URISyntaxException, IOException {
        initComponents();
        addBase();
        basesDoBanco();
        opcoesDefaConfigu();
        caminhosIniciais();
        setResizable(false);
        setTitle("Recibos do eSocial");
        imagemIco();
        setSize(600, 500);
        listaArquivosSalvos.setSize(600, 500);
        jProgressBar1.setVisible(false);
        textoBarra.setVisible(false);

    }

    public void imagemIco() throws URISyntaxException, IOException {
        String caminhoImagens = TelaAdicionarVariosRecibos.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminhoImagens = caminhoImagens.substring(1, caminhoImagens.lastIndexOf('/') + 1);

        String imagemCaminho = caminhoImagens + "\\esocial-logo.png";

        ImageIcon icone = new ImageIcon(imagemCaminho);
        this.setIconImage(icone.getImage());

        File fileImagem = new File(imagemCaminho);
        DosFileAttributeView attributes = Files.getFileAttributeView(fileImagem.toPath(), DosFileAttributeView.class);
        DosFileAttributes attrs = attributes.readAttributes();
        attributes.setHidden(true);

        fileImagem = new File(caminhoImagens + "esocial-logo.ico");
        attributes = Files.getFileAttributeView(fileImagem.toPath(), DosFileAttributeView.class);
        attrs = attributes.readAttributes();
        attributes.setHidden(true);

    }

    public DefaultListModel listaArquivos(String caminhoArquivo, String pesquisa) {
        String nomeArquivo = "";
        // Crie um objeto File para representar a pasta
        File diretorio = new File(caminhoArquivo);
        contagemNomeArquivo = 1;
        // Verifique se o diretório existe
        if (diretorio.exists() && diretorio.isDirectory()) {
            // Obtenha a lista de arquivos na pasta
            File[] arquivos = diretorio.listFiles();
            DefaultListModel listaModel = new DefaultListModel();
            // Itere sobre a lista de arquivos e imprima seus nomes
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    nomeArquivo = arquivo.getName();
                    if (nomeArquivo.contains(".sql")) {
                        contagemNomeArquivo++;
                        //System.out.println(contagemNomeArquivo);
                        if (pesquisa == null || pesquisa == "") {
                            listaModel.addElement(nomeArquivo);
                        } else if (nomeArquivo.toLowerCase().contains(pesquisa.toLowerCase())) {
                            listaModel.addElement(nomeArquivo);
                        }
                    }
                }
            }

           
            return listaModel;

        } else {
            System.out.println("O diretório especificado não existe ou não é uma pasta.");
            return null;
        }

    }

    public void caminhosIniciais() throws URISyntaxException, IOException {
        caminhoDist = TelaAdicionarVariosRecibos.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        caminhoDist = caminhoDist.substring(1, caminhoDist.lastIndexOf('/') + 1);
        caminho.setText(caminhoDist + "\\ArquivosXML");
        caminhoSalvoArquivoXML.setText(caminhoDist + "\\ArquivosXML");
        String caminhoarquivoResultado
                = caminhoDist + "\\ArquivoRe";
        caminhoAbrir = caminhoarquivoResultado;
        caminhoSalvoArquivoSQL.setText(caminhoarquivoResultado);

        listaResultados(caminhoarquivoResultado, null);
        caminhoSalvo();

        bases.setSelectedValue("Salvar arquivo.sql", true);
    }

    private void listaResultados(String caminhoarquivoResultado, String pesquisa) {
        DefaultListModel model = listaArquivos(caminhoarquivoResultado, pesquisa);
        if (model != null) {
            listaArquivosSalvos.setModel(model);
        }

    }

    public void caminhoSalvo() throws URISyntaxException, IOException {
        caminhosSalvos = new CaminhoSalvoArquivos();

        if (caminhosSalvos.getCaminhoSQL() != null && caminhosSalvos.getCaminhoSQL() != "") {
            caminhoSalvoArquivoSQL.setText(caminhosSalvos.getCaminhoSQL());
        }

        if (caminhosSalvos.getCaminhoXML() != null && caminhosSalvos.getCaminhoXML() != "") {
            caminho.setText(caminhosSalvos.getCaminhoXML());
            caminhoSalvoArquivoXML.setText(caminhosSalvos.getCaminhoXML());
        }
    }

    public void salvarFonteDados() throws IOException {
        if (fonteTipo != null) {
            if (fonteTipo.equals("terceiraFase")) {
                fonteDadosArquivos.setEventosTerceiraFase(fonteDados.getText());
            } else if (fonteTipo.equals("s2200")) {
                fonteDadosArquivos.setEventoS2200(fonteDados.getText());
            } else if (fonteTipo.equals("s2299")) {
                fonteDadosArquivos.setEventoS2299(fonteDados.getText());
            } else if (fonteTipo.equals("s3000")) {
                fonteDadosArquivos.setEventoS3000(fonteDados.getText());
            }
        }
    }

    public void opcoesDefaConfigu() {
        s2200.setSelected(true);
        s2299.setSelected(true);
        s3000.setSelected(true);
        s1200.setSelected(true);
        s1210.setSelected(true);

    }

    public void salvar() throws IOException, URISyntaxException {
        ServidoresBases bases = new ServidoresBases();
        bases.caminhoDosArquivos();
        bases.addbasenoBanco(descText.getText(), servidorText.getText(), databaseText.getText(), usrText.getText(), senhaText.getText());
        zerarLis();
        basesDoBanco();
        descText.setText(null);
        servidorText.setText(null);
        databaseText.setText(null);
        usrText.setText(null);
        senhaText.setText(null);

    }

    public void addBase() {
        model = new DefaultListModel();
        model.addElement("Salvar arquivo.sql");
        bases.setModel(model);

    }

    public void zerarLis() {
        model = null;
        addBase();
    }

    public void selecionarBase() {
        if ("Salvar arquivo.sql".equals(bases.getSelectedValue())) {
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

    public void addRecibosBanco() throws IOException, URISyntaxException {

        arquivoUpdate = "";

        selecionarBase();

        arquivosNaoAdd = "";

        quantidadeArquivo = 0;
        File diretorio = new File(caminho.getText());
        if (diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        if (arquivo.getName().contains("S-2200.xml") && s2200.isSelected()) {
                            quantidadeArquivo++;
                        }
                        if (arquivo.getName().contains("S-2299.xml") && s2299.isSelected()) {
                            quantidadeArquivo++;
                        }
                        if (arquivo.getName().contains("S-3000.xml") && s3000.isSelected()) {
                            quantidadeArquivo++;
                        }
                        if (arquivo.getName().contains("S-1200.xml") && s1200.isSelected()) {
                            quantidadeArquivo++;
                        }
                        if (arquivo.getName().contains("S-1210.xml") && s1210.isSelected()) {
                            quantidadeArquivo++;
                        }

                    }
                }
            }
        }
        
        if(quantidadeArquivo > 0){
            
      
        textoBarra.setVisible(true);
        jProgressBar1.setMaximum(0);
        jProgressBar1.setMaximum(quantidadeArquivo);
        jProgressBar1.setValue(0);
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setSize(30, 50);
       // progressBar.setStringPainted(true); // Exibir porcentagem

        Thread progressThread = new Thread(() -> {
            cont = 0;
            jProgressBar1.setVisible(true);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(caminho.getText()))) {
                for (Path file : stream) {
                    
                    File arquivoFile = file.toFile();
                    if (arquivoFile.getName().contains(".xml")) {

                        String[] tipo = arquivoFile.getName().split("-");
                        ArquivoXML arquivoXML = new ArquivoXML();
                        arquivoXML.infXML(arquivoFile, tipo[1]);

                        ArquivosESocial esocial = new ArquivosESocial(fazerInsert.isSelected());

                        if ("evtAdmissao".equals(arquivoXML.getTipoEvento())
                                || "evtDeslig".equals(arquivoXML.getTipoEvento())
                                || "evtRemun".equals(arquivoXML.getTipoEvento())
                                || "evtPgtos".equals(arquivoXML.getTipoEvento())
                                || "evtExclusao".equals(arquivoXML.getTipoEvento())) {

                            String tipoEvento = arquivoXML.getTipoEvento();

                            if ("evtAdmissao".equals(tipoEvento) && s2200.isSelected()) {
                                cont++;
                                 jProgressBar1.setValue(cont);
                                if (!insert) {
                                    arquivoUpdate
                                            += esocial.s2200(arquivoXML.getMatricula(), arquivoXML.getRecibo(), servidor, database, user, senha) + "\n\n";
                                }

                            } else if ("evtDeslig".equals(tipoEvento) && s2299.isSelected()) {
                                cont++;
                                 jProgressBar1.setValue(cont);
                                if (!insert) {
                                    arquivoUpdate
                                            += esocial.s2299(arquivoXML.getMatricula(),
                                                    arquivoXML.getRecibo(), servidor, database, user, senha)
                                            + "\n\n";
                                }

                            } else if ("evtRemun".equals(tipoEvento) && s1200.isSelected()) {
                                cont++;
                                 jProgressBar1.setValue(cont);
                                if (!insert) {
                                    arquivoUpdate
                                            += esocial.s1200(arquivoXML.getCpf(), arquivoXML.getRecibo(),
                                                    arquivoXML.getPerApur(), servidor, database, user, senha)
                                            + "\n\n";
                                } else {
                                    String[] anoMes = arquivoXML.getPerApur().split("-");
                                    int mes = Integer.parseInt(anoMes[1]);
                                    int ano = Integer.parseInt(anoMes[0]);

                                    String perApuracao = String.format("%s%s01 ", ano, mes);

                                    arquivoUpdate
                                            += "\n\n";
                                }

                            } else if ("evtPgtos".equals(tipoEvento) && s1210.isSelected()) {
                                cont++;
                                 jProgressBar1.setValue(cont);
                                if (!insert) {
                                    arquivoUpdate
                                            += esocial.s1210(arquivoXML.getCpf(),
                                                    arquivoXML.getRecibo(),
                                                    arquivoXML.getPerApur(), servidor, database, user, senha)
                                            + "\n\n";
                                }

                            } else if ("evtExclusao".equals(tipoEvento) && s3000.isSelected()) {
                                cont++;
                                 jProgressBar1.setValue(cont);
                                if (!insert) {
                                    arquivoUpdate
                                            += esocial.s3000(arquivoXML.getNrRecEvt(),
                                                    servidor, database, user, senha) + "\n\n";
                                }

                            } else {
                                arquivosNaoAdd += arquivoFile.getName() + "\n";
                            }
                            
                            
                        }
                    }
                    
                    textoBarra.setText("Processando " + cont + " de "  + quantidadeArquivo);
                }
            } catch (IOException | DirectoryIteratorException ex) {
                System.err.println(ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                JOptionPane.showMessageDialog(null, cont + " arquivo (s) adicionado (s)");
                textoBarra.setVisible(false);
                jProgressBar1.setVisible(false);
                
            
            if (cont == quantidadeArquivo) {
               // frame.setVisible(false);
                if ("txt".equals(servidor) && cont > 0) {
                    //resultado%s.sql", date.getTime() + date.getDay() + date.getYear())
                    listaResultados(caminhoAbrir, null);
                    String caminhoNomeArquivo = String.format("%s//Arquivo %s.sql",
                            caminhoSalvoArquivoSQL.getText(),
                            contagemNomeArquivo);
                    FileWriter arquivoResultado;
                    try {
                        arquivoResultado = new FileWriter(caminhoNomeArquivo);
                        PrintWriter gravarInfoAr = new PrintWriter(arquivoResultado);
                        gravarInfoAr.printf(arquivoUpdate);
                        arquivoResultado.close();
                        ProcessBuilder processBuilder = new ProcessBuilder("notepad.exe", caminhoNomeArquivo);
                        processBuilder.start();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });

        progressThread.start();
       
        } else {
            
         JOptionPane.showMessageDialog(null, "Não há registro a ser gerado");
        
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
        jDialog1 = new javax.swing.JDialog();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bases = new javax.swing.JList<>();
        caminho = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jProgressBar1 = new javax.swing.JProgressBar();
        textoBarra = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaArquivosSalvos = new javax.swing.JList<>();
        jButton9 = new javax.swing.JButton();
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
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        fonteDados = new javax.swing.JTextPane();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        fazerInsert = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        caminhoSalvoArquivoXML = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        caminhoSalvoArquivoSQL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        s2200 = new javax.swing.JRadioButton();
        s1200 = new javax.swing.JRadioButton();
        s3000 = new javax.swing.JRadioButton();
        s2299 = new javax.swing.JRadioButton();
        s1210 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 570));

        jTabbedPane2.setPreferredSize(new java.awt.Dimension(600, 677));
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel4.setPreferredSize(new java.awt.Dimension(600, 293));

        bases.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(bases);

        caminho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caminhoActionPerformed(evt);
            }
        });

        jButton2.setText("Adicionar recibos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jProgressBar1.setToolTipText("");

        textoBarra.setText("texto");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textoBarra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jTabbedPane2.addTab("Bases", jPanel4);

        listaArquivosSalvos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listaArquivosSalvos);

        jButton9.setText("Abrir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Arquivos sql", jPanel6);

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(usrText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(senhaText, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(databaseText, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descText, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(servidorText, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {databaseText, descText, jButton1, servidorText});

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
                .addGap(20, 20, 20))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {databaseText, descText, jButton1, servidorText});

        jTabbedPane2.addTab("Adicionar bases", jPanel1);

        jScrollPane3.setViewportView(fonteDados);

        jButton3.setText("3ª fase");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Salvar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("S-2200");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("S-2299");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("S-3000");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        fazerInsert.setText("Insert");
        fazerInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fazerInsertMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fazerInsert)
                        .addGap(32, 32, 32)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3)
                            .addComponent(jButton5)
                            .addComponent(jButton6)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(fazerInsert))
                .addGap(34, 34, 34))
        );

        jTabbedPane2.addTab("Fonte de dados", jPanel5);

        caminhoSalvoArquivoXML.setToolTipText("");
        caminhoSalvoArquivoXML.setName(""); // NOI18N

        jLabel1.setText("Caminho dos arquivos XML");

        jLabel5.setText("Caminho dos arquivos SQL");

        jButton8.setText("Salvar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        s2200.setText("S-2200");

        s1200.setText("S-1200");

        s3000.setText("S-3000");

        s2299.setText("S-2299");

        s1210.setText("S-1210");

        jLabel8.setText("Arquivos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(caminhoSalvoArquivoXML)
                                    .addComponent(caminhoSalvoArquivoSQL)
                                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel8)
                                .addGap(32, 32, 32)
                                .addComponent(s2200)
                                .addGap(28, 28, 28)
                                .addComponent(s2299)
                                .addGap(34, 34, 34)
                                .addComponent(s1200)
                                .addGap(18, 18, 18)
                                .addComponent(s1210)
                                .addGap(18, 18, 18)
                                .addComponent(s3000))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(633, 633, 633)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {caminhoSalvoArquivoSQL, caminhoSalvoArquivoXML, jButton8});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s2200)
                    .addComponent(s2299)
                    .addComponent(s1200)
                    .addComponent(s1210)
                    .addComponent(s3000)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(caminhoSalvoArquivoXML, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(caminhoSalvoArquivoSQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {caminhoSalvoArquivoSQL, caminhoSalvoArquivoXML, jButton8});

        caminhoSalvoArquivoXML.getAccessibleContext().setAccessibleName("");

        jTabbedPane2.addTab("Configurações", jPanel2);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Sitka Heading", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Programa desenvolvido para inserir os recibos do eSocial no sistema de \nfolha de pagamento. \n\n\n \tDesenvolvedor: Jackson Santos Nascimento\n\tE-mail: jacksonnascimento84@hotmail.com\n\tLinkedIn: www.linkedin.com/in/nascimentojackson/\n\tRepositório: github.com/Jacksonnascimento/RecibosnoSistemaFol\n\n\n");
        jTextArea1.setMaximumSize(new java.awt.Dimension(45, 90));
        jTextArea1.setMinimumSize(new java.awt.Dimension(45, 90));
        jTextArea1.setPreferredSize(new java.awt.Dimension(45, 90));
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 82, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Sobre", jPanel3);

        getContentPane().add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            salvar();
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            addRecibosBanco();
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            fonteDados.setText(fonteDadosArquivos.getEventosTerceiraFase());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "terceiraFase";

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            salvarFonteDados();
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            fonteDados.setText(fonteDadosArquivos.getEventoS2200());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "s2200";
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            fonteDados.setText(fonteDadosArquivos.getEventoS2299());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "s2299";
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            fonteDados.setText(fonteDadosArquivos.getEventoS3000());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "s3000";
    }//GEN-LAST:event_jButton7ActionPerformed

    private void caminhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caminhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caminhoActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String nomeDoArquivo = listaArquivosSalvos.getSelectedValue();
        if (nomeDoArquivo != null) {
            String caminhoArquivoSelecionado = caminhoAbrir + "\\" + nomeDoArquivo;
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "notepad.exe", caminhoArquivoSelecionado);

            try {
                processBuilder.start();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            caminhosSalvos.setCaminhos(caminhoSalvoArquivoXML.getText(), caminhoSalvoArquivoSQL.getText());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            caminhoSalvo();
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void fazerInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fazerInsertMouseClicked
        try {
            fonteDadosArquivos = new FonteDados();
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
        fonteDadosArquivos.iniciarCaminhodosEventos(fazerInsert.isSelected());
    }//GEN-LAST:event_fazerInsertMouseClicked

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        listaResultados(caminhoAbrir, null);
    }//GEN-LAST:event_jTabbedPane2MouseClicked

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
                } catch (IOException ex) {
                    Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> bases;
    private javax.swing.JTextField caminho;
    private javax.swing.JTextField caminhoSalvoArquivoSQL;
    private javax.swing.JTextField caminhoSalvoArquivoXML;
    private javax.swing.JTextField databaseText;
    private javax.swing.JTextField descText;
    private javax.swing.JCheckBox fazerInsert;
    private javax.swing.JTextPane fonteDados;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JList<String> listaArquivosSalvos;
    private javax.swing.JRadioButton s1200;
    private javax.swing.JRadioButton s1210;
    private javax.swing.JRadioButton s2200;
    private javax.swing.JRadioButton s2299;
    private javax.swing.JRadioButton s3000;
    private javax.swing.JPasswordField senhaText;
    private javax.swing.JTextField servidorText;
    private javax.swing.JLabel textoBarra;
    private javax.swing.JTextField txServidor;
    private javax.swing.JTextField usrText;
    // End of variables declaration//GEN-END:variables
}
