package bong.lines.basic.handler.common.mapping;

import bong.lines.basic.handler.common.Request;
import bong.lines.basic.handler.common.code.TYPE;
import bong.lines.basic.handler.common.factory.GetFactory;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import bong.lines.basic.handler.common.method.HTTP_METHOD;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class GetMapping extends HandlerMapping {

    private TYPE _type;
    private byte[] responseBody;
    private final StringBuffer responseContent = new StringBuffer();
    private final StringBuffer stringBuffer = new StringBuffer();

    public GetMapping(Request requset, OutputStream outputStream) {
        super(requset, outputStream);
    }

    @Override
    public void doProcess(Request requset) throws Exception{
        if(isGetForScreen(requset.getFirstLine())) {
            responseBody = (byte[])GetFactory.get(TYPE.SCREEN, requset.getFirstLine()).get();
            _type = TYPE.SCREEN;
        }

        if (!isGetForScreen(requset.getFirstLine())){
            responseContent.append(GetFactory.get(TYPE.QUERY_STRING, requset.getFirstLine()).get());
            _type = TYPE.QUERY_STRING;
        }
    }

    private boolean isGetForScreen(String requestContent) {
        return requestContent != null && HTTP_METHOD.GET.isContain(requestContent) && requestContent.contains(".html");
    }

    @Override
    public String responseHandling() {
        // DataOutputStream dos = new DataOutputStream(outputStream);

        byte[] body = null;

        switch (_type){
            case SCREEN:
                body = responseBody;
                break;
            case QUERY_STRING:
                body = responseContent
                        .toString()
                        .getBytes(StandardCharsets.UTF_8);
        }

        
        response200Header(stringBuffer, body.length);
        responseBody(stringBuffer, body);

        return stringBuffer.toString();
    }

    private void response200Header(StringBuffer buf, int lengthOfBodyContent){
        try{
            // ByteBuffer header = new ByteArrayInputStream();
            // List<Byte> bytes = new ArrayList<>();
            // dos.writeBytes("HTTP/1.1 200 OK \r\n");
            buf.append("HTTP/1.1 200 OK \r\n");

            switch (_type){
                case SCREEN:
                    // dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
                    buf.append("Content-Type: text/html;charset=utf-8 \r\n");
                    break;
                case QUERY_STRING:
                    // dos.writeBytes("Content-Type: application/json;charset=utf-8 \r\n");
                    buf.append("Content-Type: application/json;charset=utf-8 \r\n");
                    break;
            }

            // dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            buf.append("Content-Length: " + lengthOfBodyContent + "\r\n");
            // dos.writeBytes("\r\n");
            buf.append("\r\n");
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }

    private void responseBody(StringBuffer stringBuffer, byte[] body){
        try{
            // dos.write(body, 0, body.length);
            stringBuffer.append(body);
            // dos.writeBytes("\r\n");
            stringBuffer.append("\r\n");
            // dos.flush();
        }catch (Exception exception){
            exception.getMessage();
        }
    }
}
