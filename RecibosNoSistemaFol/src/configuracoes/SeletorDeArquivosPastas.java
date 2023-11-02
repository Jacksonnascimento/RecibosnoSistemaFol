/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracoes;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author jacks
 */
public class SeletorDeArquivosPastas {

    public File[] arquivosSelecionados() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // Permite seleção múltipla
        File[] selectedFiles = null;
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                if (!file.getName().contains(".xml")) {
                    JOptionPane.showMessageDialog(null, file.getName() + " não é um arquivo XML");
                    return null;
                }
            }

        }
        return selectedFiles;
    }

    public File[] ArquivoPastaSelecionada() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File[] arquivos = null;
        int returnValue = folderChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            File diretorio = new File(selectedFolder.getAbsolutePath());
            if (diretorio.exists() && diretorio.isDirectory()) {
                arquivos = diretorio.listFiles();
                for (File file : arquivos) {
                    if (!file.getName().contains(".xml")) {
                        JOptionPane.showMessageDialog(null, file.getName() + " não é um arquivo XML");
                        return null;
                    }
                }
            }
        }

        return arquivos;
    }
}
