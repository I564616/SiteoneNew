/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.servicelayer.i18n.I18NService;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.storefront.util.SiteoneXSSFilterUtil;


/**
 * @author 1229803
 *
 */

@Controller
@RequestMapping("/sdssearch")
public class SiteOneSDSSearchPageController extends AbstractSearchPageController
{

	private static final String SDS_SEARCH_CMS_PAGE = "sds-search";
	private static final String SDS_SEARCH_BREADCRUMB = "breadcrumb.sdsSearch";


	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SearchPageController.class);

	@Resource(name = "siteOneSDSProductSearchFacade")
	private SiteOneSDSProductSearchFacade siteOneSDSProductSearchFacade;

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

	@GetMapping("/results")
	public String getSDSSearchPage(final Model model) throws CMSItemNotFoundException
	{
		//putting this log on temporary basis to test SE- 6690.
		LOG.error("UE PDF getSDSSearchPage call- ");
		final ContentPageModel contentPage = getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE);
		storeCmsPageInModel(model, contentPage);
		setUpMetaDataForContentPage(model, contentPage);

		setUpBreadcrumbData(model, contentPage);
		return getViewForPage(model);
	}


	@GetMapping( params = "q")
	public String sdsRefineSearch(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "text", required = false)
	final String searchText, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		Long sdsSearchCount = 0L;
		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performSearch(searchQuery, page, showMode,
				sortCode, getSearchPageSize());


		//putting this log on temporary basis to test SE- 6690.
		LOG.error("UE PDF sdsRefineSearch call- ");

		populateModel(model, searchPageData, showMode);


		if (searchPageData.getPagination().getTotalNumberOfResults() == 0)
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE));
			setUpBreadcrumbData(model, null);
			model.addAttribute("searchText", searchText);
		}
		else
		{
			final ContentPageModel contentPage = getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE);
			storeContinueUrl(request);
			storeCmsPageInModel(model, getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE));
			setUpBreadcrumbData(model, contentPage);
			model.addAttribute("searchPageData", searchPageData);
			model.addAttribute("searchText", searchText);
		}
		sdsSearchCount = searchPageData.getPagination().getTotalNumberOfResults();
		model.addAttribute("sdsSearchCount", sdsSearchCount);
		return getViewForPage(model);
	}

	@GetMapping( params = "query")
	public @ResponseBody ProductSearchPageData<SearchStateData, ProductData> sdsRefineSearchLazyLoading(@RequestParam("query")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "text", required = false)
	final String searchText, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		//putting this log on temporary basis to test SE- 6690.
		LOG.error("UE PDF sdsRefineSearchLazyLoading call- ");

		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performSearch(searchQuery, page, showMode,
				sortCode, getSearchPageSize());

		populateModel(model, searchPageData, showMode);


		if (searchPageData.getPagination().getTotalNumberOfResults() == 0)
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE));
			setUpBreadcrumbData(model, null);
		}
		else
		{
			final ContentPageModel contentPage = getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE);
			storeContinueUrl(request);
			storeCmsPageInModel(model, getContentPageForLabelOrId(SDS_SEARCH_CMS_PAGE));
			setUpBreadcrumbData(model, contentPage);
		}

		return searchPageData;
	}

	protected ProductSearchPageData<SearchStateData, ProductData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{

		//putting this log on temporary basis to test SE- 6690.
		LOG.error("UE PDF performSearch call- ");
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(SiteoneXSSFilterUtil.filter(searchQuery));
		searchState.setQuery(searchQueryData);

		return siteOneSDSProductSearchFacade.sdsTextSearch(searchState, pageableData);
	}

	protected void setUpBreadcrumbData(final Model model, final ContentPageModel contentPage)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		if (null != contentPage)
		{


			//putting this log on temporary basis to test SE- 6690.
			LOG.error("UE PDF setUpBreadcrumbData call- ");
			final List<CMSNavigationNodeModel> navigationNodes = contentPage.getNavigationNodeList();
			if (CollectionUtils.isNotEmpty(navigationNodes))
			{
				final String navigationNodeTitle = navigationNodes.get(0).getName();
				breadcrumbs.add(new Breadcrumb("#", navigationNodeTitle, null));
			}
			else
			{
				breadcrumbs.add(new Breadcrumb("#",
						getMessageSource().getMessage(SDS_SEARCH_BREADCRUMB, null, getI18nService().getCurrentLocale()), null));
			}
		}
		else
		{
			breadcrumbs.add(new Breadcrumb("#",
					getMessageSource().getMessage(SDS_SEARCH_BREADCRUMB, null, getI18nService().getCurrentLocale()), null));
		}
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
	}

}
