/**
 *
 */
package com.siteone.facade.invoice;

import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.enums.InvoiceStatus;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.InvoiceData;
import com.siteone.facade.InvoiceEntryData;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceDetailsResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceItemsInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoicePaymentsInfoResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceResponseData;
import com.siteone.integration.services.invoice.SiteOneInvoiceWebService;


/**
 * @author 1219341
 *
 */
public class DefaultInvoiceFacade implements InvoiceFacade
{
	private CustomerAccountService customerAccountService;
	private BaseStoreService baseStoreService;
	private Converter<SiteOneInvoiceModel, InvoiceData> siteOneInvoiceConverter;
	private static final Logger LOGGER = Logger.getLogger(DefaultInvoiceFacade.class);
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String[] invoiceSortData =
	{ "InvoiceDate|Invoice date", "InvoiceNumber|Invoice number", "OrderNumber|Order number", "PONumber|PO number",
			"InvoiceTotal|Invoice Total" };
	private static final String INVOICE_NOT_FOUND_FOR_USER = "Invoice with guid %s not found for current user";
	private static final Logger LOG = Logger.getLogger(DefaultInvoiceFacade.class);

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	@Resource(name = "siteOneInvoiceWebService")
	private SiteOneInvoiceWebService siteOneInvoiceWebService;
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	private SiteOneStoreFinderService siteOneStoreFinderService;
	@Resource(name = "productFacade")
	private ProductFacade productFacade;
	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Resource(name = "b2bUnitFacade")
	protected B2BUnitFacade b2bUnitFacade;


	@Resource(name = "productModelUrlResolver")
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	/**
	 * @return the siteOneInvoiceConverter
	 */
	public Converter<SiteOneInvoiceModel, InvoiceData> getSiteOneInvoiceConverter()
	{
		return siteOneInvoiceConverter;
	}

	/**
	 * @param siteOneInvoiceConverter
	 *           the siteOneInvoiceConverter to set
	 */
	public void setSiteOneInvoiceConverter(final Converter<SiteOneInvoiceModel, InvoiceData> siteOneInvoiceConverter)
	{
		this.siteOneInvoiceConverter = siteOneInvoiceConverter;
	}


	@Override
	public SearchPageData<InvoiceData> getPagedInvoiceListForStatusesForAll(final PageableData pageableData, final String shipToId,
			final String trimmedSearchParam, final Date dateFromFinal, final Date dateToFinal, final InvoiceStatus... statuses)
	{
		final SearchPageData<SiteOneInvoiceModel> invoiceResults = ((SiteOneCustomerAccountService) customerAccountService)
				.getInvoiceListForAll(shipToId, statuses, pageableData, trimmedSearchParam, dateFromFinal, dateToFinal);
		return convertPageData(invoiceResults, getSiteOneInvoiceConverter());
	}

	@Override
	public SearchPageData<InvoiceData> getPagedInvoiceListForStatuses(final PageableData pageableData, final String shipToId,
			final String trimmedSearchParam, final Date dateFromFinal, final Date dateToFinal, final InvoiceStatus... statuses)
	{
		final SearchPageData<SiteOneInvoiceModel> invoiceResults = ((SiteOneCustomerAccountService) customerAccountService)
				.getInvoiceList(shipToId, statuses, pageableData, trimmedSearchParam, dateFromFinal, dateToFinal);
		return convertPageData(invoiceResults, getSiteOneInvoiceConverter());
	}

	protected <S, T> SearchPageData<T> convertPageData(final SearchPageData<S> source, final Converter<S, T> converter)
	{
		final SearchPageData<T> result = new SearchPageData<T>();
		result.setPagination(source.getPagination());
		result.setSorts(source.getSorts());
		result.setResults(Converters.convertAll(source.getResults(), converter));
		return result;
	}

