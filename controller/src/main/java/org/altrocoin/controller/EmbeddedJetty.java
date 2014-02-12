package org.altrocoin.controller;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Maor Bril
 * Date: 2/11/14
 * Time: 9:56 PM
 * Copyright Spidercloud Wireless INC.
 */
public class EmbeddedJetty {

    private static final int DEFAULT_PORT = 8080;
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = "org.altrocoin.controller.config";
    private static final String MAPPING_URL = "/*";
    private static final String DEFAULT_PROFILE = "dev";

    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(getPortFromArgs(args));
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        return DEFAULT_PORT;
    }

    private void startJetty(int port) throws Exception {
        Server server = new Server(port);
        server.setHandler(getServletContextHandler());
        server.start();
        server.join();
    }

    private static WebAppContext getServletContextHandler() throws IOException {
        String rootPath = EmbeddedJetty.class.getClassLoader().getResource(".").toString();
        File f = new File(rootPath.replaceAll("file:","") + "../../src/main/webapp");
        WebAppContext webapp = new WebAppContext(f.getAbsolutePath(), "");
        return webapp;
    }

    private static WebApplicationContext getContext() {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        return context;
    }

}
