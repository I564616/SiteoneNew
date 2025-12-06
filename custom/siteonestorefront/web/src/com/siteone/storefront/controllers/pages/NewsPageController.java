package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.facade.NewsData;
import com.siteone.facades.news.SiteOneNewsFacade;


@Controller
@RequestMapping("/news")
public class NewsPageController extends AbstractSearchPageController
{
	private static final String NEWS_CODE_PATH_VARIABLE_PATTERN = "{newsCode:.*}";

	@Resource(name = "siteOneNewsFacade")
	private SiteOneNewsFacade siteOneNewsFacade;

	@GetMapping( params = "!q")
	public String getNewsPage(final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId("pressRoomPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("pressRoomPage"));

		return "pages/news/pressRoomPage";
	}

	@GetMapping("/" + NEWS_CODE_PATH_VARIABLE_PATTERN)
	public String getNewsByCode(@PathVariable("newsCode") final String newsCode, final Model model) throws CMSItemNotFoundException
	{
		final NewsData newsData = siteOneNewsFacade.getNewsByCode(newsCode);
		model.addAttribute("newsData", newsData);


		storeCmsPageInModel(model, getContentPageForLabelOrId("pressRoomDetailPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("pressRoomDetailPage"));

		return "pages/news/pressRoomDetailPage";
	}

}
