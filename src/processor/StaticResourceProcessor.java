package processor;

import request.HttpRequest;
import response.HttpResponse;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
		ServletOutputStream writer = response.getOutputStream();
		FileInputStream fileInputStream = null;
		try {
			File file = new File(
					System.getProperty("user.dir") + File.separator + "webroot",
					request.getRequestURI());
			if (file.exists()) {
				String responseHeader =
						"HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
				writer.write(responseHeader.getBytes(StandardCharsets.UTF_8));

				fileInputStream = new FileInputStream(file);
				byte[] bytes = fileInputStream.readAllBytes();
				writer.write(bytes);
			} else {
				writer.write(
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
