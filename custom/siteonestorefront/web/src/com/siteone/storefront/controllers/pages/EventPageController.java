/**
 *
 */
package com.siteone.storefront.controllers.pages;


import de.hybris.platform.acceleratorfacades.device.DeviceDetectionFacade;
import de.hybris.platform.acceleratorfacades.device.data.DeviceData;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.util.MetaSanitizerUtil;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.site.BaseSiteService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

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

import com.sap.security.core.server.csi.XSSEncoder;
import com.siteone.facade.EventData;
import com.siteone.facade.EventSearchPageData;
import com.siteone.facades.events.EventSearchFacade;
import com.siteone.facades.events.SiteOneEventFacade;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * @author 1124932
 *
 */
@Controller
@RequestMapping("/events")
public class EventPageController extends AbstractSearchPageController
{
	private static final String SEARCH_META_DESCRIPTION_ON = "search.meta.description.on";
	private static final String SEARCH_META_DESCRIPTION_RESULTS = "search.meta.description.results";
	private static final String FACET_SEPARATOR = ":";
	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String EVENT_CODE_PATH_VARIABLE_PATTERN = "{eventCode:.*}";

	@Resource(name = "eventSearchFacade")
	private EventSearchFacade eventSearchFacade;

	@Resource(name = "siteOneEventFacade")
	private SiteOneEventFacade siteOneEventFacade;

	@Resource(name = "deviceDetectionFacade")
	private DeviceDetectionFacade deviceDetectionFacade;
	@Resource(name = "i18nService")
	private I18NService i18nService;
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

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

	private static final Logger LOG = Logger.getLogger(EventPageController.class);


