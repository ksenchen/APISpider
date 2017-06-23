package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlPaseUtils {
	public static List<String> getSubUrls(String htmlFilePath, String charsetName) throws IOException{
		List<String> list = new ArrayList<>();
		File file = new File(htmlFilePath);
		Document htmlDoc = Jsoup.parse(file, charsetName);
		
		List<String> cssUrlList = getUrlByTag(htmlDoc, "link", "href");
		list.addAll(cssUrlList);

		List<String> scriptList = getUrlByTag(htmlDoc, "script", "src");
		list.addAll(scriptList);
		
		List<String> frameList = getUrlByTag(htmlDoc, "frame", "src");
		list.addAll(frameList);
		
		List<String> hyperlinkList = getUrlByTag(htmlDoc, "a", "href");
		list.addAll(hyperlinkList);
		
		List<String> imgList = getUrlByTag(htmlDoc, "img", "src");
		list.addAll(imgList);
		
		return list;
	}
	
	private static List<String> getUrlByTag(Document htmlDoc, String htmlTag, String urlAtt){
		List<String> list = new ArrayList<>();
		Element content = htmlDoc.getElementsByTag("html").first();
		Elements links = content.getElementsByTag(htmlTag);
		for (Element link : links) {
			String linkHref = link.attr(urlAtt);
			if(StringUtils.isNotBlank(linkHref)){
				if(filter(linkHref)){
					list.add(linkHref);
				}
			}
		}
		return list;
	}
	
	private static boolean filter(String url){
		String regex = "javascript:[a-zA-Z0-9|_|$]{1,}\\((\\s*[a-zA-Z0-9|_|$]*\\s*|((\\s*[a-zA-Z0-9|_|$]+\\s*,\\s*)+\\s*[a-zA-Z0-9|_|$]+\\s*))\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		if(matcher.find()){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(filter("javascript:show(aa,bb,c_c,$dd);"));
	}
}
