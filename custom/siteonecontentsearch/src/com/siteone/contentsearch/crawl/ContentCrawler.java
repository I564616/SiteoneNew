/**
 *
 */
package com.siteone.contentsearch.crawl;

import java.util.Map;


/**
 * @author 965504
 *
 */
public interface ContentCrawler
{
	Map<String, String> getCrawledContentForUrl(String url);
}
