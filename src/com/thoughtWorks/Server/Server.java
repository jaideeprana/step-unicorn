package com.thoughtWorks.Server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
   ServerSocket serverSocket;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        ServerSocket serversocket = null;
        Client client;
        Response response=new Response();
        while (true) {
            try {
                serversocket = new ServerSocket(port);
                Socket connectionsocket = serversocket.accept();
                client=new Client((new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()))).readLine());
                BufferedReader input = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
                DataOutputStream output = new DataOutputStream(connectionsocket.getOutputStream());
                response.sendResponse(input,output,client);
            }
            catch (Exception e) {
            }

        }
    }

}
