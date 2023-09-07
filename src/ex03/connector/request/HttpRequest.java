package ex03.connector.request;

import ex03.org.apache.tomcat.catalina.ParameterMap;
import ex03.org.apache.tomcat.catalina.RequestUtil;
import ex03.org.apache.tomcat.catalina.SocketInputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author JasonLiu
 */
public class HttpRequest implements ServletRequest {
	protected SocketInputStream input;
	protected HashMap<String, String> headers = new HashMap<>();
	protected ArrayList<String> cookies = new ArrayList<>();
	private String queryString;
	private ParameterMap parameters = null;
	private boolean requestedSessionURL;
	private boolean requestedSessionCookie;
	private int contentLength;
	private String contentType;
	private boolean parsed;

	public HttpRequest(SocketInputStream input) {
		this.input = input;
	}

	public void setQueryString(String s) {
		this.queryString = s;
	}

	public void setMethod(String method) {
		headers.put("method", method);
	}

	public void setProtocol(String protocol) {
		headers.put("protocol", protocol);
	}

	public void setRequestedSessionId(String substring) {
		headers.put("jsessionid", substring);
	}

	public void setRequestedSessionURL(boolean b) {
		this.requestedSessionURL = b;
	}

	public void setRequestURI(String normalizedUri) {
		headers.put("uri", normalizedUri);
	}

	public InputStream getStream() {
		return null;
	}

	public String getRequestURI() {
		return headers.get("uri");
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public boolean isRequestedSessionIdFromCookie() {
		return !requestedSessionURL;
	}

	public void setRequestedSessionCookie(boolean b) {
		requestedSessionCookie = b;
	}

	public void addCookie(Cookie cookie) {
		cookies.add(cookie.getName() + "=" + cookie.getValue());
	}

	public void setContentLength(int n) {
		this.contentLength = n;
	}

	public void setContentType(String value) {
		this.contentType = value;
	}

	private void parseParameters() {
		if (parsed) {
			return;
		}
		ParameterMap results = parameters;
		if (results == null) {
			results = new ParameterMap();
		}
		results.setLocked(false);
		String encoding = getCharacterEncoding();
		if (encoding == null) {
			encoding = "ISO-8859-1";
		}
		try {
			RequestUtil.parseParameters(results, queryString, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String contentType = getContentType();
		if (contentType == null) {
			contentType = "";
		}
		int semicolon = contentType.indexOf(';');
		if (semicolon >= 0) {
			contentType = contentType.substring(0, semicolon).trim();
		} else {
			contentType = contentType.trim();
		}

		if ("POST".equals(getMethod()) && (getContentLength() > 0)
				&& "application/x-www-form-urlencoded".equals(contentType)) {
			try {
				int max = getContentLength();
				int len = 0;
				byte[] buf = new byte[getContentLength()];
				ServletInputStream is = getInputStream();
				while (len < max) {
					int next = is.read(buf, len, max - len);
					if (next < 0) {
						break;
					}
					len += next;
				}
				is.close();
				if (len < max) {
					throw new RuntimeException("Content length mismatch");
				}
				RequestUtil.parseParameters(results, buf, encoding);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		results.setLocked(true);
		parsed = true;
		parameters = results;
	}

	private String getMethod() {
		return headers.get("method");
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
		return contentLength;
	}

	@Override public String getContentType() {
		return contentType;
	}

	@Override public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	@Override public String getParameter(String s) {
		parseParameters();
		String[] values = (String[]) parameters.get(s);
		if (values != null) {
			return values[0];
		}
		return "";
	}

	@Override public Enumeration getParameterNames() {
		parseParameters();
		return (Enumeration) parameters.keySet();
	}

	@Override public String[] getParameterValues(String s) {
		parseParameters();
		List<String> values = new ArrayList<>();
		parameters.keySet().forEach(item -> {
			if (item.equals(s)) {
				values.add((String) parameters.get(item));
			}
		});
		return values.toArray(new String[0]);
	}

	@Override public ParameterMap getParameterMap() {
		parseParameters();
		return this.parameters;
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
