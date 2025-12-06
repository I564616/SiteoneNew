/**
 *
 */
package com.siteone.contentsearch.crawl.impl;

import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.siteone.contentsearch.crawl.ContentCrawler;
import com.siteone.contentsearch.tika.TikaEngineDomParser;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultContentCrawler implements ContentCrawler
{
	private static final String TAGS_TO_EXTRACT = "";

	private static final Logger LOG = Logger.getLogger(DefaultContentCrawler.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "tikaParser")
	private TikaEngineDomParser tikaParser;

	@Override
	public Map<String, String> getCrawledContentForUrl(final String url)
	{
		Map<String, String> siteCookies = null;
		final Map<String, String> contentMap = new HashMap<String, String>();

		try
		{
			final String crawlHompageUrl = configurationService.getConfiguration().getString("contentsearch.crawl.hompageurl");
			final String crawlContextPath = configurationService.getConfiguration().getString("contentsearch.crawl.contextpathurl");
			final String tagsToRemove = configurationService.getConfiguration().getString("contentsearch.remove.tags","header,head,footer,form,select,input,script,style,.hidden");

			siteCookies = tikaParser.getSiteCookiesToCrawl(crawlHompageUrl);

			final String crawlUrl = crawlContextPath + url;

			//TagsToExtract
			final Set tagToExtractSet = new HashSet();

			//Add default tags to extract
			tagToExtractSet.add("div");
			tagToExtractSet.add("span");
			tagToExtractSet.add("H1");
			tagToExtractSet.add("H2");
			tagToExtractSet.add("H3");
			tagToExtractSet.add("H4");
			tagToExtractSet.add("H5");
			tagToExtractSet.add("P");
			tagToExtractSet.add("B");
			tagToExtractSet.add("A");

			//Add tags to extract from DB
			if (TAGS_TO_EXTRACT != null && !TAGS_TO_EXTRACT.equalsIgnoreCase(""))
			{
				String[] tagArray = new String[100];
				if (TAGS_TO_EXTRACT.contains(","))
				{
					tagArray = TAGS_TO_EXTRACT.split(",");
				}
				else
				{
					tagArray = new String[]
					{ TAGS_TO_EXTRACT };
				}
				for (final String tag : tagArray)
				{
					tagToExtractSet.add(tag);
				}
			}

			final Document parsedDoc = tikaParser.parseDomUsingJSoup(crawlUrl, siteCookies);

			String pageTitle = parsedDoc.title();
			contentMap.put("title",pageTitle);
			//Removing unwanted elements from the parsed html page
			parsedDoc.select(tagsToRemove).remove();

			//Extract html elements for tags
			final Iterator tagItr = tagToExtractSet.iterator();
			while (tagItr.hasNext())
			{
				final String tag = (String) tagItr.next();
				final Elements elements = extractHtmlElements(tag, parsedDoc);

				addContentToMap(contentMap, "content", elements);

			}

			return contentMap;

		}
		catch (final Exception e)
		{
			LOG.error(e);
		}

		return contentMap;
	}

	private Elements extractHtmlElements(final String elementTag, final Document doc)
	{
		return doc.select(elementTag);
	}

	private Map addContentToMap(final Map contentMap, final String key, final Elements elements)
	{
		String content = "";
		for (final Element element : elements)
		{
			content = content + " " + element.text();
		}
		final String mapContent = (String) contentMap.get(key);
		if (null != mapContent)
		{
			content = mapContent + " " + content;
		}
		contentMap.put(key, content);
		return contentMap;
	}

}
