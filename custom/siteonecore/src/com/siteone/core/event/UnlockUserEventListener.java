package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.UnlockUserEmailProcessModel;



/**
 * Listener for "unlock user" functionality event.
 */
public class UnlockUserEventListener extends AbstractAcceleratorSiteEventListener<UnlockUserEvent>
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
	protected void onSiteEvent(final UnlockUserEvent event)
	{
		final UnlockUserEmailProcessModel unlockUserEmailProcessModel = (UnlockUserEmailProcessModel) getBusinessProcessService()
				.createProcess("unlockUser-" + event.getCustomer().getUid() + "-" + System.currentTimeMillis(),
						"unlockUserEmailProcess");
		unlockUserEmailProcessModel.setSite(event.getSite());
		unlockUserEmailProcessModel.setCustomer(event.getCustomer());
		unlockUserEmailProcessModel.setToken(event.getToken());
		unlockUserEmailProcessModel.setLanguage(event.getLanguage());
		unlockUserEmailProcessModel.setCurrency(event.getCurrency());
		unlockUserEmailProcessModel.setStore(event.getBaseStore());
		unlockUserEmailProcessModel.setExpirationTimeInSeconds(Long.valueOf(event.getExpirationTimeInSeconds()));
		getModelService().save(unlockUserEmailProcessModel);
		getBusinessProcessService().startProcess(unlockUserEmailProcessModel);
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final UnlockUserEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
		return site.getChannel();
	}
}
