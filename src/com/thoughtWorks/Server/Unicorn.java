package com.thoughtWorks.Server;

public class Unicorn {
    public static void main(String[] args) {
            Server server=new Server(9090);
            System.out.println("The Server Begins");
            server.run();
    }
}
