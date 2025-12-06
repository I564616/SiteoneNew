/**
 *
 */
package com.siteone.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.core.event.KountDeclineEvent;
import com.siteone.core.kount.service.impl.DefaultSiteOneKountService;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;


/**
 * @author Tcs
 *
 */
@Controller
@RequestMapping(value = "/checkout/multi/kount")
public class KountValidationCheckoutStepController
{
	private static final Logger LOG = Logger.getLogger(PickupDeliveryCheckoutStepController.class);

	@Resource(name = "siteOneKountService")
	private DefaultSiteOneKountService defaultSiteOneKountService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	private static final String KOUNT_RESPONSE_DECLINED = "N";

	private static final String RESPONSE_DECLINED = "D";

	private static final String DECLINED = "Decline";

	@PostMapping("/evaluateInquiry")
	@RequireHardLogIn
	public @ResponseBody String evaluateInquiry(@RequestParam(name = "kountSessionId")
	final String kountSessionId, final HttpServletRequest request)
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		sessionService.setAttribute("kountSessionId", kountSessionId);

		String kountAuto = null;
		try
		{
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			kountAuto = defaultSiteOneKountService.evaluateInquiry(cartData);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in KountValidationCheckoutStepController - evaluateInquiry(): " + exception.getMessage());
		}
		if (StringUtils.isNotBlank(kountAuto) && null != sessionService.getAttribute("kountSessionId"))
		{
			final String[] splitResponse = kountAuto.split("_");
			if (splitResponse[0].equalsIgnoreCase(RESPONSE_DECLINED))
			{
				LOG.error("Kount RIS Inquiry Declined: " + splitResponse[0]);
				if (!(sessionService.getAttribute("guestUser") != null
						&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest")))
				{
					final KountDeclineEvent kountDeclineEvent = new KountDeclineEvent();
					eventService.publishEvent(initializeEvent(kountDeclineEvent, cartData));
				}
				else
				{
					final KountDeclineEvent kountDeclineEvent = new KountDeclineEvent();
					eventService.publishEvent(initializeEventGuest(kountDeclineEvent, cartData));
				}
				return DECLINED;
			}
			return splitResponse[0];
		}
		else
		{
			return KOUNT_RESPONSE_DECLINED;
		}
	}

	public KountDeclineEvent initializeEvent(final KountDeclineEvent event, final CartData cartData)
	{

		event.setAccountNumber(cartData.getB2bCustomerData().getUnit().getUid());
		event.setAccountName(cartData.getB2bCustomerData().getUnit().getName());
		event.setCustomerName(cartData.getB2bCustomerData().getFirstName());
		event.setEmailAddress(cartData.getB2bCustomerData().getUid());
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		event.setOrderNumber(cartData.getCode());
		return event;

	}


	private KountDeclineEvent initializeEventGuest(final KountDeclineEvent event, final CartData cartData)
	{
		event.setAccountName("Guest");
		if (cartData.getGuestContactPerson().getName() != null)
		{
			event.setCustomerName(cartData.getGuestContactPerson().getName());
		}
		if (cartData.getGuestContactPerson().getUid() != null)
		{
			final String[] splitResponse = cartData.getGuestContactPerson().getUid().split("[|]");
			if (StringUtils.isNotBlank(splitResponse[1]))
			{
				event.setEmailAddress(splitResponse[1]);
			}
		}
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		event.setOrderNumber(cartData.getCode());
		return event;
	}

}
