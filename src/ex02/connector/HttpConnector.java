package ex02.connector;

import ex02.connector.processor.HttpProcessor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector {
  public void start() {
    ServerSocket serverSocket = null;
    int port = 8080;
    try {
      serverSocket = new ServerSocket(port, 1,
          InetAddress.getByName("127.0.0.1"));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    while (true) {
      Socket socket;
      try {
        socket = serverSocket.accept();
      } catch (Exception e) {
        continue;
      }
      new Thread(new connection(socket)).start();
    }
  }

  public String getScheme() {
    return "http";
  }
}

class connection implements Runnable {
  private final Socket socket;

  public connection(Socket socket) {
    this.socket = socket;
  }

  @Override public void run() {
    HttpProcessor processor = new HttpProcessor();
    processor.process(socket);
  }
}
