/**
 *
 */
package com.siteone.facades.ewallet.impl;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.ewallet.data.PaymentconfigData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commercefacades.util.CommerceUtils;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import com.granule.json.JSONObject;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.ewallet.service.SiteOneEwalletService;
import com.siteone.core.exceptions.EwalletNotFoundException;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.ewallet.SiteOneEwalletEmailNotificationService;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.exceptions.CardAlreadyPresentInUEException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInCayanException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.NickNameAlreadyPresentInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.ewallet.data.SiteOneWsEwalletResponseData;
import com.siteone.integration.global.data.SiteOneCAGlobalPaymentResponse;
import com.siteone.integration.global.data.SiteOneCAGlobalPaymentVoidRequest;
import com.siteone.integration.payment.data.CAGlobalPaymentAddress;
import com.siteone.integration.payment.data.CAGlobalPaymentCard;
import com.siteone.integration.payment.data.CAGlobalPaymentCustomer;
import com.siteone.integration.payment.data.CAGlobalPaymentDetails;
import com.siteone.integration.payment.data.CAVoidGlobalPaymentDetails;
import com.siteone.integration.payment.data.CAGlobalPaymentIndicators;
import com.siteone.integration.payment.data.CAVoidGlobalPaymentIndicators;
import com.siteone.integration.payment.data.CAGlobalPaymentTransaction;
import com.siteone.integration.payment.data.CAVoidGlobalPaymentTransaction;
import com.siteone.integration.scan.product.data.SiteOneCAGlobalPaymentRequest;
import com.siteone.integration.services.ue.SiteOneEwalletWebService;


/**
 * @author RSUBATHR
 *
 */
public class DefaultSiteOneEwalletFacadeImpl implements SiteOneEwalletFacade
{

	@Resource(name = "siteOneEwalletReverseConverter")
	private Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter;

