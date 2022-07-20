package bong.lines.basic.handler.common;

import bong.lines.basic.handler.common.mapping.GetMapping;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DispatcherServlet implements Runnable {

    private final Socket connection;

    public DispatcherServlet(Socket connection){
        this.connection = connection;
    }

    @Override
    public void run() {
        try(InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = createRequest(in);
            HandlerMapping handlerMapping = new GetMapping(in, out);
            handlerMapping.process();
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }

    private Request createRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        int BUF_SIZE = 1024*7;
        char[] buf = new char[BUF_SIZE];
        bufferedReader.read(buf);
        return new Request(String.valueOf(buf));
    }
}
