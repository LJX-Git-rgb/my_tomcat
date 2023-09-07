package ex03.connector.processor;

import ex03.connector.request.HttpRequest;
import ex03.connector.response.HttpResponse;

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
			response.sendStaticResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