	@GetMapping( params = "!q")
	public String getEventPage(final Model model, @RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode) throws CMSItemNotFoundException // NOSONAR
	{
		final String sortCode = "date-asc";
		final String searchText = "";
		EventSearchPageData<SearchStateData, EventData> searchPageData = null;
		try
		{
			searchPageData = performSearch(searchText, page, showMode, sortCode, getSearchPageSize());
			if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca")) {
				searchPageData.getResults().removeIf(event -> event.getDivision().equalsIgnoreCase("US"));
			}
			if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
				searchPageData.getResults().removeIf(event -> event.getDivision().equalsIgnoreCase("CA"));
			}		
		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}
		model.addAttribute("eventSearchPageData", searchPageData);

		populateModelForEvent(model, searchPageData, ShowMode.Page);
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		final Breadcrumb breadcrumb = new Breadcrumb("#",
				getMessageSource().getMessage("breadcrumb.toolAndResources", null, getI18nService().getCurrentLocale()), null);
		breadcrumbs.add(breadcrumb);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
		final DeviceData deviceData = deviceDetectionFacade.getCurrentDetectedDevice();
		model.addAttribute("deviceData", deviceData);
		storeCmsPageInModel(model, getContentPageForLabelOrId("siteOneEventPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("siteOneEventPage"));
		return ControllerConstants.Views.Pages.Event.SiteOneEventPage;

	}


	@GetMapping( params = "q")
	public String refineSearch(@RequestParam(value = "q") final String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) String sortCode,
			@RequestParam(value = "text", required = false) final String searchText, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		EventSearchPageData<SearchStateData, EventData> searchPageData = null;
		sortCode = "date-asc";

		try
		{
			searchPageData = performSearch(searchQuery, page, showMode, sortCode, getSearchPageSize());
		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}

		populateModelForEvent(model, searchPageData, showMode);
		if (null != searchPageData)
		{
			if (searchPageData.getPagination().getTotalNumberOfResults() == 0)
			{
				updatePageTitle(searchPageData.getFreeTextSearch(), model);
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
			}
			else
			{
				storeContinueUrl(request);
				updatePageTitle(searchPageData.getFreeTextSearch(), model);
				storeCmsPageInModel(model, getContentPageForLabelOrId("siteOneEventPage"));
			}
		}

		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		final Breadcrumb breadcrumb = new Breadcrumb("#",
				getMessageSource().getMessage("breadcrumb.toolAndResources", null, getI18nService().getCurrentLocale()), null);
		breadcrumbs.add(breadcrumb);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
		final DeviceData deviceData = deviceDetectionFacade.getCurrentDetectedDevice();
		model.addAttribute("deviceData", deviceData);
		//populateModelForEvent(model, searchPageData, ShowMode.Page);
		final String metaDescription = MetaSanitizerUtil
				.sanitizeDescription(getMessageSource().getMessage(SEARCH_META_DESCRIPTION_RESULTS, null,
						SEARCH_META_DESCRIPTION_RESULTS, getI18nService().getCurrentLocale()) + " " + searchText + " "
						+ getMessageSource().getMessage(SEARCH_META_DESCRIPTION_ON, null, SEARCH_META_DESCRIPTION_ON,
								getI18nService().getCurrentLocale())
						+ " " + getSiteName());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(searchText);
		setUpMetaData(model, metaKeywords, metaDescription);
		storeCmsPageInModel(model, getContentPageForLabelOrId("siteOneEventPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("siteOneEventPage"));
		return ControllerConstants.Views.Pages.Event.SiteOneEventPage;
	}

	protected EventSearchPageData<SearchStateData, EventData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return encodeSearchPageData(eventSearchFacade.eventSearch(searchState, pageableData));
	}

	protected EventSearchPageData<SearchStateData, EventData> encodeSearchPageData(
			final EventSearchPageData<SearchStateData, EventData> searchPageData)
	{
		final SearchStateData currentQuery = searchPageData.getCurrentQuery();

		if (currentQuery != null)
		{
			try
			{
				final SearchQueryData query = currentQuery.getQuery();
				final String encodedQueryValue = XSSEncoder.encodeHTML(query.getValue());
				query.setValue(encodedQueryValue);
				currentQuery.setQuery(query);
				searchPageData.setCurrentQuery(currentQuery);
				searchPageData.setFreeTextSearch(XSSEncoder.encodeHTML(searchPageData.getFreeTextSearch()));

				final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
				if (CollectionUtils.isNotEmpty(facets))
				{
					processFacetData(facets);
				}
			}
			catch (final UnsupportedEncodingException e)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug("Error occured during Encoding the Search Page data values", e);
				}
			}
		}
		return searchPageData;
	}

	protected void processFacetData(final List<FacetData<SearchStateData>> facets) throws UnsupportedEncodingException
	{
		for (final FacetData<SearchStateData> facetData : facets)
		{
			final List<FacetValueData<SearchStateData>> topFacetValueDatas = facetData.getTopValues();
			if (CollectionUtils.isNotEmpty(topFacetValueDatas))
			{
				processFacetDatas(topFacetValueDatas);
			}
			final List<FacetValueData<SearchStateData>> facetValueDatas = facetData.getValues();
			if (CollectionUtils.isNotEmpty(facetValueDatas))
			{
				processFacetDatas(facetValueDatas);
			}
		}
	}

	protected void processFacetDatas(final List<FacetValueData<SearchStateData>> facetValueDatas)
			throws UnsupportedEncodingException
	{
		for (final FacetValueData<SearchStateData> facetValueData : facetValueDatas)
		{
			final SearchStateData facetQuery = facetValueData.getQuery();
			final SearchQueryData queryData = facetQuery.getQuery();
			final String queryValue = queryData.getValue();
			if (StringUtils.isNotBlank(queryValue))
			{
				final String[] queryValues = queryValue.split(FACET_SEPARATOR);
				final StringBuilder queryValueBuilder = new StringBuilder();
				queryValueBuilder.append(XSSEncoder.encodeHTML(queryValues[0]));
				for (int i = 1; i < queryValues.length; i++)
				{
					queryValueBuilder.append(FACET_SEPARATOR).append(queryValues[i]);
				}
				queryData.setValue(queryValueBuilder.toString());
			}
		}
	}

	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(
				getMessageSource().getMessage("search.meta.title", null, "search.meta.title", getI18nService().getCurrentLocale())
						+ " " + searchText));
	}


	protected void populateModelForEvent(final Model model, final EventSearchPageData searchPageData, final ShowMode showMode)
	{
		final int numberPagesShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
		model.addAttribute("event_numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("eventSearchPageData", searchPageData);
		model.addAttribute("event_isShowAllAllowed", calculateShowAll(searchPageData, showMode));
		model.addAttribute("event_isShowPageAllowed", calculateShowPaged(searchPageData, showMode));
	}

	@GetMapping("/" + EVENT_CODE_PATH_VARIABLE_PATTERN)
	public String getEventByCode(@PathVariable("eventCode") final String eventCode, final Model model)
			throws CMSItemNotFoundException
	{
		final EventData eventData = siteOneEventFacade.getEventByCode(eventCode);

		model.addAttribute("eventData", eventData);

		setUpEventBreadcrumb(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId("siteOneEventDetailPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("siteOneEventDetailPage"));
		return ControllerConstants.Views.Pages.Event.SiteOneEventDetailPage;

	}

	private void setUpEventBreadcrumb(final Model model)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		final Breadcrumb breadcrumbTools = new Breadcrumb("#",
				getMessageSource().getMessage("breadcrumb.toolAndResources", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbEvents = new Breadcrumb("/events",
				getMessageSource().getMessage("breadcrumb.events", null, getI18nService().getCurrentLocale()), null);
		breadcrumbs.add(breadcrumbTools);
		breadcrumbs.add(breadcrumbEvents);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

	}
}







