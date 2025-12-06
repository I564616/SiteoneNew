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

import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.model.RequestAccountProcessModel;


/**
 * @author 1197861
 *
 */
public class RequestAccountEventListener extends AbstractAcceleratorSiteEventListener<RequestAccountEvent>
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener#getSiteChannelForEvent(de.hybris.
	 * platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected SiteChannel getSiteChannelForEvent(final RequestAccountEvent event)
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
	protected void onSiteEvent(final RequestAccountEvent event)
	{
		if (null != event.getIsEmailUniqueInHybris() && BooleanUtils.isNotTrue(event.getIsEmailUniqueInHybris()))
		{
			emailIdNotUniqueHybrisEmail(event);
		}
		else if (null != event.getIsCreateCustomerFail() && event.getIsCreateCustomerFail().booleanValue())
		{
			createCustomerFailureEmail(event);
		}
		else if (event.getTypeOfCustomer().equalsIgnoreCase("CONTRACTOR"))
		{

			if (event.getDunsResponse() != null && event.getDunsResponse().intValue() == 1)
			{
				createBranchManagerEmail(event);
			}
			else if (event.getDunsResponse() != null && (event.getDunsResponse().intValue() == 3
					|| event.getDunsResponse().intValue() == 4 || event.getDunsResponse().intValue() == 5))
			{
				createCustomerFailureEmail(event);
			}

		}

	}

	/**
	 * @param event
	 */
	private void createBranchManagerEmail(final RequestAccountEvent event)
	{
		final RequestAccountProcessModel requestProcessModel = (RequestAccountProcessModel) getBusinessProcessService()
				.createProcess("RequestAccountProcess-" + event.getCompanyName() + "-" + System.currentTimeMillis(),
						"managerEmailProcess");
		requestProcessModel.setAccountNumber(event.getAccountNumber());
		requestProcessModel.setCompanyName(event.getCompanyName());
		requestProcessModel.setAddressLine1(event.getAddressLine1());
		requestProcessModel.setAddressLine2(event.getAddressLine2());
		requestProcessModel.setCity(event.getCity());
		requestProcessModel.setProvince(event.getState());
		requestProcessModel.setZipcode(event.getZipcode());
		requestProcessModel.setPhoneNumber(event.getPhoneNumber());
		requestProcessModel.setEmailAddress(event.getEmailAddress());
		requestProcessModel.setContrPrimaryBusiness(event.getContrPrimaryBusiness());
		requestProcessModel.setServiceTypes(event.getServiceTypes());
		requestProcessModel.setContrYearsInBusiness(event.getContrYearsInBusiness());
		requestProcessModel.setContrEmpCount(event.getContrEmpCount());
		requestProcessModel.setLanguagePreference(event.getLanguagePreference());
		requestProcessModel.setBranchManagerEmail(event.getBranchManagerEmail());
		requestProcessModel.setBranchNotification(event.getBranchNotification());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		requestProcessModel.setStoreNumber(event.getStoreNumber());
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);
	}

	public void emailIdNotUniqueHybrisEmail(final RequestAccountEvent event)
	{
		final RequestAccountProcessModel requestAccountCustomerProcessModel = (RequestAccountProcessModel) getBusinessProcessService()
				.createProcess("RequestAccountCustomerEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(),
						"requestAccountCustomerEmailProcess");
		requestAccountCustomerProcessModel.setFirstName(event.getFirstName());
		requestAccountCustomerProcessModel.setEmailAddress(event.getEmailAddress());
		requestAccountCustomerProcessModel.setSite(event.getSite());
		requestAccountCustomerProcessModel.setLanguage(event.getLanguage());
		requestAccountCustomerProcessModel.setIsEmailUniqueInHybris(event.getIsEmailUniqueInHybris());
		requestAccountCustomerProcessModel.setEnrollInPartnersProgram(BooleanUtils.isTrue(event.getEnrollInPartnersProgram()));
		getModelService().save(requestAccountCustomerProcessModel);
		getBusinessProcessService().startProcess(requestAccountCustomerProcessModel);
	}

	public void createCustomerFailureEmail(final RequestAccountEvent event)
	{
		final RequestAccountProcessModel requestAccountProcessModel = (RequestAccountProcessModel) getBusinessProcessService()
				.createProcess("RequestAccountAdminEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(),
						"requestAccountEmailProcess");
		requestAccountProcessModel.setCompanyName(event.getCompanyName());
		requestAccountProcessModel.setAccountNumber(event.getAccountNumber());
		requestAccountProcessModel.setFirstName(event.getFirstName());
		requestAccountProcessModel.setLastName(event.getLastName());
		requestAccountProcessModel.setAddressLine1(event.getAddressLine1());
		requestAccountProcessModel.setAddressLine2(event.getAddressLine2());
		requestAccountProcessModel.setCity(event.getCity());
		requestAccountProcessModel.setProvince(event.getState());
		requestAccountProcessModel.setZipcode(event.getZipcode());
		requestAccountProcessModel.setPhoneNumber(event.getPhoneNumber());
		requestAccountProcessModel.setEmailAddress(event.getEmailAddress());
		requestAccountProcessModel.setHasAccountNumber(event.getHasAccountNumber());
		requestAccountProcessModel.setTypeOfCustomer(event.getTypeOfCustomer());
		requestAccountProcessModel.setContrYearsInBusiness(event.getContrYearsInBusiness());
		requestAccountProcessModel.setContrEmpCount(event.getContrEmpCount());
		requestAccountProcessModel.setContrPrimaryBusiness(event.getContrPrimaryBusiness());
		requestAccountProcessModel.setServiceTypes(event.getServiceTypes());
		requestAccountProcessModel.setLanguagePreference(event.getLanguagePreference());
		requestAccountProcessModel.setLanguage(event.getLanguage());
		requestAccountProcessModel.setSite(event.getSite());
		requestAccountProcessModel.setStore(event.getBaseStore());
		requestAccountProcessModel.setIsAccountOwner(event.getIsAccountOwner());
		requestAccountProcessModel.setStoreNumber(event.getStoreNumber());
		requestAccountProcessModel.setEnrollInPartnersProgram(BooleanUtils.isTrue(event.getEnrollInPartnersProgram()));


		if ("Contractor".equalsIgnoreCase(event.getTypeOfCustomer()) && event.getDunsResponse() != null
				&& (event.getDunsResponse().intValue() == 3 || event.getDunsResponse().intValue() == 4
						|| event.getDunsResponse().intValue() == 5))
		{
			requestAccountProcessModel.setDunsResponse(event.getDunsResponse());
			if (event.getDunsResponse().intValue() != 5)
			{
				requestAccountProcessModel.setDunsMatchCandidates(event.getCandidates());
			}
		}


		getModelService().save(requestAccountProcessModel);
		getBusinessProcessService().startProcess(requestAccountProcessModel);
	}




}