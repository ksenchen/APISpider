package utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import model.HtmlInfo;
import model.LocalHtmlInfo;

public class Consts {
	public static String HOST = null;
	public static String LOCAL_ROOT = "D:/MyDownload";
	public static String PROSTFIX_HTML = ".html";
	public static Set<String> FILE_CACHE = new HashSet<>();
	
	public static BlockingQueue<HtmlInfo> DOWNLOAD_QUENE = new ArrayBlockingQueue<>(5000);
	public static BlockingQueue<LocalHtmlInfo> PARSE_QUENE = new ArrayBlockingQueue<>(1000);
	
	private static Integer counter = 0;
	public synchronized static Integer setCounter(){
		return ++counter;
	}
	public static Integer getCounter(){
		return counter;
	}
}
