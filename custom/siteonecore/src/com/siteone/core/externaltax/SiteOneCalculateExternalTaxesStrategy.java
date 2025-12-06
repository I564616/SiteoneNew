/**
 *
 */
package com.siteone.core.externaltax;

import de.hybris.platform.commerceservices.externaltax.CalculateExternalTaxesStrategy;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.externaltax.ExternalTaxDocument;


/**
 * @author BS
 *
 */
public interface SiteOneCalculateExternalTaxesStrategy extends CalculateExternalTaxesStrategy
{
	ExternalTaxDocument calculateExternalOrderEntryTaxes(AbstractOrderModel abstractOrder);
}
