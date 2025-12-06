/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commercefacades.search.data.SearchFilterQueryData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.facades.article.populators.SiteOneArticleCategoryPopulator;
import com.siteone.facades.content.search.facade.ContentSearchFacade;
import com.siteone.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping("/articles")
public class ArticlePageController extends AbstractSearchPageController
{
	@Resource(name = "i18nService")
	private I18NService i18nService;
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	/**
	 * @return the i18nService
	 */
	@Override
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	@Override
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}


	private static final Logger LOG = Logger.getLogger(ArticlePageController.class);
	private static final String ARTICLE_CODE_PATH_VARIABLE = "{articleCode:.*}";
	private static final String ARTICLE_CATEGORY_PATH_VARIABLE = "{articleCategoryCode:.*}";
	private static final String ARTICLE = "article/";
	private static final String ARTICLE_LANDING = "articleLandingPage";
	private static final String ARTICLE_CATEGORY = "articleCategoryPage";
	private static final String ARTICLE_DETAILS = "articleDetailsPage";
	private static final String ARTICLE_PAGE = "ARTICLE_PAGE";
	private static final String ARTICLE_CA_PAGE = "ARTICLE_CA_PAGE";
	public static final String REDIRECT_PREFIX = "redirect:";

	private static final String SEO_INDEX_ENV = "storefront.seo.index.env";
	//solr content search
	private static final String SEO_CATEGORIES_INDEX = "soArticleCategory";
	private static final String SEO_ARTICLE_PAGE_TYPE_INDEX = "soContentType";


	@Resource(name = "contentSearchFacade")
	private ContentSearchFacade<ContentData> contentSearchFacade;

	@Resource
	private CMSPageService cmsPageService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "siteoneArticleCategoryPopulator")
	private SiteOneArticleCategoryPopulator siteoneArticleCategoryPopulator;


	@GetMapping
	public String getArticleLandingPage(final Model model) throws CMSItemNotFoundException
	{
		final PageableData pageableData = createPageableData(0, getArticlePageSize(), null, ShowMode.All);

		ContentSearchPageData<SearchStateData, ContentData> searchPageData = null;
		setUpArticleBreadcrumb(model, null);
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		SearchStateData searchState = null;
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			// Set up solr requst for articles
			searchState = setUpArticleSearchRequest(SEO_ARTICLE_PAGE_TYPE_INDEX, ARTICLE_PAGE);
		}
		else
		{
			searchState = setUpArticleSearchRequest(SEO_ARTICLE_PAGE_TYPE_INDEX, ARTICLE_CA_PAGE);
		}
		searchPageData = contentSearchFacade.articleContentSearch(searchState, pageableData);
		model.addAttribute("searchPageData", searchPageData);

		final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
		if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		}
		else
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(ARTICLE_LANDING));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ARTICLE_LANDING));

		return ControllerConstants.Views.Pages.Article.ArticleLandingPage;
	}



	@GetMapping("/" + ARTICLE_CATEGORY_PATH_VARIABLE)
	public String getArticleCategoryPage(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, @PathVariable("articleCategoryCode")
	final String articleCategoryCode, final Model model) throws CMSItemNotFoundException
	{
		final ContentPageModel contentPageModel = (ContentPageModel) cmsPageService.getPageForId(articleCategoryCode.toLowerCase());
		if(articleCategoryCode.chars().anyMatch(Character::isUpperCase)) {
			final String labelUrl = contentPageModel != null ? contentPageModel.getLabel() : null;
			return REDIRECT_PREFIX + labelUrl;
		}

		final PageableData pageableData = createPageableData(page, this.getArticlePageSize(), sortCode, showMode);

		// Set up solr requst for articles
		ContentSearchPageData<SearchStateData, ContentData> searchPageData = null;
		final SearchStateData searchState = setUpArticleSearchRequest(SEO_CATEGORIES_INDEX, capitalizeWord(articleCategoryCode));

		searchPageData = contentSearchFacade.articleContentSearch(searchState, pageableData);
		siteoneArticleCategoryPopulator.populate(contentPageModel, searchPageData);

		model.addAttribute("searchPageData", searchPageData);

		final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
		if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		}
		else
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		}

		String fullPath = null;
		String pathingChannel = null;
		ContentData contentData = null;
		if (searchPageData != null && CollectionUtils.isNotEmpty(searchPageData.getResults()))
		{
			contentData = searchPageData.getResults().stream().findFirst().get();
		}
		fullPath = contentPageModel != null ? contentPageModel.getFullPagePath() : null;
		pathingChannel = contentPageModel != null ? contentPageModel.getPathingChannel() : null;
		getContentPageForLabelOrId(articleCategoryCode.toLowerCase()).setFullPagePath(fullPath);
		getContentPageForLabelOrId(articleCategoryCode.toLowerCase()).setPathingChannel(pathingChannel);
		setUpArticleBreadcrumb(model, contentData.getCategoryName());
		storeCmsPageInModel(model, getContentPageForLabelOrId(articleCategoryCode.toLowerCase()));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ARTICLE_CATEGORY));

		return getViewForPage(model);
	}
	
	@GetMapping("/" + ARTICLE + ARTICLE_CODE_PATH_VARIABLE)
	public String getArticlePageByCode(@PathVariable("articleCode")
	final String articleCode, final Model model) throws CMSItemNotFoundException
	{
		final ContentPageModel contentPageModel = (ContentPageModel) cmsPageService.getPageForId(articleCode.toLowerCase());
		final String labelUrl = contentPageModel != null ? contentPageModel.getLabel() : null;
		return REDIRECT_PREFIX + labelUrl;
	}

	@GetMapping("/" + ARTICLE_CATEGORY_PATH_VARIABLE + "/" + ARTICLE_CODE_PATH_VARIABLE)
	public String getArticlePageByCode(@PathVariable("articleCategoryCode")
	final String articleCategoryCode, @PathVariable("articleCode")
	final String articleCode,final Model model) throws CMSItemNotFoundException
	{
		final ContentPageModel contentPageForCategoryModel = (ContentPageModel) cmsPageService.getPageForId(articleCategoryCode);
		
		if(contentPageForCategoryModel != null) {
   		final ContentPageModel contentPageModel = (ContentPageModel) cmsPageService.getPageForId(articleCode);
   
   		final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
   		if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
   		{
   			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
   		}
   		else
   		{
   			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
   		}
   
   		final String fullPagePath = contentPageModel != null ? contentPageModel.getFullPagePath() : null;
   
   		getContentPageForLabelOrId(ARTICLE_DETAILS).setFullPagePath(fullPagePath);
   		getContentPageForLabelOrId(ARTICLE_DETAILS)
   				.setPathingChannel(contentPageModel != null ? contentPageModel.getPathingChannel() : null);
   		storeCmsPageInModel(model, getContentPageForLabelOrId(articleCode));
   		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(articleCode));
   
   		breadcrumbForArticleDetails(model, contentPageModel);
		}
		return getViewForPage(model);
	}


	/**
	 * @param model
	 * @param fullPagePath
	 */
	private void breadcrumbForArticleDetails(final Model model, final ContentPageModel contentPageModel)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		if (contentPageModel != null)
		{
			final String categoryCode = contentPageModel.getCategory() != null ? contentPageModel.getCategory().getCode() : null;
			final String categoryName = contentPageModel.getCategory() != null ? contentPageModel.getCategory().getName() : null;
			final Breadcrumb breadcrumbLearn = new Breadcrumb("/articles",
					getMessageSource().getMessage("breadcrumb.learn&Explore", null, getI18nService().getCurrentLocale()), null);

			final Breadcrumb breadCrumbCategory = new Breadcrumb("/articles".concat("/" + categoryCode.toLowerCase()), categoryName, null);
			final Breadcrumb breadArticle = new Breadcrumb("#", contentPageModel.getPreviewTitle(), null);
			breadcrumbs.add(breadcrumbLearn);
			breadcrumbs.add(breadCrumbCategory);
			breadcrumbs.add(breadArticle);
		}


		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
	}


	private void setUpArticleBreadcrumb(final Model model, final String articleCategoryName)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		if (articleCategoryName != null)
		{
			final Breadcrumb breadcrumbArticleCategory = new Breadcrumb("/articles",
					getMessageSource().getMessage("breadcrumb.learn&Explore", null, getI18nService().getCurrentLocale()), null);
			final Breadcrumb breadCrumbCategory = new Breadcrumb("#", articleCategoryName, null);
			breadcrumbs.add(breadcrumbArticleCategory);
			breadcrumbs.add(breadCrumbCategory);
		}
		else
		{
			final Breadcrumb breadcrumbArticleLanding = new Breadcrumb("/articles",
					getMessageSource().getMessage("breadcrumb.learn&Explore", null, getI18nService().getCurrentLocale()), null);
			breadcrumbs.add(breadcrumbArticleLanding);
		}

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

	}

	protected int getArticlePageSize()
	{
		return getSiteConfigService().getInt("siteone.article.pageSize", 34);
	}

	private SearchStateData setUpArticleSearchRequest(final String searchIndexParam, final String searchParam)
	{
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		final List<SearchFilterQueryData> filterQueries = new ArrayList<>();
		final SearchFilterQueryData searchFilterQueryData = new SearchFilterQueryData();
		//set where key
		searchFilterQueryData.setKey(searchIndexParam);
		//set values for search
		final Set<String> input = new HashSet<>();
		input.add(searchParam);
		searchFilterQueryData.setValues(input);
		filterQueries.add(searchFilterQueryData);

		searchQueryData.setFilterQueries(filterQueries);
		searchState.setQuery(searchQueryData);
		return searchState;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService the baseSiteService to set
	 */
	public void setBaseSiteService(BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}	
	private static String capitalizeWord(String str) {
		String words[] = str.split("-");
		String result = "";
		for(String w : words) {
			result = result + w.substring(0,1).toUpperCase() + w.substring(1) + "-";
		}
		return result.substring(0,(result.length() - 1));
	}
}