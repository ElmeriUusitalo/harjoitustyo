package com.example.viikko9uusiyritys;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Attributes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Teatterit {
    protected static NodeList teatteri_node;
    protected static ArrayList<Teatteri> teatteri_array;
    protected static NodeList esitykset;
    protected static ArrayList<Esitys> esitys_array = new ArrayList<Esitys>() ;

    public static void readXML() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            teatteri_node = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            String ID;
            String paikka;
            teatteri_array = new ArrayList<Teatteri>();
            for (int i = 0; i < teatteri_node.getLength(); i++) {
                Node node = teatteri_node.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    ID = element.getElementsByTagName("ID").item(0).getTextContent();
                    paikka = element.getElementsByTagName("Name").item(0).getTextContent();
                    if (paikka.contains(":")) {
                        teatteri_array.add(new Teatteri(ID, paikka));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void readXML2(Teatteri valittu) {
        esitys_array.clear();
        String paivays;
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(System.currentTimeMillis());
        paivays = (formatter.format(date));
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + valittu.ID + "&dt=" + paivays;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            esitykset = doc.getDocumentElement().getElementsByTagName("Show");
            String esityksennimi;
            for (int i = 0; i < esitykset.getLength(); i++) {
                Node node = esitykset.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    esityksennimi = element.getElementsByTagName("Title").item(0).getTextContent();
                    esitys_array.add(new Esitys(esityksennimi));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}