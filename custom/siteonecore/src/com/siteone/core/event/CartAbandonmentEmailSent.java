/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.model.ModelService;

import com.siteone.core.model.CartAbandonmentEmailProcessModel;


/**
 * @author 1129929
 *
 */
public class CartAbandonmentEmailSent extends AbstractProceduralAction<CartAbandonmentEmailProcessModel>
{


	private ModelService modelService;

	/**
	 * @return the modelService
	 */
	@Override
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.processengine.action.AbstractProceduralAction#executeAction(de.hybris.platform.processengine.model
	 * .BusinessProcessModel)
	 */
	@Override
	public void executeAction(final CartAbandonmentEmailProcessModel cartAbandonmentEmailProcessModel)
	{
		final CartModel cartModel = cartAbandonmentEmailProcessModel.getCart();
		cartModel.setIsCartAbandonmentEmailSent(Boolean.TRUE);
		getModelService().save(cartModel);
		getModelService().refresh(cartModel);

	}


}
