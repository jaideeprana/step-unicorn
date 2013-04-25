package com.thoughtWorks.Server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReportGenerator {

    public void generate(String path, DataOutputStream output) throws IOException {

        FileInputStream requestedFile = new FileInputStream(path);

        byte [] buffer = new byte[1024];
        while (true) {
            int b = requestedFile.read(buffer, 0,1024);
            if (b == -1) {
                break;
            }
            output.write(buffer,0,b);
        }
        requestedFile.close();
    }

    public String getPath(Client client) throws ParserConfigurationException, IOException, SAXException {
        String path=null;
        String content = getContent(client);
        NodeList server = readConfigFile();

        if(client.getClient().contains("static") || client.getClient().contains("favicon.icon")){
            for (int temp = 0; temp < server.getLength(); temp++) {
                Node nNode = server.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    path = eElement.getElementsByTagName("root").item(0).getTextContent()+content;
                }
            }
        }

        else{
            System.out.println("dynamic");
        }
       return path;
    }

    private String getContent(Client client) {
        String clientUrl = client.getClient() +"/";
        String staticUrl = "GET /src/com/static/";
        clientUrl = clientUrl.substring(0, staticUrl.length()+10);
        String clientUrlParts[] = clientUrl.split("/");
        String contentParts[]=clientUrlParts[4].split(" ");
        return contentParts[0];
    }

    private NodeList readConfigFile() throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File("./src/com/thoughtWorks/static/serverConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("server");
    }

    public String statusCode(int return_code) {
        String s = "HTTP/1.0 ";
        switch (return_code) {
            case 200:
                s = s + "200 OK";
                break;
            case 400:
                s = s + "400 Bad Request";
                break;
            case 403:
                s = s + "403 Forbidden";
                break;
            case 404:
                s = s + "404 Not Found";
                break;
            case 500:
                s = s + "500 Internal Server Error";
                break;
            case 501:
                s = s + "501 Not Implemented";
                break;
        }
        return s;
    }

    public String contentType(int file_type, String s) {
        switch (file_type) {
            case 0:
                break;
            case 1:
                s = s + "Content-Type: image/jpeg\r\n";
                break;
            case 2:
                s = s + "Content-Type: image/gif\r\n";
                break;
            case 3:
                s = s + "Content-Type: application/x-zip-compressed\r\n";
                break;
            case 4:
                s = s + "Content-Type: image/x-icon\r\n";
                break;
            default:
                s = s + "Content-Type: text/html\r\n";
                break;
        }

        s = s + "\r\n";
        return s;
    }
}
