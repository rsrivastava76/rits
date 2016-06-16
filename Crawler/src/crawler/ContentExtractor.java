package crawler;

import java.util.concurrent.BlockingQueue;

import crawler.datamodel.CrawlerDataModel;
import crawler.persistence.Persister;
import crawler.query.CrawlerDataHolder;

public class ContentExtractor implements Runnable {

	private BlockingQueue<CrawlerDataModel> blockingQueue;
	private Persister persister;
	
	public ContentExtractor(BlockingQueue<CrawlerDataModel> blockingQueue, Persister persister) {
		this.blockingQueue = blockingQueue;
		this.persister = persister;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				CrawlerDataModel crawlerDataModel = blockingQueue.take();
				CrawlerDataHolder.updateCrawledUrls(crawlerDataModel);
				persister.persist(crawlerDataModel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
