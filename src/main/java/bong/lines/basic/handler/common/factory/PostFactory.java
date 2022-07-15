package bong.lines.basic.handler.common.factory;

import bong.lines.basic.handler.common.code.TYPE;
import bong.lines.basic.handler.common.factory.operation.LinesPost;
import bong.lines.basic.handler.common.factory.operation.PostJsonBody;

public class PostFactory {
    public static LinesPost<Object> post(TYPE type, String request){
        switch (type){
            case REQUEST_BODY_JSON:
                return new PostJsonBody();
            default:
                throw new RuntimeException("Post Exception!");
        }

    }
}
