import web_server.HttpRequest;
import web_server.HttpResponse;
import web_server.ServletProcessor;
import web_server.StaticResourceProcessor;

import java.io.File;
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
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void await() {
		while (!shutdown) {
			Socket socket = null;
			InputStream input;
			OutputStream output;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();

				HttpRequest request = new HttpRequest(input).parse();

				HttpResponse response = new HttpResponse(request, output);

				if (request.getUri().startsWith("/servlet/")) {
					ServletProcessor processor = new ServletProcessor();
					processor.process(request, response);
				} else {
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				}

				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		MyTomcat server = new MyTomcat();
		server.await();
	}
}
