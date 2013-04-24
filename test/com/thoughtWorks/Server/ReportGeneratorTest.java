package com.thoughtWorks.Server;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class ReportGeneratorTest {
    ReportGenerator reportGenerator=new ReportGenerator();
    Client client;
    @Before
    public void setUp(){
        client = Mockito.mock(Client.class);
    }
    @Test
    public void shouldReturnPathOfFileForTheGIvenRequest(){
        when(client.getClient()).thenReturn("GET /src/spike/index.html HTTP/1.1");
        assertThat(reportGenerator.getPath(client), IsEqual.equalTo("src/spike/index.html"));
    }

    @Test
    public void shouldReturnPathOfJpgFileForTheGIvenRequest(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.getPath(client), IsEqual.equalTo("src/spike/a.jpg"));
    }

    @Test
    public void shoudGivePathEvenIfFileDoesntHaveExtension(){
        when(client.getClient()).thenReturn("GET /src/spike/a HTTP/1.1");
        assertThat(reportGenerator.getPath(client), IsEqual.equalTo("src/spike/a"));
    }

    @Test
    public void shouldGiveStatusOfFileForTheGivenStatusCode(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.statusCode(200), IsEqual.equalTo("HTTP/1.0 200 OK"));
    }

    @Test
    public void shouldGiveStatusAsBadRequestForTheGivenStatusCode(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.statusCode(400), IsEqual.equalTo("HTTP/1.0 400 Bad Request"));
    }

    @Test
    public void shouldGiveStatusAsForbiddenForTheGivenStatusCode(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.statusCode(403), IsEqual.equalTo("HTTP/1.0 403 Forbidden"));
    }

    @Test
    public void shouldGiveStatusAsPageNotFoundForTheGivenStatusCode(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.statusCode(404), IsEqual.equalTo("HTTP/1.0 404 Not Found"));
    }

    @Test
    public void shouldGiveStatusAsInternalServerErrorForTheGivenStatusCode(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.statusCode(500), IsEqual.equalTo("HTTP/1.0 500 Internal Server Error"));
    }

    @Test
    public void shouldGiveStatusAsNotImplementedForTheGivenStatusCode(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        assertThat(reportGenerator.statusCode(501), IsEqual.equalTo("HTTP/1.0 501 Not Implemented"));
    }

    @Test
    public void shouldReturnContentTypeAsImageOrJpgForTheGivenFileType(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(reportGenerator.contentType(1,temp), IsEqual.equalTo("Content-Type: image/jpeg\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsImageOrGifForTheGivenFileType(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(reportGenerator.contentType(2,temp), IsEqual.equalTo("Content-Type: image/gif\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsApplicationOrZipCompressedForTheGivenFileType(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(reportGenerator.contentType(3,temp), IsEqual.equalTo("Content-Type: application/x-zip-compressed\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsImageOrIconForTheGivenFileType(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(reportGenerator.contentType(4,temp), IsEqual.equalTo("Content-Type: image/x-icon\r\n\r\n"));
    }

    @Test
    public void shouldReturnContentTypeAsHtmlOrTextForTheGivenFileType(){
        when(client.getClient()).thenReturn("GET /src/spike/a.jpg HTTP/1.1");
        String temp = "";
        assertThat(reportGenerator.contentType(5,temp), IsEqual.equalTo("Content-Type: text/html\r\n\r\n"));
    }
}
