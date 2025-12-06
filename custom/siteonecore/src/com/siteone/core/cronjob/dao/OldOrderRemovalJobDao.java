package com.siteone.core.cronjob.dao;


import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;

import java.util.Date;
import java.util.List;


public interface OldOrderRemovalJobDao
{
	public List<OrderModel> getOrdersByDate(Date date, int batchSize);

	public List<ConsignmentModel> getConsignmentsByDate(Date date, int batchSize);

	public List<ConsignmentEntryModel> getConsignmnetEntryByDate(Date date, int batchSize);
}
