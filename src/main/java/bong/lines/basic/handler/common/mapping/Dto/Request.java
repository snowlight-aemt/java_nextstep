package bong.lines.basic.handler.common.mapping.Dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

// TODO 코드 정리 필요.
@Data
public class Request {
    private RequestHeader requestHeader = new RequestHeader();
    private String body;

    public Request(String requestData) {
        int lineCount = 0;
        // TODO 마지막 라인 제거... 필요..;
        String[] payload = requestData.split("\r\n");
        
        String[] firstLine = payload[lineCount++].split(" ");
        this.setMethod(firstLine[0]);
        this.setPath(firstLine[1]);
        this.setVersion(firstLine[2]);

        while (true) {
            String item = payload[lineCount];
            if (item.equals("")) break;

            String[] keyValue = item.split(": ");
            this.getOptions().put(keyValue[0], keyValue[1]);
            lineCount++;
        }

        if (isCanReadRequestBody()) {
            this.setBody(payload[lineCount+1]);
        }
    }

    public boolean isCanReadRequestBody() {
        return this.requestHeader.isContentLength();
    }


    public void setMethod(String method) {
        requestHeader.setMethod(method);
    }
    public void setVersion(String version) {
        requestHeader.setVersion(version);
    }
    public void setPath(String path) {
        requestHeader.setPath(path);
    }
    public void setBody(String body) {
        Integer contentLength = Integer.valueOf(this.requestHeader.getContentLength());
        byte[] copy = new byte[contentLength];
        System.arraycopy(body.getBytes(), 0, copy, 0, contentLength);
        this.body = new String(copy);
    }
    
    public String getFirstLine() {
        return this.getMethod() + " "  + this.getPath() + " " + this.getVersion();
    }

    public String getMethod() {
        return requestHeader.getMethod();
    }
    public String getPath() {
        return requestHeader.getPath();
    }
    public String getVersion() {
        return requestHeader.getVersion();
    }
    public Map<String, String> getOptions() {
        return requestHeader.getOptions();
    }
}
