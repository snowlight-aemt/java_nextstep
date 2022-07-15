package bong.lines.basic.handler.common.factory.operation;

import bong.lines.basic.handler.getindexhtml.IndexHTMLHandler;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public class GetScreen implements LinesGet<Object>{

    private final String queryContent;

    @Override
    public Object get() {
        String screenName = queryContent.split(" ")[1];
        try {
            return Objects.requireNonNull(
                            IndexHTMLHandler.class
                                    .getResourceAsStream("/templates" + screenName))
                    .readAllBytes();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
