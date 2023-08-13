package web_server.request;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestInputStream extends ServletInputStream {
  private final InputStream input;

  public RequestInputStream(InputStream input) {
    this.input = input;
  }

  @Override public int read() throws IOException {
    return 0;
  }
}
