package crawler.persistence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import crawler.datamodel.CrawlerDataModel;

public class CachedPersister implements Persister {

	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	@Override
	public void persist(CrawlerDataModel crawlerDataModel) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	
}