	@Override
	public InvoiceData getInvoiceDetailsForCode(final String code, final String uid, final String orderShipmentActualId, final Boolean fromEmail)
	{
		try
		{
			B2BUnitData bUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			String bUnitPrefix = null;
			String cusNumberPrefix = null;
			InvoiceData invoice = new InvoiceData();
			if (bUnit != null && bUnit.getUid() != null)
			{
				bUnitPrefix = bUnit.getUid().trim().split("[-_]")[0].trim();
			}
			if (uid != null)
			{
				cusNumberPrefix = uid.trim().split("[-_]")[0].trim();
			}
			if (StringUtils.isNotBlank(bUnitPrefix) && StringUtils.isNotBlank(cusNumberPrefix) && bUnitPrefix.equalsIgnoreCase(cusNumberPrefix) && !fromEmail)
			{
				final SiteoneInvoiceDetailsResponseData response = siteOneInvoiceWebService.getInvoiceDetailsData(getCustomerNo(uid),
						code, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

				if (null == response)
				{
					LOG.error("Received null response for Uid " + uid + " and code " + code);
					throw new PdfNotAvailableException("Response Array is null");
				}
				invoice = populateInvoiceDetailDataResponse(response, uid, orderShipmentActualId);
			}
			else if (fromEmail)
			{
				final SiteoneInvoiceDetailsResponseData response = siteOneInvoiceWebService.getInvoiceDetailsData(getCustomerNo(uid),
						code, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

				if (null == response)
				{
					LOG.error("Received null response for Uid " + uid + " and code " + code);
					throw new PdfNotAvailableException("Response Array is null");
				}
				invoice = populateInvoiceDetailDataResponse(response, uid, orderShipmentActualId);
			}
			return invoice;
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to read invoice", resourceAccessException);
			throw new ServiceUnavailableException("404");
		}
		catch (final ModelNotFoundException e)
		{
			LOGGER.error(e);
			throw new UnknownIdentifierException(String.format(INVOICE_NOT_FOUND_FOR_USER, code));
		}
	}

	/**
	 * @param response
	 * @return
	 */
	private InvoiceData populateInvoiceDetailDataResponse(final SiteoneInvoiceDetailsResponseData response, final String uid,
			final String orderShipmentActualId)
	{
		final InvoiceData invoice = new InvoiceData();
		int CURRENCY_TOTAL_PRICE_DIGITS = Config.getInt("curency.totalprice.digits", 2);
		int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);
		final List<InvoiceEntryData> invoiceEntry = new ArrayList<InvoiceEntryData>();
		List<SiteoneInvoiceItemsInfoResponseData> sortedInvoiceEntries = null;
		if (null != response && null != response.getLineItems())
		{
			sortedInvoiceEntries = response.getLineItems().stream()
					.sorted((entry1, entry2) -> extractInt(entry1.getLineNumber()).compareTo(extractInt(entry2.getLineNumber())))
					.collect(Collectors.toList());
		}
		for (final SiteoneInvoiceItemsInfoResponseData responseEntry : sortedInvoiceEntries)
		{
			if (responseEntry != null)
			{
				final InvoiceEntryData entry = new InvoiceEntryData();
				if (responseEntry.getUnitPrice() != null)
				{
					entry.setBasePrice(truncatePrice(responseEntry.getUnitPrice(), CURRENCY_UNIT_PRICE_DIGITS));
				}
				if (responseEntry.getOriginalUnitPrice() != null)
				{
					entry.setActualItemCost(truncatePrice(responseEntry.getOriginalUnitPrice(), CURRENCY_UNIT_PRICE_DIGITS));
				}
				if (responseEntry.getUomName() != null)
				{
					entry.setUomMeasure(responseEntry.getUomName());
				}
				if (responseEntry.getQuantityOrdered() != null)
				{
					entry.setQuantity(responseEntry.getQuantityOrdered().toString());
				}
				if (responseEntry.getUomName() != null)
				{
					entry.setUnit(responseEntry.getUomName());
				}
				if (responseEntry.getItemNumber() != null)
				{
					entry.setProductItemNumber(responseEntry.getItemNumber());
				}
				if (responseEntry.getItemDescription() != null)
				{
					entry.setDescription(responseEntry.getItemDescription());
				}
				if (responseEntry.getQuantityShipped() != null)
				{
					entry.setQtyShipped(responseEntry.getQuantityShipped().intValue());
				}
				if (responseEntry.getQuantityBackOrdered() != null)
				{
					entry.setQtyOpen(responseEntry.getQuantityBackOrdered().intValue());
				}
				if (responseEntry.getTotalPrice() != null)
				{
					entry.setExtPrice(truncatePrice(responseEntry.getTotalPrice(), CURRENCY_TOTAL_PRICE_DIGITS));
				}
				if (responseEntry.getUnitPrice() != null)
				{
					entry.setNetPrice(truncatePrice(responseEntry.getUnitPrice(), CURRENCY_UNIT_PRICE_DIGITS));
				}
				if (responseEntry.getSkuid() != null)
				{
					final ProductData productdata = siteOneProductFacade
							.getProductBySearchTermForSearch(responseEntry.getSkuid().toString(), Arrays.asList(ProductOption.BASIC,
									ProductOption.URL, ProductOption.IMAGES, ProductOption.STOCK, ProductOption.AVAILABILITY_MESSAGE));
					if (productdata != null)
					{
						entry.setImages(productdata.getImages());
						entry.setProductUrl(productdata.getUrl());
					}
				}
				invoiceEntry.add(entry);
			}
		}
		if (null != response.getDeliveryInformation())
		{
			if (response.getDeliveryInformation().getContactEmail() != null)
			{
				invoice.setUser(response.getDeliveryInformation().getContactEmail());
			}
			//invoice.setBillingTerms(source.getBillingTerms());
			if (response.getDeliveryInformation().getContactName() != null)
			{
				invoice.setContact_firstName(response.getDeliveryInformation().getContactName());
			}
			if (response.getDeliveryInformation().getContactName() != null)
			{
				invoice.setUserName(response.getDeliveryInformation().getContactName());
			}
			if (response.getDeliveryInformation().getContactPhone() != null)
			{
				invoice.setContact_phone(response.getDeliveryInformation().getContactPhone());
			}
			if (response.getDeliveryInformation().getSpecialInstructions() != null)
			{
				invoice.setInstructions(response.getDeliveryInformation().getSpecialInstructions());
			}
			if (orderShipmentActualId != null)
			{
				invoice.setOrderShipmentActualId(orderShipmentActualId);
			}
			if (response.getDeliveryInformation().getContactEmail() != null)
			{
				invoice.setContact_emailId(response.getDeliveryInformation().getContactEmail());
			}
			if (response.getDeliveryInformation().getFullfillmentType() != null)
			{
				if (response.getDeliveryInformation().getFullfillmentType().equalsIgnoreCase("Pick-up"))
				{
					final String mode = "Customer Pick up";
					invoice.setDeliveryMode(mode);
				}
				else
				{
					invoice.setDeliveryMode(response.getDeliveryInformation().getFullfillmentType());
				}
			}
			if (response.getDeliveryInformation().getDeliveryStatus() != null)
			{
				invoice.setStatus(response.getDeliveryInformation().getDeliveryStatus());
			}
			if (response.getDeliveryInformation().getHomeBranchNumber() != null)
			{
				invoice.setStoreId(response.getDeliveryInformation().getHomeBranchNumber());
			}
			if (uid != null)
			{
				invoice.setShipToAccountNumber(uid);
			}
			if (uid != null)
			{
				invoice.setAccountNumber(uid);
			}
			if (null != response.getDeliveryInformation().getHomeBranchNumber())
			{
				final PointOfServiceModel posModel = siteOneStoreFinderService
						.getStoreForId(response.getDeliveryInformation().getHomeBranchNumber());
				if (null != posModel)
				{
					invoice.setStoreTitle(posModel.getTitle());
				}
			}
			if (response.getDeliveryInformation().getStoreName() != null)
			{
				invoice.setStoreName(response.getDeliveryInformation().getStoreName());
			}
			final AddressData branchAddress = new AddressData();
			final RegionData region = new RegionData();
			if (response.getDeliveryInformation().getAddress1() != null)
			{
				branchAddress.setLine1(response.getDeliveryInformation().getAddress1());
			}
			if (response.getDeliveryInformation().getAddress2() != null)
			{
				branchAddress.setLine2(response.getDeliveryInformation().getAddress2());
			}
			if (response.getDeliveryInformation().getCity() != null)
			{
				branchAddress.setTown(response.getDeliveryInformation().getCity());
			}
			if (response.getDeliveryInformation().getStateProvince() != null)
			{
				region.setIsocodeShort(response.getDeliveryInformation().getStateProvince());
			}
			branchAddress.setRegion(region);
			if (response.getDeliveryInformation().getPostalCode() != null)
			{
				branchAddress.setPostalCode(response.getDeliveryInformation().getPostalCode());
			}
			invoice.setAddress(branchAddress);
			invoice.setBranchAddress(branchAddress);
			if (response.getDeliveryInformation().getRequestedShipmentDate() != null)
			{
				if (!response.getDeliveryInformation().getRequestedShipmentDate().startsWith("0"))
				{
					invoice
							.setPickupOrDeliveryDateTime(getFormattedDate(response.getDeliveryInformation().getRequestedShipmentDate()));
				}
				else
				{
					invoice.setPickupOrDeliveryDateTime(null);
				}
			}
		}
		if (response.getSubTotal() != null)
		{
			invoice.setSubTotal(truncatePrice(response.getSubTotal(), CURRENCY_TOTAL_PRICE_DIGITS));
		}
		if (response.getTaxTotal() != null)
		{
			invoice.setTotalTax(truncatePrice(response.getTaxTotal(), CURRENCY_TOTAL_PRICE_DIGITS));
		}
		if (response.getInvoiceTotal() != null)
		{
			invoice.setOrderTotalPrice(truncatePrice(response.getInvoiceTotal(), CURRENCY_TOTAL_PRICE_DIGITS));
		}		
		if (response.getInvoiceTotal() != null)
		{
			invoice.setTotalPayment(truncatePrice(response.getInvoiceTotal(), CURRENCY_TOTAL_PRICE_DIGITS));
		}
		try
		{
			if (null != response.getAmountDue() && (response.getAmountDue().matches("-?[0-9.]*")))
			{
				invoice.setAmountDue(truncatePrice(Double.valueOf(response.getAmountDue()), CURRENCY_TOTAL_PRICE_DIGITS));
			}

			if (null != response.getFreight())
			{
				invoice.setFreight(truncatePrice(response.getFreight(), CURRENCY_TOTAL_PRICE_DIGITS));
			}
		}
		catch (final NumberFormatException numberFormatException)
		{
			LOG.error("Number format exception occured", numberFormatException);
		}

		if (response.getOrderShipmentNumber() != null)
		{
			invoice.setInvoiceNumber(response.getOrderShipmentNumber());
		}
		if (response.getInvoiceDate() != null)
		{
			if (!response.getInvoiceDate().startsWith("0"))
			{
				invoice.setInvoiceDate(getFormattedDate(response.getInvoiceDate()));
			}
			else
			{
				invoice.setInvoiceDate(null);
			}
		}
		if (response.getPoNumber() != null)
		{
			invoice.setPurchaseOrderNumber(response.getPoNumber());
		}
		SiteOnePOAPaymentInfoData accountPayment = null;
		SiteOnePaymentInfoData siteOnePaymentInfoData = null;
		if (CollectionUtils.isNotEmpty(response.getPaymentsInformation()))
		{
			final SiteoneInvoicePaymentsInfoResponseData payment = response.getPaymentsInformation().get(0);
			if (payment.getBillingTerms() != null)
			{
				invoice.setBillingTerms(payment.getBillingTerms());
			}
			if (payment.getPaymentMethod() != null)
			{
				if (payment.getPaymentMethod().equalsIgnoreCase("On Account"))
				{
					accountPayment = new SiteOnePOAPaymentInfoData();
					if (payment.getAmountPaid() != null)
					{
						accountPayment.setAmountCharged(payment.getAmountPaid());
					}
					accountPayment.setPaymentType("1");
					if (payment.getBillingTerms() != null)
					{
						accountPayment.setTermsCode(payment.getBillingTerms());
					}
				}
				else if (payment.getPaymentMethod().equalsIgnoreCase("Credit Card"))
				{
					siteOnePaymentInfoData = new SiteOnePaymentInfoData();
					siteOnePaymentInfoData.setPaymentType("3");
					if (payment.getCreditCardType() != null)
					{
						siteOnePaymentInfoData.setCardType(payment.getCreditCardType());
					}
					if (payment.getAmountPaid() != null)
					{
						siteOnePaymentInfoData.setAmountCharged(payment.getAmountPaid());
					}
					if (payment.getLast4CreditCardDigits() != null)
					{
						siteOnePaymentInfoData.setCardNumber(payment.getLast4CreditCardDigits());
					}
				}
				else
				{
					siteOnePaymentInfoData = new SiteOnePaymentInfoData();
					siteOnePaymentInfoData.setPaymentType("2");
				}
			}
			else
			{
				siteOnePaymentInfoData = new SiteOnePaymentInfoData();
				siteOnePaymentInfoData.setPaymentType("2");
			}
		}
		else
		{
			siteOnePaymentInfoData = new SiteOnePaymentInfoData();
			siteOnePaymentInfoData.setPaymentType("2");
		}
		if (siteOnePaymentInfoData != null)
		{
			invoice.setSiteOnePaymentInfoData(siteOnePaymentInfoData);
		}
		if (accountPayment != null)
		{
			invoice.setSiteOnePOAPaymentInfoData(accountPayment);
		}
		invoice.setInvoiceEntryList(invoiceEntry);
		return invoice;
	}

	@Override
	public byte[] getInvoiceByOrderShipmentActualId(final String orderShipmentActualId)
			throws PdfNotAvailableException, ServiceUnavailableException
	{
		try
		{
			final byte[] pdf = siteOneInvoiceWebService.getInvoiceByOrderShipmentActualId(orderShipmentActualId);

			if (null == pdf)
			{
				throw new PdfNotAvailableException("Pdf Byte Array is null");
			}
			return pdf;
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to read pdf", resourceAccessException);
			throw new ServiceUnavailableException("404");
		}
	}

	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	@Override
	public SearchPageData<InvoiceData> getListOfInvoiceData(final SiteoneInvoiceRequestData invoiceRequest,
			final String customerNumber)
	{
		try
		{
			B2BUnitData bUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			String bUnitPrefix = null;
			String cusNumberPrefix = null;
			SearchPageData<InvoiceData> invoice = createEmptySearchPageData();
			if (bUnit != null && bUnit.getUid() != null)
			{
				bUnitPrefix = bUnit.getUid().trim().split("[-_]")[0].trim();
			}
			if (customerNumber != null)
			{
				cusNumberPrefix = customerNumber.trim().split("[-_]")[0].trim();
			}
			if (StringUtils.isNotBlank(bUnitPrefix) && StringUtils.isNotBlank(cusNumberPrefix) && bUnitPrefix.equalsIgnoreCase(cusNumberPrefix))
			{
				final SiteoneInvoiceResponseData response = siteOneInvoiceWebService.getInvoicesData(invoiceRequest,
						getCustomerNo(customerNumber), getDivisionId(customerNumber),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));

				if (null == response)
				{
					LOG.error("Received null response for Uid " + customerNumber);
					throw new PdfNotAvailableException("Response Array is null");
				}
			   invoice = populateSearchPageDataFromInvoiceResponse(response, customerNumber);
			}
			return invoice;
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to read invoice", resourceAccessException);
			throw new ServiceUnavailableException("404");
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("PDF is not available for customer: " + customerNumber, pdfNotAvailableException);
			return null;
		}
	}
	
