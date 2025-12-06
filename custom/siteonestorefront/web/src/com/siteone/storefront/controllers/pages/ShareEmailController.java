/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.core.event.LinkToPayEvent;
import com.siteone.core.event.ShareAssemblyEvent;
import com.siteone.core.event.ShareListEvent;
import com.siteone.facade.email.SiteOneShareEmailFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;


/**
 * @author 1129929
 *
 */
@Controller
public class ShareEmailController extends AbstractSearchPageController
{
	private static final String INVOICE_NUMBER_PATH_VARIABLE_PATTERN = "{invoiceNumber:.*}";

	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";

	private static final String CART_CODE_PATH_VARIABLE_PATTERN = "{cartCode:.*}";

	private static final String PRODUCT_CODE_PATH_VARIABLE_PATTERN = "{productcode:.*}";

	private static final String SAVED_LIST_CODE_PATH_VARIABLE_PATTERN = "{savedlistcode:.*}";

	private static final String ASSEMBLY_CODE_PATH_VARIABLE_PATTERN = "{assemblycode:.*}";
	
	private static final String  BASE_SITE ="siteone";
	private static final String LINK2PAY_COOKIE_ORDERNUM="link2payCookieOrderNum";
	private static final String LINK2PAY_COOKIE_ORDERAMT="link2payCookieOrderAmt";
	private static final String ORDERNUMBER="orderNumber";
	private static final String ORDERAMOUNT="amount";

	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource
	private CommonI18NService commonI18NService;

