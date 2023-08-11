package web_server;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * @author JasonLiu
 */
public class HttpResponse implements ServletResponse {
	private final HttpRequest request;
	private final OutputStream output;
	private PrintWriter writer;

	public HttpResponse(HttpRequest request, OutputStream output) {
		this.request = request;
		this.output = output;
	}

	@Override public String getCharacterEncoding() {
		return null;
	}

	@Override public String getContentType() {
		return null;
	}

	@Override public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override public PrintWriter getWriter() {
		writer = new PrintWriter(output, true);
		return writer;
	}

	@Override public void setCharacterEncoding(String s) {

	}

	@Override public void setContentLength(int i) {

	}

	@Override public void setContentType(String s) {

	}

	@Override public void setBufferSize(int i) {

	}

	@Override public int getBufferSize() {
		return 0;
	}

	@Override public void flushBuffer() throws IOException {

	}

	@Override public void resetBuffer() {

	}

	@Override public boolean isCommitted() {
		return false;
	}

	@Override public void reset() {

	}

	@Override public void setLocale(Locale locale) {

	}

	@Override public Locale getLocale() {
		return null;
	}
}
