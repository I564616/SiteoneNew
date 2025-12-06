/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.model.InvoiceDetailsProcessModel;
import com.siteone.core.model.SiteOneInvoiceEntryModel;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.InvoiceData;
import com.siteone.facade.InvoiceEntryData;
import com.siteone.facade.invoice.InvoiceFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.core.constants.SiteoneCoreConstants;

/**
 * @author 1129929
 *
 */
public class InvoiceDetailsContext extends AbstractEmailContext<InvoiceDetailsProcessModel>
{

	private static final Logger LOG = Logger.getLogger(InvoiceDetailsContext.class);
	public static final String INVOICE_DETAILS_EMAIL_SUBJECT = "invoiceDetails";
	public static final String PRODUCTS = "products";
	private static final String CUSTOMER_EMAIL = "customerEmail";
	private static final String MESSAGE = "message";
	public static final String EMAIL_SUBJECT = "subject";
	public static final String INVOICED_DATE = "invoicedDate";
	public static final String PICKUPDATE = "pickupDeliveryDate";
	public static final String ACCOUNT_NUMBER = "accountNumber";
	public static final String STORE_TITLE = "storeTitle";
	public static final String USER_NAME = "userName";
	private static final int CURRENCY_TOTAL_PRICE_DIGITS = Config.getInt("curency.totalprice.digits", 2);
	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";

	private static final String PAYMENTTYPE = "paymentType";

	private static final String CARDNUMBER = "cardNumber";

	private static final String CARDTYPE = "cardType";
	
	private static final String TERMSCODE = "termsCode";

	private final List<ProductData> productDatas = new ArrayList<ProductData>();

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;


