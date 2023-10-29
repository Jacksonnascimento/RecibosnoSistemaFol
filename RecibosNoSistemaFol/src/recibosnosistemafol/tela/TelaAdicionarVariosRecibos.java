/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package recibosnosistemafol.tela;

import configuracoes.CaminhoSalvoArquivos;
import configuracoes.SeletorDeArquivosPastas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import recibosnosistemafol.ArquivoXML;
import recibosnosistemafol.arquivosDoEsocial.ArquivosESocial;
import recibosnosistemafol.arquivosDoEsocial.FonteDados;
import recibosnosistemafol.bases.ServidoresBases;
import java.util.Collections;

/**
 *
 * @author Jackson
 */
public final class TelaAdicionarVariosRecibos extends javax.swing.JFrame {

    private DefaultListModel model = new DefaultListModel();
    private String servidor;
    private String database;
    private String user;
    private String senha;
    private String caminhoDist;
    private ServidoresBases basesbanco;
    private FonteDados fonteDadosArquivos = new FonteDados();
    private String fonteTipo = null;
    private CaminhoSalvoArquivos caminhosSalvos;
    private int contagemNomeArquivo = 0;
    private String arquivoUpdate;
    private int cont;
    private int quantidadeArquivo;
    private File[] aquivosSelecionados;
    private String caminhoarquivoResultado;

