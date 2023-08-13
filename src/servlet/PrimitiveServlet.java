package servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {
	@Override public void init(ServletConfig config) {
		System.out.println("init");
	}

	@Override public void service(ServletRequest request,
			ServletResponse response) throws IOException {
		System.out.println("service");
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