	public SearchPageData<InvoiceData> createEmptySearchPageData()
	{
		SearchPageData<InvoiceData> data = new SearchPageData<>();
		final PaginationData pagination = new PaginationData();
		pagination.setCurrentPage(0);
		pagination.setNumberOfPages(0);
		pagination.setPageSize(0);
		pagination.setSort("");
		pagination.setTotalNumberOfResults(0);
		data.setResults(Collections.emptyList());
		data.setPagination(pagination);
		data.setSorts(getInvoiceSortData());
		return data;
	}

	@Override
	public List<InvoiceData> getDownloadListOfInvoiceData(final SiteoneInvoiceRequestData invoiceRequest,
			final String customerNumber)
	{
		final List<InvoiceData> invoices = new ArrayList<InvoiceData>();
		{
			try
			{

				final SiteoneInvoiceResponseData response = siteOneInvoiceWebService.getInvoicesData(invoiceRequest,
						getCustomerNo(customerNumber), getDivisionId(customerNumber),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
				if (CollectionUtils.isNotEmpty(response.getData()))
				{
					for (final SiteoneInvoiceInfoResponseData data : response.getData())
					{
						try
						{
							final InvoiceData invoice = getInvoiceDetailsForCode(data.getInvoiceNumber(), customerNumber, null, Boolean.FALSE);
							invoices.add(invoice);
						}
						catch (final PdfNotAvailableException e)
						{
							final InvoiceData blankInvoice = new InvoiceData();
							LOG.warn("Invoice PDF not available for UID: " + customerNumber + " and Code: "
									+ invoiceRequest.getInvoiceNumber(), e);
							invoices.add(blankInvoice);
						}
					}
				}
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to read invoice", resourceAccessException);
				throw new ServiceUnavailableException("404");
			}
		}
		return invoices;
	}

	public Integer getDivisionId(final String accountNumber)
	{

		if (accountNumber.contains("_US"))
		{
			return Integer.valueOf(1);
		}
		else
		{
			return Integer.valueOf(2);
		}
	}

	public String getCustomerNo(final String customerNumber)
	{

		if (customerNumber.contains("_US"))
		{
			return customerNumber.replace("_US", "");
		}
		else
		{
			return customerNumber.replace("_CA", "");
		}
	}

	public String setCustomerNoWithDivision(final String customerNumber)
	{
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			return customerNumber.concat("_US");
		}
		else
		{
			return customerNumber.concat("_CA");
		}
	}

