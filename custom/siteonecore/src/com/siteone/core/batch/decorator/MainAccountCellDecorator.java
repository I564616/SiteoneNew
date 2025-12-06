/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.log4j.Logger;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.order.services.SiteOneOrderService;


/**
 * @author SNavamani
 *
 */
public class MainAccountCellDecorator implements CSVCellDecorator
{
	private SiteOneOrderService siteOneOrderService;
	private SiteOneB2BUnitService siteOneB2BUnitService;
	private static final Logger LOGGER = Logger.getLogger(MainAccountCellDecorator.class);

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		siteOneB2BUnitService = (SiteOneB2BUnitService) Registry.getApplicationContext().getBean("defaultSiteOneB2BUnitService");
		siteOneOrderService = (SiteOneOrderService) Registry.getApplicationContext().getBean("defaultSiteOneOrderService");
		final String orderId = (String) srcLine.get(Integer.valueOf(position));
		String orderingAccountUid = null;
		String mainAccountUid = null;

		if (orderId == null || orderId.length() == 0)
		{
			return orderId;
		}
		else
		{
			try
			{
				orderingAccountUid = getSiteOneOrderService().getOrderingAccountforOrder(orderId);
				if (orderingAccountUid != null)
				{
					mainAccountUid = getSiteOneB2BUnitService().getMainAccountForUnit(orderingAccountUid).getUid();
				}
			}
			catch (final ModelNotFoundException e)
			{
				LOGGER.error(e);
			}

		}
		return mainAccountUid;
	}

	/**
	 * @return the siteOneB2BUnitService
	 */
	public SiteOneB2BUnitService getSiteOneB2BUnitService()
	{
		return siteOneB2BUnitService;
	}

	/**
	 * @param siteOneB2BUnitService
	 *           the siteOneB2BUnitService to set
	 */
	public void setSiteOneB2BUnitService(final SiteOneB2BUnitService siteOneB2BUnitService)
	{
		this.siteOneB2BUnitService = siteOneB2BUnitService;
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