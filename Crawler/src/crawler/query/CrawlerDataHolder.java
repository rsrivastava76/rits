package crawler.query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import crawler.datamodel.CrawlerDataModel;

public class CrawlerDataHolder {

	private static final Map<String, String> crawledUrls = new ConcurrentHashMap<>();
	private static final Set<String> erroredUrls = new HashSet<>();
	
	private static final Map<String, List<String>> queryCache = new HashMap<>();
	
	public static final void updateCrawledUrls(CrawlerDataModel crawlerDataModel) {
		crawledUrls.put(crawlerDataModel.getUrl(), crawlerDataModel.getInternalData());
		//System.out.println(crawlerDataModel.getUrl());
	}

	public static final Map<String, String> getCrawledUrls() {
		return crawledUrls;
	}
	
	public static final List<String> queryUrlsByContent(String content) {
		List<String> cachedUrls = queryCache.get(content);
		if (cachedUrls == null) {
			synchronized (cachedUrls) {
				if (cachedUrls == null) {
					cachedUrls = new LinkedList<>();
				}
			}
			cachedUrls = queryCache.get(content);
			for (Map.Entry<String, String> entry: crawledUrls.entrySet()) {
				if (entry.getValue().contains(content.toLowerCase())) {
					cachedUrls.add(entry.getKey());
				}
			}
		}
		return cachedUrls;
	}
	
	public static final Set<String> erroredUrls() {
		return erroredUrls;
	}

	public static final void addErroredUrls(String url) {
		erroredUrls.add(url);
	}
}
