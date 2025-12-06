package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.siteone.facade.InspirationData;
import com.siteone.facades.inspire.SiteOneInspirationFacade;


@Controller
@RequestMapping("/inspire")
public class InspirationPageController extends AbstractSearchPageController
{

	private static final Logger LOG = Logger.getLogger(InspirationPageController.class);

	private static final String INSPIRATION_CODE_PATH_VARIABLE_PATTERN = "{inspirationCode:.*}";
	//private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";


	@Resource(name = "siteOneInspirationFacade")
	private SiteOneInspirationFacade siteOneInspirationFacade;


	@GetMapping
	public String getInspirationGalleryPage(final Model model, @RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "pageSize", defaultValue = "24") int pageSize,
			@RequestParam(value = "sortOrder", defaultValue = "FEATURED") final String sortOrder) throws CMSItemNotFoundException // NOSONAR
	{
		String sortCode = null;


		if (sortOrder.equals("DESC") || sortOrder.equals("FEATURED"))
		{
			sortCode = "date-desc";
		}
		else
		{
			sortCode = "date-asc";
		}


		SearchPageData<InspirationData> searchPageData = null;
		try
		{
			if (pageSize <= 24)
			{
				pageSize = 24;
			}
			else if (pageSize > 24 && pageSize <= 48)
			{
				pageSize = 48;
			}
			else if (pageSize > 48)
			{
				pageSize = 72;
			}
			//pageSize = Config.getString("inspiration.per.page", pageSize);
			final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);
			searchPageData = siteOneInspirationFacade.inspirationSearch(pageableData);
		}
		catch (final ConversionException e) // NOSONAR
		{
			LOG.error(e);
		}

		populateModelForEvent(model, searchPageData, ShowMode.Page);
		
		long startInspiration = (page*pageSize) + Long.valueOf(1);
		long endInspiration = (page+1) * Long.valueOf(pageSize);
		if(endInspiration > (searchPageData.getPagination() != null ? searchPageData.getPagination().getTotalNumberOfResults() : 0))
		{
			endInspiration = searchPageData.getPagination() != null ? searchPageData.getPagination().getTotalNumberOfResults() : 0;
		}
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNumber", page);
		model.addAttribute("startInspiration", startInspiration);
		model.addAttribute("endInspiration", endInspiration);
		model.addAttribute("sortBy", sortOrder);

		storeCmsPageInModel(model, getContentPageForLabelOrId("inspirationPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("inspirationPage"));

		return "pages/inspire/inspirationPage";
	}

	@GetMapping("/" + INSPIRATION_CODE_PATH_VARIABLE_PATTERN)
	public String getInspirationListByCode(@PathVariable("inspirationCode") final String inspirationCode, final Model model)
			throws CMSItemNotFoundException
	{
		final InspirationData inspirationData = siteOneInspirationFacade.getInspirationListByCode(inspirationCode);
		model.addAttribute("inspirationData", inspirationData);

		storeCmsPageInModel(model, getContentPageForLabelOrId("inspirationDetailPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("inspirationDetailPage"));

		return "pages/inspire/inspirationDetailPage";
	}

	private void populateModelForEvent(final Model model, final SearchPageData<InspirationData> searchPageData,
			final ShowMode showMode)
	{
		//final int numberPagesShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT,Integer.parseInt(Config.getString("inspiration.per.page", "2")));
		model.addAttribute("inspiration_numberPagesShown", searchPageData.getPagination().getNumberOfPages());
		model.addAttribute("inspirationPageData", searchPageData);
		model.addAttribute("inspiration_isShowAllAllowed", calculateShowAll(searchPageData, showMode));
		model.addAttribute("inspiration_isShowPageAllowed", calculateShowPaged(searchPageData, showMode));
		model.addAttribute("inspiration_total", searchPageData.getPagination().getTotalNumberOfResults());
	}
}
