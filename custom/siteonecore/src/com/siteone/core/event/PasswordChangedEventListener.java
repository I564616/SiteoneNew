/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.PasswordChangedEmailProcessModel;


/**
 * @author 1099417
 *
 */
public class PasswordChangedEventListener extends AbstractAcceleratorSiteEventListener<PasswordChangedEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;


	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	/**
	 * @return the modelService
	 */
	protected ModelService getModelService()
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

	@Override
	protected void onSiteEvent(final PasswordChangedEvent event)
	{
		final PasswordChangedEmailProcessModel passwordChangedEmailProcessModel = (PasswordChangedEmailProcessModel) getBusinessProcessService()
				.createProcess("passwordChanged-" + event.getCustomer().getUid() + "-" + System.currentTimeMillis(),
						"passwordChangedEmailProcess");
		passwordChangedEmailProcessModel.setSite(event.getSite());
		passwordChangedEmailProcessModel.setCustomer(event.getCustomer());
		passwordChangedEmailProcessModel.setLanguage(event.getLanguage());
		passwordChangedEmailProcessModel.setCurrency(event.getCurrency());
		passwordChangedEmailProcessModel.setStore(event.getBaseStore());
		getModelService().save(passwordChangedEmailProcessModel);
		getBusinessProcessService().startProcess(passwordChangedEmailProcessModel);
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final PasswordChangedEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
		return site.getChannel();
	}
}

