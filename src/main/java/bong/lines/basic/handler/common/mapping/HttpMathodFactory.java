package bong.lines.basic.handler.common.mapping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Handler;

import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;

public class HttpMathodFactory {
    public HandlerMapping create(InputStream io, OutputStream out) throws Exception {
        // TODO Stream 에서 복제본을.. 또는 Handler에서 메소드 추출..
        BufferedReader bufferedReaderForRequest = getBufferedReaderForRequest(io);
        String readRequestContent = readRequestContent(bufferedReaderForRequest);
        String method = readRequestContent.split(" ")[0];

        System.out.println(method);
        
        switch (method) {
            case "GET":
                return new GetMapping(io, out);
            case "POST":
                return new PostMapping(io, out);
            case "DELETE":
                return new DeleteMapping(io, out);
            case "PUT":
                return new PutMapping(io, out);
            default:
                throw new IllegalArgumentException();
        }
    }

    public BufferedReader getBufferedReaderForRequest(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public String readRequestContent(BufferedReader bufferedReader) throws Exception
    {
        return bufferedReader.readLine();
    }
}
