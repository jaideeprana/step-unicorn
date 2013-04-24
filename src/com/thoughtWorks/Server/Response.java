package com.thoughtWorks.Server;

import java.io.*;

public class Response {
    private Request request=new Request();
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ReportGenerator reportGenerator=new ReportGenerator();
    private String path;
    public void sendResponse(BufferedReader input, DataOutputStream output,Client client) throws IOException {
        write(input,output,client);
    }

    private void write(BufferedReader input, DataOutputStream output,Client client) throws IOException {
        path = reportGenerator.getPath(client);
        try {
            output.writeBytes(requestHeader(200, 5));
            reportGenerator.generate(path, output);
        }
        catch (Exception e) {
            output.writeBytes(requestHeader(404, 5));
        }
        output.close();
    }

    private String requestHeader(int return_code, int file_type) {
        String s = reportGenerator.statusCode(return_code);
        s = reportGenerator.contentType(5, s);
        return s;
    }
}
