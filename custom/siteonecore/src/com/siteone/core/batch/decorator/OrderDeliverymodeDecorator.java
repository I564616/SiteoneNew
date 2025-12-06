/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1085284
 *
 */
public class OrderDeliverymodeDecorator implements CSVCellDecorator
{
	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String mode = (String) srcLine.get(position);
		if (mode == null || mode.length() == 0)
		{
			return SiteoneCoreConstants.ORDERTYPE_MULTIPLE_DELIVERIES;
		}

		if ("Pick-up".equalsIgnoreCase(mode) || "PICKUP".equalsIgnoreCase(mode))
		{
			return SiteoneCoreConstants.ORDERTYPE_PICKUP;
		}
		else if ("Delivery".equalsIgnoreCase(mode))
		{
			return SiteoneCoreConstants.ORDERTYPE_DELIVERY;
		}
		else if ("Future Pick-up".equalsIgnoreCase(mode))
		{
			return SiteoneCoreConstants.ORDERTYPE_FUTURE_PICKUP;

		}
		else if ("Store Delivery".equalsIgnoreCase(mode))
		{
			return SiteoneCoreConstants.ORDERTYPE_STORE_DELIVERY;
		}
		else if ("Direct ship".equalsIgnoreCase(mode))
		{
			return SiteoneCoreConstants.ORDERTYPE_DIRECT_SHIP;
		}
		else if ("Shipping".equalsIgnoreCase(mode))
		{
			return SiteoneCoreConstants.ORDERTYPE_SHIPPING;
		}
		else
		{
			return SiteoneCoreConstants.ORDERTYPE_MULTIPLE_DELIVERIES;
		}


	}
}

