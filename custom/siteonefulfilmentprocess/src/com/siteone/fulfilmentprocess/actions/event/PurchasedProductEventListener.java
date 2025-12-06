/**
 *
 */
package com.siteone.fulfilmentprocess.actions.event;

import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.fulfilmentprocess.service.PurchasedProductService;




/**
 * @author PBurnwal
 *
 */
public class PurchasedProductEventListener extends AbstractSiteEventListener<PurchasedProductEvent>
{

	private static final Logger LOG = Logger.getLogger(PurchasedProductEventListener.class);

	private ModelService modelService;

	private PurchasedProductService PurchasedProductService;

	@Resource(name = "defaultSiteOneB2BUnitService")
	private SiteOneB2BUnitService siteOneB2BUnitService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.commerceservices.event.AbstractSiteEventListener#onSiteEvent(de.hybris.platform.servicelayer.
	 * event.events.AbstractEvent)
	 */
	@Override
	protected void onSiteEvent(final PurchasedProductEvent event)
	{
		// YTODO Auto-generated method stub
		LOG.error("Inside PurchasedProductEventListener onSiteEvent");

		final OrderModel order = event.getProcess().getOrder();
		try
		{
			order.getEntries().forEach(entry -> {
				final ProductModel product = entry.getProduct();
				/*
				 * // final PurchProductAndOrdersModel purchasedProduct =
				 * PurchasedProductService.getPurchasedProductForSku(product.getCode()); // if (null != purchasedProduct) //
				 * { // final Set<AbstractOrderModel> orders = new HashSet<AbstractOrderModel>(); // orders.add(order); //
				 * orders.addAll(purchasedProduct.getOrders()); // purchasedProduct.setOrders(orders); //
				 * getModelService().save(purchasedProduct); // getModelService().refresh(purchasedProduct); // } // else //
				 * {
				 */ //final Set<AbstractOrderModel> orders = new HashSet<AbstractOrderModel>();
					//orders.add(order);

				final PurchProductAndOrdersModel newPurchasedProduct = getModelService().create(PurchProductAndOrdersModel.class);
				newPurchasedProduct.setProductCode(product.getCode());
				newPurchasedProduct.setProductName(product.getProductShortDesc());
				newPurchasedProduct.setItemNumber(product.getItemNumber());
				newPurchasedProduct.setOrderId(order.getCode());
				newPurchasedProduct.setUniqueKeyPurchasedProduct(order.getCode() + "-" + product.getCode());
				newPurchasedProduct.setOrderingAccount(entry.getOrder().getOrderingAccount().getUid());
				newPurchasedProduct
						.setMainAccount(siteOneB2BUnitService.getMainAccountForUnit(entry.getOrder().getUnit().getUid()).getUid());
				newPurchasedProduct.setPurchasedDate(entry.getCreationtime());
				getModelService().save(newPurchasedProduct);
				getModelService().refresh(newPurchasedProduct);
				LOG.info("Inside PurchasedProductEventListener ");

				//}
			});


		}
		catch (

		final Exception e)
		{
			LOG.error("Exception while saving purchased products", e);
			//irrespective of the error order should reach backend system.

		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.commerceservices.event.AbstractSiteEventListener#shouldHandleEvent(de.hybris.platform.
	 * servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected boolean shouldHandleEvent(final PurchasedProductEvent event)
	{
		// YTODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the purchasedProductService
	 */
	public PurchasedProductService getPurchasedProductService()
	{
		return PurchasedProductService;
	}

	/**
	 * @param purchasedProductService
	 *           the purchasedProductService to set
	 */
	public void setPurchasedProductService(final PurchasedProductService purchasedProductService)
	{
		PurchasedProductService = purchasedProductService;
	}

	/**
	 * @return the siteOnePurchasedProductsDao
	 */



}
