/**
 *
 */
package com.siteone.core.news.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneNewsModel;
import com.siteone.core.news.dao.SiteOneNewsDao;
import com.siteone.core.news.service.SiteOneNewsService;


/**
 * @author 191179
 *
 */
public class DefaultSiteOneNewsService implements SiteOneNewsService
{
	@Resource(name = "siteOneNewsDao")
	private SiteOneNewsDao siteOneNewsDao;


	@Override
	public SiteOneNewsModel getNewsByCode(final String newsCode)
	{
		final List<SiteOneNewsModel> news = siteOneNewsDao.findNewsByCode(newsCode);
		if (news != null && !news.isEmpty())
		{
			return news.get(0);
		}
		else
		{
			return null;
		}
	}
}
