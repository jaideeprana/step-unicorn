package com.thoughtWorks.Server;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class DynamicResponse {

    public void generate(DataOutputStream dataOutputStream, String ipAddress, String portInString, String resources) throws IOException {
        int port = Integer.parseInt(portInString);
        String request = "GET HTTP://" + ipAddress + ":" + portInString + resources + "  HTTP/1.1" + "\n" + "" + "\n" + "";


        Socket socket = new Socket(InetAddress.getByName(ipAddress), port);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(request);
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        respondToClient( in,dataOutputStream);
        socket.close();
    }

    protected void respondToClient(BufferedReader in,DataOutputStream dataOutputStream) throws IOException {
        String fromServer;

        while ((fromServer = in.readLine()) != null) {
            dataOutputStream.writeBytes(fromServer + "\n");
        }
        dataOutputStream.flush();
        dataOutputStream.close();
        in.close();
    }
}
