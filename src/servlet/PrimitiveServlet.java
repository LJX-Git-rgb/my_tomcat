package servlet;

import com.google.gson.Gson;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class PrimitiveServlet implements Servlet {
	@Override public void init(ServletConfig config) {
		System.out.println("init");
	}

	@Override public void service(ServletRequest request,
			ServletResponse response) throws IOException {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ServletOutputStream outputStream = response.getOutputStream();
		String responseHeader =
				"HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
		outputStream.write(responseHeader.getBytes(StandardCharsets.UTF_8));
		outputStream.print(new Gson().toJson(request.getParameterMap()));
	}

	@Override public void destroy() {
		System.out.println("destroy");
	}

	@Override public String getServletInfo() {
		return null;
	}

	@Override public ServletConfig getServletConfig() {
		return null;
	}
}