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

import com.siteone.core.model.LinkToPayProcessModel;




/**
 * @author 1197861
 *
 */
public class LinkToPayEventListener extends AbstractAcceleratorSiteEventListener<LinkToPayEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

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
	protected SiteChannel getSiteChannelForEvent(final LinkToPayEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}

	@Override
	protected void onSiteEvent(final LinkToPayEvent event)
	{
		// YTODO Auto-generated method stub
		final LinkToPayProcessModel linkToPayProcessModel = (LinkToPayProcessModel) getBusinessProcessService()
				.createProcess("linkToPayProcess-" + System.currentTimeMillis(), "linkToPayEmailProcess");
		linkToPayProcessModel.setEmail(event.getEmail());
		linkToPayProcessModel.setDate(event.getDate());
		linkToPayProcessModel.setTime(event.getTime());
		linkToPayProcessModel.setOrderAmount(event.getOrderAmount());
		linkToPayProcessModel.setOrderNumber(event.getOrderNumber());
		linkToPayProcessModel.setSite(event.getSite());
		linkToPayProcessModel.setLanguage(event.getLanguage());
		linkToPayProcessModel.setStore(event.getBaseStore());

		getModelService().save(linkToPayProcessModel);
		getBusinessProcessService().startProcess(linkToPayProcessModel);


	}








}