	protected SearchPageData<InvoiceData> populateSearchPageDataFromInvoiceResponse(final SiteoneInvoiceResponseData responseData,
			final String customerNumber)
	{
		final List<InvoiceData> invoices = new ArrayList<>();
		int CURRENCY_TOTAL_PRICE_DIGITS = Config.getInt("curency.totalprice.digits", 2);
		final SearchPageData<InvoiceData> searchPageData = new SearchPageData<InvoiceData>();
		final PaginationData pagination = new PaginationData();
		final Set<String> processedInvoiceNumbers = new HashSet<>();
		if (null != responseData)
		{
			pagination.setNumberOfPages(Integer.parseInt(responseData.getTotalPages()));
			pagination.setTotalNumberOfResults(Long.valueOf(responseData.getTotalRecords()));
			pagination.setPageSize(Integer.parseInt(responseData.getPageNumber()));
			pagination.setCurrentPage(Integer.parseInt(responseData.getPageNumber()) - 1);


			if (CollectionUtils.isNotEmpty(responseData.getData()))
			{
				for (final SiteoneInvoiceInfoResponseData data : responseData.getData())
				{
					if (processedInvoiceNumbers.contains(data.getInvoiceNumber()))
					{
						continue;
					}
					if (data.getInvoiceNumber() != null)
					{
						processedInvoiceNumbers.add(data.getInvoiceNumber());
					}
					final InvoiceData invoice = new InvoiceData();
					if (null != data.getOrderingCustomerNumber())
					{
						final B2BUnitData b2BUnit = b2bUnitFacade
								.getUnitForUid(setCustomerNoWithDivision(data.getOrderingCustomerNumber()));
						if (b2BUnit != null && b2BUnit.getName() != null)
						{
							invoice.setAccountDisplay(data.getOrderingCustomerNumber().concat(" - ").concat(b2BUnit.getName()));
						}
						else
						{
							invoice.setAccountDisplay("");
						}
					}
					else
					{
						invoice.setAccountDisplay("");
					}
					if (data.getInvoiceNumber() != null)
					{
						invoice.setInvoiceNumber(data.getInvoiceNumber());
					}
					if (data.getOrderNumber() != null)
					{
						invoice.setOrderNumber(data.getOrderNumber());
					}
					if (data.getPoNumber() != null)
					{
						invoice.setPurchaseOrderNumber(data.getPoNumber());
					}
					if (data.getTotal() != null)
					{
						invoice.setOrderTotalPrice(truncatePrice(data.getTotal(), CURRENCY_TOTAL_PRICE_DIGITS));
					}
					if (data.getBalance() != null)
					{
						invoice.setAmountDue(truncatePrice(data.getBalance(), CURRENCY_TOTAL_PRICE_DIGITS));
					}
					if (data.getActualOrderShipmentID() != null)
					{
						invoice.setOrderShipmentActualId(data.getActualOrderShipmentID());
					}
					if (data.getOrderingCustomerNumber() != null)
					{
						invoice.setShipToAccountNumber(data.getOrderingCustomerNumber());
					}
					if (data.getInvoiceDate() != null)
					{
						if (!data.getInvoiceDate().startsWith("0"))
						{
							invoice.setInvoiceDate(getFormattedDate(data.getInvoiceDate()));
						}
						else
						{
							invoice.setInvoiceDate(null);
						}
					}
					invoices.add(invoice);
				}
			}
		}
		searchPageData.setResults(invoices);
		searchPageData.setPagination(pagination);
		searchPageData.setSorts(getInvoiceSortData());
		return searchPageData;
	}

