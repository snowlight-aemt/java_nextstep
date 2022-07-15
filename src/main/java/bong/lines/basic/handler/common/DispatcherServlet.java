package bong.lines.basic.handler.common;

import bong.lines.basic.handler.common.mapping.GetMapping;
import bong.lines.basic.handler.common.mapping.mapper.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class DispatcherServlet implements Runnable {

    private final Socket connection;

    public DispatcherServlet(Socket connection){
        this.connection = connection;
    }

    @Override
    public void run() {
        try(InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HandlerMapping handlerMapping = new GetMapping(in, out);
            handlerMapping.process();
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
}
