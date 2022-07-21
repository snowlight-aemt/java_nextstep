package bong.lines.basic.handler.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Response {
    private RequestHeader requestHeader = new RequestHeader();
    private Map<String, String> options = new HashMap<>();
    private String body;
    
    // TODO 코드 정리 필요.
    public Response(String responseData) {
        String[] payload = responseData.split("\r\n");
        String[] firstLine = payload[0].split(" ");
        this.setMethod(firstLine[0]);
        this.setPath(firstLine[1]);
        this.setVersion(firstLine[2]);

        int cnt = 3;
        while (true) {
            String item = payload[cnt];
            if (item.equals("")) break;

            String[] keyValue = item.split(": ");
            this.getOptions().put(keyValue[0], keyValue[1]);
            cnt++;
        }

        if (cnt < payload.length) {
            this.setBody(payload[++cnt]);
        }
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
}
