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

import com.siteone.core.model.PointsForEquipmentProcessModel;


/**
 * @author 1003567
 *
 */
public class PointsForEquipmentEventListener extends AbstractAcceleratorSiteEventListener<PointsForEquipmentEvent>
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
	protected SiteChannel getSiteChannelForEvent(final PointsForEquipmentEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final PointsForEquipmentEvent event)
	{
		final PointsForEquipmentProcessModel requestProcessModel = (PointsForEquipmentProcessModel) getBusinessProcessService()
				.createProcess("pointsForEquipmentEmailProcess-" + event.getDealerContactName() + "-" + System.currentTimeMillis(),
						"pointsForEquipmentEmailProcess");
		requestProcessModel.setDealerName(event.getDealerName());
		requestProcessModel.setDealerContactName(event.getDealerContactName());
		requestProcessModel.setDealerAddressLine1(event.getDealerAddressLine1());
		requestProcessModel.setDealerAddressLine2(event.getDealerAddressLine2());
		requestProcessModel.setDealerCity(event.getDealerCity());
		requestProcessModel.setDealerState(event.getDealerState());
		requestProcessModel.setDealerZipCode(event.getDealerZipCode());
		requestProcessModel.setCustomerContactName(event.getCustomerContactName());
		requestProcessModel.setCustomerAddressLine1(event.getCustomerAddressLine1());
		requestProcessModel.setCustomerAddressLine2(event.getCustomerAddressLine2());
		requestProcessModel.setCustomerCity(event.getCustomerCity());
		requestProcessModel.setCustomerState(event.getCustomerState());
		requestProcessModel.setCustomerZipCode(event.getCustomerZipCode());
		requestProcessModel.setCustEmailAddress(event.getEmailAddress());
		requestProcessModel.setPhoneNum(event.getPhoneNumber());
		requestProcessModel.setCompanyName(event.getCompanyName());
		requestProcessModel.setJdlAccountNumber(event.getJdlAccountNumber());
		requestProcessModel.setSite(event.getSite());
		if (event.getFaxNumber() != null)
		{
			requestProcessModel.setFaxNum(event.getFaxNumber());
		}
		requestProcessModel.setDateOfPurProduct1(event.getDateOfPurProduct1());
		requestProcessModel.setItemDescProduct1(event.getItemDescProduct1());
		requestProcessModel.setSerialNumberProduct1(event.getSerialNumberProduct1());
		requestProcessModel.setInvoiceCostProduct1(event.getInvoiceCostProduct1());
		requestProcessModel.setLanguage(event.getLanguage());
		if (event.getDateOfPurProduct2() != null)
		{
			requestProcessModel.setDateOfPurProduct2(event.getDateOfPurProduct2());
		}

		if (event.getItemDescProduct2() != null)
		{
			requestProcessModel.setItemDescProduct2(event.getItemDescProduct2());
		}
		if (event.getSerialNumberProduct2() != null)
		{
			requestProcessModel.setSerialNumberProduct2(event.getSerialNumberProduct2());
		}
		if (event.getInvoiceCostProduct2() != null)
		{
			requestProcessModel.setInvoiceCostProduct2(event.getInvoiceCostProduct2());
		}
		if (event.getDateOfPurProduct3() != null)
		{
			requestProcessModel.setDateOfPurProduct3(event.getDateOfPurProduct3());
		}

		if (event.getItemDescProduct3() != null)
		{
			requestProcessModel.setItemDescProduct3(event.getItemDescProduct3());
		}
		if (event.getSerialNumberProduct3() != null)
		{
			requestProcessModel.setSerialNumberProduct3(event.getSerialNumberProduct3());
		}
		if (event.getInvoiceCostProduct3() != null)
		{
			requestProcessModel.setInvoiceCostProduct3(event.getInvoiceCostProduct3());
		}
		if (event.getDateOfPurProduct4() != null)
		{
			requestProcessModel.setDateOfPurProduct4(event.getDateOfPurProduct4());
		}

		if (event.getItemDescProduct4() != null)
		{
			requestProcessModel.setItemDescProduct4(event.getItemDescProduct4());
		}
		if (event.getSerialNumberProduct4() != null)
		{
			requestProcessModel.setSerialNumberProduct4(event.getSerialNumberProduct4());
		}
		if (event.getInvoiceCostProduct4() != null)
		{
			requestProcessModel.setInvoiceCostProduct4(event.getInvoiceCostProduct4());
		}
		if (event.getDateOfPurProduct5() != null)
		{
			requestProcessModel.setDateOfPurProduct5(event.getDateOfPurProduct5());
		}

		if (event.getItemDescProduct5() != null)
		{
			requestProcessModel.setItemDescProduct5(event.getItemDescProduct5());
		}
		if (event.getSerialNumberProduct5() != null)
		{
			requestProcessModel.setSerialNumberProduct5(event.getSerialNumberProduct5());
		}
		if (event.getInvoiceCostProduct5() != null)
		{
			requestProcessModel.setInvoiceCostProduct5(event.getInvoiceCostProduct5());
		}

		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);

	}
}
