package com.siteone.core.services.impl;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.OldOrderRemovalJobDao;
import com.siteone.core.services.SiteoneOldOrderRemovalCronJobService;


public class DefaultSiteoneOldOrderRemovalCronJobService implements SiteoneOldOrderRemovalCronJobService
{

	private static final Integer NO_OF_DAYS = Config.getInt("order.history.delete.days", 730);
	private static final Integer MIN_NO_OF_DAYS = Config.getInt("order.history.minimum.days", 365);
	private static final Integer ORDER_DELETE_BATCH_SIZE = Config.getInt("order.history.delete.batch.size", 100);
	private static final Logger LOG = Logger.getLogger(DefaultSiteoneOldOrderRemovalCronJobService.class);

	private OldOrderRemovalJobDao siteoneOldOrderRemovalJobDao;
	private ModelService modelService;


	@Override
	public boolean removeOldOrders()
	{
		if (NO_OF_DAYS >= MIN_NO_OF_DAYS)
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, -NO_OF_DAYS);
			final Date dateToRemoveRecords = calendar.getTime();
			purgeConsigmentEntries(dateToRemoveRecords);
			purgeConsigments(dateToRemoveRecords);
			purgeOrders(dateToRemoveRecords);
			return true;
		}
		else
		{
			LOG.error("value for 'order.history.delete.days' should not be less then 730");
		}
		return false;
	}

	public void purgeOrders(final Date dateToRemoveRecords)
	{
		final int batchSize = ORDER_DELETE_BATCH_SIZE;
		boolean purging = true;
		while (purging)
		{
			List<OrderModel> orders = new ArrayList<OrderModel>();
			orders = siteoneOldOrderRemovalJobDao.getOrdersByDate(dateToRemoveRecords, batchSize);
			LOG.error("Orders removal batchSize : " + orders.size());
			if (orders.size() < batchSize)
			{
				purging = false;
			}
			getModelService().removeAll(orders);
		}
	}

	public void purgeConsigments(final Date dateToRemoveRecords)
	{
		final int batchSize = ORDER_DELETE_BATCH_SIZE;
		boolean purging = true;
		while (purging)
		{
			List<ConsignmentModel> consignment = new ArrayList<ConsignmentModel>();
			consignment = siteoneOldOrderRemovalJobDao.getConsignmentsByDate(dateToRemoveRecords, batchSize);
			LOG.error("consignment removal batchSize : " + consignment.size());
			if (consignment.size() < batchSize)
			{
				purging = false;
			}
			getModelService().removeAll(consignment);
		}
	}

	public void purgeConsigmentEntries(final Date dateToRemoveRecords)
	{
		final int batchSize = ORDER_DELETE_BATCH_SIZE;
		boolean purging = true;
		while (purging)
		{
			List<ConsignmentEntryModel> consigmentEntries = new ArrayList<ConsignmentEntryModel>();
			consigmentEntries = siteoneOldOrderRemovalJobDao.getConsignmnetEntryByDate(dateToRemoveRecords, batchSize);
			LOG.error("consigmentEntries removal batchSize : " + consigmentEntries.size());
			if (consigmentEntries.size() < batchSize)
			{
				purging = false;
			}
			getModelService().removeAll(consigmentEntries);
		}
	}

	public OldOrderRemovalJobDao getSiteoneOldOrderRemovalJobDao()
	{
		return siteoneOldOrderRemovalJobDao;
	}

	public void setSiteoneOldOrderRemovalJobDao(final OldOrderRemovalJobDao siteoneOldOrderRemovalJobDao)
	{
		this.siteoneOldOrderRemovalJobDao = siteoneOldOrderRemovalJobDao;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}
}
