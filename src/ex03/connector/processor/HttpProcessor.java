package ex03.connector.processor;

import ex03.connector.HttpConnector;
import ex03.connector.request.HttpRequest;
import ex03.connector.response.HttpResponse;
import ex03.org.apache.tomcat.catalina.HttpHeader;
import ex03.org.apache.tomcat.catalina.HttpRequestLine;
import ex03.org.apache.tomcat.catalina.RequestUtil;
import ex03.org.apache.tomcat.catalina.SocketInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpProcessor {
  private HttpRequestLine requestLine = new HttpRequestLine();
  private HttpRequest request;
  private HttpResponse response;

  public HttpProcessor(HttpConnector connector) {

  }

  public void process(Socket socket) {
    SocketInputStream input;
    OutputStream output;
    try {
      input = new SocketInputStream(socket.getInputStream(), 2048);
      output = socket.getOutputStream();

      request = new HttpRequest(input);
      parseRequest(input, output);

      response = new HttpResponse(output);
      response.setRequest(request);
      response.setHeader("Server", "Pyrmont Servlet Container");
    } catch (Exception e) {
      try {
        socket.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      e.printStackTrace();
      return;
    }

    if (request.getRequestURI().startsWith("/servlet/")) {
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

  private void parseRequest(SocketInputStream input, OutputStream output)
      throws ServletException, IOException {
    parseRequestLine(input);
    parseHeaders(input);
  }

  private String normalize(String uri) {
    uri = uri.replace("\\", "/").trim();
    return uri;
  }

  private void parseRequestLine(SocketInputStream input)
      throws IOException, ServletException {
    input.readRequestLine(requestLine);
    String method = new String(requestLine.method, 0, requestLine.methodEnd);
    String protocol = new String(requestLine.protocol, 0,
        requestLine.protocolEnd);

    if (method.length() < 1) {
      throw new ServletException("Missing HTTP request method");
    } else if (requestLine.uriEnd < 1) {
      throw new ServletException("Missing HTTP request URI");
    }
    request.setProtocol(protocol);
    request.setMethod(method);

    // Parse any query parameters out of the request URI
    int question = requestLine.indexOf("?");
    String uri;
    if (question >= 0) {
      request.setQueryString(new String(requestLine.uri, question + 1,
          requestLine.uriEnd - question - 1));
      uri = new String(requestLine.uri, 0, question);
    } else {
      request.setQueryString("");
      uri = new String(requestLine.uri, 0, requestLine.uriEnd);
    }

    // Checking for an absolute URI (with the HTTP protocol)
    if (!uri.startsWith("/")) {
      int pos = uri.indexOf("://");
      // Parsing out protocol and host name
      if (pos != -1) {
        pos = uri.indexOf('/', pos + 3);
        if (pos == -1) {
          uri = "";
        } else {
          uri = uri.substring(pos);
        }
      }
    }

    // Parse any requested session ID out of the request URI
    String match = ";jsessionid=";
    int semicolon = uri.indexOf(match);
    if (semicolon >= 0) {
      String rest = uri.substring(semicolon + match.length());
      int semicolon2 = rest.indexOf(';');
      if (semicolon2 >= 0) {
        request.setRequestedSessionId(rest.substring(0, semicolon2));
        rest = rest.substring(semicolon2);
      } else {
        request.setRequestedSessionId(rest);
      }
      request.setRequestedSessionURL(true);
      uri = uri.substring(0, semicolon);
    } else {
      request.setRequestedSessionId(null);
      request.setRequestedSessionURL(false);
    }

    String normalizedUri = normalize(uri);
    if (normalizedUri != null) {
      request.setRequestURI(normalizedUri);
    } else {
      request.setRequestURI(uri);
      throw new ServletException("Invalid URI: " + uri + "'");
    }
  }

  private void parseHeaders(SocketInputStream input)
      throws IOException, ServletException {
    HttpHeader header = new HttpHeader();
    input.readHeader(header);
    while (header.nameEnd != 0) {
      String name = new String(header.name, 0, header.nameEnd);
      String value = new String(header.value, 0, header.valueEnd);
      if (name.equals("cookie")) {
        Cookie[] cookies = RequestUtil.parseCookieHeader(value);
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals("jsessionid")) {
            if (!request.isRequestedSessionIdFromCookie()) {
              request.setRequestedSessionId(cookie.getValue());
              request.setRequestedSessionURL(false);
              request.setRequestedSessionCookie(true);
            }
          }
          request.addCookie(cookie);
        }
      } else if (name.equals("content-length")) {
        int n;
        try {
          n = Integer.parseInt(value);
        } catch (Exception e) {
          throw new ServletException("Content length is not a number");
        }
        request.setContentLength(n);
      } else if (name.equals("content-type")) {
        request.setContentType(value);
      }
      request.addHeader(name, value);
      input.readHeader(header);
    }
  }
}