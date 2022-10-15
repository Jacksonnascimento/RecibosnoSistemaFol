/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.testes;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import recibosnosistemafol.ArquivoXML;
import recibosnosistemafol.arquivosDoEsocial.ArquivosESocial;
import recibosnosistemafol.banco.BancoDados;

/**
 *
 * @author jacks
 */
public class Testes {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        
        ArquivoXML arquixml = new ArquivoXML();
        
        File file = new File("E:\\Jackson\\GitHub\\RecibosnoSistemaFol\\ID1137989050000002022092618390800633.S-2200.xml");
        
        arquixml.infXML(file);
        
        ArquivosESocial esocial = new ArquivosESocial();
        
        esocial.s2200(arquixml.getMatricula(), arquixml.getRecibo());
              
    }
}
