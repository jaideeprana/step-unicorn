package com.thoughtWorks.Server;

import org.junit.Before;
import org.mockito.Mockito;

public class ReportGeneratorTest {
    ReportGenerator reportGenerator=new ReportGenerator();
    Client client;
    Server server;
    @Before
    public void setUp(){
        server=new Server(8080);
        client = Mockito.mock(Client.class);
    }
//    @Test
//    public void shouldReturnPathOfFileForTheGIvenRequest() throws IOException, SAXException, ParserConfigurationException {
//        when(client.getClient()).thenReturn("GET /src/spike/index.html HTTP/1.1");
//        assertThat(reportGenerator.getPath(client,server), IsEqual.equalTo("src/spike/index.html"));
//    }

//    @Test
//    public void shouldReturnPathOfJpgFileForTheGIvenRequest() throws IOException, SAXException, ParserConfigurationException {
//        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
//        assertThat(reportGenerator.getPath(client,server), IsEqual.equalTo("src/spike/a.jpg"));
//    }

//    @Test
//    public void shoudGivePathEvenIfFileDoesntHaveExtension() throws IOException, SAXException, ParserConfigurationException {
//        when(client.getClient()).thenReturn("GET /src/spike/a HTTP/1.1");
//        assertThat(reportGenerator.getPath(client,server), IsEqual.equalTo("src/spike/a"));
//    }
}
