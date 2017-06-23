package task.business.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import model.HtmlInfo;
import model.LocalHtmlInfo;
import task.business.IParse;
import utils.Consts;
import utils.CssParseUtils;
import utils.HtmlPaseUtils;
import utils.UrlUtils;

public class ParseHtml implements IParse{
	protected final Log logger = LogFactory.getLog(getClass());
	private static IParse instance;

	@Override
	public void parse(LocalHtmlInfo localHtmlInfo) {
		long start = System.currentTimeMillis();
		List<String> subUrlList = extractUrl(localHtmlInfo);
		for (String url : subUrlList) {
			url = UrlUtils.transformToAbsPath(localHtmlInfo.getUrl(), url);
			String host = null;
			String path = null;
			try {
				host = UrlUtils.getHost(url);
				path = UrlUtils.getUrlPath(url);
			} catch (MalformedURLException e) {
				System.err.println("parentUrl: " + localHtmlInfo.getUrl());
				System.err.println("errorUrl: " + url);
				e.printStackTrace();
				continue;
			}
			
			if (!Consts.HOST.equals(host)) {
				continue;
			}
			
			String localFilePath = Consts.LOCAL_ROOT + path;
			if (!Consts.FILE_CACHE.contains(localFilePath)) {
				Consts.FILE_CACHE.add(localFilePath);
				HtmlInfo info = new HtmlInfo(localHtmlInfo.getUrl(), url);
				try {
					Consts.DOWNLOAD_QUENE.put(info);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		logger.debug(localHtmlInfo.getLocalFilePath() + " 解析完成，用时：" + (System.currentTimeMillis() - start)/1000.0 + " s");
	}
	
	private List<String> extractUrl(LocalHtmlInfo localHtmlInfo) {
		List<String> subUrlList = new ArrayList<>();
		try {
			if (localHtmlInfo.getLocalFilePath().contains(".html")) {
				subUrlList = HtmlPaseUtils.getSubUrls(localHtmlInfo.getLocalFilePath(), "utf-8");
			}
			if (localHtmlInfo.getLocalFilePath().contains(".css")) {
				subUrlList = CssParseUtils.extractUrl(localHtmlInfo.getLocalFilePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subUrlList;
	}
	
	public static IParse getInstance(){
		if(instance != null){
			return instance;
		}
		synchronized (ParseHtml.class) {
			if(instance == null){
				instance = new ParseHtml();
			}
			return instance;
		}
		
	}
}
