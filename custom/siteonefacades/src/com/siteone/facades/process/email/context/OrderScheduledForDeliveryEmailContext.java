/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.context.MessageSource;

import com.siteone.core.model.OrderScheduledForDeliveryEmailProcessModel;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1188173
 *
 */
public class OrderScheduledForDeliveryEmailContext<BusinessProcessModel>
		extends AbstractEmailContext<OrderScheduledForDeliveryEmailProcessModel>
{
	private static final Logger LOG = Logger.getLogger(OrderScheduledForDeliveryEmailContext.class);
	public static final String EMAIL_SUBJECT = "subject";
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private Converter<ConsignmentModel, ConsignmentData> consignmentConverter;
	private ConsignmentData consignmentData;
	public static final String ORDER_DETAILS = "orderDetails";
	public static final String CONSIGNMENT_DETAILS = "consignmentDetails";
	public static final String REQ_DATE = "requestedDate";
	public static final String CREATED_DATE = "createdDate";
	public static final String CONTACT_PERSON_EMAIL = "contactPersonEmail";
	public static final String CONTACT_PERSON_DISPLAY_NAME = "contactPersonDisplayName";
	public static final String EMAIL_HEADING = "heading";
	public static final String CC_EMAIL = "ccEmail";
	public static final String ORDER_ACC_NO = "orderAccountNo";
	private static final String FIRST_NAME = "firstName";
	private static final String NUMBER_TOOL = "numberTool";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");
	private static final String PAYMENTTYPE = "paymentType";
	private static final String CARDNUMBER = "cardNumber";
	private static final String CARDTYPE = "cardType";
	private static final String TERMSCODE = "termsCode";
	private static final String IS_SPLIT_SHIPMENT = "isSplitShipment";
	public static final String GUESTCONTACTNUMBER = "guestContactNumber";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";
	private static final String QUOTE_NUMBER = "quoteNumber";

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	public void init(final OrderScheduledForDeliveryEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		final Map<String, String> ccEmails = new HashMap<>();
		final Map<String, String> bccEmails = new HashMap<>();

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
			put(COUNTRY_BASESITE_ID, baseSite.getUid());


		}
		storeSessionFacade.setCurrentBaseStore(businessProcessModel.getOrder().getPointOfService().getDivision().getUid());
		orderData = getOrderConverter().convert(businessProcessModel.getOrder());
		boolean isSplitShipment = false;
		if (orderData.getConsignments().size() > 1)
		{
			isSplitShipment = true;
		}
		put(IS_SPLIT_SHIPMENT, isSplitShipment);
		if(orderData.getQuoteNumber() != null)
		{
			put(QUOTE_NUMBER , businessProcessModel.getOrder().getQuoteNumber());
		}
		consignmentData = getConsignmentConverter().convert(businessProcessModel.getConsignment());
		for (final ConsignmentData consignment : orderData.getConsignments())
		{
			if (null != consignmentData)
			{
				if (consignment.getCode().equalsIgnoreCase(consignmentData.getCode()))
				{
					if(null!=consignment.getRequestedDate()){
						consignmentData.setRequestedDate(consignment.getRequestedDate());
					}
					if(null!=consignment.getCreateDate()){
						consignmentData.setCreateDate(consignment.getCreateDate());
					}
					if(null!=consignment.getSubTotal()){
						consignmentData.setSubTotal(consignment.getSubTotal());
					}
					if(null!=consignment.getTax()){
						consignmentData.setTax(consignment.getTax());
					}
					if(null!=consignment.getTotal()){
						consignmentData.setTotal(consignment.getTotal());
					}
					if(null!=consignment.getFreight()) {
						consignmentData.setFreight(consignment.getFreight());
					}
					if(null!=consignment.getDeliveryMode()) {
						consignmentData.setDeliveryMode(consignment.getDeliveryMode());
					}
					if(null!=consignment.getSpecialInstructions()) {
						consignmentData.setSpecialInstructions(consignment.getSpecialInstructions());
					}
					if(null!=consignment.getPointOfService()) {
						consignmentData.setPointOfService(consignment.getPointOfService());
					}
					if(null!=consignment.getConsignmentAddress()) {
						consignmentData.setConsignmentAddress(consignment.getConsignmentAddress());
					}
				}
			}
		}

		if(null!=orderData.getFreight()) {
			if(orderData.getFreight().contains("$")){
				String trimmedString = orderData.getFreight().trim().split("\\$")[1];
				double freightAmount = Double.parseDouble(trimmedString);
				final PriceData deliveryCostPriceData = new PriceData();
				deliveryCostPriceData.setValue(BigDecimal.valueOf(freightAmount));
				deliveryCostPriceData.setFormattedValue("$".concat(String.valueOf(freightAmount)));
				orderData.setDeliveryCost(deliveryCostPriceData);
			}
		}

		put(ORDER_DETAILS, orderData);
		put(CONSIGNMENT_DETAILS, consignmentData);
		String accNo = "";
		if (null != orderData.getOrderingAccount() && orderData.getOrderingAccount().getUid().contains("_"))
		{
			accNo = orderData.getOrderingAccount().getUid().trim().split("_")[0];
			put(ORDER_ACC_NO, accNo);
		}
		if(null!=consignmentData.getRequestedDate()){
			put(REQ_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(consignmentData.getRequestedDate()));
		}
		if(null!=consignmentData.getCreateDate()){
			put(CREATED_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(consignmentData.getCreateDate()));
		}
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());

		if (isSplitShipment)
		{
			put(EMAIL_SUBJECT,
					getMessageSource().getMessage("your.orderready.siteone2", null,
							getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())) + " #"
							+ orderData.getCode() + ".");
		}
		else
		{
			put(EMAIL_SUBJECT,
					getMessageSource().getMessage("your.orderready.siteone1", null,
							getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())) + " #"
							+ orderData.getCode() + " " + getMessageSource().getMessage("order.scheduled.delivery", null,
									getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));
		}

		if(!orderData.isGuestCustomer()){

			String orderPlacedByEmail = businessProcessModel.getOrder().getUser().getUid();
			if (orderPlacedByEmail.contains("storecontact"))
			{
				orderPlacedByEmail = businessProcessModel.getOrder().getStoreUser();
			}
			put(EMAIL, orderPlacedByEmail);

			String placedByUserDisplayName = ((B2BCustomerModel) businessProcessModel.getOrder().getUser()).getFirstName();
			if (null == placedByUserDisplayName || placedByUserDisplayName.isEmpty())
			{
				placedByUserDisplayName = businessProcessModel.getOrder().getStoreUserName();
			}
			put(DISPLAY_NAME, placedByUserDisplayName);

			String contactPersonEmail = businessProcessModel.getOrder().getContactPerson().getUid();
			if (contactPersonEmail.contains("storecontact"))
			{
				contactPersonEmail = businessProcessModel.getOrder().getStoreContact();
			}

			if (null != contactPersonEmail && StringUtils.isNotBlank(contactPersonEmail))
			{
				ccEmails.put(contactPersonEmail, contactPersonEmail);
				businessProcessModel.setCcEmails(ccEmails);
			}

		}else{
			String orderPlacedByEmail = businessProcessModel.getOrder().getUser().getUid().trim().split("\\|")[1];
			put(EMAIL, orderPlacedByEmail);

			String placedByUserDisplayName =  businessProcessModel.getOrder().getUser().getName();
			put(DISPLAY_NAME, placedByUserDisplayName);

			Collection<AddressModel> userAddresses =businessProcessModel.getOrder().getUser().getAddresses();
			userAddresses.stream().findFirst().ifPresent(address->{
				if(address.getPhone1()!=null) {
					put(GUESTCONTACTNUMBER, address.getPhone1());
				}
			});
		}

		final String bccEmail = Config.getString("bcc.email.address", null);
		if (StringUtils.isNotEmpty(bccEmail))
		{
			final String[] bccEmailList = bccEmail.split(",");
			for (final String email : bccEmailList)
			{
				bccEmails.put(email, "EcommCustomerEmails@siteone.com");
			}
		}
		businessProcessModel.setBccEmails(bccEmails);

		modelService.save(businessProcessModel);
		modelService.refresh(businessProcessModel);

		put(EMAIL_HEADING, "YOUR ORDER " + orderData.getCode() + "HAS BEEN SCHEDULED FOR DELIVERY.");

		if(orderData.isGuestCustomer()){
			put(FIRST_NAME, orderData.getB2bCustomerData().getFirstName());
		}else
			put(FIRST_NAME, ((B2BCustomerModel) businessProcessModel.getCustomer()).getFirstName());

		if(null!=orderData.getEntries()){
			orderData.getEntries().forEach(entries -> {
				if (null != entries && StringUtils.isNotEmpty(entries.getQuantityText()))
				{
					try
					{
						final double quantity = Double.parseDouble(entries.getQuantityText());
						final DecimalFormat formatter = new DecimalFormat("#0.00");
						entries.setQuantityText(formatter.format(quantity));
					}
					catch (final NumberFormatException numberFormatException)
					{
						LOG.error("Number Format Exception occured", numberFormatException);
					}
				}
			});
		}

		final LanguageModel language = businessProcessModel.getLanguage();

		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
		String paymentType = "";
		String cardNumber = "";
		String termsCode = "";
		if(null!=businessProcessModel.getOrder().getPaymentInfoList()){
			final List<SiteoneCreditCardPaymentInfoModel> paymentInfoList = businessProcessModel.getOrder().getPaymentInfoList();
			if (CollectionUtils.isNotEmpty(paymentInfoList))
			{
				final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = paymentInfoList.get(0);
				paymentType = creditCardPaymentInfo.getPaymentType();
				cardNumber = "XX" + creditCardPaymentInfo.getLast4Digits();
				LOG.info("card details>>>>>>>>>>>>>>>>>>>>>>>" + cardNumber + paymentType);

			}
		}else if(null!= businessProcessModel.getOrder().getPoaPaymentInfoList()){
			final List<SiteonePOAPaymentInfoModel> poaPaymentInfoList = businessProcessModel.getOrder().getPoaPaymentInfoList();
			if(CollectionUtils.isNotEmpty(poaPaymentInfoList)){
				final SiteonePOAPaymentInfoModel poaPaymentInfo = poaPaymentInfoList.get(0);
				paymentType = poaPaymentInfo.getPaymentType();
				termsCode = poaPaymentInfo.getTermsCode();
				LOG.info("POA details>>>>>>>>>>>>>>>>>>>>>>>" + termsCode + paymentType);
			}
		} else
		{
			paymentType = "2";
		}
		put(PAYMENTTYPE, paymentType);
		put(CARDNUMBER, cardNumber);
		put(TERMSCODE, termsCode);
		storeSessionFacade.removeCurrentBaseStore();
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
	protected BaseSiteModel getSite(final OrderScheduledForDeliveryEmailProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform.
	 * acceleratorservices.process.email.context.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final OrderScheduledForDeliveryEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.
	 * platform .acceleratorservices.process.email.context.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final OrderScheduledForDeliveryEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getOrder().getLanguage();
	}

	/**
	 * @return the consignmentConverter
	 */
	public Converter<ConsignmentModel, ConsignmentData> getConsignmentConverter()
	{
		return consignmentConverter;
	}


	/**
	 * @param consignmentConverter
	 *           the consignmentConverter to set
	 */
	public void setConsignmentConverter(final Converter<ConsignmentModel, ConsignmentData> consignmentConverter)
	{
		this.consignmentConverter = consignmentConverter;
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
