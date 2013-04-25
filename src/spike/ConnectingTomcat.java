package spike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectingTomcat {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

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

        while ((fromServer = in.readLine()) != null) {
            System.out.println(fromServer);
        }
        socket.close();
        out.close();
        in.close();

    }
}