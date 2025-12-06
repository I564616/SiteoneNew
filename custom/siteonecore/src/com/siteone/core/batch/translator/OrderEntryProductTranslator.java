/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.b2b.services.impl.DefaultB2BOrderService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import org.apache.log4j.Logger;


/**
 * This class for Retrieving the product model
 *
 * @author Ravi-so
 *
 */
public class OrderEntryProductTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(OrderEntryProductTranslator.class);
	private DefaultB2BOrderService b2bOrderService;

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		b2bOrderService = (DefaultB2BOrderService) Registry.getApplicationContext().getBean("b2bOrderService");
	}

	@Override
	public Object importValue(final String cellValue, final Item item) throws JaloInvalidParameterException
	{
		AbstractOrderEntryModel entryModel = null;

		final String[] inputOrderProduct = cellValue.split("_");

		try
		{
			final AbstractOrderModel orderModel = b2bOrderService.getAbstractOrderForCode(inputOrderProduct[1]);
			entryModel = orderModel.getEntries().stream()
					.filter(entry -> inputOrderProduct[0].equalsIgnoreCase(entry.getStoreProductCode())).findFirst().get();
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.error("Product is not available .. in exception block" + cellValue);
		}

		return entryModel;
	}

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return null;
	}
}
