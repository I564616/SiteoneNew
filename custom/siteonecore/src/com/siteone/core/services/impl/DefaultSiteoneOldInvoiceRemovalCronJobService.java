package com.siteone.core.services.impl;

import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.dao.OldInvoiceRemovalJobDao;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.core.services.SiteoneOldInvoiceRemovalCronJobService;


public class DefaultSiteoneOldInvoiceRemovalCronJobService implements SiteoneOldInvoiceRemovalCronJobService
{

	private static final Integer NO_OF_DAYS = Config.getInt("invoice.history.delete.days", 730);
	private static final Integer MIN_NO_OF_DAYS = Config.getInt("invoice.history.minimum.days", 365);
	private static final Integer INVOICE_DELETE_BATCH_SIZE = Config.getInt("invoice.history.delete.batch.size", 100);

	private static final Logger LOG = Logger.getLogger(DefaultSiteoneOldInvoiceRemovalCronJobService.class);

	private OldInvoiceRemovalJobDao siteoneOldInvoiceRemovalJobDao;
	private ModelService modelService;

	@Override
	public boolean removeOldInvoices()
	{
		if (NO_OF_DAYS >= MIN_NO_OF_DAYS)
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, -NO_OF_DAYS);
			final Date dateToRemoveRecords = calendar.getTime();

		
			purgeInvoices(dateToRemoveRecords);

			return true;
		}
		else
		{
			LOG.error("Value for 'invoice.history.delete.days' should not be less than 730");
		}
		return false;
	}

	public void purgeInvoices(final Date dateToRemoveRecords)
	{
		final int batchSize = INVOICE_DELETE_BATCH_SIZE;
		boolean purging = true;

		while (purging)
		{
			List<SiteOneInvoiceModel> invoices = new ArrayList<SiteOneInvoiceModel>();

			invoices = siteoneOldInvoiceRemovalJobDao.getInvoicesByDate(dateToRemoveRecords, batchSize);

			LOG.error("Invoices removal batchSize: " + invoices.size());

			if (invoices.size() < batchSize)
			{
				purging = false;
			}

			getModelService().removeAll(invoices);
		}
	}

	

	public OldInvoiceRemovalJobDao getSiteoneOldInvoiceRemovalJobDao()
	{
		return siteoneOldInvoiceRemovalJobDao;
	}

	public void setSiteoneOldInvoiceRemovalJobDao(final OldInvoiceRemovalJobDao siteoneOldInvoiceRemovalJobDao)
	{
		this.siteoneOldInvoiceRemovalJobDao = siteoneOldInvoiceRemovalJobDao;
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