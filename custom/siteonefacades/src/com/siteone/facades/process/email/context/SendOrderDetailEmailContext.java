/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.Config;

import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.context.MessageSource;

import com.siteone.core.model.OrderDetailEmailProcessModel;
import com.siteone.facade.order.SiteOneOrderFacade;
import com.siteone.facades.populators.PromotionPriceUtils;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1190626
 *
 */
public class SendOrderDetailEmailContext extends AbstractEmailContext<OrderDetailEmailProcessModel>
{

	private static final Logger LOG = Logger.getLogger(SendOrderDetailEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	public static final String ORDER_DETAILS = "orderDetails";
	public static final String REQ_DATE = "requestedDate";
	public static final String CREATED_DATE = "createdDate";
	public static final String ACC_NO = "accountNum";
	public static final String STATUS = "status";
	public static final String NUMBER_TOOL = "numberTool";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");
	private static final String PAYMENTTYPE = "paymentType";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	private static final String CARDNUMBER = "cardNumber";

	private static final String CARDTYPE = "cardType";
	
	private static final String TERMSCODE = "termsCode";
	
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;
	@Resource(name = "orderFacade")
	private SiteOneOrderFacade siteOneOrderFacade;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	public void init(final OrderDetailEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(businessProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + businessProcessModel + "]");
		}
		else
		{
			put(BASE_SITE, baseSite);
			// Lookup the site specific URLs
			put(BASE_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), false, ""));
			put(BASE_THEME_URL, getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, false, ""));
			put(SECURE_BASE_URL,
					getSiteBaseUrlResolutionService().getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), true, ""));
			put(MEDIA_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, false));
			put(MEDIA_SECURE_BASE_URL, getSiteBaseUrlResolutionService().getMediaUrlForSite(baseSite, true));

			put(THEME, baseSite.getTheme() != null ? baseSite.getTheme().getCode() : null);
			final LanguageModel language = businessProcessModel.getLanguage();
			LOG.info(businessProcessModel.getLanguage().getIsocode());
			if (language != null)
			{
				put(EMAIL_LANGUAGE, language);
			}
			put(COUNTRY_BASESITE_ID, baseSite.getUid());
		}

		String accNo = "";
		if (null != businessProcessModel.getUid())
		{
			accNo = businessProcessModel.getUid().trim().split("_")[0];
		}
		put(ACC_NO, accNo);
		storeSessionFacade.setCurrentBaseStore(StringUtils.substringAfterLast(businessProcessModel.getUid(),SiteoneCoreConstants.SEPARATOR_UNDERSCORE));
		final OrderData orderData = siteOneOrderFacade.getOrderDetailsPage(businessProcessModel.getShipmentCode(),businessProcessModel.getUid(),null,null,Boolean.TRUE);

      if (orderData != null)
      {

   		orderData.getEntries().forEach(entries -> {

   			if (null != entries && null != entries.getQuantity() && StringUtils.isNotEmpty(entries.getQuantity().toString()))
   			{
   				try
   				{
   					entries.setQuantityText(entries.getQuantity().toString());
   				}
   				catch (final NumberFormatException numberFormatException)
   				{
   					LOG.error("Number Format Exception occured", numberFormatException);
   				}
   			}
   		});

  		
   		if (null != orderData.getEntries() && null != orderData.getAppliedProductPromotions())
   		{
   			final List<OrderEntryData> orderDataEntries = orderData.getEntries();
      		final List<PromotionResultData> appliedPromotions = orderData.getAppliedProductPromotions();
   			PromotionPriceUtils.findSalePriceForOrderEntries(orderDataEntries, appliedPromotions);
   		}

   		put(ORDER_DETAILS, orderData);
   		if (orderData.getRequestedDate() != null)
   		{
   			put(REQ_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(orderData.getRequestedDate()));
   		}
   		if (orderData.getCreated() != null)
   		{
   			put(CREATED_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(orderData.getCreated()));
   		}
   		
   		String paymentType = "";
   		String cardNumber = "";
   		String termsCode = "";
   		if (orderData.getSiteOnePaymentInfoData() != null)
   		{
   			final SiteOnePaymentInfoData paymentInfoList = orderData.getSiteOnePaymentInfoData();
   			if (paymentInfoList.getPaymentType() != null)
   			{
   				paymentType = paymentInfoList.getPaymentType();
   			}
   			if (paymentInfoList.getCardNumber() != null)
   			{
   				cardNumber = "XX" + paymentInfoList.getCardNumber();
   			}
   			LOG.info("card details>>>>>>>>>>>>>>>>>>>>>>>" + cardNumber + paymentType);
   		}
   		if (orderData.getSiteOnePOAPaymentInfoData() != null)
   		{
   			final SiteOnePOAPaymentInfoData accountPayment = orderData.getSiteOnePOAPaymentInfoData(); 
   			termsCode = accountPayment.getTermsCode();
   		}

   		put(PAYMENTTYPE, paymentType);
   		put(CARDNUMBER, cardNumber);
   		put(TERMSCODE, termsCode);
   		put(FROM_EMAIL, emailPageModel.getFromEmail());
   		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());

   		put(EMAIL, businessProcessModel.getEmailAddress());
   		put(DISPLAY_NAME, businessProcessModel.getEmailAddress());
   		if (orderData.getStatusDisplay() != null)
   		{
   			put(STATUS, orderData.getStatusDisplay());
   		}
      }

		put(EMAIL_SUBJECT, getMessageSource().getMessage("your.siteone.orderdetail", null,
				getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
		storeSessionFacade.removeCurrentBaseStore();
	}
	
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}



	/**
	 * @return the orderData
	 */
	public OrderData getOrderData()
	{
		return orderData;
	}



	/**
	 * @return the orderConverter
	 */
	public Converter<OrderModel, OrderData> getOrderConverter()
	{
		return orderConverter;
	}



	/**
	 * @param orderConverter
	 *           the orderConverter to set
	 */
	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter)
	{
		this.orderConverter = orderConverter;
	}


	@Override
	protected BaseSiteModel getSite(final OrderDetailEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderDetailEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getCustomer();
	}

	@Override
	protected LanguageModel getEmailLanguage(final OrderDetailEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
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
