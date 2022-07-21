package bong.lines.basic.handler.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import bong.lines.basic.handler.common.mapping.DeleteMapping;
import bong.lines.basic.handler.common.mapping.GetMapping;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatcherServlet implements Runnable {

    private final Socket connection;

    public DispatcherServlet(Socket connection){
        this.connection = connection;
    }

    @Override
    public void run() {
        try(InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // Request request = createRequest(in);
            // HandlerMapping handlerMapping = new GetMapping(request, out);
            HandlerMapping handlerMapping = FactoryMethodMapper.create(in, out);
            handlerMapping.process();
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }

    static class FactoryMethodMapper {

        public static HandlerMapping create(InputStream in, OutputStream out) {
            Request request = createRequest(in);
            switch (request.getMethod()) {
                case "GET":
                    return new GetMapping(request, out);
                // case "POST":
                //     break;
                // case "PUT":
                //     break;
                case "DELETE":
                    return new DeleteMapping(request, out);
                default:
                    throw new IllegalArgumentException();
            }
        }

    // TODO 클래스 분리..!
    private static Request createRequest(InputStream in){
        String bufStr = readRequestContent(in);
        return new Request(bufStr);
    }

    private static String readRequestContent(InputStream in){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        // TODO 버퍼 사이즈 수정 필요.
        int BUF_SIZE = 1024*7;
        char[] buf = new char[BUF_SIZE];
        
        try {
            bufferedReader.read(buf);
        } catch (Exception e) {
            log.info("", e);
            throw new IllegalArgumentException(e);
        }

        String bufStr = String.valueOf(buf);

        if (checkNullofRequestLine(bufStr)) 
            throw new IllegalArgumentException();

        return bufStr;
    }

    private static boolean checkNullofRequestLine(String requestLine) {
        String firstLine = requestLine.split("\r\n")[0];
        if(!Optional.ofNullable(firstLine).isPresent()){
            return true;
        }

        if(firstLine.indexOf("/favicon.ico") > -1){
            return true;
        }

        return false;
    }
    }

    // TODO 클래스 분리..!
    private Request createRequest(InputStream in) throws IOException {
        String bufStr = readRequestContent(in);
        return new Request(bufStr);
    }

    private String readRequestContent(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        // TODO 버퍼 사이즈 수정 필요.
        int BUF_SIZE = 1024*7;
        char[] buf = new char[BUF_SIZE];
        bufferedReader.read(buf);
        String bufStr = String.valueOf(buf);

        if (checkNullofRequestLine(bufStr)) 
            throw new IllegalArgumentException();

        return bufStr;
    }

    private boolean checkNullofRequestLine(String requestLine) {
        String firstLine = requestLine.split("\r\n")[0];
        if(!Optional.ofNullable(firstLine).isPresent()){
            return true;
        }

        if(firstLine.indexOf("/favicon.ico") > -1){
            return true;
        }

        return false;
    }
}
