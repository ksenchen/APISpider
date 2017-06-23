package task.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import task.business.IDownload;
import utils.Consts;
import utils.UrlUtils;
import model.HtmlInfo;

public class ImgDownload implements IDownload{
	protected final Log logger = LogFactory.getLog(getClass());
	@Override
	public void download(HtmlInfo htmlInfo) {
		URL url;
		try {
			String imgPath = Consts.LOCAL_ROOT + UrlUtils.getUrlPath(htmlInfo.getUrl());
			String dirPath = imgPath.substring(0, imgPath.lastIndexOf("/"));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File imgFile = new File(imgPath);
			if (!imgFile.exists()) {
				imgFile.createNewFile();
			}
			
			long start = System.currentTimeMillis();
			url = new URL(htmlInfo.getUrl());
			InputStream in = url.openStream();
			OutputStream out = new FileOutputStream(imgFile);
			byte bytes[] = new byte[512];
			int length = 0;
			while ((length = in.read(bytes)) > 0) {
				out.write(bytes, 0, length);
			}
			in.close();
			out.close();
			
			int counter = Consts.setCounter();
			logger.debug(htmlInfo.getUrl() + " 下载完成，序号："  + counter + " 用时：" + (System.currentTimeMillis() - start)/1000.0 + " s" );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
