package crawler.datamodel;

public class CrawlerDataModel {

	private final String url;
	private final String data;
	private final String internalData;
	
	public CrawlerDataModel(String url, String data) {
		super();
		this.url = url;
		this.data = data;
		this.internalData = data.toLowerCase();
	}

	public String getUrl() {
		return url;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "CrawlerDataModel [url=" + url + "]";
	}

	public String getInternalData() {
		return internalData;
	}
}
