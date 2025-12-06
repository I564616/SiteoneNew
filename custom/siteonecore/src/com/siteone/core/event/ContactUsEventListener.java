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

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.siteone.core.model.ContactUsProcessModel;


/**
 * @author 1003567
 *
 */
public class ContactUsEventListener extends AbstractAcceleratorSiteEventListener<ContactUsEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	private static final String CONTACT_HARDSCAPES = "Hardscapes";

	private static final String CONTACT_DRAINAGE_SUPPORT = "Request design support for Drainage";
	private static final String CONTACT_DRAINAGE_CALLBACK = "Request a call back from SiteOne for Drainage";

	private static final String CONTACT_DRAINAGE_SUPPORT_ES = "Solicita ayuda en diseño para drenaje";
	private static final String CONTACT_DRAINAGE_CALLBACK_ES = "Solicita una llamada de SiteOne para drenaje";

	private static final String CONTACT_LIGHTING_CALLBACK = "Request a call back from SiteOne for Lighting";
	private static final String CONTACT_LIGHTING_DEMO = "Request a Demo for Lighting";
	private static final String CONTACT_LIGHTING_SUPPORT = "Request Design support for Lighting";
	private static final String CONTACT_LIGHTING_OUTDOOR = "Outdoor Lighting";

	private static final String CONTACT_LIGHTING_CALLBACK_ES = "Solicita una llamada de SiteOne para iluminación";
	private static final String CONTACT_LIGHTING_DEMO_ES = "Solicita demo de iluminación";
	private static final String CONTACT_LIGHTING_SUPPORT_ES = "Solicita ayuda en diseño de iluminación";
	private static final String CONTACT_LIGHTING_OUTDOOR_ES = "Iluminación de Exteriores";


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
	protected SiteChannel getSiteChannelForEvent(final ContactUsEvent event)
	{
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}


	@Override
	protected void onSiteEvent(final ContactUsEvent event)
	{
		final ContactUsProcessModel requestProcessModel = (ContactUsProcessModel) getBusinessProcessService().createProcess(
				"contactUsEmailProcess-" + event.getFirstName() + "-" + System.currentTimeMillis(), "contactUsEmailProcess");
		requestProcessModel.setReasonForContact(event.getReasonForContact());
		requestProcessModel.setFirstName(event.getFirstName());
		requestProcessModel.setLastName(event.getLastName());
		requestProcessModel.setTypeOfCustomer(event.getTypeOfCustomer());
		requestProcessModel.setMessageforContactUS(event.getMessage());
		requestProcessModel.setCustomerNumber(event.getCustomerNumber());
		requestProcessModel.setContactNumber(event.getPhoneNumber());
		requestProcessModel.setCustomerEmail(event.getEmail());
		requestProcessModel.setSite(event.getSite());
		requestProcessModel.setLanguage(event.getLanguage());
		requestProcessModel.setCustomerCity(event.getCustomerCity());
		requestProcessModel.setCustomerState(event.getCustomerState());
		requestProcessModel.setProjectZipcode(event.getProjectZipcode());
		if (!StringUtils.isEmpty(event.getStreetAddress()))
		{
			requestProcessModel.setStreetAddress(event.getStreetAddress());
		}
		if (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_HARDSCAPES)
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_DRAINAGE_SUPPORT))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_DRAINAGE_CALLBACK))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_DRAINAGE_SUPPORT_ES))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_DRAINAGE_CALLBACK_ES))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_DEMO))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_CALLBACK))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_SUPPORT))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_OUTDOOR))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_CALLBACK_ES))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_DEMO_ES))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_SUPPORT_ES))
				|| (requestProcessModel.getReasonForContact().equalsIgnoreCase(CONTACT_LIGHTING_OUTDOOR_ES)))
		{
			requestProcessModel.setProjectStartDate(event.getProjectStartDate());
			requestProcessModel.setInPhaseOf(event.getInPhaseOf());
			requestProcessModel.setMyProject(event.getMyProject());
			requestProcessModel.setMyBudget(event.getMyBudget());
			requestProcessModel.setIdentity(event.getIdentity());
			requestProcessModel.setIdentityName(event.getIdentityName());
		}
		getModelService().save(requestProcessModel);
		getBusinessProcessService().startProcess(requestProcessModel);


	}
}
