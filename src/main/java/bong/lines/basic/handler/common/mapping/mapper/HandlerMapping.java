package bong.lines.basic.handler.common.mapping.mapper;

import java.io.OutputStream;

import bong.lines.basic.handler.common.mapping.Dto.Request;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HandlerMapping {

    private final Request request;
    private final OutputStream outputStream;

    public HandlerMapping(Request request, OutputStream outputStream){
        this.request = request;
        this.outputStream = outputStream;
    }

    public void process() throws Exception {
        // getHeaderInfo(bufferedReader);
        doProcess(request);
        responseHandling(outputStream);
    }

    // private void getHeaderInfo(BufferedReader bufferedReader) throws Exception{
    //     String request = "";
    //     do{
    //         request = bufferedReader.readLine();
    //         log.debug("Request Header : {}", request);
    //     }
    //     while (!request.isEmpty());
    // }

    protected abstract void doProcess(Request request) throws Exception;

    protected abstract void responseHandling(OutputStream outputStream);
}
