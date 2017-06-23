package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CssParseUtils
{
	public static List<String> extractUrl(String cssPath) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(new File(cssPath)));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while((line = reader.readLine()) != null){
			buffer.append(line);
		}
		reader.close();
		List<String> list = new ArrayList<>();
		String regex = "url\\(([a-zA-Z0-9]{1,}/)+[a-zA-Z0-9]{1,}-[a-zA-Z0-9]{1,}\\.[a-zA-Z]{1,}\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(buffer.toString());
		while(matcher.find()){
			String url = matcher.group();
			url = url.substring(url.indexOf("(") + 1, url.indexOf(")"));
			list.add(url);
		}
		return list;
	}
	public static void main(String[] args) throws Exception {
		extractUrl("D:/testDownload/spring/docs/javadoc-api/spring-javadoc.css");
	}
}