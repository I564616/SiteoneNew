package com.siteone.core.jobs.customer.service.impl;

import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.commerceservices.security.SecureToken;
import de.hybris.platform.commerceservices.security.SecureTokenService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.CreatePasswordEvent;
import com.siteone.core.jobs.customer.dao.FirstTimeUserCronJobDao;
import com.siteone.core.jobs.customer.service.FirstTimeUserCronJobService;
import com.siteone.core.model.FirstTimeUserCronJobModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;


public class DefaultFirstTimeUserCronJobService implements FirstTimeUserCronJobService
{

	private static final Logger LOG = Logger.getLogger(DefaultFirstTimeUserCronJobService.class);

	private static final String[] FILE_HEADER =
	{ "FirstName", "LastName", "Email", "SetPasswordLink" };

	private static final String FIELD_DELIMITER = ",";

	private FirstTimeUserCronJobDao firstTimeUserCronJobDao;

	private ModelService modelService;

	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;

	private BaseSiteService baseSiteService;

	private SecureTokenService secureTokenService;

	private CustomerAccountService customerAccountService;

	private BaseStoreService baseStoreService;

	private EventService eventService;

	private CommonI18NService commonI18NService;

	private OKTAAPI oktaAPI;



	@Override
	public void exportFirstTimeUsers(final FirstTimeUserCronJobModel firstTimeUserCronJobModel) throws IOException
	{

		final List<B2BCustomerModel> b2bCustomers = getFirstTimeUserCronJobDao().getFirstTimeUsers();

		if (Config.getBoolean("isContactInitialLoad", false))
		{
			createFirstTimeUserFeed(b2bCustomers, firstTimeUserCronJobModel);
		}
		else
		{
			if (null != b2bCustomers)
			{
				for (final B2BCustomerModel b2bCustomer : b2bCustomers)
				{

					final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);
					final long timeStamp = tokenValiditySeconds > 0L ? new Date().getTime() : 0L;
					final SecureToken data = new SecureToken(b2bCustomer.getUid(), timeStamp);
					final String token = getSecureTokenService().encryptData(data);
					b2bCustomer.setToken(token);
					getModelService().save(b2bCustomer);
					if (null != b2bCustomer.getDefaultB2BUnit()
							&& b2bCustomer.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_CA))
					{
						final String userId = getOktaUser(b2bCustomer.getUid());
						if (null != userId)
						{
							getOktaAPI().addUserToGroup(
									Config.getString(SiteoneintegrationConstants.OKTA_HYBRIS_CA_GROUPID, StringUtils.EMPTY), userId);
							b2bCustomer.setIsFirstTimeUser(false);
						}
						else
						{
							if (createOktaUser(b2bCustomer))
							{
								getEventService()
										.publishEvent(initializeEvent(new CreatePasswordEvent(token, tokenValiditySeconds), b2bCustomer));
								b2bCustomer.setIsFirstTimeUser(false);
							}
							else
							{
								LOG.error("Something went wrong while creating user in OKTA. Unable to create user in Okta");
								b2bCustomer.setIsFirstTimeUser(true);
							}

						}
					}
					else
					{
						if (createOktaUser(b2bCustomer))
						{
							getEventService()
									.publishEvent(initializeEvent(new CreatePasswordEvent(token, tokenValiditySeconds), b2bCustomer));
							b2bCustomer.setIsFirstTimeUser(false);
						}
						else
						{
							LOG.error("Something went wrong while creating user in OKTA. Unable to create user in Okta");
							b2bCustomer.setIsFirstTimeUser(true);
						}
					}
					modelService.save(b2bCustomer);
					modelService.refresh(b2bCustomer);
				}
			}
		}
	}

	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event,
			final B2BCustomerModel b2BCustomerModel)
	{
		if (null != b2BCustomerModel.getDefaultB2BUnit()
				&& b2BCustomerModel.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
		{
			event.setBaseStore(getBaseStoreService().getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		else
		{

			event.setBaseStore(getBaseStoreService().getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(getBaseSiteService().getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		}
		event.setCustomer(b2BCustomerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	private void createFirstTimeUserFeed(final List<B2BCustomerModel> b2bCustomers,
			final FirstTimeUserCronJobModel firstTimeUserCronJobModel)
	{
		PrintWriter printWriter = null;

		try
		{

			printWriter = new PrintWriter(new FileWriter(
					Config.getString(SiteoneintegrationConstants.FIRST_TIME_USER_TARGET_LOCATION, StringUtils.EMPTY) + getFileName()));

			printHeader(printWriter);



			final Map<String, String> customerRow = new HashMap<>();

			final BaseSiteModel baseSiteModel = getBaseSiteService()
					.getBaseSiteForUID(Config.getString("siteone.site.id", "siteone"));

			//b2bCustomers.forEach(b2bCustomer ->{
			for (final B2BCustomerModel b2bCustomer : b2bCustomers)
			{

				customerRow.put("Email", b2bCustomer.getUid());
				customerRow.put("FirstName", b2bCustomer.getFirstName());
				customerRow.put("LastName", b2bCustomer.getLastName());

				final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);

				final long timeStamp = tokenValiditySeconds > 0L ? new Date().getTime() : 0L;
				final SecureToken data = new SecureToken(b2bCustomer.getUid(), timeStamp);
				final String token = getSecureTokenService().encryptData(data);


				String createPasswordLink = StringUtils.EMPTY;
				try
				{
					createPasswordLink = getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSiteModel, StringUtils.EMPTY, true,
							"/login/pw/setPassword", "token=" + getURLEncodedToken(token));

				}
				catch (final UnsupportedEncodingException unsupportedEncodingException)
				{
					// TODO Auto-generated catch block
					LOG.error("Exception occured in create password File ", unsupportedEncodingException);
				}

				customerRow.put("SetPasswordLink", createPasswordLink.substring(8));

				b2bCustomer.setToken(token);
				if (createOktaUser(b2bCustomer))
				{
					b2bCustomer.setIsFirstTimeUser(false);
				}
				else
				{
					LOG.error("Something went wrong while creating user in OKTA. Unable to create user in Okta");
					b2bCustomer.setIsFirstTimeUser(true);
				}
				modelService.save(b2bCustomer);
				modelService.refresh(b2bCustomer);

				printRow(customerRow, printWriter);
			}
		}
		catch (final IOException ioException)
		{
			LOG.error("Exception occured in create password File "
					+ (Config.getString(SiteoneintegrationConstants.FIRST_TIME_USER_TARGET_LOCATION, StringUtils.EMPTY)
							+ getFileName()),
					ioException);
			firstTimeUserCronJobModel.setResult(CronJobResult.FAILURE);
			firstTimeUserCronJobModel.setStatus(CronJobStatus.ABORTED);
		}
		finally
		{
			final Date date = new Date();
			firstTimeUserCronJobModel.setLastExecutionTime(date);
			getModelService().save(firstTimeUserCronJobModel);
			if (null != printWriter)
			{
				printWriter.close();
			}
		}
	}

	@SuppressWarnings("finally")
	private boolean createOktaUser(final B2BCustomerModel b2bCustomer)
	{
		OktaCreateOrUpdateUserResponseData oktaCreateOrUpdateUserResponseData = null;
		boolean oktaUserCreateStatus = false;
		try
		{
			oktaCreateOrUpdateUserResponseData = getOktaAPI().createUser(b2bCustomer);

			if ((null != oktaCreateOrUpdateUserResponseData && StringUtils.isNotEmpty(oktaCreateOrUpdateUserResponseData.getStatus())
					&& oktaCreateOrUpdateUserResponseData.getStatus()
							.equalsIgnoreCase(SiteoneCoreConstants.OKTA_API_CREATE_USER_STATUS))
					|| (null != oktaCreateOrUpdateUserResponseData
							&& StringUtils.isNotEmpty(oktaCreateOrUpdateUserResponseData.getErrorCode())
							&& oktaCreateOrUpdateUserResponseData.getErrorCode().equalsIgnoreCase("E0000001")))
			{
				oktaUserCreateStatus = true;
			}
		}
		catch (final Exception exception)
		{
			oktaUserCreateStatus = false;
			LOG.error("Exception occured in create okta user", exception);
		}
		finally
		{
			return oktaUserCreateStatus;
		}
	}

	@SuppressWarnings("finally")
	private String getOktaUser(final String userId)
	{
		OktaCreateOrUpdateUserResponseData oktaCreateOrUpdateUserResponseData = null;
		String oktaUserCreateStatus = null;
		try
		{
			oktaCreateOrUpdateUserResponseData = getOktaAPI().getUser(userId);

			if (null != oktaCreateOrUpdateUserResponseData && StringUtils.isNotEmpty(oktaCreateOrUpdateUserResponseData.getStatus())
					&& (oktaCreateOrUpdateUserResponseData.getStatus()
							.equalsIgnoreCase(SiteoneCoreConstants.OKTA_API_CREATE_USER_STATUS)
							|| (oktaCreateOrUpdateUserResponseData.getStatus()
									.equalsIgnoreCase(SiteoneCoreConstants.OKTA_USER_STATUS_ACTIVE))))
			{
				oktaUserCreateStatus = oktaCreateOrUpdateUserResponseData.getId();
			}
		}
		catch (final Exception exception)
		{
			oktaUserCreateStatus = null;
			LOG.error("Exception occured in create okta user", exception);
		}
		finally
		{
			return oktaUserCreateStatus;
		}
	}

	private void printRow(final Map<String, String> customerRow, final PrintWriter printWriter)
	{
		final StringBuffer feedRow = new StringBuffer();
		for (final String header : FILE_HEADER)
		{
			if (feedRow.length() != 0)
			{
				feedRow.append(FIELD_DELIMITER);
			}
			feedRow.append((customerRow.get(header) != null) ? customerRow.get(header) : StringUtils.EMPTY);
		}
		printWriter.println(feedRow);
	}

	private String getFileName()
	{
		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy_hhmmss");
		final String currentServerDate = dateFormat.format(cal.getTime());
		final String fileName = Config.getString("firsttimecustomer.feed.fileName", "FirstTimeCustomer")
				+ SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate + ".csv";
		return fileName;
	}

	private String getURLEncodedToken(final String token) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(token, "UTF-8");
	}


	private void printHeader(final PrintWriter printWriter)
	{
		final StringBuffer fileHeader = new StringBuffer();
		for (final String header : FILE_HEADER)
		{
			if (fileHeader.length() != 0)
			{
				fileHeader.append(FIELD_DELIMITER);
			}
			fileHeader.append(header);
		}

		printWriter.println(fileHeader);
	}

	/**
	 * @return the firstTimeUserCronJobDao
	 */
	public FirstTimeUserCronJobDao getFirstTimeUserCronJobDao()
	{
		return firstTimeUserCronJobDao;
	}

	/**
	 * @param firstTimeUserCronJobDao
	 *           the firstTimeUserCronJobDao to set
	 */
	public void setFirstTimeUserCronJobDao(final FirstTimeUserCronJobDao firstTimeUserCronJobDao)
	{
		this.firstTimeUserCronJobDao = firstTimeUserCronJobDao;
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
	 * @return the siteBaseUrlResolutionService
	 */
	public SiteBaseUrlResolutionService getSiteBaseUrlResolutionService()
	{
		return siteBaseUrlResolutionService;
	}

	/**
	 * @param siteBaseUrlResolutionService
	 *           the siteBaseUrlResolutionService to set
	 */
	public void setSiteBaseUrlResolutionService(final SiteBaseUrlResolutionService siteBaseUrlResolutionService)
	{
		this.siteBaseUrlResolutionService = siteBaseUrlResolutionService;
	}


	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the secureTokenService
	 */
	public SecureTokenService getSecureTokenService()
	{
		return secureTokenService;
	}

	/**
	 * @param secureTokenService
	 *           the secureTokenService to set
	 */
	public void setSecureTokenService(final SecureTokenService secureTokenService)
	{
		this.secureTokenService = secureTokenService;
	}


	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}


	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}


	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}


	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}


	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}


	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
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


	/**
	 * @return the oktaAPI
	 */
	public OKTAAPI getOktaAPI()
	{
		return oktaAPI;
	}





	public void setOktaAPI(final OKTAAPI oktaAPI)
	{
		this.oktaAPI = oktaAPI;
	}


}
