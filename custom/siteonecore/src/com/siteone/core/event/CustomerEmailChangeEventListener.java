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
import de.hybris.platform.b2b.model.B2BCustomerModel;

import com.siteone.core.model.CustomerUpdateNewEmailProcessModel;
import com.siteone.core.model.CustomerUpdateOldEmailProcessModel;

/**
 * @author ASaha
 *
 */
public class CustomerEmailChangeEventListener extends AbstractAcceleratorSiteEventListener<OktaEmailChangeEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Override
	protected void onSiteEvent(final OktaEmailChangeEvent event)
	{
		final CustomerUpdateOldEmailProcessModel customerEmailChangeOldEmailProcessModel = (CustomerUpdateOldEmailProcessModel) getBusinessProcessService()
				.createProcess("oktaEmailChange-" + event.getCustomer().getUid() + "-" + System.currentTimeMillis(),
						"customerEmailChangeOldEmailProcess");
		customerEmailChangeOldEmailProcessModel.setSite(event.getSite());
		customerEmailChangeOldEmailProcessModel.setCustomer(event.getCustomer());
		customerEmailChangeOldEmailProcessModel.setOldEmail(((B2BCustomerModel)event.getCustomer()).getOldContactEmail());
		customerEmailChangeOldEmailProcessModel.setNewEmail(((B2BCustomerModel)event.getCustomer()).getUid());
		customerEmailChangeOldEmailProcessModel.setLanguage(event.getLanguage());
		customerEmailChangeOldEmailProcessModel.setCurrency(event.getCurrency());
		customerEmailChangeOldEmailProcessModel.setStore(event.getBaseStore());
		customerEmailChangeOldEmailProcessModel.setFirstName(((B2BCustomerModel)event.getCustomer()).getFirstName());
		customerEmailChangeOldEmailProcessModel.setToken(event.getToken());

		getModelService().save(customerEmailChangeOldEmailProcessModel);
		getBusinessProcessService().startProcess(customerEmailChangeOldEmailProcessModel);

		final CustomerUpdateNewEmailProcessModel customerEmailChangeNewEmailProcessModel = (CustomerUpdateNewEmailProcessModel) getBusinessProcessService()
				.createProcess("oktaEmailChange-" + event.getCustomer().getUid() + "-" + System.currentTimeMillis(),
						"customerEmailChangeNewEmailProcess");
		customerEmailChangeNewEmailProcessModel.setSite(event.getSite());
		customerEmailChangeNewEmailProcessModel.setCustomer(event.getCustomer());
		customerEmailChangeNewEmailProcessModel.setOldEmail(((B2BCustomerModel)event.getCustomer()).getOldContactEmail());
		customerEmailChangeNewEmailProcessModel.setNewEmail(((B2BCustomerModel)event.getCustomer()).getUid());
		customerEmailChangeNewEmailProcessModel.setLanguage(event.getLanguage());
		customerEmailChangeNewEmailProcessModel.setCurrency(event.getCurrency());
		customerEmailChangeNewEmailProcessModel.setStore(event.getBaseStore());
		customerEmailChangeNewEmailProcessModel.setFirstName(((B2BCustomerModel)event.getCustomer()).getFirstName());
		customerEmailChangeNewEmailProcessModel.setToken(event.getToken());

		getModelService().save(customerEmailChangeNewEmailProcessModel);
		getBusinessProcessService().startProcess(customerEmailChangeNewEmailProcessModel);
		
		if(event.getCustomer() != null && event.getCustomer() instanceof B2BCustomerModel)
		{
			B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) event.getCustomer();
			b2bCustomerModel.setUpdateEmailFlag(null);
			getModelService().save(b2bCustomerModel);
		}
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final OktaEmailChangeEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
		return site.getChannel();
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the businessProcessService
	 */
	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService the businessProcessService to set
	 */
	public void setBusinessProcessService(BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}



}