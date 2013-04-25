package com.thoughtWorks.Server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class ReportGenerator {
    DynamicResponse dynamicResponse=new DynamicResponse();
    public void generate(String path, DataOutputStream output) throws IOException {

        FileInputStream requestedFile = new FileInputStream(path);

        byte[] buffer = new byte[1024];
        while (true) {
            int b = requestedFile.read(buffer, 0, 1024);
            if (b == -1) {
                break;
            }
            output.write(buffer, 0, b);
        }
        output.flush();
        output.close();
        requestedFile.close();
    }


    public String getPath(Client client,DataOutputStream dataOutputStream) throws ParserConfigurationException, IOException, SAXException {
        String path = null;
        String content = getFileName(client);
        NodeList server = readConfigFile();
        if (client.getClient().contains("static") || client.getClient().contains("favicon.icon")) {
            for (int temp = 0; temp < server.getLength(); temp++) {
                Node nNode = server.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    path = eElement.getElementsByTagName("root").item(0).getTextContent() + content;
                }
            }
        } else {
            dynamicResponse.generate(dataOutputStream);
        }
        return path;
    }

    protected String getFileName(Client client) {
        String clientUrl = client.getClient() + "/";
        String staticUrl = "GET /src/com/static/";
        clientUrl = clientUrl.substring(0, staticUrl.length() + 10);
        String clientUrlParts[] = clientUrl.split("/");
        String contentParts[] = clientUrlParts[4].split(" ");
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
}
