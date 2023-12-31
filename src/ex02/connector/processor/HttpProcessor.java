package ex02.connector.processor;

import ex02.connector.request.HttpRequest;
import ex02.connector.response.HttpResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpProcessor {
  public void process(Socket socket) {
    InputStream input;
    OutputStream output;
    HttpRequest request;
    try {
      input = socket.getInputStream();
      output = socket.getOutputStream();
      request = parseRequest(input);
    } catch (Exception e) {
      try {
        socket.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      e.printStackTrace();
      return;
    }

    HttpResponse response = new HttpResponse(request, output);
    response.setHeader("Server", "Pyrmont Servlet Container");
    if (request.getRequestURI().startsWith("/ex02/core/servlet/")) {
      ServletProcessor processor = new ServletProcessor();
      processor.process(request, response);
    } else {
      StaticResourceProcessor processor = new StaticResourceProcessor();
      processor.process(request, response);
    }

    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public HttpRequest parseRequest(InputStream input) throws ServletException {
    String requestStr = readRequestStr(input);
    Map<String, String> requestMap = new HashMap<>();
    Map<String, String> requestParamMap = new HashMap<>();

    String[] requestArr = requestStr.split("\r\n");
    String[] requestHeader = requestArr[0].split(" ");
    if (requestHeader.length < 3) {
      throw new ServletException("Invalid Request");
    }

    for (int i = 1; i < requestArr.length; i++) {
      String[] requestBody = requestArr[i].split(":");
      requestMap.put(requestBody[0], requestBody[1]);
    }

    String[] url = requestHeader[1].split("\\?");
    requestMap.put("method", requestHeader[0]);
    requestMap.put("uri", url[0]);
    requestMap.put("protocol", requestHeader[2]);

    if (url.length > 1) {
      String[] params = url[1].split("=");
      requestParamMap.put(params[0], params[1]);
    }
    return new HttpRequest(requestMap, requestParamMap);
  }

  private String readRequestStr(InputStream input) {
    StringBuilder stringBuilder = new StringBuilder(2048);
    int length;
    byte[] buffer = new byte[2048];
    try {
      length = input.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
      length = -1;
    }
    for (int j = 0; j < length; j++) {
      stringBuilder.append((char) buffer[j]);
    }

    return stringBuilder.toString();
  }
}
