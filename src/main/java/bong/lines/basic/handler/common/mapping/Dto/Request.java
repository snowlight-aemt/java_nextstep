package bong.lines.basic.handler.common.mapping.Dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Request {
    private RequestHeader requestHeader = new RequestHeader();
    // private Map<String, String> options = new HashMap<>();
    private String body;

    // TODO 코드 정리 필요.
    public Request(String requestData) {
        // TODO 마지막 라인 제거... 필요.
        String[] payload = requestData.split("\r\n");
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

        if (this.getOptions().containsKey("Content-Length") && cnt < payload.length) {
            Integer len = Integer.valueOf(this.getOptions().get("Content-Length"));
            // Integer len = 20;
            byte[] copy = new byte[len];
            System.arraycopy(payload[++cnt].getBytes(), 0, copy, 0, len);
            this.setBody(new String(copy));
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
    public Map<String, String> getOptions() {
        return requestHeader.getOptions();
    }
}
