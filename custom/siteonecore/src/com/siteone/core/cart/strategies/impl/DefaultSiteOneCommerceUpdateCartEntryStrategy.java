/**
 *
 */
package com.siteone.core.cart.strategies.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceUpdateCartEntryStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import com.siteone.core.cart.strategies.SiteOneCommerceUpdateCartEntryStrategy;
import com.siteone.core.store.services.SiteOneStoreFinderService;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneCommerceUpdateCartEntryStrategy extends DefaultCommerceUpdateCartEntryStrategy
		implements SiteOneCommerceUpdateCartEntryStrategy
{
	private SessionService sessionService;
	private SiteOneStoreFinderService siteOneStoreFinderService;

	/**
	 * Work out the allowed quantity adjustment for a product in the cart given a requested quantity change.
	 *
	 * @param cartModel
	 *           the cart
	 * @param productModel
	 *           the product in the cart
	 * @param quantityToAdd
	 *           the amount to increase the quantity of the product in the cart, may be negative if the request is to reduce
	 *           the quantity
	 * @param pointOfServiceModel
	 *           the PointOfService to check stock at, can be null
	 * @return the allowed adjustment
	 */

	@Override
	public CommerceCartModification updateQuantityForCartEntry(final CommerceCartParameter parameters)
			throws CommerceCartModificationException
	{
		beforeUpdateCartEntry(parameters);
		final CartModel cartModel = parameters.getCart();
		long newQuantity = parameters.getQuantity();
		final long entryNumber = parameters.getEntryNumber();
		final long quantityToAdd;
		final long maxQuantity = Config.getInt("cart.max.quantity", 99999);

		validateParameterNotNull(cartModel, "Cart model cannot be null");
		CommerceCartModification modification;

		final AbstractOrderEntryModel entryToUpdate = getEntryForNumber(cartModel, (int) entryNumber);
		validateEntryBeforeModification(newQuantity, entryToUpdate);
		// Work out how many we want to add (could be negative if we are
		// removing items)
		if (newQuantity > maxQuantity)
		{
			newQuantity = maxQuantity;
		}
		quantityToAdd = newQuantity - entryToUpdate.getQuantity().longValue();
		if (entryToUpdate.getDeliveryPointOfService() != null)
		{
			final long actualAllowedQuantityChange = quantityToAdd;
			modification = modifyEntry(cartModel, entryToUpdate, actualAllowedQuantityChange, newQuantity);
			return modification;
		}
		else
		{
			final long actualAllowedQuantityChange = quantityToAdd;
			modification = modifyEntry(cartModel, entryToUpdate, actualAllowedQuantityChange, newQuantity);
			afterUpdateCartEntry(parameters, modification);
			return modification;
		}

	}


	@Override
	public CommerceCartModification modifyEntry(final CartModel cartModel, final AbstractOrderEntryModel entryToUpdate,
			final long actualAllowedQuantityChange, final long newQuantity)
	{
		// Now work out how many that leaves us with on this entry
		final long entryNewQuantity = entryToUpdate.getQuantity().longValue() + actualAllowedQuantityChange;

		final ModelService modelService = getModelService();

		if (entryNewQuantity <= 0)
		{
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
			entry.setProduct(entryToUpdate.getProduct());

			// The allowed new entry quantity is zero or negative
			// just remove the entry
			modelService.remove(entryToUpdate);
			modelService.refresh(cartModel);
			normalizeEntryNumbers(cartModel);
			//getCommerceCartCalculationStrategy().calculateCart(cartModel);

			// Return an empty modification
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setEntry(entry);
			modification.setQuantity(0);
			// We removed all the quantity from this row
			modification.setQuantityAdded(-entryToUpdate.getQuantity().longValue());

			if (newQuantity == 0)
			{
				modification.setStatusCode(CommerceCartModificationStatus.SUCCESS);
			}
			else
			{
				modification.setStatusCode(CommerceCartModificationStatus.LOW_STOCK);
			}

			return modification;
		}
		else
		{
			// Adjust the entry quantity to the new value
			entryToUpdate.setQuantity(Long.valueOf(entryNewQuantity));
			modelService.save(entryToUpdate);
			modelService.refresh(cartModel);
			//getCommerceCartCalculationStrategy().calculateCart(cartModel);
			modelService.refresh(entryToUpdate);

			// Return the modification data
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setQuantityAdded(actualAllowedQuantityChange);
			modification.setEntry(entryToUpdate);
			modification.setQuantity(entryNewQuantity);
			modification.setStatusCode(CommerceCartModificationStatus.SUCCESS);

			return modification;
		}
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


}

