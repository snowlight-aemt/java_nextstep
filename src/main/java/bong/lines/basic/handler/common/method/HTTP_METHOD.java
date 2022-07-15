package bong.lines.basic.handler.common.method;

public enum HTTP_METHOD {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT");

    private final String httpMethod;
    HTTP_METHOD(String httpMethod){
        this.httpMethod = httpMethod;
    }

    public boolean isContain(String requestContent){
        return requestContent.contains(this.httpMethod);
    }
}
