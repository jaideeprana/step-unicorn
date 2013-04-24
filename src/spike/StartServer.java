package spike;

import java.net.ServerSocket;

public class StartServer {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket= new ServerSocket(9090);
            Address address=new Address(serverSocket);
//            address.getClientsInfo();
//            address.read();
            address.sendResponse();
            serverSocket.close();
        }
        catch(Exception e){
            System.out.println("conncection not found");
        }
    }
}
