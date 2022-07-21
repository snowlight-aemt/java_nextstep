package bong.lines.basic.handler.common.mapping;

import bong.lines.basic.handler.common.Request;
import bong.lines.basic.handler.common.code.TYPE;
import bong.lines.basic.handler.common.factory.GetFactory;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import bong.lines.basic.handler.common.method.HTTP_METHOD;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;


@Slf4j
public class DeleteMapping extends HandlerMapping {

    private TYPE _type;
    private byte[] responseBody;
    private final StringBuffer responseContent = new StringBuffer();
    public DeleteMapping(Request requset, OutputStream outputStream) {
        super(requset, outputStream);
    }

    @Override
    public void doProcess(Request requset) throws Exception{
        _type = TYPE.SCREEN;
        responseBody = "삭제되었습니다.".getBytes();
        // if(isGetForScreen(requset.getFirstLine())) {
        //     responseBody = (byte[])GetFactory.get(TYPE.SCREEN, requset.getFirstLine()).get();
        //     _type = TYPE.SCREEN;
        // }

        // if (!isGetForScreen(requset.getFirstLine())){
        //     responseContent.append(GetFactory.get(TYPE.QUERY_STRING, requset.getFirstLine()).get());
        //     _type = TYPE.QUERY_STRING;
        // }
    }

    private boolean isGetForScreen(String requestContent) {
        return requestContent != null && HTTP_METHOD.GET.isContain(requestContent) && requestContent.contains(".html");
    }

    @Override
    public void responseHandling(OutputStream outputStream) {
        DataOutputStream dos = new DataOutputStream(outputStream);

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


        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent){
        try{
            dos.writeBytes("HTTP/1.1 200 OK \r\n");

            switch (_type){
                case SCREEN:
                    dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
                    break;
                case QUERY_STRING:
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
