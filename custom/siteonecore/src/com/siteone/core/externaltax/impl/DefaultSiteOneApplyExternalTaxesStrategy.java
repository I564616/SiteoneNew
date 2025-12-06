/**
 *
 */
package com.siteone.core.externaltax.impl;

import de.hybris.platform.core.CoreAlgorithms;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.externaltax.ExternalTaxDocument;
import de.hybris.platform.externaltax.impl.DefaultApplyExternalTaxesStrategy;
import de.hybris.platform.util.TaxValue;

import java.math.BigDecimal;
import java.util.List;

import com.siteone.core.externaltax.SiteOneApplyExternalTaxesStrategy;


/**
 * @author 1099417
 *
 */
public class DefaultSiteOneApplyExternalTaxesStrategy extends DefaultApplyExternalTaxesStrategy
		implements SiteOneApplyExternalTaxesStrategy
{
	@Override
	public void applyExternalTaxes(final AbstractOrderModel order, final ExternalTaxDocument externalTaxDocument)
	{
		if (!Boolean.TRUE.equals(order.getNet()))
		{
			throw new IllegalStateException("Order " + order.getCode() + " must be of type NET to apply external taxes to it.");
		}
		else
		{
			final BigDecimal tax = this.applyTaxes(order, externalTaxDocument);
			this.setTotalTax(order, tax);
		}
	}

	protected BigDecimal applyTaxes(final AbstractOrderModel order, final ExternalTaxDocument taxDoc)
	{
		final BigDecimal[] totalTax =
		{ BigDecimal.ZERO };
		final List<TaxValue> taxes = taxDoc.getTaxesForOrderEntry(1);

		if (null != taxes && !taxes.isEmpty())
		{
			taxes.forEach(taxValue -> totalTax[0] = totalTax[0].add(BigDecimal.valueOf(taxValue.getAppliedValue())));
		}
		order.setTotalTaxValues(taxes);
		return totalTax[0];
	}

	protected void applyOrderEntryTaxes(final AbstractOrderModel order, final ExternalTaxDocument taxDoc)
	{
		final Integer digits = order.getCurrency().getDigits();
		if (digits == null)
		{
			throw new IllegalStateException(
					"Order " + order.getCode() + " has got a currency without decimal digits defined. Cannot apply external taxes.");
		}
		final BigDecimal[] totalTax =
		{ BigDecimal.ZERO };
		for (final AbstractOrderEntryModel entries : order.getEntries())
		{
			final List<TaxValue> taxes = taxDoc.getTaxesForOrderEntry(entries.getEntryNumber());

			if (null != taxes && !taxes.isEmpty())
			{
				taxes.forEach(taxValue -> totalTax[0] = BigDecimal.valueOf(taxValue.getAppliedValue()));
				entries.setTotaltax(Double.valueOf(CoreAlgorithms.round(totalTax[0].doubleValue(), digits.intValue())));
			}
		}
	}

	@Override
	protected void setTotalTax(final AbstractOrderModel order, final BigDecimal totalTaxSum)
	{
		final Integer digits = order.getCurrency().getDigits();
		if (digits == null)
		{
			throw new IllegalStateException(
					"Order " + order.getCode() + " has got a currency without decimal digits defined. Cannot apply external taxes.");
		}
		else
		{
			order.setTotalTax(Double.valueOf(CoreAlgorithms.round(totalTaxSum.doubleValue(), digits.intValue())));
		}
	}

	@Override
	public void applyExternalTaxesforOrderEntry(final AbstractOrderModel order, final ExternalTaxDocument taxDoc)
	{
		this.applyOrderEntryTaxes(order, taxDoc);

	}

}

