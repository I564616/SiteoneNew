/**
 *
 */
package com.siteone.core.jobs.cleanup.dao.impl;

import de.hybris.platform.impex.model.ImpExMediaModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import com.siteone.core.jobs.cleanup.dao.SiteoneCleanUpImpexMediaCronJobDao;


/**
 * @author SMondal
 *
 */
public class DefaultSiteoneCleanUpImpexMediaCronJobDao extends AbstractItemDao implements SiteoneCleanUpImpexMediaCronJobDao
{

	public static final String IMPEX_MEDIA_QUERY = "SELECT {" + ImpExMediaModel.PK + "} FROM {" + ImpExMediaModel._TYPECODE
			+ "} WHERE {" + ImpExMediaModel.MODIFIEDTIME
	        + "} < CONVERT(DATETIME,?deleteTillDate) ";


	@Override
	public List<ImpExMediaModel> getImpexMedia(final String deleteTillDate)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(IMPEX_MEDIA_QUERY);
		query.addQueryParameter("deleteTillDate", deleteTillDate);
		final SearchResult<ImpExMediaModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

}
