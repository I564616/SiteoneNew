/**
 *
 */
package com.siteone.core.cart.strategies;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.strategies.impl.DefaultCartValidationStrategy;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.util.Collection;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.store.services.SiteOneStoreFinderService;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneCartValidationStrategy extends DefaultCartValidationStrategy
{
	private static final Logger LOGGER = Logger.getLogger(DefaultSiteOneCartValidationStrategy.class);
	private final long maxQuantity = Config.getInt("cart.max.quantity", 99999);
	private StockService stockService;
	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;
	
	@Override
	protected Long getStockLevel(final CartEntryModel cartEntryModel)
	{
		final ProductModel product = cartEntryModel.getProduct();
		PointOfServiceModel pointOfService = cartEntryModel.getOrder().getPointOfService();
		if(pointOfService==null) {
			pointOfService=getStoreFinderService()
					.getStoreForId(((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId());
		}

		return getCommerceStockService().getStockLevelForProductAndPointOfService(product, pointOfService);
	}

	@Override
	protected CommerceCartModification validateCartEntry(final CartModel cartModel, final CartEntryModel cartEntryModel)
	{
		// First verify that the product exists
		try
		{
			getProductService().getProductForCode(cartEntryModel.getProduct().getCode());
		}
		catch (final UnknownIdentifierException e)
		{
			LOGGER.error(e.getMessage(), e);
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.UNAVAILABLE);
			modification.setQuantityAdded(0);
			modification.setQuantity(0);

			final CartEntryModel entry = new CartEntryModel()
			{
				@Override
				public Double getBasePrice()
				{
					return null;
				}

				@Override
				public Double getTotalPrice()
				{
					return null;
				}
			};
			entry.setProduct(cartEntryModel.getProduct());

			modification.setEntry(entry);

			getModelService().remove(cartEntryModel);
			getModelService().refresh(cartModel);

			return modification;
		}

		// Overall availability of this product
		final Long stockLevel = getStockLevel(cartEntryModel);

		// Overall stock quantity in the cart
		final long cartLevel = getCartLevel(cartEntryModel, cartModel);

		// Stock quantity for this cartEntry
		final long cartEntryLevel = cartEntryModel.getQuantity().longValue();

		// New stock quantity for this cartEntry
		final long newOrderEntryLevel;

		Long stockLevelForProductInBaseStore = null;

		if (stockLevel != null)
		{
			// this product is not available at the given point of service.
			if (isProductNotAvailableInPOS(cartEntryModel, stockLevel))
			{

				stockLevelForProductInBaseStore = getCommerceStockService()
						.getStockLevelForProductAndBaseStore(cartEntryModel.getProduct(), getBaseStoreService().getCurrentBaseStore());

				if (stockLevelForProductInBaseStore != null)
				{
					newOrderEntryLevel = Math.min(cartEntryLevel, stockLevelForProductInBaseStore.longValue());
				}
				else
				{
					newOrderEntryLevel = Math.min(cartEntryLevel, cartLevel);
				}
			}
			else
			{
				// if stock is available.. get either requested quantity if its lower than available stock or maximum stock.
				newOrderEntryLevel = Math.min(cartEntryLevel, stockLevel.longValue());
			}
		}
		else
		{
			// if stock is not available.. play save.. only allow quantity that was already in cart.
			newOrderEntryLevel = Math.min(cartEntryLevel, cartLevel);
		}

		// this product is not available at the given point of service.
		if (stockLevelForProductInBaseStore != null && stockLevelForProductInBaseStore.longValue() != 0)
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.MOVED_FROM_POS_TO_STORE);
			final CartEntryModel existingEntryForProduct = getExistingShipCartEntryForProduct(cartModel,
					cartEntryModel.getProduct());
			if (existingEntryForProduct != null)
			{
				getModelService().remove(cartEntryModel);
				final long quantityAdded = stockLevelForProductInBaseStore.longValue() >= cartLevel ? newOrderEntryLevel
						: cartLevel - stockLevelForProductInBaseStore.longValue();
				modification.setQuantityAdded(quantityAdded);
				final long updatedQuantity = (stockLevelForProductInBaseStore.longValue() <= cartLevel
						? stockLevelForProductInBaseStore.longValue()
						: cartLevel);
				modification.setQuantity(updatedQuantity);
				existingEntryForProduct.setQuantity(Long.valueOf(updatedQuantity));
				getModelService().save(existingEntryForProduct);
				modification.setEntry(existingEntryForProduct);
			}
			else
			{
				modification.setQuantityAdded(newOrderEntryLevel);
				modification.setQuantity(cartEntryLevel);
				cartEntryModel.setDeliveryPointOfService(null);
				modification.setEntry(cartEntryModel);
				getModelService().save(cartEntryModel);
			}

			getModelService().refresh(cartModel);

			return modification;
		}
		else if (hasConfigurationErrors(cartEntryModel))
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.CONFIGURATION_ERROR);
			modification.setQuantityAdded(cartEntryLevel);
			modification.setQuantity(cartEntryLevel);
			modification.setEntry(cartEntryModel);

			return modification;
		}
		else if (null != stockLevel && stockLevel <= Long.valueOf(0) && getInventoryHitCount(cartEntryModel))
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.LOW_STOCK);

			return modification;
		}
		else
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.SUCCESS);
			modification.setQuantityAdded(cartEntryLevel);
			modification.setQuantity(cartEntryLevel);
			modification.setEntry(cartEntryModel);

			return modification;
		}
	}

	@Override
	protected void validateDelivery(final CartModel cartModel)
	{
		if (cartModel.getDeliveryAddress() != null && isGuestUserCart(cartModel))
		{

			cartModel.setDeliveryAddress(null);
			getModelService().save(cartModel);

		}
	}

	protected boolean getInventoryHitCount(final CartEntryModel cartEntryModel)
	{
		Integer inventoryHitCount = Integer.valueOf(0);
		final Collection<StockLevelModel> stockLevelList = getStockService().getStockLevels(cartEntryModel.getProduct(),
				getBaseStoreService().getCurrentBaseStore().getWarehouses());
		if (CollectionUtils.isNotEmpty(stockLevelList))
		{
			inventoryHitCount = (((List<StockLevelModel>) stockLevelList).get(0).getInventoryHit());
		}
		if (null != inventoryHitCount && inventoryHitCount < 5)
		{
			return false;
		}
		return true;
	}

	/**
	 * @return the stockService
	 */
	public StockService getStockService()
	{
		return stockService;
	}

	/**
	 * @param stockService
	 *           the stockService to set
	 */
	public void setStockService(final StockService stockService)
	{
		this.stockService = stockService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	/**
	 * @return the storeFinderService
	 */
	public SiteOneStoreFinderService getStoreFinderService()
	{
		return storeFinderService;
	}

	/**
	 * @param storeFinderService the storeFinderService to set
	 */
	public void setStoreFinderService(SiteOneStoreFinderService storeFinderService)
	{
		this.storeFinderService = storeFinderService;
	}

}
