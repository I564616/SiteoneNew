package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.siteone.core.model.CreatePasswordEmailProcessModel;


/**
 * Listener for "creates password" functionality event.
 */
public class CreatePasswordEventListener extends AbstractAcceleratorSiteEventListener<CreatePasswordEvent>
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
	protected void onSiteEvent(final CreatePasswordEvent event)
	{
		final CreatePasswordEmailProcessModel createPasswordEmailProcessModel = (CreatePasswordEmailProcessModel) getBusinessProcessService()
				.createProcess("createPassword-" + event.getCustomer().getUid() + "-" + System.currentTimeMillis(),
						"createPasswordEmailProcess");
		createPasswordEmailProcessModel.setSite(event.getSite());
		createPasswordEmailProcessModel.setCustomer(event.getCustomer());
		createPasswordEmailProcessModel.setToken(event.getToken());
		createPasswordEmailProcessModel.setLanguage(event.getLanguage());
		createPasswordEmailProcessModel.setCurrency(event.getCurrency());
		createPasswordEmailProcessModel.setStore(event.getBaseStore());
		createPasswordEmailProcessModel.setExpirationTimeInSeconds(Long.valueOf(event.getExpirationTimeInSeconds()));
		getModelService().save(createPasswordEmailProcessModel);
		getBusinessProcessService().startProcess(createPasswordEmailProcessModel);
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final CreatePasswordEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
		return site.getChannel();
	}
}
