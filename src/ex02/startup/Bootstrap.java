package ex02.startup;

import ex02.connector.HttpConnector;

public class Bootstrap {
  public static void main(String[] args) {
    System.out.println("Start Bootstrap");
    System.out.println("http://localhost:8080/index.html");
    HttpConnector connector = new HttpConnector();
    connector.start();
  }
}
