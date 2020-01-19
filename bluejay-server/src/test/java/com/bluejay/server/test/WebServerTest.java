package com.bluejay.server.test;

import com.bluejay.server.UndertowServer;
import com.bluejay.server.WebServer;
import com.bluejay.server.WebServerConfig;
import com.bluejay.server.http.HttpRequest;
import com.bluejay.server.http.HttpResponse;
import com.bluejay.server.http.ServletRegister;
import com.bluejay.server.servlet.WebServlet;
import com.bluejay.server.utils.JsonObject;
import org.junit.Test;

public class WebServerTest {

    @Test
    public void testStartUpWebServer() {
//        WebServerConfig config = new WebServerConfig("localhost", 3380);
//        WebServer server = new UndertowServer(config);
//        server.start();
//
//        ServletRegister.registerServlet("/v1/hello", new TestServlet());
//
//        try {
//            Thread.sleep(20_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static class TestServlet extends WebServlet {

        @Override
        public HttpResponse handleGet(HttpRequest httpRequest) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.putString("status", "OK");

            return HttpResponse.returnJson(jsonObject);
        }
    }
}
