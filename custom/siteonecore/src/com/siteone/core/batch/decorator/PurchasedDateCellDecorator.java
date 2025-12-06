/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.util.CSVCellDecorator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.siteone.core.order.services.SiteOneOrderService;


/**
 * @author SNavamani
 *
 */
public class PurchasedDateCellDecorator implements CSVCellDecorator
{
	private SiteOneOrderService siteOneOrderService;
	private static final Logger LOGGER = Logger.getLogger(MainAccountCellDecorator.class);

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		siteOneOrderService = (SiteOneOrderService) Registry.getApplicationContext().getBean("defaultSiteOneOrderService");
		final String orderId = (String) srcLine.get(Integer.valueOf(position));
		Date purchasedDate = null;
		String strPurchasedDate = null;

		if (orderId == null || orderId.length() == 0)
		{
			return orderId;
		}
		else
		{
			try
			{
				purchasedDate = getSiteOneOrderService().getPurchasedDateforOrder(orderId);
				final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
				strPurchasedDate = dateFormat.format(purchasedDate);
			}
			catch (final ModelNotFoundException e)
			{
				LOGGER.error(e);
			}

		}
		return strPurchasedDate;
	}

	/**
	 * @return the siteOneOrderService
	 */
	public SiteOneOrderService getSiteOneOrderService()
	{
		return siteOneOrderService;
	}

	/**
	 * @param siteOneOrderService
	 *           the siteOneOrderService to set
	 */
	public void setSiteOneOrderService(final SiteOneOrderService siteOneOrderService)
	{
		this.siteOneOrderService = siteOneOrderService;
	}
}