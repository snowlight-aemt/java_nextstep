package bong.lines.basic.handler.common.mapping;

import bong.lines.basic.handler.common.code.TYPE;
import bong.lines.basic.handler.common.factory.GetFactory;
import bong.lines.basic.handler.common.factory.PutFactory;
import bong.lines.basic.handler.common.factory.operation.LinePut;
import bong.lines.basic.handler.common.mapping.Dto.Request;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import bong.lines.basic.handler.common.method.HTTP_METHOD;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;


@Slf4j
public class PutMapping extends HandlerMapping {

    private TYPE _type;
    private byte[] responseBody;
    private final StringBuffer responseContent = new StringBuffer();
    public PutMapping(Request request, OutputStream outputStream) {
        super(request, outputStream);
    }

    @Override
    public void doProcess(Request requset) throws Exception{
        LinePut<Object> put = PutFactory.put(TYPE.PUT_JSON, requset.getBody());
        responseContent.append(put.put());
        _type = TYPE.PUT_JSON;
    }

    @Override
    public void responseHandling(OutputStream outputStream) {
        DataOutputStream dos = new DataOutputStream(outputStream);

        byte[] body = null;

        switch (_type){
            case PUT_JSON:
                body = responseContent
                        .toString()
                        .getBytes(StandardCharsets.UTF_8);
        }


        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent){
        try{
            dos.writeBytes("HTTP/1.1 200 OK \r\n");

            switch (_type){
                case PUT_JSON:
                    dos.writeBytes("Content-Type: application/json;charset=utf-8 \r\n");
                    break;
            }

            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body){
        try{
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        }catch (Exception exception){
            exception.getMessage();
        }
    }
}
