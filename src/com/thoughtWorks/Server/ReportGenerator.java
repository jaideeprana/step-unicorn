package com.thoughtWorks.Server;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ReportGenerator {

    public void generate(String path, DataOutputStream output) throws IOException {

        FileInputStream requestedfile = new FileInputStream(path);

        byte [] buffer = new byte[1024];
        while (true) {
            int b = requestedfile.read(buffer, 0,1024);
            if (b == -1) {
                break;
            }
            output.write(buffer,0,b);
        }
        requestedfile.close();
    }

    public String getPath(Client client){
        String path=null;

            String tmp2 = new String(client.getClient());
            int start = 0;
            int end = 0;
            for (int a = 0; a < tmp2.length(); a++) {
                if (tmp2.charAt(a) == ' ' && start != 0) {
                    end = a;
                    break;
                }
                if (tmp2.charAt(a) == ' ' && start == 0) {
                    start = a;
                }
            }
            path = tmp2.substring(start + 2, end);
       return path;
    }

    public String statusCode(int return_code) {
        String s = "HTTP/1.0 ";
        switch (return_code) {
            case 200:
                s = s + "200 OK";
                break;
            case 400:
                s = s + "400 Bad Request";
                break;
            case 403:
                s = s + "403 Forbidden";
                break;
            case 404:
                s = s + "404 Not Found";
                break;
            case 500:
                s = s + "500 Internal Server Error";
                break;
            case 501:
                s = s + "501 Not Implemented";
                break;
        }
        return s;
    }

    public String contentType(int file_type, String s) {
        switch (file_type) {
            case 0:
                break;
            case 1:
                s = s + "Content-Type: image/jpeg\r\n";
                break;
            case 2:
                s = s + "Content-Type: image/gif\r\n";
                break;
            case 3:
                s = s + "Content-Type: application/x-zip-compressed\r\n";
                break;
            case 4:
                s = s + "Content-Type: image/x-icon\r\n";
                break;
            default:
                s = s + "Content-Type: text/html\r\n";
                break;
        }

        s = s + "\r\n";
        return s;
    }
}
