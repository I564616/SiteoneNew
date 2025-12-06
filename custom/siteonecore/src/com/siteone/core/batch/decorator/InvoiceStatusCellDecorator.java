/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import com.siteone.core.enums.InvoiceStatus;


/**
 * @author 1219341
 *
 */
public class InvoiceStatusCellDecorator implements CSVCellDecorator
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
			if ("INVOICED".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.INVOICED.getCode();
			}
			else if ("PAID".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.PAID.getCode();
			}
			else if ("UNPAID".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.UNPAID.getCode();
			}
			else if ("error, invoiced but payments do not match".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.ERR_INV_PAYMENT_NOT_MATCH.getCode();
			}
			else if ("Error, Data Warehouse could not process".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.ERR_DW_NOT_PROCESS.getCode();
			}
			else if ("Shipped".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.SHIPPED.getCode();
			}
			else if ("Ready to Invoice".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.READY_TO_INVOICE.getCode();
			}
			else if ("Updated to the Data Warehouse".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.UPDATED_TO_DW.getCode();
			}
			else if ("Do not update to Data Warehouse".equalsIgnoreCase(csvCell))
			{
				return InvoiceStatus.DO_NOT_UPDATE_DW.getCode();
			}
		}
		return csvCell;

	}
}
