package ebills.tools.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {
	public static boolean isInternalTesting(HttpServletRequest request) {
		String qs = request.getQueryString();
		return qs != null && qs.contains("_internal_testing_");
	}

	public static String serializeData(HttpServletRequest request) {
		if (request.getMethod().equalsIgnoreCase("POST")
				|| request.getMethod().equalsIgnoreCase("PUT")) {
			StringBuilder sb = new StringBuilder();
			Map<String, String[]> map = request.getParameterMap();
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				if (entry.getKey().toLowerCase().contains("password"))
					continue;
				for (String value : entry.getValue()) {
					sb.append(entry.getKey())
							.append('=')
							.append(value.length() > 256 ? value.substring(0,
									256) : value).append('&');
				}
			}
			return sb.toString();
		}
		String queryString = request.getQueryString();
		return queryString != null ? queryString : "";
	}

	public static Map<String, String> getParametersMap(
			HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String,String[]> vaue  = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : vaue.entrySet()) {
			String name = entry.getKey();
			String[] value = entry.getValue();
			if (value != null && value.length > 0)
				map.put(name, value[0]);
		}
		return map;
	}

	public static Map<String, String> parseParametersFromQueryString(
			String queryString) {
		if (StringUtils.isBlank(queryString))
			return Collections.emptyMap();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (String s : queryString.split("&")) {
			if (StringUtils.isBlank(s))
				continue;
			String arr[] = s.split("=", 2);
			if (!map.containsKey(arr[0]))
				map.put(arr[0], arr.length == 2 ? arr[1] : "");
		}
		return map;
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String addr = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(addr)) {
			addr = request.getHeader("X-Forwarded-For");
			int index = 0;
			if (StringUtils.isNotBlank(addr) && (index = addr.indexOf(',')) > 0)
				addr = addr.substring(0, index);
		}
		addr = StringUtils.isNotBlank(addr) ? addr : request.getRemoteAddr();
		addr = addr != null ? addr : "";
		return addr;
	}

	public static String trimPathParameter(String url) {
		if (url == null)
			return null;
		int i = url.indexOf(';');
		return i > -1 ? url.substring(0, i) : url;
	}

	public static String getBaseUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String ctxPath = request.getContextPath();
		return url.substring(
				0,
				url.indexOf(StringUtils.isBlank(ctxPath) ? "/" : ctxPath,
						url.indexOf("://") + 3)
						+ ctxPath.length());
	}

	public static String getBaseUrl(HttpServletRequest request, boolean secured) {
		return getBaseUrl(request, secured, true);
	}

	public static String getBaseUrl(HttpServletRequest request,
			boolean secured, boolean includeContextPath) {
		String host = "localhost";
		String protocol = "http";
		int port = 80;
		URL url = null;
		try {
			url = new URL(request.getRequestURL().toString());
			host = url.getHost();
			protocol = url.getProtocol();
			port = url.getPort();
			if (port <= 0)
				port = url.getDefaultPort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if ((protocol.equalsIgnoreCase("https") && secured)
				|| (protocol.equalsIgnoreCase("http") && !secured)) {
			return getBaseUrl(request);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(secured ? "https://" : "http://");
		sb.append(host);
		if (secured) {
			if (port == 8080)
				sb.append(":8443");
		} else {
			if (port == 8443)
				sb.append(":8080");
		}
		if (includeContextPath)
			sb.append(request.getContextPath());
		return sb.toString();
	}


}
