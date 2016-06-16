package crawler.query;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import crawler.datamodel.CrawlerDataModel;

public class QueryInterface implements Runnable {

	public void run() {
		while (true) {
			try {
				//System.out.println("1. Find");
//				System.out.println("1. ListInvalid");
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				String text = bufferRead.readLine();
//				String command = text.sub
				System.out.println(CrawlerDataHolder.queryUrlsByContent(text));
			} catch (Exception ex) {

			}
		}
	}
}
