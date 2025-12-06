/**
 *
 */
package com.siteone.core.batch.translator;

import de.hybris.platform.b2b.services.impl.DefaultB2BOrderService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;


/**
 * Class for get wareshouse from Order
 *
 * @author Ravi P
 *
 */
public class OrderWarehouseTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(OrderWarehouseTranslator.class);

	private DefaultB2BOrderService b2bOrderService;

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		b2bOrderService = (DefaultB2BOrderService) Registry.getApplicationContext().getBean("b2bOrderService");
	}

	@Override
	public Object importValue(final String cellValue, final Item item) throws JaloInvalidParameterException
	{
		AbstractOrderModel orderModel = null;
		WarehouseModel wareHouseModel = null;

		try
		{
			orderModel = b2bOrderService.getAbstractOrderForCode(cellValue);
			if (orderModel.getPointOfService() != null && CollectionUtils.isNotEmpty(orderModel.getPointOfService().getWarehouses()))
			{
			wareHouseModel = orderModel.getPointOfService().getWarehouses().get(0);
			}

		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.error("Warehouse is not available .. in exception block" + cellValue);
		}

		return wareHouseModel != null ? wareHouseModel : new WarehouseModel();
	}


	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		return null;
	}


}

