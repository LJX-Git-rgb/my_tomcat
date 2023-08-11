package web_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author JasonLiu
 */
public class StaticResourceProcessor {
	private HttpRequest request;
	private HttpResponse response;

	public void process(HttpRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;

		try {
			sendStaticResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendStaticResource() throws IOException {
		PrintWriter writer = response.getWriter();
		FileInputStream fileInputStream = null;
		try {
			File file = new File(Constants.WEB_ROOT, request.getUri());
			if (file.exists()) {
				String responseHeader =
						"HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
				writer.write(responseHeader);

				fileInputStream = new FileInputStream(file);
				byte[] bytes = fileInputStream.readAllBytes();
				writer.write(new String(bytes));
			} else {
				writer.write(
						"HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
								+ "Content-Length: 23\r\n" + "\r\n"
								+ "<h1>File Not Found</h1>");
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
