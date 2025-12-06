/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author RSUBATHR
 *
 */
public class SiteOnePOAPaymentInfoDataPopulator implements Populator<SiteonePOAPaymentInfoModel, SiteOnePOAPaymentInfoData>
{

	@Override
	public void populate(final SiteonePOAPaymentInfoModel source, final SiteOnePOAPaymentInfoData target)
			throws ConversionException
	{
		target.setTermsCode(source.getTermsCode());
		target.setTransRefNumber(source.getTransactionReferenceNumber());
		target.setPaymentType(source.getPaymentType());
	}

}
