package com.siteone.v2.controller;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOnePaymentUserData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.SaveCartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceSaveCartException;
import de.hybris.platform.commercewebservicescommons.dto.order.CartWsDTO;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.siteone.commerceservice.checkout.data.PaymentCartInfoDTO;
import com.siteone.commerceservices.dto.checkout.SiteOnePaymentUserWsDTO;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.payment.cayan.SiteOneCayanTransportFacade;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.ewallet.impl.DefaultSiteOneEwalletFacadeImpl;
import com.siteone.facades.exceptions.CardAlreadyPresentInUEException;
import com.siteone.facades.exceptions.NickNameAlreadyPresentInUEException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.utils.CayanBoarcardResponseForm;
import com.siteone.utils.SiteOneEwalletDataUtil;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import de.hybris.platform.servicelayer.time.TimeService;


@Controller
@RequestMapping(value = "/{baseSiteId}/checkout/siteone-payment")
@Tag(name = "Siteone Checkout Payment")
public class SiteOnePaymentMethodCheckoutController {

	private static final String CAYAN_PAYMENT_WEB_API = "siteone.cayan.payment.webApi";
	private static final String FAILURE = "Failure";
	private static final String SUCCESS = "SUCCESS";
	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";

	private static final Logger LOGGER = Logger.getLogger(SiteOnePaymentMethodCheckoutController.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;
	
	@Resource(name = "saveCartFacade")
	private SaveCartFacade saveCartFacade;
	
	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "siteOneCayanTransportFacade")
	private SiteOneCayanTransportFacade siteOneCayanTransportFacade;
	
	@Resource(name = "defaultSiteOneEwalletDataUtil")
	private SiteOneEwalletDataUtil siteOneEwalletDataUtil;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;
	
