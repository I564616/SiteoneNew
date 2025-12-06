/**
 *
 */
package com.siteone.core.jobs.customer.service.impl;


import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.commerceservices.security.SecureToken;
import de.hybris.platform.commerceservices.security.SecureTokenService;

import de.hybris.platform.util.Config;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.enums.UpdateEmailStatusEnum;
import com.siteone.core.jobs.customer.dao.CustomerUpdateCronJobDao;
import com.siteone.core.jobs.customer.service.CustomerUpdateCronJobService;
import com.siteone.core.model.CustomerUpdateCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.core.event.OktaEmailChangeEvent;
import java.util.Date;


/**
 * @author ASaha
 *
 */
public class DefaultCustomerUpdateCronJobService implements CustomerUpdateCronJobService
{
	private static final Logger LOGGER = Logger.getLogger(DefaultCustomerUpdateCronJobService.class);

	private CustomerUpdateCronJobDao oktaCustomerEmailUpdateCronJobDao;

	@Resource(name = "oktaAPI")
	private OKTAAPI oktaAPI;

	@Resource(name = "userService")
	private UserService userService;

	private ModelService modelService;
	

	private BaseStoreService baseStoreService;
	private EventService eventService;
	private CommonI18NService commonI18NService;
	private BaseSiteService baseSiteService;
	private SecureTokenService secureTokenService;


	@Override
	public void getCustomerForEmailUpdate(final CustomerUpdateCronJobModel oktaCustomerEmailUpdateCronJobModel)
	{
		final List<B2BCustomerModel> b2bCustomers = getCustomerEmailUpdateCronJobDao().getCustomerForEmailUpdate();
		LOGGER.info("b2bCustomers==" + b2bCustomers);

		if (null != b2bCustomers)
		{
			for (final B2BCustomerModel b2bCustomer : b2bCustomers)
			{
				LOGGER.info(b2bCustomer.getContactEmail() + "---" + b2bCustomer.getOldContactEmail());
				updateOktaEmail(b2bCustomer.getUid());
			}
		}
	}

	protected void updateOktaEmail(final String customerUid)
	{
		final B2BCustomerModel b2bCustomerModel = this.getUserService().getUserForUID(customerUid, B2BCustomerModel.class);

		if (null != b2bCustomerModel)
		{
			OktaCreateOrUpdateUserResponseData responseData = null;
			try
			{
				responseData = getOktaAPI().updateUser(b2bCustomerModel);
				

				if (null == responseData)
				{
					LOGGER.error("Not able to update contact in Okta for the customer "+customerUid);
				}
				else if (responseData.getStatus() != null && responseData.getStatus().equalsIgnoreCase("STAGED"))
				{
					b2bCustomerModel.setUpdateEmailFlag(UpdateEmailStatusEnum.SEND_EMAIL);
					modelService.save(b2bCustomerModel);
					modelService.refresh(b2bCustomerModel);
					
					final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_FORGOT_PASSWORD, 1800);
					final long timeStamp = tokenValiditySeconds > 0L ? new Date().getTime() : 0L;
					final SecureToken data = new SecureToken(b2bCustomerModel.getUid(), timeStamp);
					final String token = getSecureTokenService().encryptData(data);
					
					OktaEmailChangeEvent oktaEmailChangeEvent = new OktaEmailChangeEvent(token);
					/*oktaEmailChangeEvent.setFirstName(b2bCustomerModel.getFirstName());
					oktaEmailChangeEvent.setOldEmail(b2bCustomerModel.getOldContactEmail());
					oktaEmailChangeEvent.setNewEmail(b2bCustomerModel.getContactEmail());*/

					getEventService().publishEvent(initializeEvent(oktaEmailChangeEvent, b2bCustomerModel));
				}
			}
			catch(ResourceAccessException rae)
			{
				LOGGER.error(rae.getMessage() +" for the customer "+customerUid);
			}

			
		}
	}
	
	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event,
			final B2BCustomerModel b2BCustomerModel)
	{
		event.setBaseStore(getBaseStoreService().getBaseStoreForUid("siteone"));
		event.setSite(getBaseSiteService().getBaseSiteForUID(Config.getString("siteone.site.id", "siteone")));
		event.setCustomer(b2BCustomerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());

		return event;
	}

	/**
	 * @return the oktaCustomerEmailUpdateCronJobDao
	 */
	public CustomerUpdateCronJobDao getCustomerEmailUpdateCronJobDao()
	{
		return oktaCustomerEmailUpdateCronJobDao;
	}

	/**
	 * @param oktaCustomerEmailUpdateCronJobDao
	 *           the oktaCustomerEmailUpdateCronJobDao to set
	 */
	public void setCustomerEmailUpdateCronJobDao(final CustomerUpdateCronJobDao oktaCustomerEmailUpdateCronJobDao)
	{
		this.oktaCustomerEmailUpdateCronJobDao = oktaCustomerEmailUpdateCronJobDao;
	}

	/**
	 * @return the oktaAPI
	 */
	public OKTAAPI getOktaAPI()
	{
		return oktaAPI;
	}

	/**
	 * @param oktaAPI
	 *           the oktaAPI to set
	 */
	public void setOktaAPI(final OKTAAPI oktaAPI)
	{
		this.oktaAPI = oktaAPI;
	}


	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService the baseStoreService to set
	 */
	public void setBaseStoreService(BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService the commonI18NService to set
	 */
	public void setCommonI18NService(CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService the baseSiteService to set
	 */
	public void setBaseSiteService(BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService the eventService to set
	 */
	public void setEventService(EventService eventService)
	{
		this.eventService = eventService;
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
	 * @return the secureTokenService
	 */
	public SecureTokenService getSecureTokenService()
	{
		return secureTokenService;
	}

	/**
	 * @param secureTokenService the secureTokenService to set
	 */
	public void setSecureTokenService(SecureTokenService secureTokenService)
	{
		this.secureTokenService = secureTokenService;
	}

	

}
