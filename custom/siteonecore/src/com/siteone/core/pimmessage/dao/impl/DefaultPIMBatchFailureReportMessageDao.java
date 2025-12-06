/**
 *
 */
package com.siteone.core.pimmessage.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;


import com.siteone.core.model.PIMReportMessageModel;
import com.siteone.core.pimmessage.dao.PIMBatchFailureReportMessageDao;


/**
 * @author SR02012
 *
 */
public class DefaultPIMBatchFailureReportMessageDao extends AbstractItemDao implements PIMBatchFailureReportMessageDao
{
	public static final String FETCH_USER_FRIENDLY_MESSAGE_QUERY = "SELECT {" + PIMReportMessageModel.PK
			+ "} FROM {" + PIMReportMessageModel._TYPECODE + "} WHERE {" + PIMReportMessageModel.GENERATEDERRORMESSAGE
			+ "} = ?errorMessage ";

	@Override
	public String getUserFriendlyMessage(final String generatedErrorMessage)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FETCH_USER_FRIENDLY_MESSAGE_QUERY);
		query.addQueryParameter("errorMessage", generatedErrorMessage);
		final SearchResult<PIMReportMessageModel> result = getFlexibleSearchService().search(query);
		if(result.getCount()==1)
		{
			return result.getResult().get(0).getUserFriendlyErrorMessage();
		}
	   return null;
	}

}
