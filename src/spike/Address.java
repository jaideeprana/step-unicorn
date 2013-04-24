package spike;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Address {
    private Socket connectedSocket;
    private DataOutputStream dataOutputStream;

    public Address(ServerSocket serverSocket) throws IOException {
        this.connectedSocket = serverSocket.accept();
        this.dataOutputStream = new DataOutputStream(connectedSocket.getOutputStream());
    }

    public void getClientsInfo() throws IOException {
        InetAddress client = connectedSocket.getInetAddress();
        System.out.println(client);
    }

    public void read() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
        System.out.println(input.readLine());
    }

    public void sendResponse() throws IOException {
        readFromFile();
    }

    private void readFromFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./src/spike/index.html");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
        write(bufferedReader);
        dataInputStream.close();
    }

    private void write(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            dataOutputStream.writeBytes(line);
        }
    }
}
