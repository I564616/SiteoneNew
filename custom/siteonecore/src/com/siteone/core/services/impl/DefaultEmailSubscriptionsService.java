/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.servicelayer.model.ModelService;

import com.siteone.core.model.EmailSubscriptionsModel;
import com.siteone.core.services.EmailSubscriptionsService;


/**
 * @author 1091124
 *
 */
public class DefaultEmailSubscriptionsService implements EmailSubscriptionsService
{
	private ModelService modelService;


	@Override
	public void setSubscribedEmail(final EmailSubscriptionsModel emailSubscriptionsModel)
	{
		modelService.save(emailSubscriptionsModel);
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



}
