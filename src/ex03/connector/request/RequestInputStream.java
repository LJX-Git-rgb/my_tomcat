package ex03.connector.request;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestInputStream extends ServletInputStream {
  private final InputStream input;

  public RequestInputStream(InputStream input) {
    this.input = input;
  }

  @Override public int read() throws IOException {
    return 0;
  }
}
