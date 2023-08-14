package processor;

import request.HttpRequest;
import response.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author JasonLiu
 */
public class ServletProcessor {
	public void process(HttpRequest request, HttpResponse response) {
		String uri = request.getRequestURI();
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
