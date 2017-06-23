package task;

import model.LocalHtmlInfo;
import task.business.impl.ParseHtml;

public class ParseTask implements Runnable{
	private LocalHtmlInfo localHtmlInfo;
	
	public ParseTask(LocalHtmlInfo localHtmlInfo) {
		super();
		this.localHtmlInfo = localHtmlInfo;
	}

	@Override
	public void run() {
		ParseHtml.getInstance().parse(localHtmlInfo);
	}
}
