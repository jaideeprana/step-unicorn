package com.thoughtWorks.Server;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResponseTest {
    private ReportGenerator mockReportGenerator;
    private ServerSocket serverSocket;
    private Client client;
    private Socket socket;
    BufferedReader input;
    DataOutputStream output;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(8080);
        client = Mockito.mock(Client.class);
        mockReportGenerator = Mockito.mock(ReportGenerator.class);
        socket = new Socket("localhost", 8080);

        when(client.getClient()).thenReturn("GET /src/spike/index.html HTTP/1.1");

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    public void shouldReadFromFile() throws IOException {
        Response response = new Response();
        response.sendResponse(input, output, client);
        when(mockReportGenerator.getPath(client)).thenReturn("static/index.html");
        assertThat(mockReportGenerator.getPath(client), IsEqual.equalTo("static/index.html"));
        verify(mockReportGenerator).getPath(client);
    }

    @Test
    public void shouldGenerateReportForTheGivenRequest() throws IOException {
        Response response = new Response();
        when(mockReportGenerator.getPath(client)).thenReturn("static/index.html");
        response.sendResponse(input, output, client);
        assertThat(mockReportGenerator.getPath(client), IsEqual.equalTo("static/index.html"));
        verify(mockReportGenerator).generate(mockReportGenerator.getPath(client), output);
    }

    @Test
    public void shouldAddHeaderInTheResponse() throws IOException {
        Response response = new Response();
        when(mockReportGenerator.contentType(anyInt(), anyString())).thenReturn("Content-Type: text/html");
        response.sendResponse(input, output, client);
        assertThat(mockReportGenerator.contentType(5, "index.html"), IsEqual.equalTo("Content-Type: text/html"));
        verify(mockReportGenerator).contentType(5, "index.html");
    }

    @Test
    public void shouldAddStatusOfRequestToHeader() throws IOException {
        Response response = new Response();
        when(mockReportGenerator.statusCode(anyInt())).thenReturn("200 OK");
        response.sendResponse(input, output, client);
        assertThat(mockReportGenerator.statusCode(5), IsEqual.equalTo("200 OK"));
        verify(mockReportGenerator).statusCode(5);
    }
}
