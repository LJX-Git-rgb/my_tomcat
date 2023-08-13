package web_server.processor;

import web_server.request.HttpRequest;
import web_server.response.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * @author JasonLiu
 */
public class ServletProcessor {
	public void process(HttpRequest request, HttpResponse response) {
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		String servletPath =
				this.getClass().getClassLoader().getDefinedPackage("").getName()
						+ "servlet." + servletName;
		try {
			Object o = Class.forName(servletPath).getConstructor().newInstance();
			Servlet servlet = (Servlet) o;
			servlet.service(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
