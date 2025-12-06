/**
 *
 */
package com.siteone.core.generators;

import de.hybris.platform.acceleratorservices.sitemap.data.SiteMapUrlData;
import de.hybris.platform.acceleratorservices.sitemap.generator.impl.AbstractSiteMapGenerator;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.util.FlexibleSearchUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @author 1124932
 *
 */
public class SiteOneContentPageModelSiteMapGenerator extends AbstractSiteMapGenerator<ContentPageModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneContentPageModelSiteMapGenerator.class);

	@Override
	public List<SiteMapUrlData> getSiteMapUrlData(final List<ContentPageModel> models)
	{
		return Converters.convertAll(models, getSiteMapUrlDataConverter());
	}

	@Override
	protected List<ContentPageModel> getDataInternal(final CMSSiteModel siteModel)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(ContentPageModel.DEFAULTPAGE, Boolean.TRUE);
		params.put(ContentPageModel.INCLUDEINSITEMAP, Boolean.TRUE);

		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT distinct {cp." + ContentPageModel.PK + "} FROM {" + ContentPageModel._TYPECODE + " AS cp join CMSPageStatus AS cps on {cp." + ContentPageModel.PAGESTATUS + "} = {cps.PK}} WHERE ");
		queryBuilder.append(FlexibleSearchUtils.buildOracleCompatibleCollectionStatement(
				"{cp." + ContentPageModel.CATALOGVERSION + "} in (?catalogVersions)", "catalogVersions", "OR",
				getCatalogVersionService().getSessionCatalogVersions(), params));
		queryBuilder.append(" AND {cp." + ContentPageModel.DEFAULTPAGE + "}  = ?defaultPage");
		queryBuilder.append(" AND {cps.CODE} = 'active'");
		queryBuilder.append(" AND ({cp." + ContentPageModel.INCLUDEINSITEMAP + "}  = ?includeInSiteMap");
		queryBuilder.append(" OR {cp." + ContentPageModel.LABEL + "} like '/articles/%')");
		LOG.error("Excluded Content Page Data Query" + queryBuilder.toString());


		return doSearch(queryBuilder.toString(), params, ContentPageModel.class);

	}
}
