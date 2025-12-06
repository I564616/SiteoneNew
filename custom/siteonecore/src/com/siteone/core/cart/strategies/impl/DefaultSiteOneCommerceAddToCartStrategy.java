/**
 *
 */

package com.siteone.core.cart.strategies.impl;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.store.services.SiteOneStoreFinderService;


/**
 * @author 1091124
 *
 */

public class DefaultSiteOneCommerceAddToCartStrategy extends DefaultCommerceAddToCartStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCommerceAddToCartStrategy.class);

	private SessionService sessionService;
	private SiteOneStoreFinderService siteOneStoreFinderService;
	private CommerceStockService commerceStockService;
	@Resource(name = "unitService")
	private UnitService unitService;

	/**
	 * Adds an item to the cart for pickup in a given location
	 *
	 * @param parameter
	 *           Cart parameters
	 * @return Cart modification information
	 * @throws de.hybris.platform.commerceservices.order.CommerceCartModificationException
	 *
	 */
	@Override
	public CommerceCartModification addToCart(final CommerceCartParameter parameter)
			throws CommerceCartModificationException, ResourceAccessException
	{
		final CommerceCartModification modification = doAddToCart(parameter);
		getCommerceCartCalculationStrategy().calculateCart(parameter);
		afterAddToCart(parameter, modification);
		// Here the entry is fully populated, so we can search for a similar one and merge.
		mergeEntry(modification, parameter);
		return modification;
	}

	@Override
	protected CommerceCartModification doAddToCart(final CommerceCartParameter parameter) throws CommerceCartModificationException
	{
		CommerceCartModification modification;
		long quantityToAdd;
		final long maxQuantity = Config.getInt("cart.max.quantity", 99999);
		quantityToAdd = parameter.getQuantity();
		if (quantityToAdd > maxQuantity)
		{
			quantityToAdd = maxQuantity;
		}

		this.beforeAddToCart(parameter);
		validateAddToCart(parameter);

		if (isProductForCode(parameter).booleanValue())
		{
			// So now work out what the maximum allowed to be added is (note that this may be negative!)
			final long actualAllowedQuantityChange = quantityToAdd;

			if (actualAllowedQuantityChange > 0)
			{
				// We are allowed to add items to the cart
				final CartEntryModel entryModel = addCartEntry(parameter, actualAllowedQuantityChange);
				entryModel.setInventoryUOM(parameter.getInventoryUOM());
				entryModel.setIsBaseUom(parameter.getIsBaseUom()!=null?parameter.getIsBaseUom():true);
				getModelService().save(entryModel);

				final String statusCode = CommerceCartModificationStatus.SUCCESS;

				modification = createAddToCartResp(parameter, statusCode, entryModel, actualAllowedQuantityChange);
			}
			else
			{
				// Not allowed to add any quantity, or maybe even asked to reduce the quantity
				// Do nothing!
				LOG.info("DefaultSiteOneCommerceAddToCartStrategy : Quantity not allowed");
				final String status = CommerceCartModificationStatus.NO_STOCK;

				modification = createAddToCartResp(parameter, status, createEmptyCartEntry(parameter), 0);

			}
		}
		else
		{
			LOG.error("DefaultSiteOneCommerceAddToCartStrategy: Product does not exist: " +
					(parameter.getProduct() != null ? parameter.getProduct().getCode() : ""));
			modification = createAddToCartResp(parameter, CommerceCartModificationStatus.UNAVAILABLE,
					createEmptyCartEntry(parameter), 0);
		}

		return modification;
	}

	@Override
	protected UnitModel getUnit(final CommerceCartParameter parameter) throws CommerceCartModificationException
	{
		return unitService.getUnitForCode("pieces");
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

	/**
	 * @return the siteOneStoreFinderService
	 */

	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}

	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */

	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}


	/**
	 * @return the commerceStockService
	 */

	@Override
	public CommerceStockService getCommerceStockService()
	{
		return commerceStockService;
	}


	/**
	 * @param commerceStockService
	 *           the commerceStockService to set
	 */
	@Override
	public void setCommerceStockService(final CommerceStockService commerceStockService)
	{
		this.commerceStockService = commerceStockService;
	}

}
