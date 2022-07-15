package bong.lines.basic.handler.common.mapping;

import bong.lines.basic.WebServer;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.*;

class GetMappingTest {

    // todo 순수 자바 코드 상에서의 Test Code 작성하기
    @Test
    @DisplayName("GetMappingTest")
    public void GetMappingTest() throws Exception {
        // Given
        String uri = "http://localhost:8080?test=test";

        // When
        WebServer.main(null);

        // Then
        try {
            URL u = new URL(uri);
            HttpURLConnection http = (HttpURLConnection) u.openConnection();
            http.setRequestMethod("GET");

            http.getResponseCode();

        } catch (MalformedURLException e) {
            System.out.println(uri+" is not a URL I understand");
        } catch (IOException e) {

        }
    }
}