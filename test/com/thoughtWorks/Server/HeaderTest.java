package com.thoughtWorks.Server;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
public class HeaderTest {

    Header header = new Header();

    Client client;
    Server server;

    @Before
    public void setUp() {
        server = new Server(8080);
        client = Mockito.mock(Client.class);
    }

    @Test
    public void shouldGiveStatusOfFileForTheGivenStatusCode() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(header.statusCode(200), IsEqual.equalTo("HTTP/1.0 200 OK"));
    }

    @Test
    public void shouldGiveStatusAsPageNotFoundForTheGivenStatusCode() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(header.statusCode(404), IsEqual.equalTo("HTTP/1.0 404 Not Found"));
    }

    @Test
    public void shouldGiveStatusAsInternalServerErrorForTheGivenStatusCode() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(header.statusCode(500), IsEqual.equalTo("HTTP/1.0 500 Internal Server Error"));
    }

    @Test
    public void shouldReturnContentTypeAsImageOrJpgForTheGivenFileType() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(header.contentType(1, temp), IsEqual.equalTo("Content-Type: image/jpeg\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsImageOrGifForTheGivenFileType() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(header.contentType(2, temp), IsEqual.equalTo("Content-Type: image/gif\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsApplicationOrZipCompressedForTheGivenFileType() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(header.contentType(3, temp), IsEqual.equalTo("Content-Type: application/compressed\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsImageOrIconForTheGivenFileType() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(header.contentType(4, temp), IsEqual.equalTo("Content-Type: image/icon\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsHtmlOrTextForTheGivenFileType() {
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(header.contentType(5, temp), IsEqual.equalTo("Content-Type: text/html\r\n\r\n"));
    }

    @Test
    public void shouldReturnTypeOneForTheGivenFileType() {
        String path = "/src/spike/a.jpg";
        assertThat(header.getFileType(path), IsEqual.equalTo(1));
    }

    @Test
    public void shouldReturnTypeTwoForTheGivenFileType() {
        String path = "/src/spike/a.gif";
        assertThat(header.getFileType(path), IsEqual.equalTo(2));
    }

    @Test
    public void shouldReturnTypeThreeForTheGivenFileType() {
        String path = "/src/spike/a.zip";
        assertThat(header.getFileType(path), IsEqual.equalTo(3));
    }

    @Test
    public void shouldReturnTypeFourForTheGivenFileType() {
        String path = "/src/spike/a.ico";
        assertThat(header.getFileType(path), IsEqual.equalTo(4));
    }

    @Test
    public void shouldReturnTypeFiveForTheGivenFileType() {
        String path = "/src/spike/a.html";
        assertThat(header.getFileType(path), IsEqual.equalTo(5));
    }
}
