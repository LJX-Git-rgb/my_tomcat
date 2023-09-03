package ex02.connector.request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
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
	private final Map<String, String> requestMap = new HashMap<>();
	private final Map<String, String> parameterMap = new HashMap<>();

	public HttpRequest(Map<String, String> requestMap,
			Map<String, String> parameterMap) {
		this.requestMap.putAll(requestMap);
		this.parameterMap.putAll(parameterMap);
	}

	public String getRequestURI() {
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
		return parameterMap.get(s);
	}

	@Override public Enumeration getParameterNames() {
		return (Enumeration) parameterMap.keySet().stream()
				.collect(Collectors.toList());
	}

	@Override public String[] getParameterValues(String s) {
		return parameterMap.values().toArray(new String[0]);
	}

	@Override public Map<String, String> getParameterMap() {
		return parameterMap;
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
