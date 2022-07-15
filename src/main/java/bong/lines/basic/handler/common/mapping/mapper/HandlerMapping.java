package bong.lines.basic.handler.common.mapping.mapper;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

@Slf4j
public abstract class HandlerMapping {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public HandlerMapping(InputStream inputStream, OutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void process() throws Exception {
        BufferedReader bufferedReader = getBufferedReaderForRequest(inputStream);

        String requestContent = readRequestContent(bufferedReader);

        if (checkNullofRequestLine(requestContent)) return;

        getHeaderInfo(bufferedReader);

        doProcess(requestContent);

        responseHandling(outputStream);
    }

    protected abstract BufferedReader getBufferedReaderForRequest(InputStream inputStream);

    protected abstract String readRequestContent(BufferedReader bufferedReader) throws Exception;

    private boolean checkNullofRequestLine(String requestLine) {
        if(!Optional.ofNullable(requestLine).isPresent()){
            return true;
        }

        if(requestLine.indexOf("/favicon.ico") > -1){
            return true;
        }

        return false;
    }

    private void getHeaderInfo(BufferedReader bufferedReader) throws Exception{
        String request = "";

        do{
            request = bufferedReader.readLine();
            log.debug("Request Header : {}", request);
        }
        while (!request.isEmpty());
    }

    protected abstract void doProcess(String request) throws Exception;

    protected abstract void responseHandling(OutputStream outputStream);
}
