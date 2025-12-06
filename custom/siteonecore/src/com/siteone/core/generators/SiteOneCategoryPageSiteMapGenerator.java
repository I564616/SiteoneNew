/**
 *
 */
package com.siteone.core.generators;

import de.hybris.platform.acceleratorservices.sitemap.data.SiteMapUrlData;
import de.hybris.platform.acceleratorservices.sitemap.generator.impl.CategoryPageSiteMapGenerator;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.converters.Converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 1124932
 *
 */
public class SiteOneCategoryPageSiteMapGenerator extends CategoryPageSiteMapGenerator
{
	@Override
	public List<SiteMapUrlData> getSiteMapUrlData(final List<CategoryModel> models)
	{
		return Converters.convertAll(models, getSiteMapUrlDataConverter());
	}

	@Override
	protected List<CategoryModel> getDataInternal(final CMSSiteModel siteModel)
	{
		final String query = "SELECT {c.pk} FROM {Category AS c JOIN CatalogVersion AS cv ON {c.catalogVersion}={cv.pk} "
				+ " JOIN Catalog AS cat ON {cv.pk}={cat.activeCatalogVersion} "
				+ " JOIN CMSSite AS site ON {cat.pk}={site.defaultCatalog}}  WHERE {site.pk} = ?site "
				+ " AND NOT exists ({{select {cr.pk} from {CategoriesForRestriction as cr} where {cr.target} = {c.pk} }})"
				+ " AND ({c.code} LIKE 'SH%' AND {c.code} <> 'SH1')";
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("site", siteModel);
		return doSearch(query, params, CategoryModel.class);
	}
}
