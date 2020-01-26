package com.example.helloworld;

import com.bluejay.framework.ApplicationMain;
import com.bluejay.framework.BlueJayFramework;
import com.bluejay.framework.annotation.ApplicationConfiguration;
import com.bluejay.server.WebServerConfig;

@ApplicationConfiguration(
//        applicationPackage = "com.eclard.easywebserver",
        applicationName = "Hello World application"
)
public class HelloWorldApplication extends ApplicationMain {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 8085;

    public static void main(String[] args) {
        BlueJayFramework.startTheWorld(new WebServerConfig()
                .setHostname(SERVER_HOSTNAME)
                .setPort(SERVER_PORT), HelloWorldApplication.class, args);

//        Or just like this, without WebServerConfig.
//        BlueJayFramework.startTheWorld(HelloWorldApplication.class, args);
    }

    @Override
    public void onApplicationStarted() {
//        registerApplicationServlet("/v1/hello", new HelloWorldServlet());
        System.out.println("APPLICATION STARTED!!");
    }
}
