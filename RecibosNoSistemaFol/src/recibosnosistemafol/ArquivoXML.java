/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jacks
 */
public class ArquivoXML {

    private static String id;
    private static String recibo;
    private static String cpf;
    private static String matricula;
    private static String tipoEvento;
    private static String perApur;
    private static String nrRecEvt;

    public void infXML(File arquivo, String tipoArquivoEve) throws ParserConfigurationException, SAXException {
        try {
            //File file = new File("E:\\Jackson\\GitHub\\RecibosnoSistemaFol\\ID1137989050000002022092618390800633.S-2200.xml");
            File file = arquivo;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            //System.out.println(document.getDocumentElement().getNodeName());

            switch (tipoArquivoEve) {
                case "2200.xml":
                    tipoEvento = "evtAdmissao";
                    break;
                case "2299.xml":
                    tipoEvento = "evtDeslig";
                    break;
                case "1200.xml":
                    tipoEvento = "evtRemun";
                    break;
                case "1210.xml":
                    tipoEvento = "evtPgtos";
                    break;
                case "3000.xml":
                    tipoEvento = "evtExclusao";
                    break;
                default:
                    tipoEvento = "n";
                    break;

            }
            if (!"n".equals(tipoEvento)) {

                NodeList nList = document.getElementsByTagName(tipoEvento);

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    tipoEvento = nNode.getNodeName();
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        id = eElement.getAttribute("Id");
                        if (!"1210.xml".equals(tipoArquivoEve) && !"3000.xml".equals(tipoArquivoEve)) {
                            cpf = eElement.getElementsByTagName("cpfTrab").item(0).getTextContent();
                            matricula = eElement.getElementsByTagName("matricula").item(0).getTextContent();
                        } else if ("1210.xml".equals(tipoArquivoEve)) {
                            cpf = eElement.getElementsByTagName("cpfBenef").item(0).getTextContent();

                        }

                        if ("1200.xml".equals(tipoArquivoEve)
                                || "1210.xml".equals(tipoArquivoEve)) {
                            perApur = eElement.getElementsByTagName("perApur").item(0).getTextContent();

                        } else if ("3000.xml".equals(tipoArquivoEve)) {
                            nrRecEvt = eElement.getElementsByTagName("nrRecEvt").item(0).getTextContent();
                        }

                    }
                }

                nList = null;
                nList = document.getElementsByTagName("retornoEvento");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    // System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        recibo = eElement.getElementsByTagName("nrRecibo").item(0).getTextContent();

                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @return the id
     */
    public static String getId() {
        return id;
    }

    /**
     * @return the recibo
     */
    public static String getRecibo() {
        return recibo;
    }

    /**
     * @return the cpf
     */
    public static String getCpf() {
        return cpf;
    }

    /**
     * @return the matricula
     */
    public static String getMatricula() {
        return matricula;
    }

    /**
     * @return the tipoEvento
     */
    public static String getTipoEvento() {
        return tipoEvento;
    }

    public static String getPerApur() {
        return perApur;
    }

    /**
     * @return the nrRecEvt
     */
    public static String getNrRecEvt() {
        return nrRecEvt;
    }

}
