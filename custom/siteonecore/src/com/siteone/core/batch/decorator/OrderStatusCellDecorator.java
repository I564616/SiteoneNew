/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * @author 1124932
 *
 */
public class OrderStatusCellDecorator implements CSVCellDecorator
{


	@Override
	public String decorate(final int position, final Map srcLine)
	{
		final String csvCell = (String) srcLine.get(Integer.valueOf(position));
		if (csvCell == null || csvCell.length() == 0)
		{
			return csvCell;
		}
		else
		{
			if (SiteoneCoreConstants.OPEN.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.SUBMITTED.getCode();
			}
			else if (SiteoneCoreConstants.PROCESSING.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.PROCESSING.getCode();
			}
			else if (SiteoneCoreConstants.READY_FOR_PICKUP.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.READY_FOR_PICKUP.getCode();
			}
			else if (SiteoneCoreConstants.READY_FOR_DELIVERY.equalsIgnoreCase(csvCell)
					|| SiteoneCoreConstants.SCHEDULED_FOR_DELIVERY.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.SCHEDULED_FOR_DELIVERY.getCode();
			}
			else if (SiteoneCoreConstants.INVOICED.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.INVOICED.getCode();
			}
			else if (SiteoneCoreConstants.CANCELLED.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.CANCELLED.getCode();
			}
			else if (SiteoneCoreConstants.CLOSED.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.COMPLETED.getCode();
			}
			else if (SiteoneCoreConstants.SHIPPED.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.SHIPPED.getCode();
			}
			else if (SiteoneCoreConstants.SUBMITTED.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.SUBMITTED.getCode();
			}
			else if (SiteoneCoreConstants.COMPLETED.equalsIgnoreCase(csvCell))
			{
				return OrderStatus.COMPLETED.getCode();
			}
		}
		return csvCell;

	}
}
