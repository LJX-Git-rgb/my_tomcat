package web_server.connector;

import web_server.processor.HttpProcessor;
import web_server.processor.ServletProcessor;
import web_server.processor.StaticResourceProcessor;
import web_server.request.HttpRequest;
import web_server.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {
  boolean stoped = false;

  public String getScheme() {
    return "http";
  }

  public void start() {
    Thread thread = new Thread(this);
    thread.start();
  }

  @Override public void run() {
    ServerSocket serverSocket = null;
    int port = 8080;
    try {
      serverSocket = new ServerSocket(port, 1,
          InetAddress.getByName("127.0.0.1"));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    while (!stoped) {
      Socket socket = null;
      try {
        socket = serverSocket.accept();
      } catch (Exception e) {
        continue;
      }
      HttpProcessor processor = new HttpProcessor();
      processor.process(socket);
    }
  }
}
