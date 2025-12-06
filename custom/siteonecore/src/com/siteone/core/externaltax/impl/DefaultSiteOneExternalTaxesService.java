/**
 *
 */
package com.siteone.core.externaltax.impl;

import de.hybris.platform.commerceservices.externaltax.RecalculateExternalTaxesStrategy;
import de.hybris.platform.commerceservices.externaltax.impl.DefaultExternalTaxesService;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.externaltax.ExternalTaxDocument;

import jakarta.annotation.Resource;

import org.springframework.util.Assert;

import com.siteone.core.externaltax.SiteOneApplyExternalTaxesStrategy;
import com.siteone.core.externaltax.SiteOneCalculateExternalTaxesStrategy;



/**
 * @author 1099417
 *
 */
public class DefaultSiteOneExternalTaxesService extends DefaultExternalTaxesService
{
	@Resource(name = "calculateExternalTaxesStrategy")
	private SiteOneCalculateExternalTaxesStrategy siteOneCalculateExternalTaxesStrategy;

	@Resource(name = "applyExternalTaxesStrategy")
	private SiteOneApplyExternalTaxesStrategy siteOneApplyExternalTaxesStrategy;

	/**
	 * @return the siteOneCalculateExternalTaxesStrategy
	 */
	public SiteOneCalculateExternalTaxesStrategy getSiteOneCalculateExternalTaxesStrategy()
	{
		return siteOneCalculateExternalTaxesStrategy;
	}

	/**
	 * @param siteOneCalculateExternalTaxesStrategy
	 *           the siteOneCalculateExternalTaxesStrategy to set
	 */
	public void setSiteOneCalculateExternalTaxesStrategy(
			final SiteOneCalculateExternalTaxesStrategy siteOneCalculateExternalTaxesStrategy)
	{
		this.siteOneCalculateExternalTaxesStrategy = siteOneCalculateExternalTaxesStrategy;
	}

	@Override
	public boolean calculateExternalTaxes(final AbstractOrderModel abstractOrder)
	{
		if (getDecideExternalTaxesStrategy().shouldCalculateExternalTaxes(abstractOrder))
		{
			if (getRecalculateExternalTaxesStrategy().recalculate(abstractOrder))
			{
				final ExternalTaxDocument exTaxDocument = getCalculateExternalTaxesStrategy().calculateExternalTaxes(abstractOrder);
				final ExternalTaxDocument exTaxEntryDocument = getSiteOneCalculateExternalTaxesStrategy()
						.calculateExternalOrderEntryTaxes(abstractOrder);
				Assert.notNull(exTaxDocument, "ExternalTaxDocument should not be null");
				// check if external tax calculation was successful
				if (!exTaxDocument.getAllTaxes().isEmpty())
				{
					getApplyExternalTaxesStrategy().applyExternalTaxes(abstractOrder, exTaxDocument);
					if (!exTaxEntryDocument.getAllTaxes().isEmpty())
					{
						getSiteOneApplyExternalTaxesStrategy().applyExternalTaxesforOrderEntry(abstractOrder, exTaxEntryDocument);
					}
					getSessionService().setAttribute(SESSION_EXTERNAL_TAX_DOCUMENT, exTaxDocument);
					saveOrder(abstractOrder);
					return true;
				}
				else
				{
					// the external tax calculation failed
					getSessionService().removeAttribute(RecalculateExternalTaxesStrategy.SESSION_ATTIR_ORDER_RECALCULATION_HASH);
					clearSessionTaxDocument();
					clearTaxValues(abstractOrder);
					saveOrder(abstractOrder);
				}
			}
			else
			{
				// get the cached tax document
				getApplyExternalTaxesStrategy().applyExternalTaxes(abstractOrder, getSessionExternalTaxDocument(abstractOrder));
				saveOrder(abstractOrder);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the siteOneApplyExternalTaxesStrategy
	 */
	public SiteOneApplyExternalTaxesStrategy getSiteOneApplyExternalTaxesStrategy()
	{
		return siteOneApplyExternalTaxesStrategy;
	}

	/**
	 * @param siteOneApplyExternalTaxesStrategy
	 *           the siteOneApplyExternalTaxesStrategy to set
	 */
	public void setSiteOneApplyExternalTaxesStrategy(final SiteOneApplyExternalTaxesStrategy siteOneApplyExternalTaxesStrategy)
	{
		this.siteOneApplyExternalTaxesStrategy = siteOneApplyExternalTaxesStrategy;
	}
}


