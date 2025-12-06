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
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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

import com.siteone.core.model.OrderTrackingLinkEmailProcessModel;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1188173
 *
 */
public class OrderTrackingLinkEmailContext<BusinessProcessModel> extends AbstractEmailContext<OrderTrackingLinkEmailProcessModel>
{


	private static final Logger LOG = Logger.getLogger(OrderTrackingLinkEmailContext.class);
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
	public static final String SHIPPING_FEE_BRANCH = "isShippingFeeBranch";
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	public void init(final OrderTrackingLinkEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{

		LOG.error("Order tracking test at the start of init method in context ");

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
		consignmentData = getConsignmentConverter().convert(businessProcessModel.getConsignment());
		URL trackingUrl = null;
		if (businessProcessModel.getConsignment() != null)
		{
			final ConsignmentModel consignmentModel = businessProcessModel.getConsignment();
			if (consignmentModel.getTrackingLink() != null)
			{
				try
				{
					trackingUrl = URI.create(consignmentModel.getTrackingLink()).toURL();
					LOG.info("Tracking URL " + trackingUrl);
				}
				catch (final MalformedURLException e)
				{
					LOG.error("Invalid Tracking URL", e);
				}

			}
		}
		for (final ConsignmentData consignment : orderData.getConsignments())
		{
			if (null != consignmentData)
			{
				if (consignment.getCode().equalsIgnoreCase(consignmentData.getCode()))
				{
					consignmentData.setRequestedDate(consignment.getRequestedDate());
					consignmentData.setCreateDate(consignment.getCreateDate());
					consignmentData.setSubTotal(consignment.getSubTotal());
					consignmentData.setTax(consignment.getTax());
					consignmentData.setTotal(consignment.getTotal());
					consignmentData.setFreight(consignment.getFreight());
					consignmentData.setDeliveryMode(consignment.getDeliveryMode());
					consignmentData.setSpecialInstructions(consignment.getSpecialInstructions());
					consignmentData.setPointOfService(consignment.getPointOfService());
					consignmentData.setConsignmentAddress(consignment.getConsignmentAddress());
					if (consignment.getTrackingUrl() != null)
					{
						consignmentData.setTrackingUrl(consignment.getTrackingUrl());
						LOG.error("Tracking Link 21 " + consignment.getTrackingUrl());
					}
					else
					{
						consignmentData.setTrackingUrl(trackingUrl);
						LOG.error("Tracking error 77 " + trackingUrl);
					}

				}
			}
		}

		final BigDecimal subTotal = orderData.getTotalPrice().getValue().setScale(2,RoundingMode.HALF_UP);
		final PriceData subTotalPriceData = new PriceData();
		subTotalPriceData.setValue(subTotal);
		subTotalPriceData.setFormattedValue("$".concat(subTotal.toString()));
		orderData.setSubTotal(subTotalPriceData);

		put(ORDER_DETAILS, orderData);
		put(CONSIGNMENT_DETAILS, consignmentData);
		String accNo = "";
		if (null != orderData.getOrderingAccount() && orderData.getOrderingAccount().getUid().contains("_"))
		{
			accNo = orderData.getOrderingAccount().getUid().trim().split("_")[0];
		}
		put(ORDER_ACC_NO, accNo);
		put(REQ_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(consignmentData.getRequestedDate()));
		put(CREATED_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(consignmentData.getCreateDate()));
		put(SHIPPING_FEE_BRANCH, businessProcessModel.getIsShippingFeeBranch());


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
							+ orderData.getCode() + " " + getMessageSource().getMessage("order.ready.go", null,
									getCommonI18NService().getLocaleForIsoCode(businessProcessModel.getLanguage().getIsocode())));
		}

		final LanguageModel language = businessProcessModel.getLanguage();
		LOG.info(businessProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}

		if (!orderData.isGuestCustomer())
		{

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
		}
		else
		{

			final String orderPlacedByEmail = businessProcessModel.getOrder().getUser().getUid().trim().split("\\|")[1];
			put(EMAIL, orderPlacedByEmail);

			final String placedByUserDisplayName = businessProcessModel.getOrder().getUser().getName();
			put(DISPLAY_NAME, placedByUserDisplayName);

			final Collection<AddressModel> userAddresses = businessProcessModel.getOrder().getUser().getAddresses();
			userAddresses.stream().findFirst().ifPresent(address -> {
				if (address.getPhone1() != null)
				{
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

		//put(CONTACT_PERSON_EMAIL, businessProcessModel.getOrder().getContactPerson().getUid(),"ContactPerson");

		if (orderData.isGuestCustomer())
		{
			put(FIRST_NAME, orderData.getB2bCustomerData().getFirstName());
		}
		else
		{
			put(FIRST_NAME, ((B2BCustomerModel) businessProcessModel.getCustomer()).getFirstName());
		}


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
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);

		String paymentType = "";
		String cardNumber = "";
		String termsCode = "";
		final List<SiteoneCreditCardPaymentInfoModel> paymentInfoList = businessProcessModel.getOrder().getPaymentInfoList();
		final List<SiteonePOAPaymentInfoModel> poaPaymentInfoList = businessProcessModel.getOrder().getPoaPaymentInfoList();
		if (CollectionUtils.isNotEmpty(paymentInfoList))
		{
			final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = paymentInfoList.get(0);
			paymentType = creditCardPaymentInfo.getPaymentType();
			cardNumber = "XX" + creditCardPaymentInfo.getLast4Digits();
			LOG.info("card details>>>>>>>>>>>>>>>>>>>>>>>" + cardNumber + paymentType);

		}
		else if (CollectionUtils.isNotEmpty(poaPaymentInfoList))
		{
			final SiteonePOAPaymentInfoModel poaPaymentInfo = poaPaymentInfoList.get(0);
			paymentType = poaPaymentInfo.getPaymentType();
			termsCode = poaPaymentInfo.getTermsCode();
			LOG.info("POA details>>>>>>>>>>>>>>>>>>>>>>>" + termsCode + paymentType);

		}
		else
		{
			paymentType = "2";
		}
		put(PAYMENTTYPE, paymentType);
		put(CARDNUMBER, cardNumber);
		put(TERMSCODE, termsCode);
		storeSessionFacade.removeCurrentBaseStore();
		LOG.error("Order tracking test at the end of init method in context ");

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


	@Override
	protected BaseSiteModel getSite(final OrderTrackingLinkEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
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
	protected CustomerModel getCustomer(final OrderTrackingLinkEmailProcessModel businessProcessModel)
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
	protected LanguageModel getEmailLanguage(final OrderTrackingLinkEmailProcessModel businessProcessModel)
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
