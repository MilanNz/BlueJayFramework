# BlueJayFramework

BlueJay Framework is an open source java based framework. It is built on top of Undertow / Tomcat embedded web server.
BlueJay application is stand-alone java application and can be started using java -jar.

It is developed for fun, every contribution is welcome.

### BlueJay framework dependency:

    <dependency>
        <groupId>com.bluejay</groupId>
        <artifactId>bluejay-framework</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
    
    <!-- Webserver dependency -->
    <dependency>
        <groupId>com.bluejay</groupId>
        <artifactId>bluejay-undertow</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

### Quick example of completed BlueJay application in Java:
Main application class where everything starts:

```
@ApplicationConfiguration(
        applicationName = "Hello World application"
)
public class HelloWorldApplication extends ApplicationMain {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 8085;

    public static void main(String[] args) {
        BlueJayFramework.startTheWorld(new WebServerConfig()
                .setHostname(SERVER_HOSTNAME)
                .setPort(SERVER_PORT), HelloWorldApplication.class, args);
    }

    @Override
    public void onApplicationStarted() {
        System.out.println("APPLICATION STARTED!!");
    }
}
```

Servlet example:
```
@WebServletConfig(path = "/v1/hello")
public class HelloWorldServlet extends WebServlet {

    @Override
    public HttpResponse handleGet(HttpRequest easyRequest) {
        return HttpResponse.returnJson(new JsonObject()
                .putString("status", "OK")
                .putString("message", "hello, from BlueJay framework ;)"));
    }

    @Override
    public HttpResponse handlePost(HttpRequest easyRequest) {
        String body = easyRequest.getBody();
        if (body != null) {
            return HttpResponse.jsonResponse("Hello:  " + body, 200);
        }

        return HttpResponse.jsonResponse("Hello from the POST method: ", 200);
    }
}
```

#### If you see in console following text, that means everything works fine :)
```
   ____  _                _             
  | __ )| |_   _  ___    | | __ _ _   _ 
  |  _ \| | | | |/ _ \_  | |/ _` | | | |
  | |_) | | |_| |  __/ |_| | (_| | |_| |
  |____/|_|\__,_|\___|\___/ \__,_|\__, |
                                  |___/ 
BlueJay version: 1.0
Web server, Undertow version: Undertow - 2.0.29.Final
WebServerConfig { serverOption='undertow', serverDebugMode=true, hostname='localhost', port=8085, numberOfWorkers=0(Not set), bufferSize=0(Not set) }
Server is running on localhost:8085
-->
```

## Soon more details..
