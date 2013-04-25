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
        String content = getContent(client);
        boolean isStatic=true;

        NodeList staticServer = readConfigFile("server");
        if (client.getClient().contains("static") || client.getClient().contains("favicon.icon")) {
            path = readFromConfigureFile(path, content, staticServer,"root",isStatic);
        }

        else {
            NodeList dynamicServer=readConfigFile("dynamic-proxy-pass");
            String ipAddress=readFromConfigureFile(path, content, dynamicServer,"ipAddress",!isStatic);

            String port=readFromConfigureFile(path, content, dynamicServer,"port",!isStatic);

            String middle=readFromConfigureFile(path, content, dynamicServer,"middle",!isStatic);

            dynamicResponse.generate(dataOutputStream,ipAddress,port,middle + "/" + content);
        }
        return path;
    }

    private String readFromConfigureFile(String path, String content, NodeList staticServer,String tag,boolean isStatic) {
        for (int temp = 0; temp < staticServer.getLength(); temp++) {
            Node nNode = staticServer.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if(isStatic){
                path = eElement.getElementsByTagName(tag).item(0).getTextContent() + content;
                }
                else{
                    path = eElement.getElementsByTagName(tag).item(0).getTextContent();
                }
            }
        }
        return path;
    }

    private String getContent(Client client) {
        String clientUrl = client.getClient() + "/";
        String staticUrl = "GET /src/com/static/";
        clientUrl = clientUrl.substring(0, staticUrl.length() + 10);
        String clientUrlParts[] = clientUrl.split("/");
        String contentParts[] = clientUrlParts[4].split(" ");
        return contentParts[0];
    }

    private NodeList readConfigFile(String tag) throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File("./src/com/thoughtWorks/static/serverConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(tag);
    }
}
