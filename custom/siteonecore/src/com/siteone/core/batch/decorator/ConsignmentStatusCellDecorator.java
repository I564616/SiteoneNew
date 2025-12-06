/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * This is translator for coverting delivery codes to consignment csvCell codes
 *
 * @author Ravi P
 *
 */
public class ConsignmentStatusCellDecorator implements CSVCellDecorator
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
			if (SiteoneCoreConstants.SCHEDULED.equalsIgnoreCase(csvCell)
					|| SiteoneCoreConstants.SCHEDULED_FOR_DELIVERY.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.SCHEDULED_FOR_DELIVERY.getCode();
			}
			else if (SiteoneCoreConstants.IN_TRANSIT.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.IN_TRANSIT.getCode();
			}
			else if (SiteoneCoreConstants.FINISHED.equalsIgnoreCase(csvCell)
					|| SiteoneCoreConstants.CLOSED.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.COMPLETED.getCode();
			}
			else if (SiteoneCoreConstants.OPEN.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.SUBMITTED.getCode();
			}
			else if (SiteoneCoreConstants.PROCESSING.equalsIgnoreCase(csvCell)
					|| SiteoneCoreConstants.RECEIVED.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.PROCESSING.getCode();
			}
			else if (SiteoneCoreConstants.READY_FOR_PICKUP.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.READY_FOR_PICKUP.getCode();
			}
			else if (SiteoneCoreConstants.INVOICED.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.INVOICED.getCode();
			}
			else if (SiteoneCoreConstants.CANCELLED.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.CANCELLED.getCode();
			}
			else if (SiteoneCoreConstants.SHIPPED.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.SHIPPED.getCode();
			}
			else if (SiteoneCoreConstants.NEW.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.NEW.getCode();
			}
			else if (SiteoneCoreConstants.STARTED.equalsIgnoreCase(csvCell))
			{
				return ConsignmentStatus.STARTED.getCode();
			}
		}
		return csvCell;

	}
}
