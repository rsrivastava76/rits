package crawler.query;

import java.util.List;

public interface Query {

	public List<String> queryUrlsByContent(String content);
}
