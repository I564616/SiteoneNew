/**
 *
 */
package com.siteone.ruleengineservices.converters;

import de.hybris.order.calculation.money.Currency;
import de.hybris.platform.ruleengineservices.converters.AbstractOrderRaoToCurrencyConverter;
import de.hybris.platform.ruleengineservices.rao.AbstractOrderRAO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;


/**
 * @author 1219341
 *
 */
public class AbstractSiteOneOrderRaoToCurrencyConverter extends AbstractOrderRaoToCurrencyConverter
{
	private static final Logger LOG = Logger.getLogger(AbstractSiteOneOrderRaoToCurrencyConverter.class);

	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	private BaseStoreService baseStoreService;

	@Override
	public Currency convert(final AbstractOrderRAO source) throws ConversionException
	{
		final String currencyIso = source.getCurrencyIsoCode();
		ServicesUtil.validateParameterNotNull(currencyIso, "currencyIso must not be null");
		//final Integer digits = getBaseStoreService().getBaseStoreForUid("siteone").getDefaultCurrency().getDigits();
		final int digits = Config.getInt("currency.digits", 3);
		LOG.info("Currency Digits " + digits);
		return new Currency(currencyIso, digits);
	}

	@Override
	public Currency convert(final AbstractOrderRAO paramSOURCE, final Currency paramTARGET) throws ConversionException
	{
		throw new UnsupportedOperationException();
	}
}
