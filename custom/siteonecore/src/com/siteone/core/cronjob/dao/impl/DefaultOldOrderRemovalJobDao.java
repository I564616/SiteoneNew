package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.OldOrderRemovalJobDao;


public class DefaultOldOrderRemovalJobDao extends AbstractItemDao implements OldOrderRemovalJobDao
{

	private static final Logger LOG = Logger.getLogger(DefaultOldOrderRemovalJobDao.class);

	@Override
	public List<OrderModel> getOrdersByDate(final Date date, final int batchSize)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {o:pk} FROM {Order AS o} WHERE {o:date} < ?date");
		query.addQueryParameter("date", date);
		query.setCount(batchSize);
		query.setStart(0);
		return getFlexibleSearchService().<OrderModel> search(query).getResult();
	}

	@Override
	public List<ConsignmentModel> getConsignmentsByDate(final Date date, final int batchSize)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {consignment} where {creationtime} < ?date");
		query.addQueryParameter("date", date);
		query.setCount(batchSize);
		query.setStart(0);
		return getFlexibleSearchService().<ConsignmentModel> search(query).getResult();
	}

	@Override
	public List<ConsignmentEntryModel> getConsignmnetEntryByDate(final Date date, final int batchSize)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {consignmententry} where {creationtime} < ?date");
		query.addQueryParameter("date", date);
		query.setCount(batchSize);
		query.setStart(0);
		return getFlexibleSearchService().<ConsignmentEntryModel> search(query).getResult();
	}

}