	@Resource(name = "siteOneEwalletCreditCardConverter")
	private Converter<SiteoneEwalletCreditCardModel, SiteOneEwalletData> siteOneEwalletCreditCardConverter;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "creditCardNameMapping")
	private CreditCardNameMapping creditCardNameMapping;

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;


	private ModelService modelService;

	private SessionService sessionService;


	private static final Logger LOG = Logger.getLogger(DefaultSiteOneEwalletFacadeImpl.class);

	private static final String ALL_UNITS = "All";

	private static final String ASSIGN = "Assign";

	private static final String REVOKE = "Revoke";

	private static final String SITEONE_PAYMENT = "siteone-payment";

	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	private static final String EWALLET_NOT_FOUND = "Unable to fetch the Ewallet details in hybris";

	private static final String EWALLET_RESPONSE_FAILURE = " FAILURE";

	private static final String EWALLET_RESPONSE_SUCCESS = "SUCCESS";

	@Resource(name = "siteOneEwalletWebService")
	private SiteOneEwalletWebService siteOneEwalletWebService;

	@Resource(name = "siteOneCayanPaymentFacade")
	private SiteOneCayanPaymentFacade siteOneCayanPaymentFacade;

	@Resource(name = "ewalletService")
	private SiteOneEwalletService ewalletService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "b2bUserFacade")
	private B2BUserFacade b2bUserFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteOneEwalletEmailNotificationService")
	private SiteOneEwalletEmailNotificationService siteOneEwalletEmailNotificationService;

	@Resource(name = "siteonecustomerConverter")
	private Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter;


	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	@Override
	public void updateEwalletDetails(final String vaultToken, final String nickName, final String expDate)
			throws EwalletNotCreatedOrUpdatedInCayanException, EwalletNotCreatedOrUpdatedInUEException, ServiceUnavailableException,
			Exception
	{

		final SiteoneEwalletCreditCardModel creditCardModel = ewalletService.getCreditCardDetails(vaultToken);
		SiteOneWsEwalletResponseData ewalletResponseData = null;
		final String expiryDate = expDate.replace("-", "");
		String updateBoardCardResponse = null;
		final String dbExpDate = creditCardModel.getExpDate().toString();
		final String dbNNickName = creditCardModel.getNickName().toString();
		boolean isUEFailed = false;
		final DateFormat dateformat = new SimpleDateFormat("MMyy");
		final Date givenExpiryDate = dateformat.parse(expiryDate);
		final Date currentDate = new Date();

		try
		{
			if (dbExpDate.equalsIgnoreCase(expiryDate) && dbNNickName.equalsIgnoreCase(nickName))
			{
				throw new Exception("Values are same");
			}

			if (expiryDate != null && expiryDate != "" && !dbExpDate.equalsIgnoreCase(expiryDate))
			{
				updateBoardCardResponse = siteOneCayanPaymentFacade.updateBoardedCard(vaultToken, expiryDate);
				creditCardModel.setExpDate(expiryDate);
			}
			if (nickName == null || nickName == "")
			{
				creditCardModel.setNickName(creditCardModel.getCreditCardType() + "-" + creditCardModel.getLast4Digits());
			}
			else if (!dbNNickName.equalsIgnoreCase(nickName) && !nickName.contains("-" + creditCardModel.getLast4Digits()))
			{
				creditCardModel.setNickName(nickName + "-" + creditCardModel.getLast4Digits());
			}
			else
			{
				creditCardModel.setNickName(nickName);
			}
			if (SiteoneFacadesConstants.SUCCESS.equalsIgnoreCase(updateBoardCardResponse)
					|| (!dbNNickName.equalsIgnoreCase(nickName)))
			{
				ewalletResponseData = siteOneEwalletWebService.createOrUpdOrDelEwallet(creditCardModel,
						SiteoneFacadesConstants.UPDATE,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			}
			else
			{
				LOG.error("Not able to update ewallet details in cayan");
				throw new EwalletNotCreatedOrUpdatedInCayanException("Ewallet details not updated in Cayan");
			}

			if (null != ewalletResponseData && ewalletResponseData.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.TRUE))
			{
				getModelService().save(creditCardModel);
				siteOneEwalletEmailNotificationService.sendEmailNotification(creditCardModel.getCreditCardType(),
						creditCardModel.getLast4Digits(), SiteoneFacadesConstants.EDIT);
			}
			else
			{
				isUEFailed = true;
				LOG.error("Not able to update Ewallet in UE : " + ewalletResponseData.getErrorMessage());
				throw new EwalletNotCreatedOrUpdatedInUEException("Ewallet not updated in UE");
			}
		}
		catch (final ModelSavingException modelSavingException)
		{
			LOG.error(modelSavingException.getMessage(), modelSavingException);
			throw new ModelSavingException("Ewallet not updated in Hybris");
		}
		finally
		{
			if (SiteoneFacadesConstants.SUCCESS.equalsIgnoreCase(updateBoardCardResponse) && isUEFailed)
			{
				siteOneCayanPaymentFacade.updateBoardedCard(vaultToken, dbExpDate);
				LOG.error("Successfully reverted the cayan update for : " + dbExpDate);
			}
		}


	}

	@Override
	public String addEwalletDetails(final SiteOneEwalletData ewalletData) throws Exception
	{
		SiteOneWsEwalletResponseData ewalletResponseData = null;
		final SiteoneEwalletCreditCardModel creditCardModel = new SiteoneEwalletCreditCardModel();
		siteOneEwalletReverseConverter.convert(ewalletData, creditCardModel);

		try
		{
			//UE call invocation
			ewalletResponseData = siteOneEwalletWebService.createOrUpdOrDelEwallet(creditCardModel,
					SiteoneFacadesConstants.ADD_EWALLET_OPERATION,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

			if (ewalletResponseData != null && ewalletResponseData.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.TRUE))
			{
				creditCardModel.setCusttreeNodeCreditCardId(ewalletResponseData.getCustTreeNodeCreditCardId());
				final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
				final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sessionShipTo.getUid());
				final Set<B2BUnitModel> unitModel = new HashSet<B2BUnitModel>();
				unitModel.add(b2bUnitModel);
				creditCardModel.setUnitUID(unitModel);
				getModelService().save(creditCardModel);
				siteOneEwalletEmailNotificationService.sendEmailNotification(creditCardModel.getCreditCardType(),
						creditCardModel.getLast4Digits(), "ADD");
				return creditCardModel.getCusttreeNodeCreditCardId();
			}
			else if (ewalletResponseData != null && null != ewalletResponseData.getErrorMessage()
					&& ewalletResponseData.getErrorMessage().contains("Card already present for customer"))
			{

				LOG.error("Card already present for customer : " + ewalletResponseData.getErrorMessage());
				throw new CardAlreadyPresentInUEException("Card already present for customer");
			}
			else if (ewalletResponseData != null && null != ewalletResponseData.getErrorMessage()
					&& ewalletResponseData.getErrorMessage().contains("Nickname should be different for each credit card"))
			{

				LOG.error("Nick Name already present for customer : " + ewalletResponseData.getErrorMessage());
				throw new NickNameAlreadyPresentInUEException("Nickname should be different for each credit card");
			}
			else
			{
				LOG.error("Invalid UE Repsonse for add-eWallet : " + ewalletResponseData.getErrorMessage());
				throw new Exception("Invalid UE reponse for add-eWallet");
			}

		}
		catch (final Exception ex)
		{
			LOG.error("Error on while adding eWallet details", ex);
			throw ex;
		}
		finally
		{
			if (ewalletResponseData == null || !ewalletResponseData.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.TRUE))
			{
				siteOneCayanPaymentFacade.unboardCard(ewalletData.getValutToken());
				LOG.info("Successfully unboarded the card, after UE call failed");
			}

		}

	}

	@Override
	public void deleteCardFromEwallet(final String vaultToken) throws Exception
	{
		final SiteoneEwalletCreditCardModel creditCardModel = ewalletService.getCreditCardDetails(vaultToken);
		final List<Object> ewalletOrderStatus = ewalletService.getEwalletOrderStatus(vaultToken);
		SiteOneWsEwalletResponseData ewalletResponseData = null;

		try
		{
			if (CollectionUtils.isEmpty(ewalletOrderStatus))
			{
				ewalletResponseData = siteOneEwalletWebService.createOrUpdOrDelEwallet(creditCardModel,
						SiteoneFacadesConstants.DELETE,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				if (ewalletResponseData != null && ewalletResponseData.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.TRUE))
				{
					creditCardModel.setActive(Boolean.FALSE);
					getModelService().save(creditCardModel);
					siteOneEwalletEmailNotificationService.sendEmailNotification(creditCardModel.getCreditCardType(),
							creditCardModel.getLast4Digits(), SiteoneFacadesConstants.DELETE);
				}
				else
				{
					LOG.error("Invalid UE Repsonse for delete eWallet : " + ewalletResponseData.getErrorMessage());
					throw new Exception("Invalid Response from UE Delete eWallet");
				}
			}
			else
			{
				throw new Exception("Unable to delete card, Your Previous Order has not yet completed");
			}
		}
		catch (final ModelSavingException modelSavingException)
		{
			LOG.error(modelSavingException.getMessage(), modelSavingException);
			throw modelSavingException;
		}
		catch (final Exception ex)
		{
			LOG.error("Error on while Delete eWallet details", ex);
			throw ex;
		}
	}

	@Override
	public SearchPageData<SiteOneEwalletData> getPagedEWalletDataForUnit(final PageableData pageableData, final String userUnitId,
			final String trimmedSearchParam, final String sortCode, final Boolean shipToFlag)
	{
		SearchPageData<SiteoneEwalletCreditCardModel> eWalletCards = null;
		if (userUnitId == ALL_UNITS || userUnitId.equalsIgnoreCase("All"))
		{
			final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			eWalletCards = ewalletService.getEWalletCardDetails(pageableData, defaultUnit.getUid(), trimmedSearchParam, sortCode,
					shipToFlag);
		}
		else
		{
			eWalletCards = ewalletService.getEWalletCardDetails(pageableData, userUnitId, trimmedSearchParam, sortCode, shipToFlag);
		}
		return CommerceUtils.convertPageData(eWalletCards, getSiteOneEwalletCreditCardConverter());
	}

	@Override
	public void updateEwalletToUser(final String vaultToken, final List<String> custUIDs, final String operationType)
			throws Exception
	{
		final SiteoneEwalletCreditCardModel siteoneEwalletCreditCardModel = ewalletService.getCreditCardDetails(vaultToken);

		final List<B2BCustomerModel> customerModel = ((SiteOneB2BUserFacade) b2bUserFacade).getCustomers(custUIDs);
		if (operationType.equalsIgnoreCase(ASSIGN))
		{
			assignEwallet(siteoneEwalletCreditCardModel, customerModel, SiteoneFacadesConstants.GRANT);
		}
		else if (operationType.equalsIgnoreCase(REVOKE))
		{
			revokeEwallet(siteoneEwalletCreditCardModel, customerModel, SiteoneFacadesConstants.REVOKE);
		}
	}

	/**
	 * @param siteoneEwalletCreditCardModel
	 * @param customerModel
	 */
	private void revokeEwallet(final SiteoneEwalletCreditCardModel siteoneEwalletCreditCardModel,
			final List<B2BCustomerModel> customerModel, final String operationType) throws Exception
	{

		final Set<B2BCustomerModel> newcustomerModel = new HashSet<B2BCustomerModel>(
				siteoneEwalletCreditCardModel.getCustomerUID());
		newcustomerModel.removeAll(customerModel);
		siteoneEwalletCreditCardModel.setCustomerUID(newcustomerModel);

		SiteOneWsEwalletResponseData ewalletResponseData = null;
		ewalletResponseData = siteOneEwalletWebService.assignOrRevokeEwallet(siteoneEwalletCreditCardModel, operationType,
				customerModel, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		if (ewalletResponseData != null && ewalletResponseData.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.TRUE))
		{
			getModelService().save(siteoneEwalletCreditCardModel); //Hybris Call
			//Email Notification
			siteOneEwalletEmailNotificationService.sendEmailNotification(siteoneEwalletCreditCardModel.getCreditCardType(),
					siteoneEwalletCreditCardModel.getLast4Digits(), SiteoneFacadesConstants.ACCESSTM,
					siteoneEwalletCreditCardModel.getNickName(), customerModel);
			siteOneEwalletEmailNotificationService.sendEmailNotification(siteoneEwalletCreditCardModel.getCreditCardType(),
					siteoneEwalletCreditCardModel.getLast4Digits(), SiteoneFacadesConstants.ACCESSADMIN,
					siteoneEwalletCreditCardModel.getNickName(), null);
		}
		else
		{
			LOG.error("Invalid Response from UE Revoke eWallet : " + ewalletResponseData.getErrorMessage());
			throw new Exception("Invalid Response from UE Revoke eWallet");
		}
	}



	/**
	 * @param siteoneEwalletCreditCardModel
	 * @param customerModel
	 * @param operationType
	 */
	private void assignEwallet(final SiteoneEwalletCreditCardModel siteoneEwalletCreditCardModel,
			final List<B2BCustomerModel> customerModel, final String operationType) throws Exception
	{

		final Set<B2BCustomerModel> newcustomerModel = new HashSet<B2BCustomerModel>(customerModel);
		newcustomerModel.addAll(siteoneEwalletCreditCardModel.getCustomerUID());
		siteoneEwalletCreditCardModel.setCustomerUID(newcustomerModel);

		SiteOneWsEwalletResponseData ewalletResponseData = null;
		ewalletResponseData = siteOneEwalletWebService.assignOrRevokeEwallet(siteoneEwalletCreditCardModel, operationType,
				customerModel, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		if (ewalletResponseData != null && ewalletResponseData.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.TRUE))
		{
			getModelService().save(siteoneEwalletCreditCardModel); //Hybris Call
			//Email Notification
			siteOneEwalletEmailNotificationService.sendEmailNotification(siteoneEwalletCreditCardModel.getCreditCardType(),
					siteoneEwalletCreditCardModel.getLast4Digits(), SiteoneFacadesConstants.ACCESSTM,
					siteoneEwalletCreditCardModel.getNickName(), customerModel);
			siteOneEwalletEmailNotificationService.sendEmailNotification(siteoneEwalletCreditCardModel.getCreditCardType(),
					siteoneEwalletCreditCardModel.getLast4Digits(), SiteoneFacadesConstants.ACCESSADMIN,
					siteoneEwalletCreditCardModel.getNickName(), null);
		}
		else
		{
			LOG.error("Invalid Response from UE Assign eWallet : " + ewalletResponseData.getErrorMessage());
			throw new Exception("Invalid Response from UE Assign eWallet");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.ewallet.SiteOneEwalletFacade#ewalletDetails(java.lang.String)
	 */
	@Override
	public SiteOneEwalletData ewalletDetails(final String vaultToken)
	{
		final SiteoneEwalletCreditCardModel siteoneEwalletCreditCardModel = ewalletService.getCreditCardDetails(vaultToken);
		final SiteOneEwalletData siteoneEwalletData = new SiteOneEwalletData();
		return getSiteOneEwalletCreditCardConverter().convert(siteoneEwalletCreditCardModel, siteoneEwalletData);
	}

	@Override
	public List<SiteOneEwalletData> getEWalletDataForCheckout(final String userUnitId)
	{

		final List<SiteoneEwalletCreditCardModel> eWalletCards = ewalletService.getEWalletCardDetailsForCheckout(userUnitId);

		return getSiteOneEwalletCreditCardConverter().convertAll(eWalletCards);
	}


	public Converter<SiteoneEwalletCreditCardModel, SiteOneEwalletData> getSiteOneEwalletCreditCardConverter()
	{
		return siteOneEwalletCreditCardConverter;
	}

	public void setSiteOneWsEwalletConverter(
			final Converter<SiteoneEwalletCreditCardModel, SiteOneEwalletData> siteOneEwalletCreditCardConverter)
	{
		this.siteOneEwalletCreditCardConverter = siteOneEwalletCreditCardConverter;
	}

	public Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> getSiteOneEwalletReverseConverter()
	{
		return siteOneEwalletReverseConverter;
	}

	public void setSiteOneEwalletReverseConverter(
			final Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter)
	{
		this.siteOneEwalletReverseConverter = siteOneEwalletReverseConverter;
	}


	protected ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the siteOneEwalletWebService
	 */
	public SiteOneEwalletWebService getSiteOneEwalletWebService()
	{
		return siteOneEwalletWebService;
	}


	/**
	 * @param siteOneEwalletWebService
	 *           the siteOneEwalletWebService to set
	 */
	public void setSiteOneEwalletWebService(final SiteOneEwalletWebService siteOneEwalletWebService)
	{
		this.siteOneEwalletWebService = siteOneEwalletWebService;
	}

	/**
	 * @return the ewalletService
	 */
	public SiteOneEwalletService getSiteOneEwalletService()
	{
		return ewalletService;
	}


	/**
	 * @param ewalletService
	 *           the ewalletService to set
	 */
	public void setSiteOneEwalletService(final SiteOneEwalletService ewalletService)
	{
		this.ewalletService = ewalletService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.ewallet.SiteOneEwalletFacade#populatePaymentconfig(java.lang.String,
	 * org.springframework.ui.Model)
	 */
	@Override
	public void populatePaymentconfig(final String pageName, final Model model)
	{

		Boolean payWithEwallet = Boolean.FALSE;
		Boolean payWithCC = Boolean.FALSE;
		boolean showBoiBranch = false;
		boolean showBoiState = false;
		Boolean showManageEwallet = Boolean.FALSE;
		Boolean isPOAEnabled = Boolean.FALSE;

		if (isEWalletEnabled())
		{
			final List<String> arrayOfStates = getBOLActiveStates();
			if (!CollectionUtils.isEmpty(arrayOfStates) && null != storeSessionFacade.getSessionStore()
					&& null != storeSessionFacade.getSessionStore().getAddress().getRegion())
			{
				showBoiState = arrayOfStates.contains("All") || arrayOfStates.stream().anyMatch(
						t -> t.equalsIgnoreCase(storeSessionFacade.getSessionStore().getAddress().getRegion().getIsocodeShort()));
			}

			final List<String> arrayOfBranches = getBOLActiveBranches();
			if (!CollectionUtils.isEmpty(arrayOfBranches) && null != storeSessionFacade.getSessionStore())
			{
				showBoiBranch = arrayOfBranches.contains("All") || arrayOfBranches.stream()
						.anyMatch(t -> t.equalsIgnoreCase(storeSessionFacade.getSessionStore().getStoreId()));
			}

			if (showBoiState || showBoiBranch)
			{
				showManageEwallet = Boolean.TRUE;
				if (pageName.equalsIgnoreCase(SITEONE_PAYMENT))
				{
					if (isEWalletPaymentEnabled())
					{
						payWithEwallet = Boolean.TRUE;
						model.addAttribute("payWithEwallet", payWithEwallet);
					}
					if (isCCPaymentEnabled())
					{

						payWithCC = Boolean.TRUE;
						model.addAttribute("payWithCC", payWithCC);
					}

					getSessionService().setAttribute("showCCOption", showManageEwallet);

					if (isPOAEnabled())
					{
						isPOAEnabled = Boolean.TRUE;
					}
					getSessionService().setAttribute("isPOAEnabled", isPOAEnabled);
				}
				else
				{
					model.addAttribute("showManageEwallet", showManageEwallet);
				}
			}
			else if (pageName.equalsIgnoreCase(SITEONE_PAYMENT))
			{
				getSessionService().setAttribute("showCCOption", showManageEwallet);

				getSessionService().setAttribute("isPOAEnabled", isPOAEnabled);
			}
		}
		else if (pageName.equalsIgnoreCase(SITEONE_PAYMENT))
		{
			getSessionService().setAttribute("showCCOption", showManageEwallet);

			getSessionService().setAttribute("isPOAEnabled", isPOAEnabled);
		}

	}

	/**
	 * @return
	 */
	private boolean isCCPaymentEnabled()
	{
		final String paywithCC = siteOneFeatureSwitchCacheService.getValueForSwitch("PaywithCC");
		final boolean paywithCCFlag = Boolean.parseBoolean(paywithCC);
		return paywithCCFlag;
	}

	/**
	 * @return
	 */
	private boolean isEWalletPaymentEnabled()
	{
		final String paywithEwallet = siteOneFeatureSwitchCacheService.getValueForSwitch("PaywithEwallet");
		final boolean paywithEwalletFlag = Boolean.parseBoolean(paywithEwallet);
		return paywithEwalletFlag;
	}

	/**
	 * @return
	 */
	private List<String> getBOLActiveBranches()
	{
		final String branches = siteOneFeatureSwitchCacheService.getValueForSwitch("BranchesAllowedforBOL");
		List<String> arrayOfBranches = new ArrayList<>();
		if (null != branches)
		{
			arrayOfBranches = Arrays.asList(branches.split(","));
		}
		return arrayOfBranches;
	}

	/**
	 * @return
	 */
	private List<String> getBOLActiveStates()
	{
		final String states = siteOneFeatureSwitchCacheService.getValueForSwitch("StatesAllowedforBOL");
		List<String> arrayOfStates = new ArrayList<>();
		if (null != states)
		{
			arrayOfStates = Arrays.asList(states.split(","));
		}
		return arrayOfStates;
	}

	/**
	 * @return
	 */
	private boolean isEWalletEnabled()
	{
		final String ewalletAccessFlagValue = siteOneFeatureSwitchCacheService.getValueForSwitch("EwalletAccess");
		final boolean ewalletAccessFlag = Boolean.parseBoolean(ewalletAccessFlagValue);
		return ewalletAccessFlag;
	}

	private boolean isPOAEnabled()
	{
		final String POAaccess = siteOneFeatureSwitchCacheService.getValueForSwitch("isPOAEnabled");
		final boolean isPOAEnabled = Boolean.parseBoolean(POAaccess);
		return isPOAEnabled;
	}




	@Override
	public SearchPageData<CustomerData> getPagedAssignRevokeUsers(final PageableData pageableData, final String userUnitId,
			final String sortCode, final String action, final String vaultToken)
	{
		SearchPageData<B2BCustomerModel> users = null;
		final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		users = ewalletService.getPagedAssignRevokeUsers(pageableData, defaultUnit.getUid(), sortCode, action, vaultToken);
		//return siteonecustomerConverter.convertAll(eWalletCards);
		return CommerceUtils.convertPageData(users, getSiteOneCustomerConverter());
	}


	public Converter<B2BCustomerModel, CustomerData> getSiteOneCustomerConverter()
	{
		return siteonecustomerConverter;
	}

	public void setSiteOneCustomerConverter(final Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter)
	{
		this.siteonecustomerConverter = siteonecustomerConverter;
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService
	 *           the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(final SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.facades.ewallet.SiteOneEwalletFacade#filterValidEwalletData(de.hybris.platform.commercefacades.user.
	 * data.CustomerData)
	 */
	@Override
	public List<SiteOneEwalletData> filterValidEwalletData(final CustomerData customerData)
	{
		final DateFormat dateformat = new SimpleDateFormat("MMyy");
		final Date currentDate = new Date();
		//subtracting one month from current date
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MONTH, -1);
		final Date previousMonth = calendar.getTime();

		List<SiteOneEwalletData> ewalletData = null;
		List<SiteOneEwalletData> filteredEwalletData = null;
		if (customerData.getRoles().contains(B2BConstants.B2BADMINGROUP))
		{
			final String userUnitId = customerData.getUnit().getUid();
			ewalletData = getEWalletDataForCheckout(userUnitId);
		}
		else
		{
			ewalletData = getSiteOneEwalletCreditCardConverter().convertAll(customerData.getVaultToken());
		}

		filteredEwalletData = ewalletData.stream().filter(newEwallet -> {
			try
			{
				return dateformat.parse(newEwallet.getCardExpirationDate()).after(previousMonth);
			}
			catch (final ParseException e)
			{
				LOG.error("Unable to parse expiry date");
			}
			return false;
		}).collect(Collectors.toList());
		return filteredEwalletData;
	}

	@Override
	public PaymentconfigData populatePaymentconfig()
	{
		Boolean payWithEwallet = Boolean.FALSE;
		Boolean payWithCC = Boolean.FALSE;
		boolean showBoiBranch = false;
		boolean showBoiState = false;
		Boolean showManageEwallet = Boolean.FALSE;
		Boolean isPOAEnabled = Boolean.FALSE;

		final PaymentconfigData paymentconfigData = new PaymentconfigData();

		if (isEWalletEnabled())
		{
			final List<String> arrayOfStates = getBOLActiveStates();
			if (!CollectionUtils.isEmpty(arrayOfStates))
			{
				showBoiState = arrayOfStates.contains("All") || arrayOfStates.stream().anyMatch(
						t -> t.equalsIgnoreCase(storeSessionFacade.getSessionStore().getAddress().getRegion().getIsocodeShort()));
			}

			final List<String> arrayOfBranches = getBOLActiveBranches();
			if (!CollectionUtils.isEmpty(arrayOfBranches))
			{
				showBoiBranch = arrayOfBranches.contains("All") || arrayOfBranches.stream()
						.anyMatch(t -> t.equalsIgnoreCase(storeSessionFacade.getSessionStore().getStoreId()));
			}

			if (showBoiState || showBoiBranch)
			{
				showManageEwallet = Boolean.TRUE;
				getSessionService().setAttribute("showCCOption", showManageEwallet);
				if (isEWalletPaymentEnabled())
				{
					payWithEwallet = Boolean.TRUE;
					paymentconfigData.setPayWithEwallet(payWithEwallet);
				}
				if (isCCPaymentEnabled())
				{

					payWithCC = Boolean.TRUE;
					paymentconfigData.setPayWithCC(payWithCC);
				}

				if (isPOAEnabled())
				{
					isPOAEnabled = Boolean.TRUE;
				}
				paymentconfigData.setIsPOAEnabled(isPOAEnabled);

			}
			else
			{
				getSessionService().setAttribute("showCCOption", showManageEwallet);

				paymentconfigData.setIsPOAEnabled(isPOAEnabled);
			}
		}
		else
		{
			getSessionService().setAttribute("showCCOption", showManageEwallet);

			paymentconfigData.setIsPOAEnabled(isPOAEnabled);
		}
		getSessionService().setAttribute("isPOAEnabled", isPOAEnabled);
		return paymentconfigData;
	}

	@Override
	public String getUsersRevokeEwalletData(final String listOfCustIds, final String vaultToken, final String operationType)
	{
		try
		{
			final List<String> listOfCustomerIds = Arrays.asList(listOfCustIds.split(","));
			updateEwalletToUser(vaultToken, listOfCustomerIds, operationType);
		}
		catch (final ModelSavingException modelExp)
		{
			LOG.error("Unable to " + operationType + " the Ewallet details in hybris", modelExp);

			return EWALLET_RESPONSE_FAILURE;
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			return EWALLET_RESPONSE_FAILURE;
		}
		catch (final EwalletNotCreatedOrUpdatedInUEException UEExp)
		{
			LOG.error("Unable to update the Ewallet details in UE", UEExp);
			return EWALLET_RESPONSE_FAILURE;

		}
		catch (final EwalletNotFoundException ewalletException)
		{
			LOG.error(EWALLET_NOT_FOUND, ewalletException);
			return EWALLET_RESPONSE_FAILURE;

		}
		catch (final Exception ex)
		{
			LOG.error("Failed to update Credit Card details", ex);
			return EWALLET_RESPONSE_FAILURE;
		}
		return EWALLET_RESPONSE_SUCCESS;
	}

	@Override
	public String editEwalletData(final String vaultToken, final String nickName, final String expDate)
	{
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-yy");
		final YearMonth yearMonth = YearMonth.parse(expDate, format);

		if (yearMonth.compareTo(YearMonth.now()) < 0)
		{
			return "EXPFAILURE";
		}

		try
		{

			updateEwalletDetails(vaultToken, nickName, expDate); //service  Call

		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			return EWALLET_RESPONSE_FAILURE;
		}
		catch (final EwalletNotFoundException ewalletException)
		{
			LOG.error(EWALLET_NOT_FOUND, ewalletException);
			return EWALLET_RESPONSE_FAILURE;

		}
		catch (final EwalletNotCreatedOrUpdatedInCayanException cayanExp)
		{
			LOG.error("Unable to Update Ewallet details in Cayan", cayanExp);
			return EWALLET_RESPONSE_FAILURE;

		}
		catch (final EwalletNotCreatedOrUpdatedInUEException ewalletUEExp)
		{
			LOG.error("Unable to update the Ewallet details in UE", ewalletUEExp);
			return EWALLET_RESPONSE_FAILURE;

		}
		catch (final ModelSavingException modelExp)
		{
			LOG.error("Unable to update the Ewallet details in hybris", modelExp);
			return EWALLET_RESPONSE_FAILURE;

		}
		catch (final Exception ex)
		{
			LOG.error("Failed to update Credit Card details", ex);
			if (ex.getMessage().equalsIgnoreCase("Values are same"))
			{
				return "SAME";
			}
			else
			{
				return EWALLET_RESPONSE_FAILURE;
			}
		}


		return EWALLET_RESPONSE_SUCCESS;
	}

	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	public I18NService getI18nService()
	{
		return i18nService;
	}

	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	@Override
	public String deleteCardForShipTo(final String vaultToken)
	{
		try
		{
			deleteCardFromEwallet(vaultToken); //service  Call

		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to create contact", resourceAccessException);
			return EWALLET_RESPONSE_FAILURE;
		}
		catch (final EwalletNotFoundException ewalletException)
		{

			LOG.error(EWALLET_NOT_FOUND, ewalletException);
			return EWALLET_RESPONSE_FAILURE;
		}
		catch (final ModelSavingException modelExp)
		{

			LOG.error("Unable to delete the Ewallet details in hybris", modelExp);
			return EWALLET_RESPONSE_FAILURE;
		}
		catch (final Exception ex)
		{
			LOG.error("Failed to delete Credit Card details", ex);
			return EWALLET_RESPONSE_FAILURE;
		}

		return EWALLET_RESPONSE_SUCCESS;
	}

	@Override
	public SiteOnePaymentInfoData addGlobalPaymentDetails(final String token)
	{
		SiteOneCAGlobalPaymentResponse responseData = null;
		final CartData cartData = b2bCheckoutFacade.getCheckoutCart();
		AddressData address = new AddressData();
		if (cartData.getDeliveryAddress() != null)
		{
			address = cartData.getDeliveryAddress();
		}
		else
		{
			address = cartData.getPickupAddress();
		}

		String authToken = null;
		try
		{
			authToken = createAuthTokenV2();
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in AuthToken - GlobalPaymewnt: " + exception.getMessage());
		}
		final String amount = cartData.getTotalPriceWithTax().getValue().toString();
		final SiteOneCAGlobalPaymentRequest requestData = populateGlobalPaymentRequestData(token, amount, address);
		final SiteOneCAGlobalPaymentVoidRequest voidRequest = populateVoidPaymentData(amount);
		SiteOnePaymentInfoData paymentInfo = null;
		try
		{
			//UE call invocation
			responseData = siteOneEwalletWebService.addGlobalPayment(authToken, requestData);

			if (responseData != null && (responseData.getStatus().equalsIgnoreCase("Approved")
					|| responseData.getStatus().equalsIgnoreCase("SETTLED") || responseData.getStatus().equalsIgnoreCase("CAPTURED")
					|| responseData.getStatus().equalsIgnoreCase("AUTHORIZED")))
			{
				LOG.error("Global Payment Successfully Approved");
				paymentInfo = populateGlobalPaymentInfoData(responseData, address);
				paymentInfo.setGlobalAuthToken(authToken);
				return paymentInfo;
			}

			else
			{
				if (responseData != null && responseData.getStatus().equalsIgnoreCase("Failed"))
				{
					//void call
					siteOneEwalletWebService.updateVoidPayment(null, requestData.getPayment().getReference_id(), authToken);
				}
				LOG.error("Invalid UE Repsonse for add-eWallet : " + responseData.getStatus());
				return null;

			}

		}
		catch (final Exception ex)
		{
			LOG.error("Error on while adding eWallet details", ex);
			throw ex;
		}
		finally
		{
			if (responseData == null || (responseData != null && responseData.getStatus().equalsIgnoreCase("Failed")))
			{
				//void call
				LOG.info("Successfully unboarded the card, after UE call failed");
				return null;
			}

		}

	}

	/**
	 * @param amount
	 * @return
	 */
	private SiteOneCAGlobalPaymentVoidRequest populateVoidPaymentData(final String amount)
	{
		final SiteOneCAGlobalPaymentVoidRequest requestData = new SiteOneCAGlobalPaymentVoidRequest();
		final CAVoidGlobalPaymentDetails paymentDetails = new CAVoidGlobalPaymentDetails();
		paymentDetails.setAmount(amount);
		requestData.setPayment(paymentDetails);
		final CAVoidGlobalPaymentTransaction transaction = new CAVoidGlobalPaymentTransaction();
		final CAVoidGlobalPaymentIndicators indicators = new CAVoidGlobalPaymentIndicators();
		indicators.setGenerate_receipt(true);
		transaction.setProcessing_indicators(indicators);
		requestData.setTransaction(transaction);
		return requestData;
	}

	public String createAuthTokenV2() throws Exception
	{
		// Create header

		final JSONObject jwtHeaderObj = new JSONObject();
		jwtHeaderObj.put("alg", "HS256").put("typ", "JWT");

		// Create payload
		final JSONObject jwtPayloadObj = new JSONObject();
		jwtPayloadObj.put("account_credential", Config.getString(SiteoneintegrationConstants.GLOBAL_ACCOUNT_CREDS, null));
		jwtPayloadObj.put("region", "CA");
		jwtPayloadObj.put("type", "AuthTokenV2");
		jwtPayloadObj.put("ts", System.currentTimeMillis());

		// Encode header and payload
		final String jwtPayloadATBase64 = Base64.getUrlEncoder()
				.encodeToString(jwtPayloadObj.toString().getBytes(StandardCharsets.UTF_8));
		// Base64 encoding of Header
		final String jwtHeaderATBase64 = Base64.getUrlEncoder()
				.encodeToString(jwtHeaderObj.toString().getBytes(StandardCharsets.UTF_8));
		// Concatenating encoded Header and Payload with "."
		final String jwtMessage = jwtHeaderATBase64 + "." + jwtPayloadATBase64;

		// Create Signature using HMAC-SH256 algorithm and ApiSecret as the secret
		final Mac sha256HMAC = Mac.getInstance("HmacSHA256");
		final SecretKeySpec secretKey = new SecretKeySpec(
				String.valueOf(Config.getString(SiteoneintegrationConstants.GLOBAL_API_SECRET_KEY, null)).getBytes(), "HmacSHA256");
		sha256HMAC.init(secretKey);
		final String hashSignature = Base64.getUrlEncoder()
				.encodeToString(sha256HMAC.doFinal(jwtMessage.getBytes(StandardCharsets.UTF_8)));

		/**
		 * Create the JWT AuthToken by concatenating Base64 URL encoded header, payload and signature
		 */

		final String token = jwtHeaderATBase64 + "." + jwtPayloadATBase64 + "." + hashSignature;


		return token;
	}

	public SiteOnePaymentInfoData populateGlobalPaymentInfoData(final SiteOneCAGlobalPaymentResponse responseData,
			AddressData address)
	{
		final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
		if (address == null)
		{
			address = new AddressData();
		}
		if (null != getSessionService().getAttribute("globalPaymentAddress"))
		{
			address.setLine1(getSessionService().getAttribute("globalPaymentAddress"));
		}

		if (null != getSessionService().getAttribute("globalPostalCode"))
		{
			address.setPostalCode(getSessionService().getAttribute("globalPostalCode"));
		}

		siteOnePaymentData.setAuthCode(responseData.getSale_id());
		final String cardNumber = responseData.getCard().getMasked_card_number();
		siteOnePaymentData.setCardNumber(cardNumber.substring(cardNumber.length() - 4));
		siteOnePaymentData.setCardType(responseData.getCard().getType());
		siteOnePaymentData.setAvs(responseData.getAvs_response());
		siteOnePaymentData.setZipCode(address.getPostalCode());
		siteOnePaymentData.setStatus(responseData.getStatus());
		siteOnePaymentData.setToken(responseData.getSale_id());
		siteOnePaymentData.setExpDate(responseData.getCard().getExpiry_month() + "/" + responseData.getCard().getExpiry_year());
		//siteOnePaymentData.setCvv(responseData.getCvResponse());
		siteOnePaymentData.setEntryMode(responseData.getTransaction().getEntry_type());
		siteOnePaymentData.setAddress(address.getLine1());
		siteOnePaymentData.setVaultToken(responseData.getPayment().getReference_id());
		siteOnePaymentData.setTransRefNumber(getAlphaNumericString());
		siteOnePaymentData.setApplicationLabel(responseData.getCard().getType());
		siteOnePaymentData.setSaveCard(false);
		siteOnePaymentData.setPaymentType("3");
		siteOnePaymentData.setIseWalletCard(false);
		siteOnePaymentData.setAmountCharged(Double.valueOf(responseData.getPayment().getAmount()));
		return siteOnePaymentData;
	}

	public SiteOneCAGlobalPaymentRequest populateGlobalPaymentRequestData(final String token, final String amount,
			AddressData addressData)
	{
		final SiteOneCAGlobalPaymentRequest requestData = new SiteOneCAGlobalPaymentRequest();
		final CAGlobalPaymentCard card = new CAGlobalPaymentCard();
		card.setTemporary_token(token);
		requestData.setCard(card);
		if (addressData == null)
		{
			addressData = new AddressData();
		}
		if (null != getSessionService().getAttribute("globalPaymentAddress"))
		{
			addressData.setLine1(getSessionService().getAttribute("globalPaymentAddress"));
		}

		if (null != getSessionService().getAttribute("globalPostalCode"))
		{
			addressData.setPostalCode(getSessionService().getAttribute("globalPostalCode"));
		}
		final CAGlobalPaymentAddress address = new CAGlobalPaymentAddress();
		if (null != addressData)
		{
			address.setLine1(addressData.getLine1());
			address.setPostal_code(addressData.getPostalCode());
		}
		final CAGlobalPaymentCustomer customer = new CAGlobalPaymentCustomer();
		customer.setBilling_address(address);
		requestData.setCustomer(customer);
		final CAGlobalPaymentDetails paymentDetails = new CAGlobalPaymentDetails();
		paymentDetails.setAmount(amount);
		paymentDetails.setCurrency_code("124");
		paymentDetails.setReference_id("MER-".concat(UUID.randomUUID().toString()));
		requestData.setPayment(paymentDetails);
		final CAGlobalPaymentTransaction transaction = new CAGlobalPaymentTransaction();
		transaction.setCountry_code("124");
		final CAGlobalPaymentIndicators indicators = new CAGlobalPaymentIndicators();
		indicators.setAddress_verification_service(true);
		indicators.setGenerate_receipt(true);
		transaction.setProcessing_indicators(indicators);
		requestData.setTransaction(transaction);
		return requestData;
	}

	public String getAlphaNumericString()
	{
		// chose a Character random from this String
		final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

		// create StringBuffer size of AlphaNumericString
		final StringBuilder sb = new StringBuilder(8);

		for (int i = 0; i < 8; i++)
		{

			// generate a random number between
			// 0 to AlphaNumericString variable length
			final int index = (int) (AlphaNumericString.length() * ThreadLocalRandom.current().nextDouble());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	/**
	 * @return the creditCardNameMapping
	 */
	public CreditCardNameMapping getCreditCardNameMapping()
	{
		return creditCardNameMapping;
	}

	/**
	 * @param creditCardNameMapping
	 *           the creditCardNameMapping to set
	 */
	public void setCreditCardNameMapping(final CreditCardNameMapping creditCardNameMapping)
	{
		this.creditCardNameMapping = creditCardNameMapping;
	}

	@Override
	public void voidGlobalPaymentDetails(final SiteOnePaymentInfoData siteOnePaymentInfoData)
	{
		// YTODO Auto-generated method stub
		final SiteOneCAGlobalPaymentVoidRequest voidRequest = populateVoidPaymentData(
				siteOnePaymentInfoData.getAmountCharged().toString());
		siteOneEwalletWebService.updateVoidPayment(voidRequest, siteOnePaymentInfoData.getVaultToken(),
				siteOnePaymentInfoData.getGlobalAuthToken());

	}

}
