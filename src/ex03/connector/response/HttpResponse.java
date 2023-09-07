package ex03.connector.response;

import ex02.connector.response.ResponseOutStream;
import ex03.connector.request.HttpRequest;
import ex03.org.apache.tomcat.catalina.ResponseStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author JasonLiu
 */
public class HttpResponse implements ServletResponse {
	private HttpRequest request;
	private OutputStream output;
	private PrintWriter writer;

	public HttpResponse(OutputStream output) {
		this.output = new ResponseOutStream(output);
	}

	@Override public String getCharacterEncoding() {
		return null;
	}

	@Override public String getContentType() {
		return null;
	}

	@Override public ServletOutputStream getOutputStream() throws IOException {
		return new ex03.connector.response.ResponseStream(this);
	}

	@Override public PrintWriter getWriter() {
		ResponseStream newStream = new ResponseStream(this);
		newStream.setCommit(false);
		OutputStreamWriter osr = new OutputStreamWriter(newStream,
				StandardCharsets.UTF_8);
		writer = new ResponseWriter(osr);
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

	public void setHeader(String name, String value) {
		//		output.setHeader(name, value);
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public void write(int b) {

	}

	public void write(byte[] b, int off, int actual) {

	}

	public void finishResponse() {
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	public void sendStaticResource() throws IOException {
		FileInputStream fileInputStream = null;
		try {
			File file = new File(
					System.getProperty("user.dir") + File.separator + "webroot",
					request.getRequestURI());
			if (file.exists()) {
				String responseHeader =
						"HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
				output.write(responseHeader.getBytes());

				fileInputStream = new FileInputStream(file);
				byte[] bytes = fileInputStream.readAllBytes();
				output.write(bytes);
			} else {
				output.write(
						("HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
								+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>")
								.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}
}