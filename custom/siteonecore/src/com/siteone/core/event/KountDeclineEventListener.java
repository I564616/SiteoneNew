/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import jakarta.annotation.Resource;

import com.siteone.core.model.KountDeclineProcessModel;


/**
 * @author BS
 *
 */
public class KountDeclineEventListener extends AbstractAcceleratorSiteEventListener<KountDeclineEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener#getSiteChannelForEvent(de.hybris.
	 * platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected SiteChannel getSiteChannelForEvent(final KountDeclineEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.commerceservices.event.AbstractSiteEventListener#onSiteEvent(de.hybris.platform.servicelayer.
	 * event.events.AbstractEvent)
	 */
	@Override
	protected void onSiteEvent(final KountDeclineEvent event)
	{
		final KountDeclineProcessModel kountProcessModel = (KountDeclineProcessModel) getBusinessProcessService()
				.createProcess("kountDeclineProcess-" + System.currentTimeMillis(), "kountDeclineProcess");
		kountProcessModel.setAccountName(event.getAccountName());
		kountProcessModel.setAccountNumber(event.getAccountNumber());
		kountProcessModel.setCustomerName(event.getCustomerName());
		kountProcessModel.setCustomerEmailAddress(event.getEmailAddress());
		kountProcessModel.setOrderNumber(event.getOrderNumber());
		kountProcessModel.setSite(event.getSite());
		kountProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());
		getModelService().save(kountProcessModel);
		getBusinessProcessService().startProcess(kountProcessModel);

	}

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



}
