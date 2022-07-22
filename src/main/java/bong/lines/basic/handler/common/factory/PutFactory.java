package bong.lines.basic.handler.common.factory;

import bong.lines.basic.handler.common.code.TYPE;
import bong.lines.basic.handler.common.factory.operation.LinePut;
import bong.lines.basic.handler.common.factory.operation.PutJsonBody;

public class PutFactory {
    public static LinePut<Object> put(TYPE type, String body){
        switch (type){
            case PUT_JSON:
                return new PutJsonBody(body);
            default:
                throw new RuntimeException("Post Exception!");
        }

    }
}
