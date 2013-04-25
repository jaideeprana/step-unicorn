package com.thoughtWorks.Server;


public class Header {

    public String statusCode(int return_code) {
        String status = "HTTP/1.0 ";
        switch (return_code) {
            case 200:
                status = status + "200 OK";
                break;
            case 404:
                status = status + "404 Not Found";
                break;
            case 500:
                status = status + "500 Internal Server Error";
                break;
        }
        return status;
    }

    public String contentType(int file_type, String contentType) {
        switch (file_type) {
            case 0:
                break;
            case 1:
                contentType = contentType + "Content-Type: image/jpeg\r\n";
                break;
            case 2:
                contentType = contentType + "Content-Type: image/gif\r\n";
                break;
            case 3:
                contentType = contentType + "Content-Type: application/compressed\r\n";
                break;
            case 4:
                contentType = contentType + "Content-Type: image/icon\r\n";
                break;
            default:
                contentType = contentType + "Content-Type: text/html\r\n";
                break;
        }

        contentType = contentType + "\r\n";
        return contentType;
    }

    public int getFileType(String path){
        int type_is = 0;
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            type_is = 1;
        }
        if (path.endsWith(".gif")) {
            type_is = 2;
        }
        if (path.endsWith(".zip") ) {
            type_is = 3;
        }
        if (path.endsWith(".ico")) {
            type_is = 4;
        }
        if (path.endsWith(".html") || path.endsWith(".txt")) {
            type_is = 5;
        }

        return type_is;
    }
}
