package com.siteone.core.order.hook;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.order.hook.CommercePlaceOrderMethodHook;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.stock.exception.InsufficientStockLevelException;
import de.hybris.platform.util.Config;

import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class ReserveStockPlaceOrderHook implements CommercePlaceOrderMethodHook
{

	private static final String RESERVED_STRING = "RESERVED";
	private static final String RESERVE_STOCK_ENABLED = "siteone.reserve.stock.enabled";
	private static final Logger LOG = Logger.getLogger(ReserveStockPlaceOrderHook.class);

	private StockService stockService;
	private WarehouseService warehouseService;
	private SessionService sessionService;

	public StockService getStockService()
	{
		return stockService;
	}

	public void setStockService(final StockService stockService)
	{
		this.stockService = stockService;
	}

	public WarehouseService getWarehouseService()
	{
		return warehouseService;
	}

	public void setWarehouseService(final WarehouseService warehouseService)
	{
		this.warehouseService = warehouseService;
	}

	@Override
	public void afterPlaceOrder(final CommerceCheckoutParameter parameter, final CommerceOrderResult orderModel)
			throws InvalidCartException
	{

		if (Config.getBoolean(RESERVE_STOCK_ENABLED, true))
		{
			try
			{
				final PointOfServiceData sessionPOS = (PointOfServiceData) sessionService.getAttribute("sessionStore");
				final WarehouseModel warehouse = getWarehouseService().getWarehouseForCode(sessionPOS.getStoreId());
				final AbstractOrderModel order = parameter.getCart();
				final List<AbstractOrderEntryModel> entries = order.getEntries();
				entries.forEach(entry -> {
					try
					{
						getStockService().reserve(entry.getProduct(), warehouse, entry.getQuantity().intValue(), RESERVED_STRING);
					}
					catch (final InsufficientStockLevelException e)
					{
						//Ignore. No big deal.
						LOG.error("Unable to reserve stock for the product with code " + entry.getProduct().getCode(), e);
					}
				});
			}
			catch (final Exception e)
			{
				//Ignore. No big deal.
				LOG.error("Unable to reserve stock for the product with code " + orderModel.getOrder().getCode(), e);
			}
		}
	}

	@Override
	public void beforePlaceOrder(final CommerceCheckoutParameter parameter) throws InvalidCartException
	{
		//Ignore. No big deal.
	}

	@Override
	public void beforeSubmitOrder(final CommerceCheckoutParameter parameter, final CommerceOrderResult result)
			throws InvalidCartException
	{
		//Ignore. No big deal.
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
}
