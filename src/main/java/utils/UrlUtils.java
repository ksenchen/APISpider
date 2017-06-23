package utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;


public class UrlUtils {
	public static String getHtmlName(String url, String indexUrl) {
		String htmlName = url.replace(indexUrl, "");
		if (StringUtils.isBlank(htmlName)) {
			htmlName = "index.html";
		}
		return htmlName;
	}
	
	public static String transformToAbsPath(String parentUrl, String subUrl){
		try {
			URL absUrl = new URL(parentUrl);
			URL parseUrl = new URL(absUrl, subUrl);
			return parseUrl.toString();
		} catch (Exception e) {
			return subUrl;
		}
	}

	public static String getUrlPath(String url) throws MalformedURLException{
		URL u = new URL(url);
		return u.getPath();
	}
	
	public static String getHost(String url) throws MalformedURLException{
		URL u = new URL(url);
		return u.getHost();
	}
}
