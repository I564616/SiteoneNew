/**
 *
 */
package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.exceptions.FlexibleSearchException;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.FindDuplicateSequenceCronJobDao;


/**
 * @author LR03818
 *
 */
public class DefaultFindDuplicateSequenceCronJobDao extends AbstractItemDao implements FindDuplicateSequenceCronJobDao
{
	private static final Logger logger = Logger.getLogger(DefaultFindDuplicateSequenceCronJobDao.class);

	private CatalogVersionService catalogVersionService;
	private static final String CATALOGID = "siteoneProductCatalog";
	private static final String CATALOGVERSIONNAME = "Online";
	private static final String CATALOGVERSION = "catalogVersion";

	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	@Override
	public List<List<Object>> getVariantCategoriesWithDublicateSequence()
	{
		try
		{
			final FlexibleSearchQuery query = new FlexibleSearchQuery(
					"select {vc.code}, {vvc.sequence}, count({vvc.pk}) from {CATEGORYCATEGORYRELATION AS CCR}, {VariantCategory as vc}, {VariantValueCategory as vvc} "
							+ "where {vc.catalogversion}=?catalogVersion and {vvc.catalogversion}=?catalogVersion and {CCR.source}={vc.pk} and {CCR.target}={vvc.pk} "
							+ "group by {vc.code}, {vvc.sequence} having count({vvc.pk})>1");
			query.addQueryParameter(CATALOGVERSION, getCatalogVersionService().getCatalogVersion(CATALOGID, CATALOGVERSIONNAME));
			query.setResultClassList(Arrays.asList(String.class, String.class, Integer.class));
			final SearchResult<List<Object>> searchResult = getFlexibleSearchService().search(query);
			if (null != searchResult && searchResult.getCount() != 0)
			{
				return searchResult.getResult();
			}
		}
		catch (final FlexibleSearchException e)
		{
			logger.error("error in executing query to find duplicate sequence - ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public List<VariantValueCategoryModel> getVariantValueCategoryBySequence(final String variantCategoryCode,
			final String sequence)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {vvc.pk} from {CATEGORYCATEGORYRELATION AS CCR}, {VariantCategory as vc}, {VariantValueCategory as vvc} "
						+ "where {vc.catalogversion}=?catalogVersion and {vvc.catalogversion}=?catalogVersion and {CCR.source}={vc.pk} "
						+ "and {CCR.target}={vvc.pk} and {vc.code}=?variantCategoryCode and {vvc.sequence}=?sequence");
		query.addQueryParameter(CATALOGVERSION, getCatalogVersionService().getCatalogVersion(CATALOGID, CATALOGVERSIONNAME));
		query.addQueryParameter("variantCategoryCode", variantCategoryCode);
		query.addQueryParameter("sequence", sequence);
		return getFlexibleSearchService().<VariantValueCategoryModel> search(query).getResult();
	}

	@Override
	public String getMaxSequenceByVariantCategory(final String variantCategoryCode)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select max({vvc.sequence}) from {CATEGORYCATEGORYRELATION AS CCR}, {VariantCategory as vc}, {VariantValueCategory as vvc} "
						+ "where {vc.catalogversion}=?catalogVersion and {vvc.catalogversion}=?catalogVersion and {CCR.source}={vc.pk} "
						+ "and {CCR.target}={vvc.pk} and {vc.code}=?variantCategoryCode");
		query.addQueryParameter(CATALOGVERSION, getCatalogVersionService().getCatalogVersion(CATALOGID, CATALOGVERSIONNAME));
		query.addQueryParameter("variantCategoryCode", variantCategoryCode);
		query.setResultClassList(Arrays.asList(String.class));
		final SearchResult<String> result = this.getFlexibleSearchService().search(query);
		if (null != result.getResult() && CollectionUtils.isNotEmpty(result.getResult()))
		{
			return result.getResult().get(0);
		}
		return null;
	}
}