	private Date getFormattedDate(final String inputDateStr)
	{
		Date inputDate = null;
		final DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try
		{
			inputDate = dateFormatFrom.parse(inputDateStr, new ParsePosition(0));
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage(), exception);
		}

		return inputDate;
	}


	private List<SortData> getInvoiceSortData()
	{
		final List<SortData> sorts = new ArrayList<>();
		for (final String sort : invoiceSortData)
		{
			final SortData data = new SortData();
			final String[] sortDatas = sort.split("\\|");
			data.setCode(sortDatas[0].trim());
			data.setName(sortDatas[1].trim());
			sorts.add(data);
		}
		return sorts;
	}

	private String truncatePrice(final Double priceToTruncate, final int toDigits)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat;
			if (toDigits == 3)
			{
				decimalFormat = new DecimalFormat("#,##0.000");
			}
			else
			{
				decimalFormat = new DecimalFormat("#,##0.00");
			}
			final String truncatedPrice = decimalFormat
					.format(BigDecimal.valueOf(priceToTruncate.doubleValue()).setScale(toDigits, BigDecimal.ROUND_HALF_UP));
			return truncatedPrice;
		}
		else
		{
			return null;
		}
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

	public UrlResolver<ProductModel> getProductModelUrlResolver()
	{
		return productModelUrlResolver;
	}

	public void setProductModelUrlResolver(final UrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

	@Override
	public InvoiceData getInvoiceShipmentActualId(final String invoiceNumber, final String uid)
	{
		return null;
	}

	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}

	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}
	
	
	/**
	 * @return the siteOneInvoiceWebService
	 */
	public SiteOneInvoiceWebService getSiteOneInvoiceWebService()
	{
		return siteOneInvoiceWebService;
	}

	/**
	 * @param siteOneInvoiceWebService the siteOneInvoiceWebService to set
	 */
	public void setSiteOneInvoiceWebService(SiteOneInvoiceWebService siteOneInvoiceWebService)
	{
		this.siteOneInvoiceWebService = siteOneInvoiceWebService;
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

	/**
	 * @return the b2bUnitFacade
	 */
	public B2BUnitFacade getB2bUnitFacade()
	{
		return b2bUnitFacade;
	}

	/**
	 * @param b2bUnitFacade the b2bUnitFacade to set
	 */
	public void setB2bUnitFacade(B2BUnitFacade b2bUnitFacade)
	{
		this.b2bUnitFacade = b2bUnitFacade;
	}

}
