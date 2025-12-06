/**
 *
 */
package com.siteone.facades.news.impl;

import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneNewsModel;
import com.siteone.core.news.service.SiteOneNewsService;
import com.siteone.facade.NewsData;
import com.siteone.facades.news.SiteOneNewsFacade;


/**
 * @author 191179
 *
 */
public class DefaultSiteOneNewsFacade implements SiteOneNewsFacade
{
	@Resource(name = "siteOneNewsService")
	private SiteOneNewsService siteOneNewsService;

	@Resource(name = "siteoneNewsConverter")
	private Converter<SiteOneNewsModel, NewsData> siteoneNewsConverter;

	@Override
	public NewsData getNewsByCode(final String newsCode)
	{
		final SiteOneNewsModel newsModel = siteOneNewsService.getNewsByCode(newsCode);
		return siteoneNewsConverter.convert(newsModel);
	}
	
	@Override
	public List<NewsData> getNewsDataList(List<SiteOneNewsModel> newsModelList)
	{
		List<NewsData> newsDataList = new ArrayList<>();
		
		for(SiteOneNewsModel newsModel:newsModelList){
			NewsData newsData = siteoneNewsConverter.convert(newsModel);
			newsDataList.add(newsData);
		}
		return newsDataList;
	}
}
