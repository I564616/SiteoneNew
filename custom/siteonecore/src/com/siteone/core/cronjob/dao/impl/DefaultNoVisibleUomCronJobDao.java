/**
 *
 */
package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.siteone.core.cronjob.dao.NoVisibleUomCronJobDao;
import com.siteone.core.model.InventoryUPCModel;


/**
 * @author HR03708
 *
 */
public class DefaultNoVisibleUomCronJobDao extends AbstractItemDao implements NoVisibleUomCronJobDao
{
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
	public List<String> getAllProducts()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"Select {p.code} from {Product as p} where {p.catalogversion}=?catalogVersion  and ({p.isProductOffline})=0 and ({p.isProductDiscontinued}) =0 and {p.modifiedtime}>?lastChecked and ({p.productKind} != 'Base' or {p.productKind} is null) order by {p.modifiedtime} desc");
		query.addQueryParameter(CATALOGVERSION, getCatalogVersionService().getCatalogVersion(CATALOGID, CATALOGVERSIONNAME));
		query.addQueryParameter("lastChecked", getTimestamp());
		query.setResultClassList(Collections.singletonList(String.class));
		final SearchResult<String> result = getFlexibleSearchService().search(query);
		return result.getResult();

	}

	@Override
	public boolean isNoVisibleUomProduct(final String pCode)
	{
		final String queryString = "Select {i.PK} from {Product as p JOIN InventoryUPC as i ON {p.pk} = {i.owner}} where {p.code}=?productCode and {i.hideUPCOnline}=0";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter(CATALOGVERSION, getCatalogVersionService().getCatalogVersion(CATALOGID, CATALOGVERSIONNAME));
		fQuery.addQueryParameter("productCode", pCode);
		final SearchResult<InventoryUPCModel> result = getFlexibleSearchService().search(fQuery);
		return result.getCount() <= 0;
	}


	@Override
	public boolean isZeroMultiplierProduct(final String pCode)
	{
		final String queryString = "Select {i.PK} from  {Product as p JOIN InventoryUPC as i ON {p.pk} = {i.owner}} where {i.hideUPCOnline}='0' and {i.inventoryMultiplier}='0' and {p.code}=?productCode";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(queryString);
		fQuery.addQueryParameter(CATALOGVERSION, getCatalogVersionService().getCatalogVersion(CATALOGID, CATALOGVERSIONNAME));
		fQuery.addQueryParameter("productCode", pCode);
		final SearchResult<InventoryUPCModel> result = getFlexibleSearchService().search(fQuery);
		return result.getCount() > 0;

	}

	private Date getTimestamp()
	{
		final Calendar c = Calendar.getInstance();
		final Date currentTime = new Date();
		c.setTime(currentTime);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR, 0);
		c.add(Calendar.DATE, -7);
		return c.getTime();
	}

}
