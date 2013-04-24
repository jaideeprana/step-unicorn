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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestTest {
    private ServerSocket serverSocket;

    @Before
    public void setUp() throws IOException {
        serverSocket =new ServerSocket(8080);
    }
    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    public void shouldReturnIpAddressOfClient() throws IOException {
        Socket socket=new Socket("localhost",8080);
        Request mockRequest= Mockito.mock(Request.class);
        when(mockRequest.connect(serverSocket)).thenReturn(socket);
        socket.close();
        when(mockRequest.getClientsInfo()).thenReturn("/127.0.0.1");
        assertThat(String.valueOf(mockRequest.getClientsInfo()), IsEqual.equalTo("/127.0.0.1"));
        verify(mockRequest).getClientsInfo();
    }

    @Test
    public void shouldReadTheClientsRequest() throws Exception {
        Request mockRequest= Mockito.mock(Request.class);
        when(mockRequest.read()).thenReturn("GET index.html");
        assertThat(String.valueOf(mockRequest.read()), IsEqual.equalTo("GET index.html"));
        verify(mockRequest).read();
    }
}
