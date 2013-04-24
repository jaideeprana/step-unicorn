package com.thoughtWorks.Server;

public class Unicorn {
    public static void main(String[] args) {
        try{
            Server server=new Server(9090);
            System.out.println("The Server Begins");
            server.run();
        }
        catch(Exception e){
            System.out.println("connection not found");
        }
    }
}