package bong.lines.basic.handler.common.mapping.mapper;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import bong.lines.basic.handler.common.Request;
import bong.lines.basic.handler.common.Response;

@Slf4j
public abstract class HandlerMapping {

    private final Request request;
    // private final OutputStream outputStream;


    public HandlerMapping(Request request, OutputStream outputStream){
        this.request = request;
        // this.outputStream = outputStream;
    }

    public Response process() throws Exception {

        // getHeaderInfo(bufferedReader);

        doProcess(request);

        String responseContent = responseHandling();

        return new Response(responseContent);
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

    protected abstract String responseHandling();
}
