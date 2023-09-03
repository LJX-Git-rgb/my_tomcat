package ex03.connector.response;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseOutStream extends ServletOutputStream {
  private final OutputStream output;

  public ResponseOutStream(OutputStream output) {
    this.output = output;
  }

  @Override public void write(int b) throws IOException {
    output.write(b);
  }
}
