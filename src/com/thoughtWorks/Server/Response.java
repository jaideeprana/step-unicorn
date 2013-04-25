package com.thoughtWorks.Server;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Response {
    private Request request=new Request();
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ReportGenerator reportGenerator=new ReportGenerator();
    private Header header=new Header();

    public void sendResponse( DataOutputStream output,Client client) throws IOException, ParserConfigurationException, SAXException {
        write(output,client);
    }

    private void write( DataOutputStream output,Client client) throws IOException, ParserConfigurationException, SAXException {
        String path = reportGenerator.getPath(client,output);
        try {
            output.writeBytes(requestHeader(200, header.getFileType(path)));
            reportGenerator.generate(path, output);
            output.close();
        }
        catch (Exception e) {
            output.writeBytes(requestHeader(404,header.getFileType(path)));

        }
    }

    private String requestHeader(int returnCode, int fileType) {
        String status;
        status = header.statusCode(returnCode);
        status = status + "\r\n";
        status = status + "Connection: close\r\n";
        status = status + "Server: Step-Unicorn\r\n";

        status = header.contentType(fileType, status);
        return status;
    }
}