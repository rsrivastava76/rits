package crawler.persistence;

import crawler.datamodel.CrawlerDataModel;

public interface Persister {

	public void persist(CrawlerDataModel crawlerDataModel);
}
