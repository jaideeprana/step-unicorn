package com.thoughtWorks.Server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        Response response=new Response();
        while (true) {
            try {
                Socket connectedSocket=createSocket();
                Client client= getClient(connectedSocket);
                BufferedReader input = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
                DataOutputStream output = new DataOutputStream(connectedSocket.getOutputStream());
                response.sendResponse(input,output,client);
                sleep(500);
            }
            catch (Exception e) {
            }
        }
    }
    public Socket createSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        return serverSocket.accept();
    }

    public Client getClient(Socket connectedSocket) throws IOException {
        return new Client((new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()))).readLine());
    }
}