	@Resource(name = "commerceCartService")
	private CommerceCartService commerceCartService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "siteOneShareEmailFacade")
	private SiteOneShareEmailFacade siteOneShareEmailFacade;

	public static final String REDIRECT_PREFIX = "redirect:";


	@Resource(name = "productService")
	private ProductService productService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;



	@GetMapping("/invoiceDetailsEmail/" + INVOICE_NUMBER_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public @ResponseBody String shareInvoiceDetailsEmail(@PathVariable("invoiceNumber") final String code,
			@RequestParam(value = "email", required = false) final String emails, @RequestParam(value = "uid") final String uid, 
			final Model model, @RequestHeader(value = "referer", required = false) final String referer) throws CMSItemNotFoundException // NOSONAR
	{
		siteOneShareEmailFacade.shareInvoiceEmailForCode(code, emails, uid);
		return REDIRECT_PREFIX + referer;
	}

	@GetMapping("/sendorderdetails/" + ORDER_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public @ResponseBody String shareOrderDetailsEmail(@PathVariable("orderCode") final String orderCode,
			@RequestParam(value = "email", required = false) final String emails,
			@RequestParam(value = "invoiceCode") final String invoiceCode,
			@RequestParam(value = "uid", required = false) final String uid,
			@RequestHeader(value = "referer", required = false) final String referer)
	{
		siteOneShareEmailFacade.shareOrderDetailEmailForCode(orderCode, invoiceCode, emails, uid);
		return REDIRECT_PREFIX + referer;
	}

	@GetMapping("/shareCartEmail/" + CART_CODE_PATH_VARIABLE_PATTERN)
	public @ResponseBody String shareCartEmail(@PathVariable("cartCode") final String code,
			@RequestParam(value = "email", required = false) final String emails, final Model model,
			@RequestHeader(value = "referer", required = false) final String referer) throws CMSItemNotFoundException // NOSONAR
	{
		siteOneShareEmailFacade.shareCartEmailForCode(code, emails);
		return REDIRECT_PREFIX + referer;
	}

	@GetMapping("/shareProductEmail/" + PRODUCT_CODE_PATH_VARIABLE_PATTERN)
	public @ResponseBody String sharedProductEmail(@PathVariable("productcode") final String code,
			@RequestParam(value = "email", required = false) final String emails, final Model model,
			@RequestHeader(value = "referer", required = false) final String referer) throws CMSItemNotFoundException // NOSONAR
	{
		siteOneShareEmailFacade.sharedProductEmailForCode(code, emails);
		return REDIRECT_PREFIX + referer;

	}


	@GetMapping("/shareListEmail/" + SAVED_LIST_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public @ResponseBody String sharedListEmail(@PathVariable("savedlistcode") final String code,
			                                 @RequestParam(value = "email", required = false) final String emails,
												@RequestParam(value = "cprice", required = false) final boolean cPrice,
												@RequestParam(value = "rprice", required = false) final boolean rPrice,
			@RequestHeader(value = "referer", required = false) final String referer) throws CMSItemNotFoundException // NOSONAR
	{
		final String[] emailList = emails.split(",");
		siteoneSavedListFacade.shareListEmail(code, cPrice, rPrice, emailList);
		return REDIRECT_PREFIX + referer;
	}

	@GetMapping("/shareAssemblyEmail/" + ASSEMBLY_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public @ResponseBody String sharedAssemblyEmail(@PathVariable("assemblycode") final String code,
			@RequestParam(value = "email", required = false) final String emails,
			@RequestHeader(value = "referer", required = false) final String referer) throws CMSItemNotFoundException // NOSONAR
	{
		final String[] emailList = emails.split(",");

		final SavedListData savedListData = siteoneSavedListFacade.getDetailsPage(code);

		final String listName = savedListData.getName();
		final String userName = (customerFacade.getCurrentCustomer()).getFirstName();

		for (final String email : emailList)
		{
			eventService.publishEvent(initializeEvent(new ShareAssemblyEvent(), code, email, userName, listName));
		}
		return REDIRECT_PREFIX + referer;

	}

	public ShareAssemblyEvent initializeEvent(final ShareAssemblyEvent event, final String code, final String email,
			final String userName, final String assemblyName)
	{
		event.setBaseStore(baseStoreService.getBaseStoreForUid(BASE_SITE));
		event.setSite(baseSiteService.getBaseSiteForUID(BASE_SITE));
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setAssemblyCode(code);
		event.setAssemblyName(assemblyName);
		event.setUserName(userName);
		event.setEmail(email);

		return event;
	}



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
	
	@GetMapping("/sendOrderConfirmationEmail")
	public @ResponseBody String shareOrderConfirmationEmail(@RequestParam(value = "email", required = false)
	final String emails, @RequestHeader(value = "referer", required = false)
	final String referer, final HttpServletRequest request) throws CMSItemNotFoundException // NOSONAR
	{
		final String[] emailList = emails.split(",");
		String orderNum = null;
		String orderAmt = null;
		String orderNumber = null;
		String orderAmount = null;
		if ((null != WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERNUM))
				&& (null != WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERAMT)))
		{
			final Cookie l2pEmailOrderNumberCookie = WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERNUM);

			if (null != l2pEmailOrderNumberCookie)
			{
				orderNum = l2pEmailOrderNumberCookie.getValue();
			}
			final Cookie l2pEmailOrderAmountCookie = WebUtils.getCookie(request, LINK2PAY_COOKIE_ORDERAMT);

			if (null != l2pEmailOrderAmountCookie)
			{
				orderAmt = l2pEmailOrderAmountCookie.getValue();
			}
		}
		if (null != sessionService.getAttribute(ORDERNUMBER))
		{
			orderNumber = sessionService.getAttribute(ORDERNUMBER);
		}
		else
		{
			orderNumber = orderNum;
		}
		if (null != sessionService.getAttribute(ORDERAMOUNT))
		{
			orderAmount = sessionService.getAttribute(ORDERAMOUNT);
		}
		else
		{
			orderAmount = orderAmt;
		}
		for (final String email : emailList)
		{
			eventService.publishEvent(initializeEvent(new LinkToPayEvent(), email, orderNumber, orderAmount));
		}
		sessionService.removeAttribute(ORDERNUMBER);
		sessionService.removeAttribute(ORDERAMOUNT);

		return REDIRECT_PREFIX + referer;
	}

	public LinkToPayEvent initializeEvent(final LinkToPayEvent event, final String email, final String orderNumber,
			final String orderAmount)
	{

		event.setSite(baseSiteService.getBaseSiteForUID(BASE_SITE));
		event.setEmail(email);
		event.setLanguage(commonI18NService.getCurrentLanguage());
		event.setBaseStore(baseStoreService.getBaseStoreForUid(BASE_SITE));
		final DateFormat dateFormat = DateFormat.getDateInstance();
		final Calendar cals = Calendar.getInstance();
		final String currentDate = dateFormat.format(cals.getTime());
		final Date date = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
		event.setDate(currentDate);
		event.setTime(sdf.format(date));
		event.setOrderNumber(orderNumber);
		event.setOrderAmount(orderAmount);
		return event;
	}
}

