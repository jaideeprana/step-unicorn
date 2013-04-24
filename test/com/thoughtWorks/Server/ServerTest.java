package com.thoughtWorks.Server;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServerTest {

    private Server mockServer;
    private ServerSocket serverSocket;
    private Client client;
    private Response response;

    @Before
    public void shouldCreateFocketForTheServer() throws IOException {
        serverSocket =new ServerSocket(8080);
        client = Mockito.mock(Client.class);
        response = Mockito.mock(Response.class);
        Socket socket=new Socket("localhost",8080);
        mockServer= Mockito.mock(Server.class);
        when(mockServer.createSocket()).thenReturn(socket);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    public void shouldConnectToServer() throws IOException {
        Socket socket=mockServer.createSocket();
        assertThat(socket.getPort(), IsEqual.equalTo(8080));
        verify(mockServer).createSocket();
    }

    @Test
    public void shouldReturnClientInformationWhenAnyClientConnectToServer() throws IOException {
        Socket socket=mockServer.createSocket();
        when(client.getClient()).thenReturn("GET /src/spike/index.html HTTP/1.1");
        when(mockServer.getClient(new Socket())).thenReturn(client);
        mockServer.getClient(socket);
        verify(mockServer).getClient(socket);
    }

    @Test
    public void shouldCreateResponseForTheGivenRequest() throws IOException {
        mockServer.run();
        when(client.getClient()).thenReturn("GET /src/spike/index.html HTTP/1.1");
        Socket socket=mockServer.createSocket();
        mockServer.getClient(socket);
        verify(client,never()).getClient();
        verify(mockServer).run();
    }
}
