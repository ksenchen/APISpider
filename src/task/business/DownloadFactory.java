package task.business;

import java.util.HashMap;
import java.util.Map;

import task.business.impl.HtmlDownload;
import task.business.impl.ImgDownload;


import model.HtmlInfo;

public class DownloadFactory {
	public static Map<String, IDownload> map = new HashMap<String, IDownload>();

	public static IDownload getDownload(HtmlInfo htmlInfo) {
		String key = null;
		String url = htmlInfo.getUrl();
		if (url.lastIndexOf(".gif") > 0 || url.lastIndexOf(".png") > 0) {
			key = "img";
		} 
		else {
			key = "html";
		}
		
		if(map.get(key) != null){
			return map.get(key);
		}
		
		if(key.equals("img")){
			synchronized (DownloadFactory.class) {
				if (map.get(key) == null) {
					map.put(key, new ImgDownload());
				}
			}
		}
		
		if(key.equals("html")){
			synchronized (DownloadFactory.class) {
				if (map.get(key) == null) {
					map.put(key, new HtmlDownload());
				}
			}
		}
		return map.get(key);
	}
}
