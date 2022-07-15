package bong.lines.basic.handler.common.mapping;

import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PutMapping extends HandlerMapping {

    public PutMapping(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    @Override
    protected BufferedReader getBufferedReaderForRequest(InputStream inputStream) {
        return null;
    }

    @Override
    protected String readRequestContent(BufferedReader bufferedReader) throws IOException, Exception {
        return null;
    }

    @Override
    protected void doProcess(String request) throws Exception {

    }

    @Override
    protected void responseHandling(OutputStream outputStream) {

    }
}