    /**
     * Creates new form TelaAdicionarVariosRecibos
     *
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
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
        attributes.setHidden(true);

        fileImagem = new File(caminhoImagens + "esocial-logo.ico");
        attributes = Files.getFileAttributeView(fileImagem.toPath(), DosFileAttributeView.class);
        attributes.readAttributes();
        attributes.setHidden(true);

    }

    public DefaultListModel listaArquivos(String caminhoArquivo, String pesquisa) {
        String nomeArquivo;
        // Crie um objeto File para representar a pasta
        File diretorio = new File(caminhoArquivo);
        contagemNomeArquivo = 1;
        List<Integer> nomesArquivosInt = new ArrayList<>();
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
                        
                        nomeArquivo = nomeArquivo.replace("Arquivo", "").replace(".sql", "").trim();
                        int nomeQuantidade = Integer.parseInt(nomeArquivo);
                        
                       nomesArquivosInt.add(nomeQuantidade);
                       // nomesArquivos.add(nomeArquivo);
                       // listaModel.addElement(nomeArquivo);

                    }
                }
            }
            
            Collections.sort(nomesArquivosInt);
            for(int i : nomesArquivosInt){
                listaModel.addElement("Arquivo " + i + ".sql");
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
        caminhoarquivoResultado = caminhoDist + "\\ArquivoRe";
        
        File pasta = new File(caminhoarquivoResultado);
        if(!pasta.exists()){
            pasta.mkdir();
        }

        listaResultados(caminhoarquivoResultado, null);

        bases.setSelectedValue("Salvar arquivo.sql", true);
    }

    private void listaResultados(String caminhoarquivoResultado, String pesquisa) {
        DefaultListModel modelR = listaArquivos(caminhoarquivoResultado, pesquisa);
        if (modelR != null) {
            listaArquivosSalvos.setModel(modelR);
        }

    }

    public void salvarFonteDados() throws IOException {
        if (fonteTipo != null) {
            switch (fonteTipo) {
                case "terceiraFase" ->
                    fonteDadosArquivos.setEventosTerceiraFase(fonteDados.getText());
                case "s2200" ->
                    fonteDadosArquivos.setEventoS2200(fonteDados.getText());
                case "s2299" ->
                    fonteDadosArquivos.setEventoS2299(fonteDados.getText());
                case "s3000" ->
                    fonteDadosArquivos.setEventoS3000(fonteDados.getText());
                default -> {
                }
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
        ServidoresBases basesS = new ServidoresBases();
        basesS.caminhoDosArquivos();
        basesS.addbasenoBanco(descText.getText(), servidorText.getText(), databaseText.getText(), usrText.getText(), senhaText.getText());
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
        if (aquivosSelecionados == null) {
            JOptionPane.showMessageDialog(null, "Selecione um ou mais arquivos XML ou uma pasta que contenha arquivos XML");
        } else {
            quantidadeArquivo = 0;

            File[] arquivos = aquivosSelecionados;
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

            if (quantidadeArquivo > 0) {

                textoBarra.setVisible(true);
                jProgressBar1.setMaximum(0);
                jProgressBar1.setMaximum(quantidadeArquivo);
                jProgressBar1.setValue(0);
                jProgressBar1.setStringPainted(true);
                jProgressBar1.setSize(30, 50);

                Thread progressThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        cont = 0;
                        jProgressBar1.setVisible(true);
                        for (File file : aquivosSelecionados) {

                            File arquivoFile = file;
                            if (arquivoFile.getName().contains(".xml")) {

                                String[] tipo = arquivoFile.getName().split("-");
                                ArquivoXML arquivoXML = new ArquivoXML();
                                try {
                                    arquivoXML.infXML(arquivoFile, tipo[1]);
                                } catch (ParserConfigurationException ex) {
                                    Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SAXException ex) {
                                    Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                ArquivosESocial esocial = null;
                                try {
                                    esocial = new ArquivosESocial(fazerInsert.isSelected());
                                } catch (URISyntaxException ex) {
                                    Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                if (((("evtAdmissao".equals(arquivoXML.getTipoEvento()) || "evtDeslig".equals(ArquivoXML.getTipoEvento())) || "evtRemun".equals(ArquivoXML.getTipoEvento())) || "evtPgtos".equals(ArquivoXML.getTipoEvento()))
                                        || "evtExclusao".equals(ArquivoXML.getTipoEvento())) {
                                    String tipoEvento = ArquivoXML.getTipoEvento();

                                    if ("evtAdmissao".equals(tipoEvento) && s2200.isSelected()) {
                                        cont++;
                                        jProgressBar1.setValue(cont);

                                        try {
                                            arquivoUpdate += esocial.s2200(ArquivoXML.getMatricula(), ArquivoXML.getRecibo(),
                                                    servidor, database, user, senha) + "\n\n";
                                        } catch (IOException ex) {
                                            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else if ("evtDeslig".equals(tipoEvento) && s2299.isSelected()) {
                                        cont++;
                                        jProgressBar1.setValue(cont);

                                        try {
                                            arquivoUpdate += esocial.s2299(ArquivoXML.getMatricula(),
                                                    ArquivoXML.getRecibo(), servidor, database, user, senha)
                                                    + "\n\n";
                                        } catch (IOException ex) {
                                            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else if ("evtRemun".equals(tipoEvento) && s1200.isSelected()) {
                                        cont++;
                                        jProgressBar1.setValue(cont);

                                        try {
                                            arquivoUpdate += esocial.s1200(ArquivoXML.getCpf(), ArquivoXML.getRecibo(),
                                                    ArquivoXML.getPerApur(), servidor, database, user, senha) + "\n\n";
                                        } catch (IOException ex) {
                                            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else if ("evtPgtos".equals(tipoEvento) && s1210.isSelected()) {
                                        cont++;
                                        jProgressBar1.setValue(cont);

                                        try {
                                            arquivoUpdate += esocial.s1210(ArquivoXML.getCpf(),
                                                    ArquivoXML.getRecibo(),
                                                    ArquivoXML.getPerApur(), servidor, database, user, senha) + "\n\n";
                                        } catch (IOException ex) {
                                            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else if ("evtExclusao".equals(tipoEvento) && s3000.isSelected()) {
                                        cont++;
                                        jProgressBar1.setValue(cont);

                                        try {
                                            arquivoUpdate += esocial.s3000(ArquivoXML.getNrRecEvt(),
                                                    servidor, database, user, senha) + "\n\n";
                                        } catch (IOException ex) {
                                            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }
                                }
                            }

                            textoBarra.setText("Processando " + cont + " de " + quantidadeArquivo);
                        }

                        //
                        JOptionPane.showMessageDialog(null, cont + " arquivo (s) adicionado (s)");
                        textoBarra.setVisible(false);
                        jProgressBar1.setVisible(false);

                        if (cont == quantidadeArquivo) {
                            // frame.setVisible(false);
                            if ("txt".equals(servidor) && cont > 0) {
                                //resultado%s.sql", date.getTime() + date.getDay() + date.getYear())
                                listaResultados(caminhoarquivoResultado, null);
                                String caminhoNomeArquivo = String.format("%s//Arquivo %s.sql",
                                        caminhoarquivoResultado,
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
                    }
                });

                progressThread.start();

            } else {

                JOptionPane.showMessageDialog(null, "Não há registro a ser gerado");

            }

        } //else JOptionPane.showMessageDialog(null, "Não há registro a ser gerado");
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
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jProgressBar1 = new javax.swing.JProgressBar();
        textoBarra = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        s2200 = new javax.swing.JRadioButton();
        s2299 = new javax.swing.JRadioButton();
        s1200 = new javax.swing.JRadioButton();
        s3000 = new javax.swing.JRadioButton();
        s1210 = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaArquivosSalvos = new javax.swing.JList<>();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
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

        jButton2.setText("Adicionar recibos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jProgressBar1.setToolTipText("");

        textoBarra.setText("texto");

        jButton10.setText("Seletor de pasta");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setText("Seletor de arquivos");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        s2200.setText("S-2200");

        s2299.setText("S-2299");

        s1200.setText("1200");

        s3000.setText("S-3000");

        s1210.setText("S-1210");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(s3000, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(158, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(s2200, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s2299, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(s1200, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(s1210, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(textoBarra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s2200)
                    .addComponent(s2299)
                    .addComponent(s1200)
                    .addComponent(s3000)
                    .addComponent(s1210))
                .addGap(24, 24, 24)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(186, Short.MAX_VALUE))
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

        jButton11.setText("Deletar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
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
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(258, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 263, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Sobre", jPanel3);

        getContentPane().add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        listaResultados(caminhoarquivoResultado, null);
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void fazerInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fazerInsertMouseClicked
        try {
            fonteDadosArquivos = new FonteDados();
        } catch (URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
        fonteDadosArquivos.iniciarCaminhodosEventos(fazerInsert.isSelected());
    }//GEN-LAST:event_fazerInsertMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            fonteDados.setText(fonteDadosArquivos.getEventoS3000());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "s3000";
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            fonteDados.setText(fonteDadosArquivos.getEventoS2299());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "s2299";
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            fonteDados.setText(fonteDadosArquivos.getEventoS2200());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "s2200";
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            salvarFonteDados();
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            fonteDados.setText(fonteDadosArquivos.getEventosTerceiraFase());
        } catch (IOException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fonteTipo = "terceiraFase";
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            salvar();
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        List<String> nomeDoArquivo = listaArquivosSalvos.getSelectedValuesList();
        if (listaArquivosSalvos.getSelectedValue() != null) {
            if (nomeDoArquivo != null && JOptionPane.showConfirmDialog(null, "Deseja continuar?", "Deletar arquivo", 0) == 0) {
                for (String item : nomeDoArquivo) {
                    File arquivoDeletar = new File(caminhoarquivoResultado + "\\" + item);
                        if (arquivoDeletar.exists()) {
                            UIManager.put("OptionPane.yesButtonText", "Sim");
                            UIManager.put("OptionPane.noButtonText", "Não");
                            arquivoDeletar.delete();
                        }
                    }
                    listaResultados(caminhoarquivoResultado, null);
                }
            }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        List<String> nomeDoArquivo = listaArquivosSalvos.getSelectedValuesList();
        if (nomeDoArquivo != null) {
            for (String item : nomeDoArquivo) {

                ProcessBuilder processBuilder = new ProcessBuilder(
                    "notepad.exe", caminhoarquivoResultado + "\\" + item);
                    try {
                        processBuilder.start();
                    } catch (IOException ex) {
                        Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            /*  String nomeDoArquivo = listaArquivosSalvos.getSelectedValue();
            if (nomeDoArquivo != null) {
                String caminhoArquivoSelecionado = caminhoAbrir + "\\" + nomeDoArquivo;
                ProcessBuilder processBuilder = new ProcessBuilder(
                    "notepad.exe", caminhoArquivoSelecionado);

                try {
                    processBuilder.start();
                } catch (IOException ex) {
                    Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } */
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        SeletorDeArquivosPastas seletorArquivos = new SeletorDeArquivosPastas();
        aquivosSelecionados = seletorArquivos.arquivosSelecionados();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        SeletorDeArquivosPastas seletorArquivos = new SeletorDeArquivosPastas();
        aquivosSelecionados = seletorArquivos.ArquivoPastaSelecionada();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            addRecibosBanco();
        } catch (IOException | URISyntaxException ex) {
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
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new TelaAdicionarVariosRecibos().setVisible(true);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(TelaAdicionarVariosRecibos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> bases;
    private javax.swing.JTextField databaseText;
    private javax.swing.JTextField descText;
    private javax.swing.JCheckBox fazerInsert;
    private javax.swing.JTextPane fonteDados;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
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
