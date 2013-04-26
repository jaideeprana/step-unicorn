package com.thoughtWorks.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ResponseTest {
    private ReportGenerator mockReportGenerator;
    private ServerSocket serverSocket;
    private Client client;
    private Socket socket;
    DataOutputStream output;
    Response response;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(8080);
        client = Mockito.mock(Client.class);
        mockReportGenerator = Mockito.mock(ReportGenerator.class);
        socket = new Socket("localhost", 8080);

        when(client.getClient()).thenReturn("GET /src/spike/index.html HTTP/1.1");

        output = new DataOutputStream(socket.getOutputStream());
        response=new Response();
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    public void shouldGiveResponseHeaderForTheGivenRequestWhenPageIsAvailable() throws IOException, ParserConfigurationException, SAXException {
        String expected = "HTTP/1.0 200 OK\r\n" +
                "Connection: close\r\n" +
                "Server: Step-Unicorn\r\n" ;
        assertTrue(response.requestHeader(200,5).contains(expected));
    }

    @Test
    public void shouldNotGiveResponseHeaderForTheGivenRequestWhenPageIsNotAvailable() throws IOException, ParserConfigurationException, SAXException {
        String expected = "HTTP/1.0 \r\n" +
                "Connection: close\r\n" +
                "Server: Step-Unicorn\r\n" ;
        assertTrue(response.requestHeader(400,5).contains(expected));
    }

    @Test
    public void shouldGiveContentTypeInHeaderAsImageForTheGivenRequest() throws IOException, ParserConfigurationException, SAXException {
        String expected = "Content-Type: image/gif\r\n";
        assertTrue(response.requestHeader(200,2).contains(expected));
    }

    @Test
    public void shouldGiveCurrentDateInHeaderForGivenRequest() throws IOException, ParserConfigurationException, SAXException {
        System.out.println(response.requestHeader(200,2));
        String expected = "Date: ";
        assertTrue(response.requestHeader(200,2).contains(expected));
    }

//    @Test
//    public void shouldGetPathForTheGivenRequest() throws IOException, ParserConfigurationException, SAXException {
//        when(mockReportGenerator.getPath(client,output)).thenReturn("static/index.html");
//        response.sendResponse( output, client);

//        assertThat(mockReportGenerator.getPath(client), IsEqual.equalTo("static/index.html"));
//        verify(mockReportGenerator).generate(mockReportGenerator.getPath(client), output);
//    }

//    @Test
//    public void shouldAddHeaderInTheResponse() throws IOException, ParserConfigurationException, SAXException {
//        Response response = new Response();
//        when(mockReportGenerator.contentType(anyInt(), anyString())).thenReturn("Content-Type: text/html");
//        response.sendResponse( output, client);
//        assertThat(mockReportGenerator.contentType(5, "index.html"), IsEqual.equalTo("Content-Type: text/html"));
//        verify(mockReportGenerator).contentType(5, "index.html");
//    }
//
//    @Test
//    public void shouldAddStatusOfRequestToHeader() throws IOException, ParserConfigurationException, SAXException {
//        Response response = new Response();
//        when(mockReportGenerator.statusCode(anyInt())).thenReturn("200 OK");
//        response.sendResponse( output, client);
//        assertThat(mockReportGenerator.statusCode(5), IsEqual.equalTo("200 OK"));
//        verify(mockReportGenerator).statusCode(5);
//    }
}
