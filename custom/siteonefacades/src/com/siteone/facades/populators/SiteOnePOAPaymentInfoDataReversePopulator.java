/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.apache.log4j.Logger;


/**
 * @author pelango
 *
 */
public class SiteOnePOAPaymentInfoDataReversePopulator implements Populator<SiteOnePOAPaymentInfoData, SiteonePOAPaymentInfoModel>
{

	private static final Logger LOG = Logger.getLogger(SiteOnePOAPaymentInfoDataReversePopulator.class);

	@Override
	public void populate(final SiteOnePOAPaymentInfoData source, final SiteonePOAPaymentInfoModel target)
			throws ConversionException
	{
		target.setTermsCode(source.getTermsCode());
		target.setTransactionReferenceNumber(source.getTransRefNumber());
		target.setPaymentType(source.getPaymentType());
		target.setAmountCharged(source.getAmountCharged());
	}

}
