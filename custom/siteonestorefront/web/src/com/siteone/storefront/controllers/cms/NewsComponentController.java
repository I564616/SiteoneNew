/**
 *
 */
package com.siteone.storefront.controllers.cms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOneNewsModel;
import com.siteone.core.model.components.NewsComponentModel;
import com.siteone.facade.NewsData;
import com.siteone.facades.news.SiteOneNewsFacade;
import com.siteone.storefront.controllers.ControllerConstants;


@Controller("NewsComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.NewsComponent)
public class NewsComponentController extends AbstractAcceleratorCMSComponentController<NewsComponentModel>
{

	@Resource(name = "siteOneNewsFacade")
	private SiteOneNewsFacade siteOneNewsFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final NewsComponentModel component)
	{
		List<NewsData> newsDataList = new ArrayList<>();
		final List<SiteOneNewsModel> newsList = component.getNewsList();

		newsDataList = siteOneNewsFacade.getNewsDataList(newsList);

		Collections.sort(newsDataList, new SortbyPubDate());
		model.addAttribute("newsDataList", newsDataList);
	}

}

class SortbyPubDate implements Comparator<NewsData>
{
	// Used for sorting in descending order of
	// news publication date
	@Override
	public int compare(final NewsData a, final NewsData b)
	{
		if (a.getNewsPublishDate().getTime() > b.getNewsPublishDate().getTime())
		{
			return -1;
		}
		else if (a.getNewsPublishDate().getTime() < b.getNewsPublishDate().getTime())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
