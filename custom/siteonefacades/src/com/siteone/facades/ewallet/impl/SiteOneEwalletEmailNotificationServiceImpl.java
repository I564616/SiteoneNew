/**
 *
 */
package com.siteone.facades.ewallet.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.siteone.core.event.EwalletNotificationEvent;
import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.ewallet.SiteOneEwalletEmailNotificationService;


/**
 * @author PElango
 *
 */
public class SiteOneEwalletEmailNotificationServiceImpl implements SiteOneEwalletEmailNotificationService
{
	private static final Logger LOG = Logger.getLogger(SiteOneEwalletEmailNotificationServiceImpl.class);

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "b2bUnitFacade")
	protected B2BUnitFacade b2bUnitFacade;
	
	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteonecustomerConverter")
	private Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter;

	@Resource(name = "defaultSiteOneEwalletDao")
	DefaultSiteOneEwalletDao defaultSiteOneEwalletDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.core.ewallet.service.SiteOneEwalletEmailNotificationService#sendEmailNotification(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void sendEmailNotification(final String cardType, final String cardNumber, final String operation)
	{

		eventService.publishEvent(initializeEvent(operation, cardType, cardNumber, new EwalletNotificationEvent(), null));
	}

	protected EwalletNotificationEvent initializeEvent(final String operation, final String cardType, final String cardNumber,
			final EwalletNotificationEvent eWalletNotificationEvent, final String nickName)
	{

		final Gson gson = new Gson();
		final B2BUnitData defaultUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitWithAdministratorInfo();
		final B2BUnitData parentUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getParentUnitForCustomer();
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final CustomerData customerData = siteonecustomerConverter.convert(customerModel);
		final Map<String, String> ccMail = new HashMap<>();
		final String toMail = customerData!=null?customerData.getEmail():null;

		defaultUnit.getAdministrators().forEach(customer -> {
			if (!customer.getUid().equalsIgnoreCase(customerData.getUid()))
			{
				ccMail.put(customer.getUid(), customer.getUid());
			}
		});

		if (!defaultUnit.getUid().equalsIgnoreCase(defaultUnit.getReportingOrganization().getUid()))
		{
			final List<String> unitIds = new ArrayList<String>();
			unitIds.add(defaultUnit.getReportingOrganization().getUid());
			if (parentUnit.getUid() != null 
					&& !parentUnit.getUid().equalsIgnoreCase(defaultUnit.getReportingOrganization().getUid()))
			{
				unitIds.add(parentUnit.getUid());
			}
			final List<B2BCustomerModel> adminUser = defaultSiteOneEwalletDao.getAdminUsers(unitIds);
			adminUser.forEach(customer -> {
				ccMail.put(customer.getUid(), customer.getUid());
			});
		}
		ccMail.remove(toMail);
		LOG.info("toMail : " + gson.toJson(toMail));
		LOG.info("ccMail1 : " + StringUtils.join(ccMail.keySet(), ","));
		eWalletNotificationEvent.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		eWalletNotificationEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
		eWalletNotificationEvent.setFirstName(customerModel.getFirstName());
		eWalletNotificationEvent.setOperationType(operation);
		eWalletNotificationEvent.setCardNumber(cardNumber);
		eWalletNotificationEvent.setCardType(cardType);
		eWalletNotificationEvent.setEmail(toMail);
		eWalletNotificationEvent.setCcMail(ccMail);
		eWalletNotificationEvent.setNickName(nickName);
		return eWalletNotificationEvent;
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

	@Override
	public void sendEmailNotification(final String cardType, final String cardNumber, final String operation,
			final String nickName, final List<B2BCustomerModel> b2bModel)
	{
		if (operation.equalsIgnoreCase("ACCESSADMIN"))
		{
			eventService.publishEvent(initializeEvent(operation, cardType, cardNumber, new EwalletNotificationEvent(), nickName));
		}
		else
		{
			b2bModel.forEach(customer -> {
				final EwalletNotificationEvent eWalletNotificationEvent = new EwalletNotificationEvent();
				eWalletNotificationEvent.setSite(baseSiteService.getBaseSiteForUID("siteone"));
				eWalletNotificationEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
				eWalletNotificationEvent.setFirstName(customer.getFirstName());
				eWalletNotificationEvent.setOperationType(operation);
				eWalletNotificationEvent.setCardNumber(cardNumber);
				eWalletNotificationEvent.setCardType(cardType);
				eWalletNotificationEvent.setNickName(nickName);
				eWalletNotificationEvent.setEmail(customer.getEmail());
				eventService.publishEvent(eWalletNotificationEvent);
			});

		}

	}

}