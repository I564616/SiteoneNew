/**
 *
 */
package com.siteone.contentsearch.tika;

import java.util.Map;

import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.nodes.Document;

import com.siteone.contentsearch.exception.TikaEngineException;



/**
 *
 * @author 965504
 *
 */
public interface TikaEngineDomParser
{
	public BodyContentHandler parseDom(String url) throws TikaEngineException;

	public Document parseDomUsingJSoup(final String url, final Map<String, String> siteCookies) throws TikaEngineException;

	public Map<String, String> getSiteCookiesToCrawl(final String url) throws TikaEngineException;

}
