import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import model.HtmlInfo;
import model.LocalHtmlInfo;
import task.DownloadTask;
import task.ParseTask;
import utils.Consts;
import utils.UrlUtils;


public class Bootstrap {
	private static String indexUrl = "http://docs.spring.io/spring/docs/4.1.0.BUILD-SNAPSHOT/javadoc-api/";
	
	public static void main(String[] args) {
		final ExecutorService downloadThreadPool = Executors.newFixedThreadPool(30);
		final ExecutorService parseThreadPool = Executors.newFixedThreadPool(30);
		
		try {
			Consts.HOST = UrlUtils.getHost(indexUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HtmlInfo indexHtmlInfo = new HtmlInfo("", indexUrl);
		try {
			Consts.DOWNLOAD_QUENE.put(indexHtmlInfo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		downloadThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				while(true){
					HtmlInfo htmlInfo = null;
					try {
						htmlInfo = Consts.DOWNLOAD_QUENE.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					downloadThreadPool.submit(new DownloadTask(htmlInfo));
				}
			}
		});
		
		parseThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				while(true){
					LocalHtmlInfo localHtmlInfo = null;
					try {
						localHtmlInfo = Consts.PARSE_QUENE.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					parseThreadPool.submit(new ParseTask(localHtmlInfo));
				}
			}
		});
		
		parseThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				while(true){
					int prevCounter = Consts.getCounter();
					try {
						TimeUnit.SECONDS.sleep(60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(prevCounter == Consts.getCounter()){
						System.exit(0);
						break;
					}
					else{
						System.out.println(Consts.getCounter());
					}
				}
			}
		});
	}
}
