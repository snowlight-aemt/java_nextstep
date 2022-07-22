package bong.lines.basic.handler.common.mapping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import bong.lines.basic.handler.common.mapping.Dto.Request;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FactoryMethodMapper {
    // TODO 버퍼 사이즈 확인 필요.
    public static int BUF_SIZE = 1024*3;

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
                throw new IllegalArgumentException("not supported exception.");
        }
    }

    private static Request createRequest(InputStream in){
        return new Request(readRequestContent(in));
    }

    private static String readRequestContent(InputStream in){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        char[] buf = new char[BUF_SIZE];
        
        try {
            bufferedReader.read(buf);
        } catch (Exception e) {
            throw new IllegalArgumentException("wrong data read.", e);
        }

        String bufStr = String.valueOf(buf);
        if (checkNullofRequestLine(bufStr)) 
            throw new IllegalArgumentException("HTTP header wrong and favicon.ico request.");

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