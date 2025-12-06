/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.facade.InvoiceData;


/**
 * @author 1219341
 *
 */
public class SiteOneInvoiceListPopulator implements Populator<SiteOneInvoiceModel, InvoiceData>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final SiteOneInvoiceModel source, final InvoiceData target) throws ConversionException
	{
		target.setInvoiceNumber(source.getInvoiceNumber());
		target.setInvoiceDate(source.getInvoicedDate());
		target.setOrderNumber(source.getOrderNumber());
		target.setShipToAccountNumber(source.getAccountNumber());
		target.setPurchaseOrderNumber(source.getPurchaseOrderNumber());
		target.setAccountDisplay(source.getAccountDisplay());
		target.setOrderTotalPrice(truncatePrice(source.getOrderTotalPrice()));
		if (null != source.getStatus())
		{
			target.setStatus(source.getStatus().getCode());
		}
	}

	/**
	 * This method is used to truncate the price into 2.
	 *
	 * @param priceToTruncate
	 *           - price needs to be truncated.
	 *
	 * @return truncated price
	 */
	private String truncatePrice(final Double priceToTruncate)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat = new DecimalFormat("0.000");
			decimalFormat.setRoundingMode(RoundingMode.DOWN);

			final String truncatedPrice = decimalFormat.format(priceToTruncate);
			return truncatedPrice;
		}
		else
		{
			return null;
		}
	}

}
