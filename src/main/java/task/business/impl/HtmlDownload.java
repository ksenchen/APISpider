package task.business.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import model.HtmlInfo;
import model.LocalHtmlInfo;
import task.business.IDownload;
import utils.Consts;
import utils.UrlUtils;

public class HtmlDownload implements IDownload {
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public void download(HtmlInfo htmlInfo) {
		String localFileName = getLocalFile(htmlInfo);
		String downloadUrl = htmlInfo.getUrl();
		
		long start = System.currentTimeMillis();
		String line = null;
		try {
			URL url = new URL(downloadUrl);
			InputStream input = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(localFileName))));
			while ((line = reader.readLine()) != null) {
				writer.write(line, 0, line.length());
			}
			reader.close();
			writer.close();
			int counter = Consts.setCounter();
			logger.debug(downloadUrl + " 下载完成, 序号：" + counter + " 用时：" + (System.currentTimeMillis() - start)/1000.0 + " s");
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof UnknownHostException) {
				System.err.println("下载出错了,请检查网络设置");
				System.exit(0);
			}
			if (e instanceof FileNotFoundException) {
				System.err.println(downloadUrl + " 文件不存在");
			}

			System.out.println("parentUrl " + htmlInfo.getParentUrl());
			System.out.println("url " + htmlInfo.getUrl());
		}
		
		LocalHtmlInfo localHtmlInfo = new LocalHtmlInfo(localFileName, htmlInfo.getUrl());
		try {
			Consts.PARSE_QUENE.put(localHtmlInfo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getLocalFile(HtmlInfo htmlInfo) {
		String downloadUrl = htmlInfo.getUrl();

		String urlPath = "";
		try {
			urlPath = UrlUtils.getUrlPath(downloadUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		String tempName = urlPath.substring(urlPath.lastIndexOf("/"));
		if (StringUtils.isNotBlank(tempName)) {
			if (!tempName.contains(".")) {
				urlPath += "index.html";
			}
		} else {
			urlPath += "index.html";
		}
		String localFileName = Consts.LOCAL_ROOT + urlPath;

		String dirPath = localFileName.substring(0,
				localFileName.lastIndexOf("/"));
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(localFileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return localFileName;
	}

}
