package task;

import task.business.DownloadFactory;
import model.HtmlInfo;

public class DownloadTask implements Runnable {
	private HtmlInfo htmlInfo;

	public DownloadTask(HtmlInfo htmlInfo) {
		super();
		this.htmlInfo = htmlInfo;
	}

	@Override
	public void run() {
		DownloadFactory.getDownload(htmlInfo).download(htmlInfo);
	}

}
