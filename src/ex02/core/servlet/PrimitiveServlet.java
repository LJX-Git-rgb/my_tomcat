package ex02.core.servlet;

import com.google.gson.Gson;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PrimitiveServlet implements Servlet {
	@Override public void init(ServletConfig config) {
		System.out.println("init");
	}

	@Override public void service(ServletRequest request,
			ServletResponse response) throws IOException {
		PrintWriter printWriter = response.getWriter();
		String responseHeader =
				"HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
		printWriter.write(
				Arrays.toString(responseHeader.getBytes(StandardCharsets.UTF_8)));
		printWriter.print(new Gson().toJson(request.getParameterMap()));
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