	@Resource(name = "timeService")
	private TimeService timeService;
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/paymentInfo")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "paymentInfo", summary = "Get the payment details", description = "Get the payment details")
	public PaymentCartInfoDTO getPaymentPageDetails(@RequestParam(value = "storeId", required = false)	final String storeId,
			 @Parameter(description = "unitId") @RequestParam(required = false) final String unitId) throws CommerceSaveCartException
	{
		PaymentCartInfoDTO paymentCartInfo = new PaymentCartInfoDTO();
		
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(null);
			
			b2bCheckoutFacade.setDeliveryAddressIfAvailable();
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
			final String webApi = configurationService.getConfiguration().getString(CAYAN_PAYMENT_WEB_API);
			paymentCartInfo.setWebApi(webApi);
			List<SiteOneEwalletData> eWallet = new ArrayList<>();
			final CustomerData customerData = ((DefaultSiteOneCustomerFacade) customerFacade).getCurrentCustomer();
			eWallet = ((DefaultSiteOneEwalletFacadeImpl) siteOneEwalletFacade).filterValidEwalletData(customerData);
			final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			if (StringUtils.isNotEmpty(b2bUnit.getCreditCode()))
			{
				paymentCartInfo.setCreditCode(b2bUnit.getCreditCode());
				paymentCartInfo.setIsPOAEnabled(isPOAEnabled(b2bUnit));
				
			}
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFulfilmentStatus(cartData);
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFreights(cartData, fulfilmentStatus);
			}
			
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(CartData.class, CartWsDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    CartWsDTO cartWsDTO = mapper.map(cartData, CartWsDTO.class);
			paymentCartInfo.setCartData(cartWsDTO);
			paymentCartInfo.setEwallet(eWallet);
			return paymentCartInfo;
		}
		catch(Exception ex)
	    {
			LOGGER.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getPaymentPageDetails ");
	    }
	}


	@PostMapping("/getBoardCardTransportKey")
	@ApiBaseSiteIdParam
	@Operation(operationId = "getBoardCardTransportKey", summary = "Get transportkey", description = "Get transportkey for boardcard")
	public @ResponseBody String getBoardCardTransportKey()
	{
		String transportKey = null;
		try
		{
		transportKey = siteOneCayanTransportFacade.getBoardCardTransportKeyForMobile();
		Gson gson = new Gson();
		return gson.toJson(transportKey);
		}
		catch(Exception ex)
	    {
			LOGGER.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getBoardCardTransportKey ");
	    }
	}
	
	@GetMapping("/add-ewalletStatus=User_Cancelled")
	@ApiBaseSiteIdParam
	@Operation(operationId = "addEwalletCancel", summary = "Cancel add ewallet iframe", description = "Cancel add ewallet iframe")
	public @ResponseBody String cancelPayment()
	{
		try
		{
			Gson gson = new Gson();
			return gson.toJson(FAILURE);
		}
		catch(Exception ex)
	    {
			LOGGER.error(ex);
	        throw new RuntimeException("Exception occured while calling through method cancelPayment ");
	    }
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/add-ewallet")
	@ApiBaseSiteIdParam
	@Operation(operationId = "addEwallet", summary = "Add credit card in wallet", description = "Add credit card in wallet")
	public @ResponseBody String addEwallet( @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@RequestParam(value = "saveCard", defaultValue = "true") final boolean saveCard,@RequestParam(value = "cartId", required = false) final String cartId,
			final CayanBoarcardResponseForm boardCardRespForm)
	{
		Gson gson = new Gson();
		if (null != boardCardRespForm
				&& boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_APPROVED))
		{
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			if(saveCard)
			{
			final SiteOneEwalletData ewalletData = siteOneEwalletDataUtil.convert(boardCardRespForm, boardCardRespForm.getNickName());
			try
			{
				siteOneEwalletFacade.addEwalletDetails(ewalletData);
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOGGER.error("Not able to establish connection with UE to create contact", resourceAccessException);
				return gson.toJson("We're unable to add your card at this time.");
			}
			catch (final CardAlreadyPresentInUEException cardAlreadyPresentInUEException)
			{
				LOGGER.error("Card already present for customer in UE", cardAlreadyPresentInUEException);
				return gson.toJson("Card already present for customer.");
			}
			catch (final NickNameAlreadyPresentInUEException nickNameAlreadyPresentInUEException)
			{
				LOGGER.error("Nick Name already present for customer in UE", nickNameAlreadyPresentInUEException);
				return gson.toJson("Nickname should be different for each credit card.");
			}
			catch (final Exception e)
			{
				LOGGER.error("Failed to add Credit Card details", e);
				return gson.toJson("We're unable to add your card at this time.");
			}
			}
		}
		else if(null != boardCardRespForm
				&& (boardCardRespForm.getStatus().equalsIgnoreCase("FAILED") || boardCardRespForm.getStatus().equalsIgnoreCase("DECLINED")))
					{
					
					if (null != cartId)
						{
						final LinkToPayCayanResponseModel cayanResponse = new LinkToPayCayanResponseModel();
						
						final CustomerData customerData = ((DefaultSiteOneCustomerFacade) customerFacade).getCurrentCustomer();
						
						if(null != customerData)								
							{							
							cayanResponse.setCustomerName(customerData.getFirstName() +" "+customerData.getLastName());
							cayanResponse.setEmail(customerData.getEmail());
							cayanResponse.setToEmails(customerData.getEmail());
							}
							cayanResponse.setLast4Digits(								
									String.valueOf(boardCardRespForm.getCardNumber().substring(boardCardRespForm.getCardNumber().length() - 4)));			
							cayanResponse.setCreditCardZip(boardCardRespForm.getZipCode());
							cayanResponse.setTransactionStatus(boardCardRespForm.getStatus());
							cayanResponse.setTransactionStatus("Decline");													
							
							cayanResponse.setOrderNumber(cartId);
							cayanResponse.setCartID(cartId);
							
							siteoneLinkToPayAuditLogService.saveSiteoneCCAuditLog(cayanResponse);
							
							LOGGER.error("Invalid Boardcard response 1 : " + boardCardRespForm.getStatus());
							
							 final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
									.getSiteoneCCAuditDetails(cartId);
							if (null != auditModel)
							{
								final int declineCountLimit = Integer
										.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
								final int declineCount = auditModel.getDeclineCount().intValue();
								LOGGER.error("declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);
								if (declineCount >= declineCountLimit)
								{									
								LOGGER.error("Credit Card Decline limit crossed ");						
								return gson.toJson("Decline");						
								}
								else
								{
									LOGGER.error("Invalid Boardcard response 2 : " + boardCardRespForm.getStatus());
									if (boardCardRespForm.getErrorMessage() == null)
									{
										return gson.toJson("We're unable to add your card at this time.");
									}
									else
									{
										return gson.toJson(boardCardRespForm.getErrorMessage());
									}
								}
							}
							else
								{								
								if (boardCardRespForm.getErrorMessage() == null)
								{									
									return gson.toJson("We're unable to add your card at this time.");
								}
								else
								{									
									return gson.toJson(boardCardRespForm.getErrorMessage());
								}
								}
						}
					}
		else
		{
			LOGGER.error("Invalid Boardcard response  3: " + boardCardRespForm.getStatus());
			if (boardCardRespForm.getErrorMessage() == null)
			{
				return gson.toJson("We're unable to add your card at this time.");
			}
			else
			{
				return gson.toJson(boardCardRespForm.getErrorMessage());
			}
		}
		return gson.toJson(SUCCESS);
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/payment")
	@ApiBaseSiteIdParam
	@Operation(operationId = "payment", summary = "Get the paayment info", description = "Get the paayment info")
	public @ResponseBody SiteOnePaymentUserWsDTO getPaymentDetails(@RequestParam(value = "storeId", required = false)	final String storeId,  
																   @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
																   @RequestParam(value = "cartId", required = false) final String cartId) throws CommerceSaveCartException
	{

		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			if (null != storeId && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId)){
				sessionService.setAttribute("isMixedCartEnabled","mixedcart");
			}
			((SiteOneCartFacade) cartFacade).restoreSessionCart(null);

			final SiteOnePaymentUserData siteOnePaymentUserData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getPaymentOptions();
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
			boolean isCCDisabledAtDC = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getIsCCDisabledAtDC(cartData);
			if(BooleanUtils.isTrue(isCCDisabledAtDC))
			{
				siteOnePaymentUserData.getPaymentconfigData().setPayWithCC(false);
			}
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateDeliveryFee(cartData);
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFulfilmentStatus(cartData);
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFreights(cartData, fulfilmentStatus);
			}
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(CartData.class, CartWsDTO.class);
		    CartWsDTO cartWsDTO = mapper.map(cartData, CartWsDTO.class);
		    mapperFactory.classMap(SiteOnePaymentUserData.class, SiteOnePaymentUserWsDTO.class);
		    SiteOnePaymentUserWsDTO siteOnePaymentUserDTO = mapper.map(siteOnePaymentUserData, SiteOnePaymentUserWsDTO.class);
		    siteOnePaymentUserDTO.setCartData(cartWsDTO);
		    siteOnePaymentUserDTO.setIsAdmin(((SiteOneB2BUnitService) b2bUnitService).isAdminUser());
		    
		    if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("BulkDeliveryBranches", storeSessionFacade.getSessionStore().getStoreId()))
			{
		    	siteOnePaymentUserDTO.setIsBulkDeliveryBranch(true);
			}
			else
			{
				siteOnePaymentUserDTO.setIsBulkDeliveryBranch(false);
			}
		    
		    if(null != cartId)
		    {
		    final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
					.getSiteoneCCAuditDetails(cartId);
			if (null != auditModel)
			{
				final int declineCountLimit = Integer
						.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
				final int declineCount = auditModel.getDeclineCount().intValue();
				LOGGER.error("declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);
			
				if (declineCount < declineCountLimit)
				{
					siteOnePaymentUserDTO.setIsCreditPaymentBlocked(Boolean.FALSE);
				}
				else
				{
					LOGGER.error("Limit crossed for credit card attempts ");			
					
					try
					{

						final int creditCardBlockedSpan = Integer
								.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("CreditCardBlockedSpan"));

						final Date lastModification = auditModel.getModifiedtime();
						final Date currentDate = timeService.getCurrentTime();
						final Calendar threshold = Calendar.getInstance();
						threshold.setTime(currentDate);
						threshold.add(Calendar.HOUR, -creditCardBlockedSpan);
						if (lastModification.before(threshold.getTime()))
						{
							LOGGER.error("inside if condition 1 ");
							siteoneLinkToPayAuditLogService.resetSiteoneCCAuditLog(cartData.getCode());
							siteOnePaymentUserDTO.setIsCreditPaymentBlocked(Boolean.FALSE);
						}
						else
						{
							LOGGER.error("inside else condition 1 ");
							siteOnePaymentUserDTO.setIsCreditPaymentBlocked(Boolean.TRUE);
						}
					}
					catch (final Exception e)
					{
						LOGGER.error("Failed to get Card details getPaymentDetails", e);
						siteOnePaymentUserDTO.setIsCreditPaymentBlocked(Boolean.TRUE);						
					}
				}
			}		    
			else
			{
				siteOnePaymentUserDTO.setIsCreditPaymentBlocked(Boolean.FALSE);
			}
		    }
		    else
		    {
		    	siteOnePaymentUserDTO.setIsCreditPaymentBlocked(Boolean.FALSE);
		    }
		    return siteOnePaymentUserDTO;
		}
		catch(Exception ex)
	    {
			LOGGER.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getPaymentDetails ");
	    }
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/getAccountInformation")
	@ApiBaseSiteIdParam
	@Operation(operationId = "getAccountInformation", summary = "Get the account info", description = "Get the account info")
	public @ResponseBody SiteOneCustInfoData getAccountInformation()
			throws InvalidCartException, CommerceCartModificationException
	{
		try
		{
			final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
			SiteOneCustInfoData custInfoData = null;
			if (sessionShipTo!=null)	
			{
				custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(sessionShipTo.getUid());
			} 
			else if (sessionShipTo==null || !(custInfoData!=null && custInfoData.getCustomerCreditInfo()!=null && custInfoData.getCustomerCreditInfo().getCreditTermDescription()!=null 
						&& !custInfoData.getCustomerCreditInfo().getCreditTermDescription().trim().isEmpty())) 
			{
				final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
				final String unitId = b2bUnit.getUid();
				custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(unitId);
			}
			return custInfoData;
		}
		catch(Exception ex)
	    {
			LOGGER.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getAccountInformation ");
	    }
	}
	
	public List<SiteOneEwalletData> getEWallet(final CustomerData customer)
	{
		final String userUnitId = customer.getUnit().getUid();
		return siteOneEwalletFacade.getEWalletDataForCheckout(userUnitId);

	}
	
	private boolean isPOAEnabled(final B2BUnitModel b2bUnit)
	{

		if ((StringUtils.isNotEmpty(b2bUnit.getCreditCode()) && !SiteoneFacadesConstants.POA_CREDIT_CODE.contains(b2bUnit.getCreditCode()))
				&& (null != b2bUnit.getIsPayOnAccount() && b2bUnit.getIsPayOnAccount())
				|| (StringUtils.isNotEmpty(b2bUnit.getAccountGroupCode()) && b2bUnit.getAccountGroupCode().equalsIgnoreCase("JDC")))
		{
			if (StringUtils.isNotEmpty(b2bUnit.getCreditTermCode()) && !b2bUnit.getCreditTermCode().equalsIgnoreCase("CASH"))
			{

				return true;
			}
		}

		return false;
	}
		
}