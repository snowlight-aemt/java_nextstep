package bong.lines.basic.handler.common.factory;

import bong.lines.basic.handler.common.code.TYPE;
import bong.lines.basic.handler.common.factory.operation.GetQueryString;
import bong.lines.basic.handler.common.factory.operation.GetScreen;
import bong.lines.basic.handler.common.factory.operation.LinesGet;

public class GetFactory {
    public static LinesGet<Object> get(TYPE _type, String queryContent){
        switch (_type){
            case QUERY_STRING:
                return new GetQueryString(queryContent);
            case SCREEN:
                return new GetScreen(queryContent);
            default:
                throw new RuntimeException("Not Found Exception");
        }
    }
}
