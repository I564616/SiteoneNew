/**
 *
 */
package com.siteone.core.externaltax;

import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.externaltax.ApplyExternalTaxesStrategy;
import de.hybris.platform.externaltax.ExternalTaxDocument;


/**
 * @author BS
 *
 */
public interface SiteOneApplyExternalTaxesStrategy extends ApplyExternalTaxesStrategy
{
	void applyExternalTaxesforOrderEntry(AbstractOrderModel order, ExternalTaxDocument taxDoc);
}