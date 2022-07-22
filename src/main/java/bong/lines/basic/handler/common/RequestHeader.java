package bong.lines.basic.handler.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RequestHeader {
    private String method;
    private String path;
    private String version;
    private Map<String, String> options = new HashMap<>();
}
