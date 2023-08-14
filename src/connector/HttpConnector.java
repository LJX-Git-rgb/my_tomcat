package connector;

import processor.HttpProcessor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {
  private boolean stoped = false;

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
      Socket socket;
      try {
        socket = serverSocket.accept();
      } catch (Exception e) {
        continue;
      }
      HttpProcessor processor = new HttpProcessor(this);
      processor.process(socket);
    }
  }

  public String getScheme() {
    return "http";
  }
}
