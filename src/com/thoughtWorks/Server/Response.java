package com.thoughtWorks.Server;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Response {
    private Request request=new Request();
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ReportGenerator reportGenerator=new ReportGenerator();

    public void sendResponse( DataOutputStream output,Client client,Server server) throws IOException, ParserConfigurationException, SAXException {
        write(output,client,server);
    }

    private void write( DataOutputStream output,Client client,Server server) throws IOException, ParserConfigurationException, SAXException {
        String path = reportGenerator.getPath(client,server);
        try {
            output.writeBytes(requestHeader(200, 5));
            reportGenerator.generate(path, output);
            output.close();
        }
        catch (Exception e) {
            output.writeBytes(requestHeader(404, 5));

        }
    }

    private String requestHeader(int returnCode, int fileType) {
        String status;
        status = reportGenerator.statusCode(returnCode);
        status = status + "\r\n";
        status = status + "Connection: close\r\n";
        status = status + "Server: Step-Unicorn\r\n";

        status = reportGenerator.contentType(5, status);
        return status;
    }
}