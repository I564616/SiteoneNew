/**
 *
 */
package com.siteone.core.event;

/**
*
*/

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import jakarta.annotation.Resource;

import com.siteone.core.model.DeclinedCardAttemptEmailProcessModel;


/**
 * @author PP10513
 *
 */
public class DeclinedCardAttemptEmailEventListener extends AbstractAcceleratorSiteEventListener<DeclinedCardAttemptEmailEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
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
	 * @return the businessProcessService
	 */
	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService
	 *           the businessProcessService to set
	 */
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	@Override
	protected void onSiteEvent(final DeclinedCardAttemptEmailEvent event)
	{
		final DeclinedCardAttemptEmailProcessModel processModel = (DeclinedCardAttemptEmailProcessModel) getBusinessProcessService()
				.createProcess("DeclinedCardAttemptEmailProcess-" + System.currentTimeMillis(), "declinedCardAttemptEmailProcess");

		processModel.setToEmails(event.getEmailAddress());
		processModel.setOrderAmount(event.getOrderAmount());
		processModel.setOrderNumber(event.getOrderNumber());
		processModel.setCustomerName(event.getCustomerName());
		processModel.setSite(event.getSite());
		processModel.setDate(event.getDate());
		processModel.setLanguage(getCommonI18NService().getCurrentLanguage());
		getModelService().save(processModel);
		getBusinessProcessService().startProcess(processModel);
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final DeclinedCardAttemptEmailEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

}




