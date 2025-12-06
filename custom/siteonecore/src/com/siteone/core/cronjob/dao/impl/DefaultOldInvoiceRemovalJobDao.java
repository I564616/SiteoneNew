package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.OldInvoiceRemovalJobDao;
import com.siteone.core.model.SiteOneInvoiceModel;



public class DefaultOldInvoiceRemovalJobDao extends AbstractItemDao implements OldInvoiceRemovalJobDao
{

	private static final Logger LOG = Logger.getLogger(DefaultOldInvoiceRemovalJobDao.class);

	@Override
	public List<SiteOneInvoiceModel> getInvoicesByDate(final Date date, final int batchSize)

	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {i:pk} FROM {SiteOneInvoice AS i} WHERE {i:creationtime} < ?date");
		query.addQueryParameter("date", date);
		query.setCount(batchSize);
		query.setStart(0);
		return getFlexibleSearchService().<SiteOneInvoiceModel> search(query).getResult();
	}

}
