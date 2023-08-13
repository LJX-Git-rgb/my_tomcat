import web_server.request.HttpRequest;
import web_server.response.HttpResponse;
import web_server.processor.ServletProcessor;
import web_server.processor.StaticResourceProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author JasonLiu
 */
public class MyTomcat {
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;

	private ServerSocket serverSocket;

	public MyTomcat() {
		
	}

	public static void main(String[] args) {
		MyTomcat server = new MyTomcat();
		server.await();
	}
}
