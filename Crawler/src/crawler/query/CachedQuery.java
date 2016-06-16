package crawler.query;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class CachedQuery implements Runnable, Query {

	@Override
	public List<String> queryUrlsByContent(String content) {
		return CrawlerDataHolder.queryUrlsByContent(content);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("1. Query");
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				String text = bufferRead.readLine();
				String content = text.substring(text.indexOf(" "));
				System.out.println(CrawlerDataHolder.queryUrlsByContent(content));
			} catch (Exception ex) {

			}
		}
	}

}
