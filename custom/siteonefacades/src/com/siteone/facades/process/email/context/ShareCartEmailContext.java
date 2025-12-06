/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.Config;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.context.MessageSource;

import com.siteone.core.model.ShareCartEmailProcessModel;
import com.siteone.facades.populators.PromotionPriceUtils;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import de.hybris.platform.search.restriction.SearchRestrictionService;


/**
 * @author 1219341
 *
 */
public class ShareCartEmailContext extends AbstractEmailContext<ShareCartEmailProcessModel>
{

	public static final String EMAIL_SUBJECT = "subject";
	public static final String CART = "cart";
	public static final String ACCOUNT_NAME = "accountName";
	public static final String ACCOUNT_NUMBER = "accountNumber";
	public static final String CUSTOMER_NAME = "customerName";
	public static final String CUSTOMER_EMAIL = "customerEmail";


	private Converter<CartModel, CartData> cartConverter;
	private static final String FIRST_NAME = "firstName";
	private static final String NUMBER_TOOL = "numberTool";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name="searchRestrictionService")
 	private SearchRestrictionService searchRestrictionService;

	@Override
	public void init(final ShareCartEmailProcessModel shareCartEmailProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(shareCartEmailProcessModel, emailPageModel);
		put(EMAIL, shareCartEmailProcessModel.getEmailAddress());
		put(DISPLAY_NAME, shareCartEmailProcessModel.getEmailAddress());
		
		try
		{
			this.searchRestrictionService.disableSearchRestrictions();
			final CartData cartData = getCartConverter().convert(shareCartEmailProcessModel.getCart());
			if(shareCartEmailProcessModel.getCustomer() instanceof B2BCustomerModel) {
				B2BCustomerModel customer = (B2BCustomerModel) shareCartEmailProcessModel.getCustomer();
				if(customer.getDefaultB2BUnit().getUid().endsWith("_CA")) {
					storeSessionFacade.setCurrentBaseStore("CA");
				}
				if(customer.getDefaultB2BUnit().getUid().endsWith("_US")) {
					storeSessionFacade.setCurrentBaseStore("US");
				}
			}
			if (null != cartData)
			{
				final List<OrderEntryData> cartDataEntries = cartData.getEntries();
				final List<PromotionResultData> appliedPromotions = cartData.getAppliedProductPromotions();
				if (null != cartDataEntries && null != appliedPromotions)
				{
					PromotionPriceUtils.findSalePriceForOrderEntries(cartDataEntries, cartData.getAppliedProductPromotions());
				}
			}
			final LanguageModel language = shareCartEmailProcessModel.getLanguage();
			if (language != null)
			{
				put(EMAIL_LANGUAGE, language);
			}
			put(CART, cartData);
			put(ACCOUNT_NAME, shareCartEmailProcessModel.getAccountName());
			put(ACCOUNT_NUMBER, shareCartEmailProcessModel.getAccountNumber());
			put(CUSTOMER_NAME, shareCartEmailProcessModel.getCustomerName());
			put(CUSTOMER_EMAIL, shareCartEmailProcessModel.getCustomerEmail());
			final BaseSiteModel baseSite = getSite(shareCartEmailProcessModel);
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
			//put(FROM_DISPLAY_NAME, shareCartEmailProcessModel.getCustomer().getDisplayName());
		}
         
		
		
		finally {
			this.searchRestrictionService.enableSearchRestrictions();
		}

		if (shareCartEmailProcessModel.getCustomer() instanceof B2BCustomerModel)
		{
			put(FIRST_NAME, ((B2BCustomerModel) shareCartEmailProcessModel.getCustomer()).getFirstName());

		}

		else
		{
			put(FIRST_NAME, "Siteone");
		}

		if (shareCartEmailProcessModel.getCustomer() instanceof B2BCustomerModel)
		{
			put(EMAIL_SUBJECT,
					((B2BCustomerModel) shareCartEmailProcessModel.getCustomer()).getFirstName()
							+ getMessageSource().getMessage("share.cart.siteone", null,
									getCommonI18NService().getLocaleForIsoCode(shareCartEmailProcessModel.getLanguage().getIsocode())));
		}
		else
		{
			put(EMAIL_SUBJECT, getMessageSource().getMessage("share.cart.siteone1", null,
					getCommonI18NService().getLocaleForIsoCode(shareCartEmailProcessModel.getLanguage().getIsocode())));
		}
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
	}


	@Override
	protected BaseSiteModel getSite(final ShareCartEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final ShareCartEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getCustomer();
	}


	@Override
	protected LanguageModel getEmailLanguage(final ShareCartEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}


	/**
	 * @return the cartConverter
	 */
	public Converter<CartModel, CartData> getCartConverter()
	{
		return cartConverter;
	}


	/**
	 * @param cartConverter
	 *           the cartConverter to set
	 */
	public void setCartConverter(final Converter<CartModel, CartData> cartConverter)
	{
		this.cartConverter = cartConverter;
	}



	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}


	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
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



}
