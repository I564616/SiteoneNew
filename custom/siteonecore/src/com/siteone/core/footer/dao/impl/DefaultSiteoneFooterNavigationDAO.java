/**
 * 
 */
package com.siteone.core.footer.dao.impl;

import de.hybris.platform.acceleratorcms.model.components.FooterNavigationComponentModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.services.BaseStoreService;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.footer.dao.SiteoneFooterNavigationDAO;
/**
 * 
 */
public class DefaultSiteoneFooterNavigationDAO implements SiteoneFooterNavigationDAO
{
	private static final Logger LOGGER = Logger.getLogger(DefaultSiteoneFooterNavigationDAO.class);
	private static final String CATALOG_VERSION = "Online";
	private static final String CATALOG_US = "siteoneContentCatalog";
	private static final String CATALOG_CA = "siteoneCAContentCatalog";
	
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public FooterNavigationComponentModel getFooterNode()
	{
		CatalogVersionModel catalogversion = catalogVersionService.getCatalogVersion(CATALOG_US, CATALOG_VERSION);
		String basestore = baseStoreService.getCurrentBaseStore().getUid();
		if(basestore.equalsIgnoreCase("siteone")) {
			catalogversion = catalogVersionService.getCatalogVersion(CATALOG_US, CATALOG_VERSION);
		}
		if(basestore.equalsIgnoreCase("siteoneCA")) {
			catalogversion = catalogVersionService.getCatalogVersion(CATALOG_CA, CATALOG_VERSION);
		}
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {FNN.PK} FROM {FooterNavigationComponent AS FNN} WHERE {FNN.CATALOGVERSION} = ?catalogversion");
		query.addQueryParameter("catalogversion", catalogversion);
		final SearchResult<FooterNavigationComponentModel> result = flexibleSearchService.search(query);
		return result.getResult() != null ? result.getResult().get(0) : null;

	}

}
