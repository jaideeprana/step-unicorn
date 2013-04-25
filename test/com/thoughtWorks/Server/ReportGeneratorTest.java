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
import static org.mockito.Mockito.*;

public class ReportGeneratorTest {
    ReportGenerator reportGenerator=new ReportGenerator();
    ServerSocket serverSocket;
    Client client;
    Server server;
    DataOutputStream output;
    Socket socket;
    DynamicResponse mockDynamicResponse;
    @Before
    public void setUp() throws IOException {
        serverSocket=new ServerSocket(8080);
        socket=new Socket("localhost",8080);
        server=new Server(8080);
        mockDynamicResponse =Mockito.mock(DynamicResponse.class);
        client = Mockito.mock(Client.class);
        output = new DataOutputStream(socket.getOutputStream());
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }
    @Test
    public void shouldReturnPathOfFileForTheGivenRequest() throws IOException, SAXException, ParserConfigurationException {
        when(client.getClient()).thenReturn("GET /src/com/static/index.html HTTP/1.1");
        assertTrue(reportGenerator.getPath(client, output).contains("static"));
    }

    @Test
    public void shouldReturnPathOfJpgFileForTheGivenRequest() throws IOException, SAXException, ParserConfigurationException {
        when(client.getClient()).thenReturn("GET /src/com/static/index.jpg HTTP/1.1");
        assertTrue(reportGenerator.getPath(client, output).contains("static"));
        verify(client,times(2)).getClient();
    }

    @Test
    public void shouldGiveFileNameForRequestOfCLient() throws IOException, SAXException, ParserConfigurationException {
        when(client.getClient()).thenReturn("GET /src/com/static/a.html HTTP/1.1");
        assertTrue(reportGenerator.getFileName(client).equals("a.html"));
    }

    @Test
    public void shouldGiveFileNameIfRequestIsDynamic() throws IOException, SAXException, ParserConfigurationException {
        when(client.getClient()).thenReturn("GET /src/com/spike/a.html HTTP/1.1");
        assertTrue(reportGenerator.getFileName(client).equals("a.html"));
    }

    @Test
    public void shouldGivePathEvenIfFileDoesNotHaveExtension() throws IOException, SAXException, ParserConfigurationException {
        when(client.getClient()).thenReturn("GET /src/com/static/a HTTP/1.1");
        assertTrue(reportGenerator.getPath(client, output).contains("static"));
    }


}
