/* [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.controllers.pages;


import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;
import com.siteone.facade.payment.cayan.SiteOneCayanTransportFacade;
import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;
import com.siteone.integration.services.ue.SiteOneLinkToPayWebService;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.CayanBoarcardResponseForm;
import com.siteone.storefront.util.SiteOnePaymentInfoUtil;



/**
 * Controller for a link to pay page
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping("/**/link-to-pay")
public class LinkToPayController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(LinkToPayController.class);
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "verifyRecaptchaUtils")
	private VerifyRecaptchaUtils verifyRecaptchaUtils;

	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	@Resource
	private CommonI18NService commonI18NService;

	@Resource(name = "customerFacade")
	private SiteOneCustomerFacade customerFacade;

	@Resource(name = "siteOneCayanTransportFacade")
	private SiteOneCayanTransportFacade siteOneCayanTransportFacade;

	@Resource(name = "creditCardNameMapping")
	private CreditCardNameMapping creditCardNameMapping;

	@Resource(name = "siteOnePaymentInfoUtil")
	private SiteOnePaymentInfoUtil siteOnePaymentInfoUtil;

	@Resource(name = "siteOneLinkToPayWebService")
	private SiteOneLinkToPayWebService siteOneLinkToPayWebService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;


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


	private static final String FORM_GLOBAL_ERROR = "form.global.error";

	public static final String REDIRECT_PREFIX = "redirect:";

	private static final String DECLINED = "Decline";
	private static final String PENDING = "Pending";
	private static final String LINK2PAY_COOKIE_ORDERNUM="link2payCookieOrderNum";
	private static final String LINK2PAY_COOKIE_ORDERAMT="link2payCookieOrderAmt";
	private static final String  BASE_SITE ="siteone";


	@GetMapping
	private String decodedJWTToken(@RequestParam(value = "token", required = true)
	final String token, final Model model) throws JSONException, CMSItemNotFoundException, NoSuchAlgorithmException
	{
		boolean isTokenValid = false;
		final String[] claims = token.split("\\.");
		String orderNumber = null;
		String amount = null;
		if (claims.length == 3)
		{
			final JSONObject payload = new JSONObject(new String(customerFacade.decode(claims[1])));
			if (payload.length() > 0)
			{
				if (payload.has("exp") && claims[2] != null)
				{
					isTokenValid = customerFacade.isTokenExpired(claims[1], claims[0], claims[2]);
					if (isTokenValid)
					{
						if (payload.has("OrderNumber") && !payload.isNull("OrderNumber") && payload.has("Amount")
								&& !payload.isNull("Amount") && StringUtils.isNotBlank(payload.getString("OrderNumber"))
								&& StringUtils.isNotBlank(payload.getString("Amount")))
						{
							orderNumber = payload.getString("OrderNumber");
							amount = payload.getString("Amount");
							sessionService.setAttribute("orderNumber", orderNumber);
							sessionService.setAttribute("amount", amount);
							sessionService.setAttribute("link2Pay_token", token);
						}
					}

				}
				else
				{
					throw new JSONException("Payload doesn't contain expiry " + payload);
				}
			}
			else
			{

				throw new JSONException("Payload is Empty: ");
			}
		}
		else
		{
			throw new IllegalArgumentException("Invalid Token format");
		}
		model.addAttribute("orderNumber", orderNumber);
		model.addAttribute("orderAmount", amount);
		model.addAttribute("tokenValid", isTokenValid);
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		storeCmsPageInModel(model, getContentPageForLabelOrId("linktopayverification"));
		return ControllerConstants.Views.Pages.Account.LinkToPayVerificationPage;
	}


	@GetMapping(value = "/fetchOrderDetails")
	public @ResponseBody String fetchOrderDetails(@RequestParam(value = "orderNumber", required = true)
	final String orderNumber, @RequestParam(value = "orderAmount", required = true)
	final String orderAmount, final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam(value = "kountSessionId", required = true)
			final String kountSessionId, final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		final Cookie l2pCookieOrderNum = new Cookie(LINK2PAY_COOKIE_ORDERNUM, StringUtils.normalizeSpace(orderNumber));
		l2pCookieOrderNum.setMaxAge(10 * 24 * 60 * 60);
		l2pCookieOrderNum.setPath("/");
		l2pCookieOrderNum.setHttpOnly(true);
		l2pCookieOrderNum.setSecure(true);
		response.addCookie(l2pCookieOrderNum);

		final Cookie l2pCookieOrderAmt = new Cookie(LINK2PAY_COOKIE_ORDERAMT, StringUtils.normalizeSpace(orderAmount));
		l2pCookieOrderAmt.setMaxAge(10 * 24 * 60 * 60);
		l2pCookieOrderAmt.setPath("/");
		l2pCookieOrderAmt.setHttpOnly(true);
		l2pCookieOrderAmt.setSecure(true);
		response.addCookie(l2pCookieOrderAmt);

		SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData = null;
		if (null != orderNumber)
		{
			linkToPayOrderResponseData = customerFacade.getOrderDetails(orderNumber);
		}

		if (linkToPayOrderResponseData != null && linkToPayOrderResponseData.getResult() != null)
		{
			LOG.error("Fetched Order response success status " + linkToPayOrderResponseData.getIsSuccess()
					+ " , LinktoPay Status is " + linkToPayOrderResponseData.getResult().getLinkToPayStatus()
					+ " , LinkToPay transaction status is " + linkToPayOrderResponseData.getResult().getTransactionStatus());
		}
		else
		{
			LOG.error("linkToPayOrderResponseData is null");
		}

		sessionService.setAttribute("kountSessionId", kountSessionId);

		if (null != linkToPayOrderResponseData && linkToPayOrderResponseData.getIsSuccess()
				&& linkToPayOrderResponseData.getResult().getTransactionStatus().equalsIgnoreCase(PENDING))
		{
			final CartData cartData = new CartData();
			cartData.setCode(orderNumber);
			final PriceData priceData = new PriceData();
			priceData.setValue(new BigDecimal(orderAmount));
			cartData.setTotalPriceWithTax(priceData);
			LOG.error("checking kount response for request " + request);
			String kountCallStatus = customerFacade.fetchOrderDetailsResponse(linkToPayOrderResponseData, orderNumber,request,kountSessionId,cartData,orderAmount);
			LOG.error("kount call status is " +kountCallStatus);
			return kountCallStatus;			
		}
		else
		{
			LOG.error("Transactional Status is not pending");
			return DECLINED;
		}
	}
	
	@GetMapping(value = "/paymentDetails")
	public @ResponseBody String fetchPaymentDetails(final CayanBoarcardResponseForm boardCardRespForm, final Model model,
			final HttpServletRequest request)
	{
		String status = "failed";
		String orderNum = null;
		String orderAmt = null;
		if ((null != WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERNUM))
				&& (null != WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERAMT)))
		{
			final Cookie l2pOrderNumberCookie = WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERNUM);

			if (null != l2pOrderNumberCookie)
			{
				orderNum = l2pOrderNumberCookie.getValue();
			}
			final Cookie l2pOrderAmountCookie = WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERAMT);

			if (null != l2pOrderAmountCookie)
			{
				orderAmt = l2pOrderAmountCookie.getValue();
			}
		}
		try
		{
			if (null != boardCardRespForm)
			{
				LOG.error("Cayan Status is :" + boardCardRespForm.getStatus());
				if (boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_APPROVED))
				{
					SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData = null;
					if (null != orderNum)
					{
						linkToPayOrderResponseData = customerFacade.getOrderDetails(orderNum);
					}
					LOG.error("Inside Cayan Status approved" + orderNum);
				
					updateLinkToPayPaymentDetails(boardCardRespForm, orderNum, orderAmt);
					status = "success";
				}
				else
				{
					LOG.error("Inside Cayan Status declined else block"+ orderNum);
					saveAuditForSiteoneCCFail(boardCardRespForm,orderNum,orderAmt);
					status = "failed";
				}
			}
			else
			{	
				saveAuditForSiteoneCCFail(boardCardRespForm,orderNum,orderAmt);
				status = "failed";
				LOG.error("failed  cayan Status call else block");
			}
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in LinkToPay Controller - updateLinkToPayPaymentDetails(): " + exception.getMessage());
		}
		return status;
	}
	
	
	public void saveAuditForSiteoneCCFail(final CayanBoarcardResponseForm boardCardRespForm, final String orderNum, final String orderAmt)
	{
		SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData = null;

		if (null != orderNum)
		{
			linkToPayOrderResponseData = customerFacade.getOrderDetails(orderNum);
		}
		final LinkToPayCayanResponseModel cayanResponse = new LinkToPayCayanResponseModel();
		if (null != boardCardRespForm)
		{
			cayanResponse.setLast4Digits(
					String.valueOf(boardCardRespForm.getCardNumber().substring(boardCardRespForm.getCardNumber().length() - 4)));
			if (linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName() != null)
			{
				cayanResponse.setCustomerName(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName());
			}
			if (linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail() != null)
			{
				cayanResponse.setEmail(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
			}	
			cayanResponse.setCreditCardZip(boardCardRespForm.getZipCode());
			cayanResponse.setTransactionStatus(boardCardRespForm.getStatus());
		}
		else
		{
			cayanResponse.setLast4Digits("1234");
			cayanResponse.setEmail(linkToPayOrderResponseData.getResult().getBillingInfo().getEnteredByEmail());
			cayanResponse.setCustomerName(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName());
			cayanResponse.setCreditCardZip("123456");
			cayanResponse.setTransactionStatus("Decline");			
		}
		
		cayanResponse.setAmountCharged(orderAmt);
		
		cayanResponse.setToEmails(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
		cayanResponse.setToken(sessionService.getAttribute("link2Pay_token"));
		
		cayanResponse.setOrderNumber(orderNum);
		cayanResponse.setCartID(orderNum);	
		siteoneLinkToPayAuditLogService.saveSiteoneCCAuditLog(cayanResponse);
	}

	public void updateLinkToPayPaymentDetails(final CayanBoarcardResponseForm boardCardRespForm, final String orderNum,
			final String orderAmt)
	{	SiteOneWsLinkToPayOrderResponseData linkToPayOrderResponseData = null;
	   
		if (null != orderNum)
	{
		linkToPayOrderResponseData = customerFacade.getOrderDetails(orderNum);
	}

		LOG.error("Inside update linktopay payment details");
		final LinkToPayCayanResponseModel cayanResponse = new LinkToPayCayanResponseModel();
		cayanResponse.setAuthCode(boardCardRespForm.getAuthCode());
		cayanResponse.setLast4Digits(
				String.valueOf(boardCardRespForm.getCardNumber().substring(boardCardRespForm.getCardNumber().length() - 4)));
		if (sessionService.getAttribute("amount") != null)
		{
			cayanResponse.setAmountCharged(sessionService.getAttribute("amount"));
		}
		else
		{
			cayanResponse.setAmountCharged(orderAmt);
		}
	 cayanResponse.setSite(baseSiteService.getBaseSiteForUID(BASE_SITE));
		cayanResponse.setLanguage(commonI18NService.getCurrentLanguage());
		final DateFormat dateFormat = DateFormat.getDateInstance();
		final Calendar cals = Calendar.getInstance();
		final String currentDate = dateFormat.format(cals.getTime());
		final Date date = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
		cayanResponse.setDate(currentDate);
		cayanResponse.setPoNumber(linkToPayOrderResponseData.getResult().getBillingInfo().getPONumber());
		cayanResponse.setEmail(linkToPayOrderResponseData.getResult().getBillingInfo().getEnteredByEmail());
		cayanResponse.setCustomerName(linkToPayOrderResponseData.getResult().getContactInfo().getCustomerName());
		cayanResponse.setToEmails(linkToPayOrderResponseData.getResult().getContactInfo().getContactEmail());
		cayanResponse.setExpDate(boardCardRespForm.getExpDate());
		cayanResponse.setCreditCardType(creditCardNameMapping.getCardTypeName().get(boardCardRespForm.getCardType()));
		cayanResponse.setToken(boardCardRespForm.getToken());
		cayanResponse.setCreditCardZip(boardCardRespForm.getZipCode());
		cayanResponse.setTransactionStatus(boardCardRespForm.getStatus());
		cayanResponse.setAid(null);
		cayanResponse.setApplicationLabel(creditCardNameMapping.getCardTypeName().get(boardCardRespForm.getCardType()));
		cayanResponse.setPinStatement(null);
		cayanResponse.setTransactionReferenceNumber(siteOnePaymentInfoUtil.getAlphaNumericString());
		cayanResponse.setDeclineCode(boardCardRespForm.getStatus());
		cayanResponse.setCorrelationID(UUID.randomUUID().toString());
		cayanResponse.setExternalSystemId("2");
		if (sessionService.getAttribute("orderNumber") != null)
		{
			cayanResponse.setOrderNumber(sessionService.getAttribute("orderNumber"));
		}
		else
		{
			cayanResponse.setOrderNumber(orderNum);
		}
		LOG.error("Inside Cayan Response Model- update()" + cayanResponse.getOrderNumber());
		customerFacade.linkToPayPaymentSubmitToUE(cayanResponse);
		siteoneLinkToPayAuditLogService.saveAuditLog(cayanResponse);
	}
}

