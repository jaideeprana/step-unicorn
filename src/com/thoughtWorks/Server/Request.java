package com.thoughtWorks.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Request {
    private Socket connectedSocket;

    public Socket connect(ServerSocket serverSocket) throws IOException {
        return this.connectedSocket=serverSocket.accept();
    }

    public String getClientsInfo() throws IOException {
        InetAddress client = connectedSocket.getInetAddress();
        return String.valueOf(client);
    }

    public String read() throws IOException {
        return (new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()))).readLine();
    }
}
