package com.thoughtWorks.Server;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class DynamicResponse {

    public void generate(DataOutputStream dataOutputStream) throws IOException {
        String ipAddress = "10.10.5.126", portInString = "8080", resources = "/forum/login";
        int port = Integer.parseInt(portInString);
        String request = "GET HTTP://" + ipAddress + ":" + portInString + resources + "  HTTP/1.1" + "\n" + "" + "\n" + "";
        Socket socket = new Socket(InetAddress.getByName(ipAddress), port);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(request);
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        respondToClient( in,dataOutputStream);
//        socket.close();
//        out.close();

    }

    private void respondToClient(BufferedReader in,DataOutputStream dataOutputStream) throws IOException {
        String fromServer;
        while ((fromServer = in.readLine()) != null) {
//            dataOutputStream.writeChars("aaa");
            System.out.println(fromServer);
        }
        dataOutputStream.writeBytes(fromServer);
        dataOutputStream.flush();
//        dataOutputStream.close();
//
//        System.out.println("done for the day");
//        in.close();
    }
}
