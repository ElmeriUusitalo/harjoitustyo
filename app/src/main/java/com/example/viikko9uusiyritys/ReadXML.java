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

public class ReadXML {
    private NodeList theatre_node;
    private NodeList shows;
    private ArrayList<Movie> shows_array = new ArrayList<Movie>() ;

    //reads xml and returns ArrayList<Theatres>
    public ArrayList<Theatres> readAreasXML() {
        //variables
        String paikka;
        String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
        ArrayList<Theatres> theatre_array = new ArrayList<>();

        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            theatre_node = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < theatre_node.getLength(); i++) {
                Node node = theatre_node.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String ID = element.getElementsByTagName("ID").item(0).getTextContent();
                    paikka = element.getElementsByTagName("Name").item(0).getTextContent();
                    if (paikka.contains(":")) {
                        theatre_array.add(new Theatres(ID, paikka));
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
        return theatre_array;
    }
/*
    public static void readscheduleXML(Theatres theatre) {
        shows_array.clear();
        String paivays;
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(System.currentTimeMillis());
        paivays = (formatter.format(date));
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + theatre.ID + "&dt=" + paivays;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            shows = doc.getDocumentElement().getElementsByTagName("Show");
            String esityksennimi;
            for (int i = 0; i < shows.getLength(); i++) {
                Node node = shows.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    esityksennimi = element.getElementsByTagName("Title").item(0).getTextContent();
                    shows_array.add(new Movie(esityksennimi));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }*/
}