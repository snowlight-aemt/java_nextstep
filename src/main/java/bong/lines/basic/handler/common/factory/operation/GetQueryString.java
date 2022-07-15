package bong.lines.basic.handler.common.factory.operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GetQueryString implements LinesGet<Object>{

    private final int KEY_ONLY = 1;
    private final int KEY_AND_VALUE = 2;
    private final int KEY = 0;
    private final int VALUE = 1;
    private final int HAS_QEURYSTRING = 1;
    private final int QEURYSTRING = 1;
    private final int URI = 1;

    private final String requestContent;

    @Override
    public String get() {

        final StringBuilder responseContent = new StringBuilder();

        log.debug("For Mapping Value : {}", requestContent);

        String[] request_args = requestContent.split(" ");

        if(!request_args[URI].contains("?"))
            return "ERROR";

        String[] url_and_queryString = request_args[URI].split("\\?");

        if(url_and_queryString.length <= HAS_QEURYSTRING)
            return "ERROR";

        if(!url_and_queryString[QEURYSTRING].contains("&") && !url_and_queryString[QEURYSTRING].contains("="))
            return "ERROR";

        String[] queryString = url_and_queryString[QEURYSTRING].split("&");


        int keyCount = queryString.length;

        responseContent.append("{");

        for (String key_and_value : queryString) {

            String key = "";
            String value = "";


            if(key_and_value.split("=").length == KEY_ONLY){
                key = key_and_value.split("=")[KEY];
            }

            if(key_and_value.split("=").length == KEY_AND_VALUE){
                key = key_and_value.split("=")[KEY];
                value = key_and_value.split("=")[VALUE];
            }

            responseContent.append("\"");
            responseContent.append(key);
            responseContent.append("\":\"");
            responseContent.append(value);
            responseContent.append("\"");

            if(--keyCount != 0)
                responseContent.append(",");

        }
        responseContent.append("}");

        return responseContent.toString();
    }
}
