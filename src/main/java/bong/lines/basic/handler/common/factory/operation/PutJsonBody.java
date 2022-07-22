package bong.lines.basic.handler.common.factory.operation;

public class PutJsonBody implements LinePut<Object> {
    private String body; 

    public PutJsonBody(String body) {
        this.body = body;
    }

    @Override
    public String put() {
        return this.body;
    }

}
