/**
 *
 */
package com.siteone.core.email.service.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.product.impl.DefaultProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.Arrays;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.email.service.SiteOneShareEmailService;
import com.siteone.core.event.InvoiceDetailsEvent;
import com.siteone.core.event.SendOrderDetailsEvent;
import com.siteone.core.event.ShareCartEvent;
import com.siteone.core.event.SharedProductEvent;
import com.siteone.core.model.SiteOneInvoiceModel;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOneShareEmailService implements SiteOneShareEmailService
{
	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "productService")
	private DefaultProductService productService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "commerceCartService")
	private CommerceCartService commerceCartService;
	
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

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
	public void shareInvoiceEmailForCode(final String code, final String emails, final String uid)
	{
		final String[] emailList = emails.split(",");

		for (final String email : emailList)
		{
			eventService.publishEvent(initializeEvent(new InvoiceDetailsEvent(), code, email, uid));

		}
	}

	public InvoiceDetailsEvent initializeEvent(final InvoiceDetailsEvent event, final String code,
			final String email, String uid)
	{
		final String countryCode = StringUtils.substringAfterLast(uid,SiteoneCoreConstants.SEPARATOR_UNDERSCORE);
		if(countryCode.equalsIgnoreCase(SiteoneCoreConstants.CA_ISO_CODE)) {
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		} else {
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setEmailAddress(email);
		event.setUid(uid);
		event.setInvoiceNumber(code);
		return event;
	}

	@Override
	public void shareOrderDetailEmailForCode(final String orderCode, final String code, final String emails, final String uid)
	{
		final String[] emailList = emails.split(",");

		for (final String email : emailList)
		{

			eventService.publishEvent(initializeEvent(new SendOrderDetailsEvent(), orderCode, code, email, uid));
		}


	}

	public SendOrderDetailsEvent initializeEvent(final SendOrderDetailsEvent event, final String orderCode, final String code, final String email, final String uid)
	{

		final String countryCode = StringUtils.substringAfterLast(uid,SiteoneCoreConstants.SEPARATOR_UNDERSCORE);
		if(countryCode.equalsIgnoreCase(SiteoneCoreConstants.CA_ISO_CODE)) {
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
		} else {
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
		}
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		event.setEmailAddress(email);
		event.setOrderCode(orderCode);
		event.setCode(code);
		event.setUid(uid);

		return event;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.email.service.SiteOneShareEmailService#getsharedProductEmailForCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sharedProductEmailForCode(final String code, final String emails)
	{
		final String[] emailList = emails.split(",");

		final ProductModel productModel = productService.getProductForCode(code);
		final ProductData productData = productFacade.getProductForCodeAndOptions(code,
				Arrays.asList(ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE));

		final String stockavailabilitymessage = productData.getStockAvailabilityMessage();
		final String userName = (customerFacade.getCurrentCustomer()).getFirstName();

		for (final String email : emailList)
		{
			eventService
					.publishEvent(initializeEvent(new SharedProductEvent(), productModel, email, userName, stockavailabilitymessage));
		}

	}

	public SharedProductEvent initializeEvent(final SharedProductEvent event, final ProductModel productModel, final String email,
			final String userName, final String stockavailabilitymessage)
	{
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
		}
		else 
		{
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
		}
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());

		event.setProductModel(productModel);
		event.setUserName(userName);
		event.setEmail(email);
		event.setStockavailabilitymessage(stockavailabilitymessage);

		return event;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.email.service.SiteOneShareEmailService#getsharedCartEmailForCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void shareCartEmailForCode(final String code, final String emails)
	{
		final String[] emailList = emails.split(",");

		final CustomerModel customerModel = (CustomerModel) userService.getCurrentUser();
		//final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final CartModel cartModel = commerceCartService.getCartForCodeAndUser(code, customerModel);

		for (final String email : emailList)
		{
			eventService.publishEvent(initializeEvent(new ShareCartEvent(), cartModel, email, customerModel));

		}
	}

	public ShareCartEvent initializeEvent(final ShareCartEvent event, final CartModel cart, final String email,
			final CustomerModel customerModel)
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setEmailAddress(email);
		event.setCart(cart);
		if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_US))
		{
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
		}
		else 
		{
			event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
			event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
		}
		if (customer != null)
		{
		if (customer.getDefaultB2BUnit() != null)
		{
		if (customer.getDefaultB2BUnit().getName() != null)
		{
		event.setAccountName(customer.getDefaultB2BUnit().getName());
		}
		if (customer.getDefaultB2BUnit().getUid() != null)
		{
		event.setAccountNumber(customer.getDefaultB2BUnit().getUid().endsWith(SiteoneCoreConstants.INDEX_OF_CA) ? customer.getDefaultB2BUnit().getUid().replace(SiteoneCoreConstants.INDEX_OF_CA, "") :
			customer.getDefaultB2BUnit().getUid().replace(SiteoneCoreConstants.INDEX_OF_US, ""));
		}
		}
		if (customer.getName() != null)
		{
		event.setCustomerName(customer.getName());
		}
		if (customer.getEmail() != null)
		{
		event.setCustomerEmail(customer.getEmail());
		}
		}
		event.setCustomer(customerModel);
		return event;
	}

	

}
