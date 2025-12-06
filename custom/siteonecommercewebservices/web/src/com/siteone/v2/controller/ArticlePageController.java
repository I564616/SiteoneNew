package com.siteone.v2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;

import com.siteone.facades.content.search.facade.ContentSearchFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.v2.controller.SiteOneProductSearchController.ShowMode;
import com.siteone.contentsearch.ContentSearchPageWsDTO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Resource;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchFilterQueryData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Controller
@RequestMapping(value = "/{baseSiteId}/articles")
@Tag(name = "Articles")
public class ArticlePageController extends AbstractSearchPageController {

	private static final String ARTICLE_PAGE = "ARTICLE_PAGE";
	private static final String SEO_ARTICLE_PAGE_TYPE_INDEX = "soContentType";
	private static final String SEO_CATEGORIES_INDEX = "soArticleCategory";
	private static final String EXCEPTION = "Exception occurred while calling the method";

	private static final Logger LOG = Logger.getLogger(ArticlePageController.class);

	@Resource(name = "contentSearchFacade")
	private ContentSearchFacade<ContentData> contentSearchFacade;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@GetMapping
	@ResponseBody
	@Operation(operationId = "getArticles", summary = "Get all the articles", description = "Returns all the articles.")
	@ApiBaseSiteIdParam
	public ContentSearchPageWsDTO getArticleLandingPage(
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields) {
		try {
		final PageableData pageableData = createPageableData(page, getArticlePageSize(), sortCode, showMode);
		// Set up solr requst for articles
		final SearchStateData searchState = setUpArticleSearchRequest(SEO_ARTICLE_PAGE_TYPE_INDEX, ARTICLE_PAGE);

		ContentSearchPageData<SearchStateData, ContentData> searchPageData = null;
		searchPageData = contentSearchFacade.articleContentSearch(searchState, pageableData);

		ContentSearchPageWsDTO contentSearchPageDTO = getDataMapper().map(searchPageData, ContentSearchPageWsDTO.class, fields);
		return contentSearchPageDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getArticleLandingPage");
		}
	}

	protected int getArticlePageSize() {
		return siteConfigService.getInt("mobileapp.articles.pageSize", 10);
	}

	private SearchStateData setUpArticleSearchRequest(final String searchIndexParam, final String searchParam) {
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		final List<SearchFilterQueryData> filterQueries = new ArrayList<>();
		final SearchFilterQueryData searchFilterQueryData = new SearchFilterQueryData();
		// set where key
		searchFilterQueryData.setKey(searchIndexParam);
		// set values for search
		final Set<String> input = new HashSet<>();
		input.add(searchParam);
		searchFilterQueryData.setValues(input);
		filterQueries.add(searchFilterQueryData);

		searchQueryData.setFilterQueries(filterQueries);
		searchState.setQuery(searchQueryData);
		return searchState;
	}


	@GetMapping("/getArticlesByCategory/{articleCategoryCode}")
	@ResponseBody
	@Operation(operationId = "getArticlesByCategory", summary = "Get the articles by category", description = "Returns the articles by category.")
	@ApiBaseSiteIdParam
	public ContentSearchPageWsDTO getArticleCategoryPage(
			@PathVariable("articleCategoryCode") final String articleCategoryCode,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields) {
		try {
		final PageableData pageableData = createPageableData(page, getArticlePageSize(), null, showMode);

		// Set up solr requst for articles
		ContentSearchPageData<SearchStateData, ContentData> searchPageData = null;
		final SearchStateData searchState = setUpArticleSearchRequest(SEO_CATEGORIES_INDEX, articleCategoryCode);
		searchPageData = contentSearchFacade.articleContentSearch(searchState, pageableData);
		ContentSearchPageWsDTO contentSearchPageDTO = getDataMapper().map(searchPageData, ContentSearchPageWsDTO.class, fields);
		return contentSearchPageDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getArticleCategoryPage");
		}
	}

}