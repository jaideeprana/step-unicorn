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
import java.net.InetAddress;
import java.net.Socket;

public class ReportGenerator {

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
        requestedFile.close();
    }

    private void generateForDynamic(Server clientSocket) throws IOException {
        String ipAddress = "10.10.5.126", portInString = "8080", resources = "/forum/login";
        int port = Integer.parseInt(portInString);
        String request = "GET HTTP://" + ipAddress + ":" + portInString + resources + "  HTTP/1.1" + "\n" + "" + "\n" + "";
        Socket socket = new Socket(InetAddress.getByName(ipAddress), port);
        PrintWriter  out = new PrintWriter(socket.getOutputStream());
        out.println(request);
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        respondToClient(clientSocket, in);

        socket.close();
        out.close();
    }

    private void respondToClient(Server clientSocket, BufferedReader in) throws IOException {
        String fromServer;
        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getClientSocket().getOutputStream());

        while ((fromServer = in.readLine()) != null) {
            dataOutputStream.writeChars(fromServer);
            System.out.println(fromServer);
        }
        dataOutputStream.writeChars("");
        dataOutputStream.writeChars("");
        in.close();
    }

    public String getPath(Client client, Server clientServer) throws ParserConfigurationException, IOException, SAXException {
        String path = null;
        String content = getContent(client);
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
            generateForDynamic(clientServer);
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

    private NodeList readConfigFile() throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File("./src/com/thoughtWorks/static/serverConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("server");
    }







}
