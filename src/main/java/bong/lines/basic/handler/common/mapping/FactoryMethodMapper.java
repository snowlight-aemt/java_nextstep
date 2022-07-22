package bong.lines.basic.handler.common.mapping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import bong.lines.basic.handler.common.Request;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FactoryMethodMapper {

    public static HandlerMapping create(InputStream in, OutputStream out) {
        Request request = createRequest(in);
        switch (request.getMethod()) {
            case "GET":
                return new GetMapping(request, out);
            // case "POST":
            //     break;
            case "PUT":
                return new PutMapping(request, out);
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
            // log.info("", e);
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