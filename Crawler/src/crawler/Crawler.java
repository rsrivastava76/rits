package crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.datamodel.CrawlerDataModel;
import crawler.persistence.CachedPersister;
import crawler.persistence.Persister;
import crawler.query.CrawlerDataHolder;
import crawler.query.QueryInterface;

public class Crawler {

	private static final String ROOT = "/";

	private static final String A_HREF = "a[href]";

	private static final String HREF = "href";

	private static final String BASE_URL = "http://news.google.com";

	private Persister persister = new CachedPersister();
	
	private ExecutorService executorService = Executors.newFixedThreadPool(20);

	private BlockingQueue<CrawlerDataModel> blockingQueue = new LinkedBlockingQueue<>();

	private Thread queryInterfaceThread = new Thread(new QueryInterface());

	private Thread contentExtractorThread = new Thread(new ContentExtractor(
			blockingQueue, persister));

	public static void main(String[] args) {
		new Crawler().crawl();
	}

	private void crawl() {
		contentExtractorThread.start();
		queryInterfaceThread.start();
		try {
			Document doc = Jsoup.connect(BASE_URL).get();
			blockingQueue.put(new CrawlerDataModel(BASE_URL, doc.html()));
			Elements links = doc.select(A_HREF);
			for (final Element link : links) {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						String hyperlink = null; 
						try {
							hyperlink = link.attr(HREF);
							if (hyperlink.startsWith(ROOT)) {
								hyperlink = BASE_URL + hyperlink;
							}
							
							Document doc = Jsoup.connect(hyperlink).get();
							blockingQueue.put(new CrawlerDataModel(hyperlink, doc.html()));
						} catch (Exception e) {
							CrawlerDataHolder.addErroredUrls(hyperlink);
						}
					}
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
