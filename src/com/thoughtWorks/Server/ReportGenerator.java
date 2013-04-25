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
import java.net.UnknownHostException;

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

    private void generateForDynamic(Server clientSocket) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String ipAddress="10.10.5.126";
        String portInString="8080";
        String resources="/forum/login";
        int port= Integer.parseInt(portInString);
        String request="GET HTTP://" + ipAddress + ":" + portInString + resources  +"HTTP/1.1";

        try {
            socket = new Socket(InetAddress.getByName("10.10.5.126"), 8080);
            out = new PrintWriter(socket.getOutputStream());
            out.println("GET HTTP://10.10.5.126:8080/forum/login  HTTP/1.1");
            out.println("");
            out.println("");
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: 10.10.5.126.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 10.10.5.126.");
            System.exit(1);
        }
        String fromServer;
        DataOutputStream dataOutputStream =new DataOutputStream (clientSocket.getClientSocket().getOutputStream());

        while ((fromServer = in.readLine()) != null) {
            dataOutputStream.writeChars(fromServer);
            System.out.println(fromServer);
        }
        dataOutputStream.writeChars("");
        dataOutputStream.writeChars("");

        socket.close();
        out.close();
        in.close();
    }

    public String getPath(Client client,Server clientServer) throws ParserConfigurationException, IOException, SAXException {
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
            generateForDynamic(clientServer);
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
