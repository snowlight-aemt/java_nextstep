package bong.lines.basic.handler.common.mapping.Dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RequestHeader {
    private String method;
    private String path;
    private String version;
    private Map<String, String> options = new HashMap<>();

    public boolean isContentLength() {
        return options.containsKey("Content-Length");
    }

    public String getContentLength() {
        return options.get("Content-Length");
    }
}
