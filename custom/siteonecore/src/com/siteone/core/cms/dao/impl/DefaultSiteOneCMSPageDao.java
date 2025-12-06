/**
 *
 */
package com.siteone.core.cms.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.daos.impl.DefaultCMSPageDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.FlexibleSearchUtils;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.siteone.core.cms.dao.SiteOneCMSPageDao;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOneCMSPageDao extends DefaultCMSPageDao implements SiteOneCMSPageDao
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCMSPageDao.class);

	@Override
	public Collection<ContentPageModel> findCategoryLandingPages(final CategoryModel category,
			final Collection<CatalogVersionModel> catalogVersions)
	{
		final HashMap queryParameters = new HashMap();
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT {pk} FROM {ContentPage} WHERE {label} like (?path) AND {defaultPage} = 1 AND ");
		queryBuilder.append(FlexibleSearchUtils.buildOracleCompatibleCollectionStatement("{catalogVersion} in (?catalogVersions)",
				"catalogVersions", "OR", catalogVersions, queryParameters));
		queryParameters.put("path", "%" + category.getCode().toLowerCase());
		final SearchResult result = this.search(queryBuilder.toString(), queryParameters);
		return result.getResult();
	}

}