	@Resource(name = "siteOneFacadeMessageSource")
	private MessageSource messageSource;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	
	@Resource(name = "invoiceFacade")
	private InvoiceFacade invoiceFacade;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

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

	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}

	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}

	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}
	
	@Override
	public void init(final InvoiceDetailsProcessModel invoiceDetailsProcessModel, final EmailPageModel emailPageModel)
	{
		final BaseSiteModel baseSite = getSite(invoiceDetailsProcessModel);
		final String countryCode = StringUtils.substringAfterLast(invoiceDetailsProcessModel.getUid(),SiteoneCoreConstants.SEPARATOR_UNDERSCORE);
		storeSessionFacade.setCurrentBaseStore(countryCode);
		final InvoiceData invoiceData = invoiceFacade.getInvoiceDetailsForCode(invoiceDetailsProcessModel.getInvoicNum(),
				invoiceDetailsProcessModel.getUid(), null, Boolean.TRUE);

		if (null != invoiceData.getUser())
		{
			final B2BCustomerModel userModel = (B2BCustomerModel) b2bCustomerService.getUserForUID(invoiceData.getUser());
			if (null != userModel)
			{
				put(USER_NAME, userModel.getFirstName() + " " + userModel.getLastName());
			}

		}


		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + invoiceDetailsProcessModel + "]");
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
		
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		if (null != invoiceData.getInvoiceDate())
		{
			put(INVOICED_DATE, new SimpleDateFormat("MMMM dd, YYYY").format(invoiceData.getInvoiceDate()));
		}

		if (null != invoiceData.getPickupOrDeliveryDateTime())
		{
			put(PICKUPDATE, new SimpleDateFormat("MMMM dd, YYYY").format(invoiceData.getPickupOrDeliveryDateTime()));
		}

		if (invoiceDetailsProcessModel.getUid() != null)
		{
			put(ACCOUNT_NUMBER, StringUtils.split(invoiceDetailsProcessModel.getUid(), "_")[0]);
		}

		if (null != invoiceData.getStoreId())
		{
			final PointOfServiceModel posModel = getSiteOneStoreFinderService().getStoreForId(invoiceData.getStoreId());
			if (null != posModel)
			{
				put(STORE_TITLE, posModel.getTitle());
			}
		}
		if (invoiceData.getStoreId() != null)
		{
			invoiceData.setBranchNumber(invoiceData.getStoreId());
		}
		if (invoiceData.getInvoiceNumber() != null)
		{
			invoiceData.setInvoiceNumber(invoiceData.getInvoiceNumber());
		}
		if (invoiceDetailsProcessModel.getUid() != null)
		{
			invoiceData.setAccountDisplay(fetchUserAccountId(invoiceDetailsProcessModel.getUid()));
		}
		if (invoiceData.getContact_phone() != null)
		{
			invoiceData.setContact_phone(invoiceData.getContact_phone());
		}
		if (invoiceData.getBranchAddress() != null)
		{
			AddressData brDetails = invoiceData.getBranchAddress();
			final AddressData branchAddress = new AddressData();
			branchAddress.setLine1(brDetails.getLine1());
			branchAddress.setLine2(brDetails.getLine2());
			branchAddress.setTown(brDetails.getTown());
			branchAddress.setRegion(brDetails.getRegion());
			branchAddress.setPostalCode(brDetails.getPostalCode());
			if (invoiceData.getContact_phone() != null)
			{
			branchAddress.setPhone(invoiceData.getContact_phone());
			}
			invoiceData.setAddress(branchAddress);
			invoiceData.setBranchAddress(branchAddress);
		}
		if (invoiceData.getInstructions() != null)
		{
			invoiceData.setInstructions(invoiceData.getInstructions());
		}

		//Truncating price
		try
		{
			if (null != invoiceData.getFreight())
			{
				invoiceData.setFreight(invoiceData.getFreight());
			}

			if (null != invoiceData.getTotalPayment())
			{
				invoiceData.setTotalPayment(invoiceData.getTotalPayment());
			}

			if (null != invoiceData.getAmountDue())
			{
				invoiceData.setAmountDue(invoiceData.getAmountDue());
			}

		}
		catch (final NumberFormatException numberFormatException)
		{
			LOG.error("Number format exception occured", numberFormatException);
		}

		invoiceData.setSubTotal(invoiceData.getSubTotal());
		invoiceData.setOrderTotalPrice(invoiceData.getOrderTotalPrice());
		invoiceData.setTotalTax(invoiceData.getTotalTax());


		//Sort the invoice entry by entryNumber

		if (null != invoiceData.getInvoiceEntryList())
		{
			List<InvoiceEntryData> sortedInvoiceEntries = new ArrayList<>();
			for(InvoiceEntryData entries : invoiceData.getInvoiceEntryList())
			{
				InvoiceEntryData invoiceEntry = new InvoiceEntryData();
				if (entries != null)
				{
					invoiceEntry.setBasePrice(entries.getBasePrice());
					invoiceEntry.setExtPrice(entries.getExtPrice());
					invoiceEntry.setProductItemNumber(entries.getProductItemNumber());
					invoiceEntry.setDescription(entries.getDescription());					
					
					final ProductModel productModel = siteOneProductService.getProductBySearchTermForSearch(entries.getProductItemNumber());
					if (null != productModel)
					{
						final ProductData productData = productConverter.convert(productModel);
						productDatas.add(productData);
					}

					if (StringUtils.isNotEmpty(entries.getQuantity()))
					{
						try
						{
							final double quantity = Double.parseDouble(entries.getQuantity());
							final DecimalFormat formatter = new DecimalFormat("#0.00");
							entries.setQuantity(formatter.format(quantity));
							invoiceEntry.setQuantity(formatter.format(quantity));
							LOG.error("entries quantity" + entries.getQuantity());
							LOG.error("invoiceEntry quantity" + invoiceEntry.getQuantity());
						}
						catch (final NumberFormatException numberFormatException)
						{
							LOG.error("Number Format Exception occured", numberFormatException);
						}
					}
				
				}
				sortedInvoiceEntries.add(invoiceEntry);
			}
			put(PRODUCTS, productDatas);
			invoiceData.setInvoiceEntryList(sortedInvoiceEntries);
		}

		final LanguageModel language = invoiceDetailsProcessModel.getLanguage();
		LOG.info(invoiceDetailsProcessModel.getLanguage().getIsocode());
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);
		}


		String paymentType = "";
		String cardNumber = "";
		String cardType = "";
		String termsCode = "";
		final SiteOnePaymentInfoData paymentInfoList = invoiceData.getSiteOnePaymentInfoData();
		final SiteOnePOAPaymentInfoData poaPaymentInfoList = invoiceData.getSiteOnePOAPaymentInfoData();
		if (paymentInfoList != null)
		{
			paymentType = paymentInfoList.getPaymentType();
			cardNumber = "XX" + paymentInfoList.getCardNumber();
			cardType = paymentInfoList.getApplicationLabel();
			LOG.info("card details>>>>>>>>>>>>>>>>>>>>>>>" + cardNumber + paymentType);
			
		}else if (poaPaymentInfoList != null){
			paymentType = poaPaymentInfoList.getPaymentType();
			termsCode = poaPaymentInfoList.getTermsCode();
			LOG.info("POA details>>>>>>>>>>>>>>>>>>>>>>>" + termsCode + paymentType);
			
		}
		else
		{
			paymentType = "2";
		}
		put(PAYMENTTYPE, paymentType);
		put(CARDNUMBER, cardNumber);
		put(CARDTYPE, cardType);
		put(TERMSCODE, termsCode);

		put(INVOICE_DETAILS_EMAIL_SUBJECT, invoiceData);
		put(EMAIL, invoiceDetailsProcessModel.getEmailAddress());
		put(DISPLAY_NAME, invoiceDetailsProcessModel.getEmailAddress());
		put(EMAIL_SUBJECT, getMessageSource().getMessage("invoice.subject", null,
				getCommonI18NService().getLocaleForIsoCode(invoiceDetailsProcessModel.getLanguage().getIsocode())));
		storeSessionFacade.removeCurrentBaseStore();
	}

	@Override
	protected BaseSiteModel getSite(final InvoiceDetailsProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final InvoiceDetailsProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	@Override
	protected LanguageModel getEmailLanguage(final InvoiceDetailsProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	private Integer extractInt(final String entryNumber)
	{
		if (entryNumber.isEmpty())
		{
			return Integer.valueOf(0);
		}
		else
		{
			try
			{
				return Integer.valueOf(Integer.parseInt(entryNumber));
			}
			catch (final NumberFormatException numberFormatException)
			{
				return Integer.valueOf(0);
			}

		}
	}

	/**
	 * This method is used to truncate the price into 2.
	 *
	 * @param priceToTruncate
	 *           - price needs to be truncated.
	 *
	 * @return truncated price
	 */
	private String truncatePrice(final Double priceToTruncate, final int decimal)
	{
		if (null != priceToTruncate)
		{
			DecimalFormat decimalFormat = null;
			if (decimal == 3)
			{
				decimalFormat = new DecimalFormat("#,##0.000");
			}
			else
			{
				decimalFormat = new DecimalFormat("#,##0.00");
			}

			final String truncatedPrice = decimalFormat
					.format(BigDecimal.valueOf(priceToTruncate.doubleValue()).setScale(decimal, BigDecimal.ROUND_HALF_UP));
			return truncatedPrice;
		}
		else
		{
			return null;
		}


	}
	
	private String fetchUserAccountId(String account) {
		String userAccountId = (account.contains(SiteoneCoreConstants.INDEX_OF_CA) ? account.replace(SiteoneCoreConstants.INDEX_OF_CA, "") :
			account.replace(SiteoneCoreConstants.INDEX_OF_US, ""));
		return userAccountId;
	}


}
