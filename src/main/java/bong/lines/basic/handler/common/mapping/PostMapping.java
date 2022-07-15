package bong.lines.basic.handler.common.mapping;

import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PostMapping extends HandlerMapping {

    public PostMapping(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    @Override
    protected BufferedReader getBufferedReaderForRequest(InputStream inputStream) {
        return null;
    }

    @Override
    protected String readRequestContent(BufferedReader bufferedReader) throws IOException, Exception {
        return bufferedReader.readLine();
    }

    @Override
    protected void doProcess(String request) throws Exception {
        switch (whichPostType(request)){
            case "Value" :
                break;
            default:
                throw new RuntimeException("POST Type Missing");
        }


    }

    private String whichPostType(String request) {
        return "Value";
    }

    @Override
    protected void responseHandling(OutputStream outputStream) {

    }
}
