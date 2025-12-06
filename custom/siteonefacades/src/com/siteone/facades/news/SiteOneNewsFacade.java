/**
 *
 */
package com.siteone.facades.news;

import java.util.List;

import com.siteone.core.model.SiteOneNewsModel;
import com.siteone.facade.NewsData;


/**
 * @author 191179
 *
 */
public interface SiteOneNewsFacade
{
	public NewsData getNewsByCode(final String newsCode);

	public List<NewsData> getNewsDataList(List<SiteOneNewsModel> newsModelList);
}
