package web_server.request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author JasonLiu
 */
public class HttpRequest implements ServletRequest {
	private final InputStream input;
	private final Map<String, String> requestMap = new HashMap<>();
	private final Map<String, String> requestParamMap = new HashMap<>();

	public HttpRequest(InputStream input) {
		this.input = input;
	}

	public HttpRequest parse() {
		StringBuilder stringBuilder = new StringBuilder(2048);
		int length;
		byte[] buffer = new byte[2048];
		try {
			length = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			length = -1;
		}
		for (int j = 0; j < length; j++) {
			stringBuilder.append((char) buffer[j]);
		}

		String requestStr = stringBuilder.toString();
		String[] requestArr = requestStr.split("\r\n");
		for (int i = 1; i < requestArr.length; i++) {
			String[] requestBody = requestArr[i].split(":");
			requestMap.put(requestBody[0], requestBody[1]);
		}

		String[] requestHeader = requestArr[0].split(" ");
		if (requestHeader.length < 3) {
			System.out.println(requestStr);
			return this;
		}
		String[] url = requestHeader[1].split("\\?");
		requestMap.put("method", requestHeader[0]);
		requestMap.put("uri", url[0]);
		requestMap.put("protocol", requestHeader[2]);

		if (url.length > 1) {
			String[] params = url[1].split("=");
			requestParamMap.put(params[0], params[1]);
		}

		return this;
	}

	public String getUri() {
		return requestMap.get("uri");
	}

	@Override public Object getAttribute(String s) {
		return null;
	}

	@Override public Enumeration getAttributeNames() {
		return null;
	}

	@Override public String getCharacterEncoding() {
		return null;
	}

	@Override public void setCharacterEncoding(String s)
			throws UnsupportedEncodingException {

	}

	@Override public int getContentLength() {
		return 0;
	}

	@Override public String getContentType() {
		return null;
	}

	@Override public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	@Override public String getParameter(String s) {
		return requestParamMap.get(s);
	}

	@Override public Enumeration getParameterNames() {
		return (Enumeration) requestParamMap.keySet().stream().collect(Collectors.toList());
	}

	@Override public String[] getParameterValues(String s) {
		return requestParamMap.values().toArray(new String[0]);
	}

	@Override public Map<String, String> getParameterMap() {
		return requestParamMap;
	}

	@Override public String getProtocol() {
		return null;
	}

	@Override public String getScheme() {
		return null;
	}

	@Override public String getServerName() {
		return null;
	}

	@Override public int getServerPort() {
		return 0;
	}

	@Override public BufferedReader getReader() throws IOException {
		return null;
	}

	@Override public String getRemoteAddr() {
		return null;
	}

	@Override public String getRemoteHost() {
		return null;
	}

	@Override public void setAttribute(String s, Object o) {

	}

	@Override public void removeAttribute(String s) {

	}

	@Override public Locale getLocale() {
		return null;
	}

	@Override public Enumeration getLocales() {
		return null;
	}

	@Override public boolean isSecure() {
		return false;
	}

	@Override public RequestDispatcher getRequestDispatcher(String s) {
		return null;
	}

	@Override public String getRealPath(String s) {
		return null;
	}

	@Override public int getRemotePort() {
		return 0;
	}

	@Override public String getLocalName() {
		return null;
	}

	@Override public String getLocalAddr() {
		return null;
	}

	@Override public int getLocalPort() {
		return 0;
	}
}
