package bong.lines.basic.handler.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.Setter;

@Data
public class Request {
    private String method;
    private String path;
    private String version;
    private Map<String, String> options = new HashMap<>();
    private String body;
    
    // TODO 코드 정리 필요.
    public Request(String requestData) {
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

        if (cnt < payload.length) {
            this.setBody(payload[++cnt]);
        }
    }


}
