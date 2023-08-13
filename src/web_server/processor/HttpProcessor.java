package web_server.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpProcessor {
  public void process(Socket socket) {
    InputStream input = null;
    OutputStream output = null;
    try {
      input = new SocketInputStream(socket.getInputStream(), 2048);
      output = socket.getOutputStream();
      request = new HttpRequest(input);
      response = new HttpResponse(output);
      response.setRequest(request);
      response.setHeader("Server", "Pyrmont Servlet Container");
      parseRequest(input, output);
      parseHeaders(input);
      //check if this is a request for a servlet or a static resource
      //a request for a servlet begins with "/servlet/"
      if (request.getRequestURI().startsWith("/servlet/")) {
        ServletProcessor processor = new ServletProcessor();
        processor.process(request, response);
      } else {
        StaticResourceProcessor processor = new StaticResourceProcessor();
        processor.process(request, response);
      }
      // Close the socket
      socket.close();
      // no shutdown for this application
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
