/**
 *
 */
package com.siteone.facade.order.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.comment.data.CommentData;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.ContactSellerEvent;
import com.siteone.core.event.ExpiredQuoteUpdEvent;
import com.siteone.core.event.QuoteApprovalEvent;
import com.siteone.core.event.RequestQuoteEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.model.QuoteItemDetailsModel;
import com.siteone.core.savedList.service.SiteoneSavedListService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.order.SiteoneQuotesFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.SiteOneAddressPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.quote.data.DeviveryAddressQuoteData;
import com.siteone.facades.quote.data.QuoteApprovalHistoryData;
import com.siteone.facades.quote.data.QuoteApprovalItemData;
import com.siteone.facades.quote.data.QuoteDetailsData;
import com.siteone.facades.quote.data.QuoteItemDetailsData;
import com.siteone.facades.quote.data.QuotesData;
import com.siteone.facades.quote.data.ShiptoitemData;
import com.siteone.facades.quote.data.ShiptoquoteData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.order.data.QuoteApprovalHistoryResponseData;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.order.data.SiteOneQuoteDetails;
import com.siteone.integration.order.data.SiteOneQuoteShiptoRequestData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoResponseData;
import com.siteone.integration.order.data.SiteOneRequestQuoteDetails;
import com.siteone.integration.order.data.SiteOneRequestQuoteRequestData;
import com.siteone.integration.order.data.SiteOneRequestQuoteResponseData;
import com.siteone.integration.quotes.order.data.SiteOneQuotesListResponseData;
import com.siteone.integration.quotes.order.data.SiteoneQuotesRequestData;
import com.siteone.integration.quotes.order.data.SiteoneQuotesSortRequestData;
import com.siteone.integration.services.ue.SiteOneQuotesWebService;



/**
 * @author AA04994
 *
 */
public class DefaultSiteoneQuotesFacade implements SiteoneQuotesFacade
{
	@Resource(name = "siteOneQuotesWebService")
	private SiteOneQuotesWebService siteOneQuotesWebService;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "customerFacade")
	private SiteOneCustomerFacade customerFacade;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "siteoneSavedListService")
	private SiteoneSavedListService siteoneSavedListService;

	@Resource(name = "siteoneSavedListConverter")
	private Converter<Wishlist2Model, SavedListData> siteoneSavedListConverter;

	@Resource(name = "defaultSiteOneB2BUnitService")
	private SiteOneB2BUnitService defaultSiteOneB2BUnitService;

	@Resource(name = "defaultSiteOneAddressPopulator")
	private SiteOneAddressPopulator siteoneAddressPopulator;

	@Resource(name = "siteOneProductUOMService")
	private SiteOneProductUOMService siteOneProductUOMService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	private ProductService productService;

	private static final Logger LOG = Logger.getLogger(DefaultSiteoneQuotesFacade.class);

	private Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter;

	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String SITE_ONE = "siteone";
	private static final String ALL = "All";
	private static final String OPEN = "Open";
	private static final String FULL = "Full";
	private static final String EXPIRED = "Expired";
	private static final String FULLY_APPROVED = "Fully Approved";

	@Override
	public List<QuotesData> getQuotes(final String customerNumber, final String skipCount, final String toggle)
			throws ParseException
	{
		final Integer QUOTES_LIMIT = Integer.valueOf(Config.getInt("ue.quotes.request.limit", 250));
		final Integer QUOTES_SHIPTO_LIMIT = Integer.valueOf(Config.getInt("ue.shipto.quotes.request.limit", 250));
		List<QuotesData> quotesData = new ArrayList<QuotesData>();
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		final SiteoneQuotesRequestData quotesRequest = new SiteoneQuotesRequestData();
		if (unit != null && unit.getUid() != null && customerNumber == null)
		{
			quotesRequest.setCustomerNumber(getCustomerNo(unit.getUid()));
		}
		else if (customerNumber != null && customerNumber.contains("-"))
		{
			quotesRequest.setCustomerNumber(customerNumber.substring(0, customerNumber.indexOf("-")));
		}
		else
		{
			quotesRequest.setCustomerNumber(customerNumber);
		}
		quotesRequest.setIncludeDetails(false);
		if (unit.getUid().contains("_US"))
		{
	      quotesRequest.setDivisionID(String.valueOf(1));
		}
		else
		{
	      quotesRequest.setDivisionID(String.valueOf(2));
		}
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		final List<SiteoneQuotesSortRequestData> sortList = new ArrayList();
		final SiteoneQuotesSortRequestData sort = new SiteoneQuotesSortRequestData();
		quotesRequest.setEndDate(now.plusDays(2).toString());
		if (siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate") != null)
		{
			quotesRequest.setStartDate(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate"));
		}
		else
		{
			quotesRequest.setStartDate(now.minusMonths(6).toString());
		}
		final List<String> customerNum = new ArrayList<>(Arrays.asList(quotesRequest.getCustomerNumber()));
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate(now.plusDays(2).toString());
		if (siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate") != null)
		{
			shiptoRequest.setStartDate(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate"));
		}
		else
		{
			shiptoRequest.setStartDate(now.minusMonths(6).toString());
		}

		shiptoRequest.setLimit(QUOTES_SHIPTO_LIMIT);
		shiptoRequest.setSkip(Integer.valueOf(0));
		if (StringUtils.isNotBlank(toggle) && !toggle.equalsIgnoreCase(ALL))
		{
			if (toggle.equalsIgnoreCase(OPEN))
			{
				shiptoRequest.setApprovalStatus(OPEN);
				shiptoRequest.setExpired(Boolean.FALSE);
			}
			else if (toggle.equalsIgnoreCase(FULL))
			{
				shiptoRequest.setApprovalStatus(FULLY_APPROVED);
				shiptoRequest.setExpired(Boolean.FALSE);
			}
			else if (toggle.equalsIgnoreCase(EXPIRED))
			{
				shiptoRequest.setExpired(Boolean.TRUE);
			}
		}
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = siteOneQuotesWebService.shiptoQuote(shiptoRequest);
		for (final SiteOneQuoteShiptoResponseData shipToList : shiptoRespData)
		{
		final QuotesData quoteCountData = new QuotesData();
		quoteCountData.setQuoteCount(shipToList.getQuoteCount());
		if (siteOneFeatureSwitchCacheService.getValueForSwitch("TotalPageSize") != null)
		{
		final Integer pageSize = Integer.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("TotalPageSize"));
		quoteCountData.setPageSize(pageSize);
		}
		if (siteOneFeatureSwitchCacheService.getValueForSwitch("QuotesRequestLimit") != null) {
			quotesRequest.setLimit(Integer.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("QuotesRequestLimit")));
		}
		else
		{
			quotesRequest.setLimit(QUOTES_LIMIT);
		}
		quotesRequest.setSkip(Integer.valueOf(0));
		if (StringUtils.isNotBlank(toggle) && !toggle.equalsIgnoreCase(ALL))
		{
			if (toggle.equalsIgnoreCase(OPEN))
				{
					quotesRequest.setApprovalStatus(OPEN);
					quotesRequest.setExpired(Boolean.FALSE);
				}
				else if (toggle.equalsIgnoreCase(FULL))
				{
					quotesRequest.setApprovalStatus(FULLY_APPROVED);
					quotesRequest.setExpired(Boolean.FALSE);
				}
				else if (toggle.equalsIgnoreCase(EXPIRED))
				{
					quotesRequest.setExpired(Boolean.TRUE);
				}
			}
			sort.setFieldName("LastModfDate");
			sort.setAscending(false);
			sortList.add(sort);
			quotesRequest.setSort(sortList);
			final List<SiteOneQuotesListResponseData> quotesResponse = siteOneQuotesWebService.getQuotes(quotesRequest);

			quotesData = populateQuotes(quotesResponse);
			quotesData.add(quoteCountData);

		}
		return quotesData;
	}

	private List<QuotesData> populateQuotes(final List<SiteOneQuotesListResponseData> quotesResponse) throws ParseException
	{ // YTODO Auto-generated method stub

		final List<QuotesData> quotesData = new ArrayList<QuotesData>();
		if (CollectionUtils.isNotEmpty(quotesResponse))
		{
			for (final SiteOneQuotesListResponseData quotesList : quotesResponse)
			{
				final QuotesData data = new QuotesData();
				final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
				if (null != quotesList && null != quotesList.getCustomerName())
				{
					data.setAccountName(quotesList.getCustomerName());
				}
				else if (null != customer && null != customer.getDefaultB2BUnit() && null != customer.getDefaultB2BUnit().getName())
				{
					data.setAccountName(customer.getDefaultB2BUnit().getName());
				}
				if (null != quotesList && null != quotesList.getQuoteHeaderID())
				{
					data.setQuoteId(quotesList.getQuoteHeaderID().toString());
				}
				if (null != quotesList && null != quotesList.getQuoteHeaderID())
				{
					data.setQuoteNumber(String.valueOf(quotesList.getQuoteHeaderID()));
				}
				if (null != quotesList && null != quotesList.getCustomerNumber())
				{
					data.setAccountNumber(quotesList.getCustomerNumber());
				}
				if (null != quotesList && null != quotesList.getJobName())
				{
					data.setJobName(quotesList.getJobName());
				}
				if (quotesList.getCreateDate() != null)
				{
					data.setDateSubmitted(updateDateFormat(quotesList.getCreateDate()));
				}
				if (quotesList.getExpirationDate() != null)
				{
					data.setExpirationDate(updateDateFormat(quotesList.getExpirationDate()));
				}
					if (null != quotesList && null != quotesList.getLastModfDate())
					{
						data.setLastModfDate(updateDateFormat(quotesList.getLastModfDate()));
					}
					if (null != quotesList && null != quotesList.getStatus())
					{
					data.setStatus(quotesList.getStatus());
				   }
				if (null != quotesList && null != quotesList.getNotes())
				{
					data.setNotes(quotesList.getNotes());
				}
				final PriceData totalPrice = new PriceData();
				Double price = 0.0;
				if (quotesList.getQuoteTotal() != null)
				{
					price = quotesList.getQuoteTotal();
				}
				totalPrice.setValue(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP));
				data.setTotalPrice(totalPrice);
				if (quotesList.getApprovalStatus() != null)
				{
					if (quotesList.getApprovalStatus() == 2)
					{
						data.setIsFullQuoteApproved(Boolean.TRUE);
					}
					else if (quotesList.getApprovalStatus() == 1)
					{
						data.setIsFullQuoteApproved(Boolean.FALSE);
					}
					if (quotesList.getApprovalStatus() == 1 || quotesList.getApprovalStatus() == 2)
					{
						data.setIsQuoteApproved(Boolean.TRUE);
					}
					else
					{
						data.setIsQuoteApproved(Boolean.FALSE);
					}
				}
				quotesData.add(data);
			}
		}
		return quotesData;
	}

	@Override
	public QuoteDetailsData getQuoteDetails(final String quoteHeaderID) throws ParseException
	{
		//final CustomerModel customerModel = getModelService().create(CustomerModel.class);
		final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), quoteHeaderID);

		return populateQuoteDetails(quoteDetails);
	}


	private QuoteDetailsData populateQuoteDetails(final SiteOneQuoteDetailResponseData quoteDetails) throws ParseException
	{
		final QuoteDetailsData quoteDetailsData = new QuoteDetailsData();
		B2BUnitData bUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		String bUnitPrefix = null;
		String cusNumberPrefix = null;
		if (bUnit != null && bUnit.getUid() != null)
		{
			bUnitPrefix = bUnit.getUid().trim().split("[-_]")[0].trim();
		}
		if (quoteDetails != null && quoteDetails.getCustomerNumber() != null)
		{
			cusNumberPrefix = quoteDetails.getCustomerNumber().trim().split("-")[0].trim();
		}
		if (quoteDetails != null && StringUtils.isNotBlank(bUnitPrefix) && StringUtils.isNotBlank(cusNumberPrefix) && bUnitPrefix.equalsIgnoreCase(cusNumberPrefix))
		{
			String branchNumber = null;
			if (null != quoteDetails.getBranchNumber())
			{
				branchNumber = quoteDetails.getBranchNumber();
			}
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			if (null != customer && null != customer.getDefaultB2BUnit() && null != customer.getDefaultB2BUnit().getBillingAddress()
					&& null != customer.getDefaultB2BUnit().getShippingAddress())
			{
				final AddressData accountInfo = new AddressData();
				siteoneAddressPopulator.populate(customer.getDefaultB2BUnit().getBillingAddress(), accountInfo);
				if (null != customer.getDefaultB2BUnit().getName())
				{
					quoteDetailsData.setAccountName(customer.getDefaultB2BUnit().getName());
					accountInfo.setTitle(customer.getDefaultB2BUnit().getName());
				}
				quoteDetailsData.setAccountInfo(accountInfo);
			}
			final DeviveryAddressQuoteData deliveryData = new DeviveryAddressQuoteData();

			if (quoteDetails.getDeliveryStreet() != null && quoteDetails.getDeliveryCity() != null
					&& quoteDetails.getDeliveryStreet() != null && quoteDetails.getDeliveryZip() != null)
			{
				deliveryData.setDeliveryCity(quoteDetails.getDeliveryCity());
				deliveryData.setDeliveryState(quoteDetails.getDeliveryState());
				deliveryData.setDeliveryStreet(quoteDetails.getDeliveryStreet());
				deliveryData.setDeliveryZip(quoteDetails.getDeliveryZip());
				deliveryData.setCustomerName(customer.getDefaultB2BUnit().getName());
				quoteDetailsData.setDeliveryDetails(deliveryData);
			}
			else
			{
				quoteDetailsData.setDeliveryDetails(null);
			}
			PointOfServiceData posData = new PointOfServiceData();
			if (null != branchNumber && null != storeFinderFacade.getStoreForId(branchNumber))
			{
				posData = storeFinderFacade.getStoreForId(branchNumber);
				quoteDetailsData.setBranchInfo(posData);
			}
			if (quoteDetails.getQuoteHeaderID() != null)
			{
				quoteDetailsData.setQuoteId(quoteDetails.getQuoteHeaderID().toString());
			}
			if (quoteDetails.getQuoteHeaderID() != null)
			{
				quoteDetailsData.setQuoteNumber(quoteDetails.getQuoteHeaderID());
			}
			if (quoteDetails.getWriter() != null)
			{
				quoteDetailsData.setWriter(quoteDetails.getWriter());
			}
			if (quoteDetails.getCustomerNumber() != null)
			{
				quoteDetailsData.setCustomerNumber(quoteDetails.getCustomerNumber());
			}
			final String uid = setCustomerNoWithDivision(quoteDetails.getCustomerNumber());
			final B2BUnitModel spcficCustomer = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
			if (spcficCustomer != null)
			{
				if (spcficCustomer.getAccountManagerName() != null && StringUtils.isNotBlank(spcficCustomer.getAccountManagerName()))
				{
					quoteDetailsData.setAccountManager(spcficCustomer.getAccountManagerName());
				}
				if (spcficCustomer.getAccountManagerEmail() != null
						&& StringUtils.isNotBlank(spcficCustomer.getAccountManagerEmail()))
				{
					quoteDetailsData.setAccountManagerEmail(spcficCustomer.getAccountManagerEmail());
				}
			}
			quoteDetailsData.setJobName(quoteDetails.getJobName());
			if (quoteDetails.getCreateDate() != null)
			{
				quoteDetailsData.setDateSubmitted(updateDateFormat(quoteDetails.getCreateDate()));
			}
			if (quoteDetails.getExpirationDate() != null)
			{
				quoteDetailsData.setExpDate(updateDateFormat(quoteDetails.getExpirationDate()));
			}
			if (quoteDetails.getLastModfDate() != null)
			{
				quoteDetailsData.setLastModfDate(updateDateFormat(quoteDetails.getLastModfDate()));
			}
			if (quoteDetails.getBranchManagerEmail() != null)
			{
				quoteDetailsData.setBranchManagerEmail(quoteDetails.getBranchManagerEmail());
			}
			if (quoteDetails.getWriterEmail() != null)
			{
				quoteDetailsData.setWriterEmail(quoteDetails.getWriterEmail());
			}
			if (quoteDetails.getPricerEmail() != null)
			{
				quoteDetailsData.setPricerEmail(quoteDetails.getPricerEmail());
			}
			if (quoteDetails.getShowOnline() != null)
			{
				quoteDetailsData.setShowOnline(quoteDetails.getShowOnline());
			}
			if (quoteDetails.getIsFullApproval() != null)
			{
				quoteDetailsData.setIsFullApproval(quoteDetails.getIsFullApproval());
			}
			else
			{
				quoteDetailsData.setIsFullApproval(Boolean.FALSE);
			}
			if (quoteDetails.getBranchManager() != null)
			{
				quoteDetailsData.setBranchManager(quoteDetails.getBranchManager());
			}
			if (quoteDetails.getBranchManagerID() != null)
			{
				quoteDetailsData.setBranchManagerID(quoteDetails.getBranchManagerID());
			}
			if (quoteDetails.getBranchManagerNodeID() != null)
			{
				quoteDetailsData.setBranchManagerNodeID(quoteDetails.getBranchManagerNodeID());
			}
			if (quoteDetails.getHighmarkNumber() != null)
			{
				quoteDetailsData.setLegalDisclaimer(Boolean.TRUE);
			}
			else
			{
				quoteDetailsData.setLegalDisclaimer(Boolean.FALSE);
			}
			quoteDetailsData.setStatus(quoteDetails.getStatus());
			final List<QuoteItemDetailsData> quoteItemDetails = new ArrayList<>();
			Double price = 0.0;
			Double apiPrice = 0.0;
			if (quoteDetails.getQuoteTotal() != null)
			{
				apiPrice = quoteDetails.getQuoteTotal();
			}
			Double remainingPrice = 0.0;
			for (final SiteOneQuoteDetails detail : quoteDetails.getDetails())
			{
				final QuoteItemDetailsData quoteItemObj = new QuoteItemDetailsData();
				if ((detail.getDeleted() != null && BooleanUtils.isFalse(detail.getDeleted())) || detail.getDeleted() == null)
				{

				if (null != detail.getItemNumber())
				{
					quoteItemObj.setItemNumber(detail.getItemNumber());
				}
				if (null != detail.getLineNumber())
				{
					quoteItemObj.setLineNumber(detail.getLineNumber());
				}
				if (null != detail.getQuoteDetailID())
				{
					quoteItemObj.setQuoteDetailID(detail.getQuoteDetailID().toString());
				}
				if (null != detail.getItemDescription())
				{
					quoteItemObj.setItemDescription(detail.getItemDescription());
				}
				if (null != detail.getInventoryUOM())
				{
					quoteItemObj.setUOM(detail.getInventoryUOM());
				}
				if (null != detail.getApprovalDate())
				{
					quoteItemObj.setApprovalDate(detail.getApprovalDate().substring(5, 10).replace('-', '/'));
				}
				if (null != detail.getLastModfDate())
				{
					quoteItemObj.setItemLastModfDate(updateDateFormat(detail.getLastModfDate()));
				}
				if (null != detail.getApprovalHistoryCount())
				{
					quoteItemObj.setApprovalHistoryCount(detail.getApprovalHistoryCount());
				}
				if (null != detail.getTotalApprovedQty())
				{
					quoteItemObj.setTotalApprovedQty(detail.getTotalApprovedQty());
				}
				quoteItemObj.setQuantity(detail.getQuantity());
				quoteItemObj.setUnitPrice(detail.getUnitPrice());
				if (detail.getExtendedPrice() != null)
				{
				quoteItemObj.setExtPrice(detail.getExtendedPrice());
				if (detail.getItemNumber() != null && !detail.getItemNumber().equalsIgnoreCase("C") && !detail.getItemNumber().equalsIgnoreCase("ST"))
				{
				price = price + detail.getExtendedPrice();
				}
				}
				else if (detail.getUnitPrice() != null && detail.getQuantity() != null && detail.getItemNumber() != null && !detail.getItemNumber().equalsIgnoreCase("C") && !detail.getItemNumber().equalsIgnoreCase("ST"))
				{
				price = price + (detail.getUnitPrice() * detail.getQuantity());
				}
				if (null != detail.getApprovalDate())
				{
				if (detail.getExtendedPrice() != null)
				{
				if (detail.getItemNumber() != null && !detail.getItemNumber().equalsIgnoreCase("C") && !detail.getItemNumber().equalsIgnoreCase("ST"))
				{
				remainingPrice = remainingPrice + detail.getExtendedPrice();
				}
				}
				else if (detail.getUnitPrice() != null && detail.getQuantity() != null && detail.getItemNumber() != null && !detail.getItemNumber().equalsIgnoreCase("C") && !detail.getItemNumber().equalsIgnoreCase("ST"))
				{
				remainingPrice = remainingPrice + (detail.getUnitPrice() * detail.getQuantity());
				}
				}
				quoteItemObj.setNotes(detail.getNotes());
				quoteItemObj.setSkuId(String.valueOf(detail.getSKUID()));
				quoteItemDetails.add(quoteItemObj);
			    quoteDetailsData.setItemDetails(quoteItemDetails);
				}
			}
			final String priceData = String.format("%.2f", price);
			final String remainingFormtPrice = String.format("%.2f", remainingPrice);
			quoteDetailsData.setBidTotal(apiPrice.toString());
			final Double reminPrc = (Double.valueOf(priceData)) - (Double.valueOf(remainingFormtPrice));
			final String reminFrmPrc = String.format("%.2f", reminPrc);
			if (reminFrmPrc != null)
			{
				quoteDetailsData.setRemainingBidTotal(reminFrmPrc);
			}
		}
		return quoteDetailsData;
	}

	public String updateDateFormat(final String date) throws ParseException
	{
		String strDate = "";
		if (StringUtils.isNotEmpty(date) || null != date)
		{
			final String dateStr = date.split("T")[0].replace("-", "/");
			final String[] arrOfStr = dateStr.split("/");
			strDate = arrOfStr[1] + "/" + arrOfStr[2] + "/" + arrOfStr[0];
		}
		return strDate;
	}

	@Override
	public void quoteListEmail(final String quoteNumber, final String itemCount, final String productList,
			final String accountManagerEmail, final String branchManagerEmail, final String writerEmail, final String pricerEmail,
			final String quoteId, final String writer, final String accountManager, final String poNumber,
			final String optionalNotes, final String quotesBr, final String customerNumber)
	{
		final QuoteDetailsData qotList = new QuoteDetailsData();
		if (null != quoteNumber)
		{
			qotList.setQuoteNumber(Integer.parseInt(quoteNumber));
		}
		if (null != writer)
		{
			qotList.setWriter(writer);
		}
		if (accountManager != null && StringUtils.isNotBlank(accountManager))
		{
			qotList.setAccountManager(accountManager);
		}
		else
		{
			qotList.setAccountManager("SiteOne Sales");
		}
		final String uid = setCustomerNoWithDivision(customerNumber);
		final B2BUnitModel spcficCustomer = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
		if (spcficCustomer != null)
		{
			if (spcficCustomer.getPhoneNumber() != null)
			{
				qotList.setPhoneNumber(spcficCustomer.getPhoneNumber());
			}
			if (spcficCustomer.getName() != null)
			{
				qotList.setAccountName(spcficCustomer.getName());
			}
			if (spcficCustomer.getUid() != null)
			{
				qotList.setAccountId(spcficCustomer.getUid());
			}
			if (spcficCustomer.getInsideSalesRepEmail() != null)
			{
				qotList.setSalesRepEmail(spcficCustomer.getInsideSalesRepEmail());
			}
		}
		final List<QuoteItemDetailsData> quoteItemDetails = new ArrayList<>();
		final String[] products = productList.split(Pattern.quote("|"));

		for (final String product : products)
		{
			final String[] splitData = product.split(Pattern.quote("^"));
			final QuoteItemDetailsData prdList = new QuoteItemDetailsData();
			prdList.setItemNumber(splitData[0]);
			prdList.setItemDescription(splitData[1]);
			prdList.setQuantity(Double.valueOf(splitData[2]));
			prdList.setUnitPrice(Double.valueOf(splitData[3]));
			prdList.setUOM(splitData[4]);
			prdList.setExtPrice(Double.valueOf(splitData[5]));
			if (splitData.length >= 8)
			{
				prdList.setApprovalCheck(splitData[7]);
			}
			if (splitData.length >= 9)
			{
				double iniAppQty = Double.valueOf(splitData[8]);
				int finAppQty = (int) Math.round(iniAppQty);
				prdList.setApprovedQty(Integer.valueOf(finAppQty));
			}
			if (splitData.length >= 10)
			{
				prdList.setLineNumber(Integer.valueOf(splitData[9]));
			}
			quoteItemDetails.add(prdList);
		}
		qotList.setItemDetails(quoteItemDetails);
		eventService.publishEvent(initializeEvent(new QuoteApprovalEvent(), qotList, itemCount, accountManagerEmail,
				branchManagerEmail, writerEmail, pricerEmail, quoteId, poNumber, optionalNotes, quotesBr));
	}

	public QuoteApprovalEvent initializeEvent(final QuoteApprovalEvent event, final QuoteDetailsData qotList,
			final String itemCount, final String accountManagerEmail, final String branchManagerEmail, final String writerEmail,
			final String pricerEmail, final String quoteId, final String poNumber, final String optionalNotes, final String quotesBr)
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (customer != null)
		{
			if (customer.getDefaultB2BUnit() != null)
			{
				if (customer.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
				}
				else
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
				}
			}
		}

		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		event.setQuoteNumber(String.valueOf(qotList.getQuoteNumber()));
		final List<QuoteItemDetailsModel> modfItemDetails = new ArrayList<>();
		final List<QuoteItemDetailsModel> nonModfItemDetails = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(qotList.getItemDetails()))
		{
			for (final QuoteItemDetailsData prdList : qotList.getItemDetails())
			{
				final QuoteItemDetailsModel quote = new QuoteItemDetailsModel();
				quote.setItemNumber(prdList.getItemNumber());
				quote.setItemDescription(prdList.getItemDescription());
				quote.setQuantity(String.valueOf(prdList.getQuantity()));
				quote.setUnitPrice(String.valueOf(prdList.getUnitPrice()));
				quote.setUOM(prdList.getUOM());
				quote.setExtPrice(String.valueOf(prdList.getExtPrice()));
				if (prdList.getApprovalCheck() != null)
				{
					quote.setApprovalCheck(prdList.getApprovalCheck());
				}
				if (prdList.getApprovedQty() != null)
				{
					quote.setApprovedQty(prdList.getApprovedQty());
				}
				if (prdList.getLineNumber() != null)
				{
					quote.setLineNumber(prdList.getLineNumber());
				}
								
				if (quote.getApprovalCheck() != null && "true".equalsIgnoreCase(quote.getApprovalCheck()))
				{
					modfItemDetails.add(quote);
				}
				else
				{
					nonModfItemDetails.add(quote);
				}
			}
		}
		event.setItemDetails(nonModfItemDetails);
		event.setModifiedItemDetails(modfItemDetails);
		if (customer != null)
		{
			if (customer.getFirstName() != null)
			{
				event.setCustomerName(customer.getFirstName());
			}
			if (customer.getEmail() != null)
			{
				event.setCustomerEmailAddress(customer.getEmail());
			}
		}
		if (quotesBr != null)
		{
			final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(quotesBr);
			if (pos != null && pos.getBranchManagerEmail() != null)
			{
				event.setBranchManagerEmail(pos.getBranchManagerEmail());
			}
		}
		if (accountManagerEmail != null)
		{
			event.setAccountManagerEmail(accountManagerEmail);
		}
		event.setInsideSalesRepEmail(qotList.getSalesRepEmail());
		event.setPhoneNumber(qotList.getPhoneNumber());
		event.setAccountName(qotList.getAccountName());
		event.setAccountId(qotList.getAccountId());
		event.setApproverName(qotList.getAccountManager());
		event.setPoNumber(poNumber);
		event.setOptionalNotes(optionalNotes);
		event.setWriterEmail(writerEmail);
		event.setPricerEmail(pricerEmail);
		if (quoteId != null)
		{
			event.setQuoteId(quoteId);
		}
		if (itemCount != null)
		{
			if (itemCount.equals("full"))
			{
				event.setIsFullQuote(true);
			}
			else
			{
				event.setIsFullQuote(false);
				event.setItemCount(itemCount);
			}
		}

		return event;
	}

	@Override
	public ContactSellerEvent initializeEvent(final ContactSellerEvent event, final String quoteNumber, final String quoteComments,
			final String accountManagerEmail, final String branchManagerEmail, final String writerEmail, final String pricerEmail,
			final String quoteId, final String writer, final String accountManager, final String quotesBr,
			final String customerNumber)
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (customer != null)
		{
			if (customer.getDefaultB2BUnit() != null)
			{
				if (customer.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
				}
				else
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
				}
			}
		}

		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		if (customer != null)
		{
			if (customer.getName() != null)
			{
				event.setCustomerName(customer.getName());
			}
			if (customer.getEmail() != null)
			{
				event.setCustomerEmailAddress(customer.getEmail());
			}
		}
		if (accountManager != null && StringUtils.isNotBlank(accountManager))
		{
			event.setApproverName(accountManager);
		}
		else
		{
			event.setApproverName("SiteOne Sales");
		}
		if (quotesBr != null)
		{
			final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(quotesBr);
			if (pos != null && pos.getBranchManagerEmail() != null)
			{
				event.setBranchManagerEmail(pos.getBranchManagerEmail());
			}
		}
		if (accountManagerEmail != null)
		{
			event.setAccountManagerEmail(accountManagerEmail);
		}
		final String uid = setCustomerNoWithDivision(customerNumber);
		final B2BUnitModel spcficCustomer = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
		if (spcficCustomer != null)
		{
			if (spcficCustomer.getPhoneNumber() != null)
			{
				event.setPhoneNumber(spcficCustomer.getPhoneNumber());
			}
			if (spcficCustomer.getName() != null)
			{
				event.setAccountName(spcficCustomer.getName());
			}
			if (spcficCustomer.getUid() != null)
			{
				event.setAccountId(spcficCustomer.getUid());
			}
			if (spcficCustomer.getInsideSalesRepEmail() != null)
			{
				event.setInsideSalesRepEmail(spcficCustomer.getInsideSalesRepEmail());
			}
		}
		event.setQuoteNumber(quoteNumber);
		event.setQuoteComments(quoteComments);
		event.setWriterEmail(writerEmail);
		event.setPricerEmail(pricerEmail);
		if (quoteId != null)
		{
			event.setQuoteId(quoteId);
		}
		return event;
	}

	@Override
	public void sortComments(final AbstractOrderData orderData)
	{
		if (orderData != null)
		{
			if (CollectionUtils.isNotEmpty(orderData.getComments()))
			{
				final List<CommentData> sortedComments = orderData.getComments().stream()
						.sorted((comment1, comment2) -> comment2.getCreationDate().compareTo(comment1.getCreationDate()))
						.collect(Collectors.toList());
				orderData.setComments(sortedComments);
			}

			if (CollectionUtils.isNotEmpty(orderData.getEntries()))
			{
				for (final OrderEntryData orderEntry : orderData.getEntries())
				{
					if (CollectionUtils.isNotEmpty(orderEntry.getComments()))
					{
						final List<CommentData> sortedEntryComments = orderEntry.getComments().stream()
								.sorted((comment1, comment2) -> comment2.getCreationDate().compareTo(comment1.getCreationDate()))
								.collect(Collectors.toList());

						orderEntry.setComments(sortedEntryComments);
					}
					else if (orderEntry.getProduct() != null && orderEntry.getProduct().getMultidimensional() != null
							&& orderEntry.getProduct().getMultidimensional())
					{
						if (CollectionUtils.isNotEmpty(orderEntry.getEntries()))
						{
							for (final OrderEntryData multiDOrderEntry : orderEntry.getEntries())
							{
								if (CollectionUtils.isNotEmpty(multiDOrderEntry.getComments()))
								{
									final List<CommentData> sortedMultiDOrderEntryComments = multiDOrderEntry.getComments().stream()
											.sorted((comment1, comment2) -> comment2.getCreationDate().compareTo(comment1.getCreationDate()))
											.collect(Collectors.toList());

									multiDOrderEntry.setComments(sortedMultiDOrderEntryComments);
								}
							}
						}
					}
				}
			}
		}
	}

	private String getSource()
	{
		final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null)
		{
			final HttpServletRequest request = requestAttributes.getRequest();
			if (request != null && request.getHeader("User-Agent") != null)
			{
				if (StringUtils.containsIgnoreCase(request.getHeader("User-Agent"), "SiteOneEcomApp"))
				{
					return "App";
				}
			}
			return "Web";
		}
		return null;
	}

	@Override
	public void updateQuote(final String quoteHeaderID, final String productList)
	{

		final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), quoteHeaderID);
		final List<SiteOneQuoteDetails> quoteItemDetails = new ArrayList<>();
		final String[] products = productList.split(Pattern.quote("|"));
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		final OffsetDateTime est = now.minusHours(4);
		final CustomerData customer = ((DefaultSiteOneCustomerFacade) customerFacade).getCurrentCustomer();
		quoteDetails.setLastApproverEmail(customer.getUid());
		quoteDetails.setLastApprovalSource(getSource());
		for (final SiteOneQuoteDetails detail : quoteDetails.getDetails())
		{
			for (final String header : products)
			{
				final String[] splitData = header.split(Pattern.quote("^"));
				if (detail.getQuoteDetailID() != null && splitData[6].equalsIgnoreCase(detail.getQuoteDetailID().toString()))
				{
					if (splitData.length >= 9 && StringUtils.isNotBlank(splitData[8]))
					{
						double iniAppQty = Double.valueOf(splitData[8]);
						int finAppQty = (int) Math.round(iniAppQty);
						detail.setApprovedQty(Integer.valueOf(finAppQty));
					}
				}
			}
			if (detail.getItemNumber() != null && !detail.getItemNumber().equalsIgnoreCase("C")
					&& !detail.getItemNumber().equalsIgnoreCase("ST"))
			{
				detail.setApprovalDate(est.toString());
			}
			quoteItemDetails.add(detail);
		}
		quoteDetails.setDetails(quoteItemDetails);
		LOG.error("Inside update quote method " + quoteDetails.getDetails().get(0).getApprovalDate());
		siteOneQuotesWebService.updateQuote(quoteDetails, quoteHeaderID);
	}

	@Override
	public void updateQuoteDetail(final String quoteHeaderID, final String productList)
	{
		final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), quoteHeaderID);
		final List<SiteOneQuoteDetails> quoteItemDetails = quoteDetails.getDetails();
		final String[] products = productList.split(Pattern.quote("|"));
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		final OffsetDateTime est = now.minusHours(4);
		final CustomerData customer = ((DefaultSiteOneCustomerFacade) customerFacade).getCurrentCustomer();
		quoteDetails.setLastApproverEmail(customer.getUid());
		quoteDetails.setLastApprovalSource(getSource());
		int i = 0;
		for (final SiteOneQuoteDetails itemDetail : quoteItemDetails)
		{
			for (final String header : products)
			{
				final String[] splitData = header.split(Pattern.quote("^"));
				LOG.error("The detail id is " + splitData[6]);
				if (itemDetail.getQuoteDetailID() != null && splitData[6].equalsIgnoreCase(itemDetail.getQuoteDetailID().toString()))
				{
					quoteItemDetails.get(i).setApprovalDate(est.toString());
					if(splitData.length >= 9 && StringUtils.isNotBlank(splitData[8]))
					{
						double iniAppQty = Double.valueOf(splitData[8]);
						int finAppQty = (int) Math.round(iniAppQty);
						quoteItemDetails.get(i).setApprovedQty(Integer.valueOf(finAppQty));
					}
					break;
				}
			}
			i++;
		}
		quoteDetails.setDetails(quoteItemDetails);
		LOG.error("Inside update quote details method " + quoteDetails);
		siteOneQuotesWebService.updateQuoteDetail(quoteDetails, quoteHeaderID);
	}
	
	@Override
	public String expiredQuoteUpdFlow(final String quoteNumber, final String notes, final String customerNumber, final String quoteTotal) throws ParseException
	{
		final SiteOneQuoteDetailResponseData quoteDetails = siteOneQuotesWebService.getQuotesDetails(
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), quoteNumber);
		eventService.publishEvent(initializeEvent(new ExpiredQuoteUpdEvent(), quoteNumber, notes, customerNumber, quoteTotal, quoteDetails));
		
		return "Success";
	}
	
	public ExpiredQuoteUpdEvent initializeEvent(final ExpiredQuoteUpdEvent event, final String quoteNumber, 
			final String notes, final String customerNumber, final String quoteTotal, final SiteOneQuoteDetailResponseData quoteDetails) throws ParseException
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final String uid = setCustomerNoWithDivision(customerNumber);
		final B2BUnitModel spcficCustomer = (B2BUnitModel) b2bUnitService.getUnitForUid(uid);
		if (customer != null)
		{
			if (customer.getDefaultB2BUnit() != null)
			{
				if (customer.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
				}
				else
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
				}
			}
		}

		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		if (quoteNumber != null)
		{
			event.setQuoteNumber(quoteNumber);
		}
		if (notes != null)
		{
			event.setNotes(notes);
		}
		if (quoteTotal != null)
		{
			event.setQuoteTotal(quoteTotal);
		}
		
		if (customer != null)
		{
			if (customer.getName() != null)
			{
				event.setCustomerName(customer.getName());
			}
			if (customer.getUid() != null)
			{
				event.setCustomerEmailAddress(customer.getUid());
			}
		}

		if (spcficCustomer != null)
		{
			if (spcficCustomer.getPhoneNumber() != null)
			{
				event.setPhoneNumber(spcficCustomer.getPhoneNumber());
			}
			if (spcficCustomer.getName() != null)
			{
				event.setAccountName(spcficCustomer.getName());
			}
			if (spcficCustomer.getUid() != null)
			{
				event.setAccountId(spcficCustomer.getUid().trim().split("[_]")[0].trim());
			}
			if (spcficCustomer.getAccountManagerEmail() != null)
			{
				event.setAccountManagerEmail(spcficCustomer.getAccountManagerEmail());
			}
			if (spcficCustomer.getInsideSalesRepEmail() != null)
			{
				event.setInsideSalesRepEmail(spcficCustomer.getInsideSalesRepEmail());
			}
		}
		
		if (quoteDetails != null)
		{
			if (quoteDetails.getJobName() != null)
			{
				event.setJobName(quoteDetails.getJobName());
			}
			if (quoteDetails.getExpirationDate() != null)
			{
				event.setExpDate(updateDateFormat(quoteDetails.getExpirationDate()));
			}
			if (quoteDetails.getBranchManagerEmail() != null)
			{
				event.setBranchManagerEmail(quoteDetails.getBranchManagerEmail());
			}
			if (quoteDetails.getWriterEmail() != null)
			{
				event.setWriterEmail(quoteDetails.getWriterEmail());
			}
			if (quoteDetails.getPricerEmail() != null)
			{
				event.setPricerEmail(quoteDetails.getPricerEmail());
			}
		}

		return event;
	}

	@Override
	public ShiptoitemData shiptoQuote(final boolean showOnline, final String quotesStatus)
	{
		final List<String> listOfShipTos = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();

		final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (null != defaultUnit)
		{
			final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(defaultUnit.getUid());

			for (final B2BUnitData child : childs)
			{
				if (!child.getUid().equalsIgnoreCase(defaultUnit.getUid()))
				{
					final String shipID = String.valueOf(getCustomerNo(child.getUid()));
					listOfShipTos.add(shipID);
				}
			}
			final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
			final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
			shiptoRequest.setCustomerNumbers(listOfShipTos);
			shiptoRequest.setShowOnline(Boolean.TRUE);
			shiptoRequest.setEndDate(now.plusDays(2).toString());
			if (siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate") != null)
			{
				shiptoRequest.setStartDate(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate"));
			}
			else
			{
				shiptoRequest.setStartDate(now.minusYears(1).toString());
			}
			shiptoRequest.setLimit(Integer.valueOf(150));
			shiptoRequest.setSkip(Integer.valueOf(0));
			LOG.error("shipto request " + shiptoRequest.getCustomerNumbers());
			if (CollectionUtils.isNotEmpty(shiptoRequest.getCustomerNumbers()))
			{
				final List<SiteOneQuoteShiptoResponseData> shiptoRespData = siteOneQuotesWebService.shiptoQuote(shiptoRequest);
				for (final SiteOneQuoteShiptoResponseData shipToList : shiptoRespData)
				{
					final ShiptoquoteData shiptoQuoteData = new ShiptoquoteData();
					if (shipToList != null)
					{
						if (shipToList.getCustomerNumber() != null)
						{
							shiptoQuoteData.setCustomerNumber(shipToList.getCustomerNumber());
						}
						if (shipToList.getCustomerName() != null)
						{
							shiptoQuoteData.setCustomerName(shipToList.getCustomerName());
						}
						if (shipToList.getQuoteCount() != null)
						{
							shiptoQuoteData.setQuoteCount(String.valueOf(shipToList.getQuoteCount()));
						}
					}
					shipListData.add(shiptoQuoteData);
				}
				shiptoQuoteItemData.setItemDetails(shipListData);
				return shiptoQuoteItemData;
			}
			else
			{
				shiptoQuoteItemData.setItemDetails(shipListData);
				return shiptoQuoteItemData;
			}
		}
		return shiptoQuoteItemData;
	}
	
	@Override
	public QuoteApprovalItemData quoteApprovalHistory(String quoteDetailID) throws ParseException
	{
		final QuoteApprovalItemData approvalHistoryItemData = new QuoteApprovalItemData();
		final List<QuoteApprovalHistoryData> historyListData = new ArrayList<>();
		final List<QuoteApprovalHistoryResponseData> historyRespData = siteOneQuotesWebService.quoteApprovalHistory(Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), quoteDetailID);
		
		if (historyRespData != null)
		{
			for (final QuoteApprovalHistoryResponseData historyList : historyRespData)
			{
				final QuoteApprovalHistoryData approvalHistoryData = new QuoteApprovalHistoryData();
				
				if (historyList.getQuoteDetailID() != null)
				{
					approvalHistoryData.setQuoteDetailID(historyList.getQuoteDetailID());
				}
				if (historyList.getApprovalDate() != null)
				{
					approvalHistoryData.setApprovalDate(updateDateFormat(historyList.getApprovalDate()));
				}
				if (historyList.getApprovedQty() != null)
				{
					approvalHistoryData.setApprovedQty(historyList.getApprovedQty());
				}
				historyListData.add(approvalHistoryData);
			}
			approvalHistoryItemData.setItemDetails(historyListData);
			return approvalHistoryItemData;
		}
		else
		{
			approvalHistoryItemData.setItemDetails(historyListData);
			return approvalHistoryItemData;
		}
	}

	@Override
	public String requestQuote(final String jobName, final String jobStartDate, final String jobDescription, final String branch,
			final String notes, final String comments, final String productsList, final String listCode,
			final String selectedProductList) throws ParseException

	{
		final SiteOneRequestQuoteRequestData quotesRequest = new SiteOneRequestQuoteRequestData();
		quotesRequest.setJobName(jobName);
		quotesRequest.setQuoteHeaderID(0);
		quotesRequest.setQuoteStatusID(12);
		quotesRequest.setQuoteTypeID(2);
		quotesRequest.setJobStartDate(jobStartDate.concat("T00:00:00Z"));
		quotesRequest.setJobDescription(jobDescription);
		quotesRequest.setBranchNumber(branch);
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			quotesRequest.setDivisionCode("US");
			quotesRequest.setDivisionID(Integer.valueOf(1));
		}
		else
		{
			quotesRequest.setDivisionCode("CA");
			quotesRequest.setDivisionID(Integer.valueOf(2));
		}
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (null != unit.getGuid())
		{
			quotesRequest.setCustTreeNodeID(unit.getGuid());
		}

		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		quotesRequest.setCreateDate(now.toString());
		quotesRequest.setExpirationDate(now.plusDays(30).toString());
		quotesRequest.setLastModfDate(now.toString());
		quotesRequest.setCreateID(Integer.valueOf(108242));
		quotesRequest.setCustomerNumber(getCustomerNo(unit.getUid()));
		if (StringUtils.isNotBlank(notes))
		{
			quotesRequest.setNotes(notes);
		}
		int count = 0;
		final List<SiteOneRequestQuoteDetails> quoteItemDetails = new ArrayList<>();
		final List<QuoteItemDetailsModel> requestQuoteCustomInfo = new ArrayList<>();
		final List<QuoteItemDetailsModel> requestQuoteProductInfo = new ArrayList<>();
		if (StringUtils.isNotBlank(productsList))
		{
			final String[] products = productsList.split(Pattern.quote("|"));
			for (final String product : products)
			{
				final String[] splitData = product.split(Pattern.quote("^"));
				final SiteOneRequestQuoteDetails prdList = new SiteOneRequestQuoteDetails();
				final QuoteItemDetailsModel reqList = new QuoteItemDetailsModel();
				count++;
				prdList.setLineNumber(count);
				prdList.setItemNumber(splitData[0]);
				prdList.setItemDescription(splitData[1]);
				prdList.setQuantity(Double.valueOf(splitData[2]));
				if (splitData.length == 4)
				{
					prdList.setUnitPrice(Double.valueOf(splitData[3]));
				}
				prdList.setCreateDate(now.toString());
				prdList.setLastModfDate(now.toString());
				prdList.setCreateID(Integer.valueOf(108242));
				prdList.setLastModfID(Integer.valueOf(108242));
				quoteItemDetails.add(prdList);

				reqList.setItemNumber(splitData[0]);
				reqList.setItemDescription(splitData[1]);
				reqList.setQuantity(splitData[2]);
				requestQuoteCustomInfo.add(reqList);
			}
		}
		if (StringUtils.isNotBlank(listCode))
		{
			final Wishlist2Model savedListModel = siteoneSavedListService.getSavedListDetail(listCode);
			LOG.error("Quote entries123 " + savedListModel.getEntries().size());
			LOG.error("Quote savelist" + savedListModel.getName());
			final List<Wishlist2Model> listData = new ArrayList();
			List<Wishlist2EntryModel> listEntryModel = new ArrayList();
			final SavedListData savedListData = new SavedListData();
			Wishlist2Model savedListModel1 = new Wishlist2Model();
			siteoneSavedListConverter.convert(savedListModel, savedListData);
			int i = 0;
			for (final Wishlist2EntryModel entry : savedListModel.getEntries())
			{
				listEntryModel.add(entry);
				i++;
				if (i % 20 == 0)
				{
					savedListModel1.setEntries(listEntryModel);
					listData.add(savedListModel1);
					savedListModel1 = new Wishlist2Model();
					listEntryModel = new ArrayList();
				}
			}
			savedListModel1.setEntries(listEntryModel);
			listData.add(savedListModel1);
			final Map<String, String> cspData = new HashMap<>();
			for (final Wishlist2Model data : listData)
			{
				cspData.putAll(siteoneSavedListFacade.getCspCall(data.getEntries()));
			}
			int listCount = 0;
			double totalQuantity = 0;
			if (CollectionUtils.isNotEmpty(savedListData.getEntries()))
			{
				for (final SavedListEntryData entry : savedListData.getEntries())
				{
					if (null != entry && null != entry.getProduct())
					{
						if (quoteItemDetails.size() > 0)
						{
							listCount = quoteItemDetails.size();
						}
						listCount++;
						final SiteOneRequestQuoteDetails listEntry = new SiteOneRequestQuoteDetails();
						final QuoteItemDetailsModel reqProductList = new QuoteItemDetailsModel();
						listEntry.setLineNumber(listCount);
						listEntry.setItemNumber(entry.getProduct().getItemNumber());
						listEntry.setItemDescription(entry.getProduct().getName());
						listEntry.setSKUID(Integer.valueOf(entry.getProduct().getCode()));
						final InventoryUPCModel inventoryUPCModel = siteOneProductUOMService
								.getInventoryUOMById(String.valueOf(entry.getInventoryUom()));
						if (null != inventoryUPCModel)
						{
							final String inventoryUomId = inventoryUPCModel.getInventoryUPCID();
							final ProductModel product = getProductService().getProductForCode(entry.getProduct().getCode());
							final InventoryUPCModel inventoryUOM = product.getUpcData().stream()
									.filter(upc -> upc.getInventoryUPCID().equals(inventoryUomId)).findFirst().orElse(null);
							if (entry.getQty() != null && inventoryUOM != null)
							{
								totalQuantity = (entry.getQty().doubleValue()) * (inventoryUOM.getInventoryMultiplier()).intValue();
								listEntry.setQuantity(totalQuantity);
							}
						}
						else
						{
							final List<InventoryUPCModel> invUpc = siteOneProductUOMService
									.getSellableUOMsById(String.valueOf(listEntry.getSKUID()));
							if (null != invUpc)
							{
								if (CollectionUtils.isNotEmpty(invUpc))
								{
									final Collection<InventoryUPCModel> counts = invUpc.stream()
											.filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
											.collect(Collectors.toList());
									if (CollectionUtils.isNotEmpty(counts))
									{
										for (final InventoryUPCModel upcData : counts)
										{
											if (upcData != null && upcData.getInventoryUPCDesc()
													.equalsIgnoreCase(entry.getProduct().getSingleUomDescription()))
											{
												final Double totalQuantities = (entry.getQty().doubleValue())
														* (upcData.getInventoryMultiplier());
												listEntry.setQuantity(totalQuantities);
											}
										}
									}
								}
							}
						}
						if (cspData.get(entry.getProduct().getCode()) != null)
						{
							listEntry.setUnitPrice(Double.valueOf(cspData.get(entry.getProduct().getCode())));
						}
						else if (entry.getProduct() != null && entry.getProduct().getPrice() != null)
						{
							listEntry.setUnitPrice(entry.getProduct().getPrice().getValue().doubleValue());
						}
						listEntry.setCreateDate(now.toString());
						listEntry.setLastModfDate(now.toString());
						listEntry.setCreateID(Integer.valueOf(108242));
						listEntry.setLastModfID(Integer.valueOf(108242));
						quoteItemDetails.add(listEntry);

						reqProductList.setItemNumber(entry.getProduct().getItemNumber());
						reqProductList.setItemDescription(entry.getProduct().getName());
						reqProductList.setQuantity(String.valueOf(entry.getQty()));
						if (null != inventoryUPCModel)
						{
							final String inventoryUomId = inventoryUPCModel.getInventoryUPCID();
							final ProductModel product = getProductService().getProductForCode(entry.getProduct().getCode());
							final InventoryUPCModel inventoryUOM = product.getUpcData().stream()
									.filter(upc -> upc.getInventoryUPCID().equals(inventoryUomId)).findFirst().orElse(null);
							if (entry.getQty() != null && listEntry.getUnitPrice() != null && inventoryUOM != null)
							{
								final double lstprc = (listEntry.getUnitPrice().doubleValue())
										* (inventoryUOM.getInventoryMultiplier()).intValue();
								final String formtPrice = String.format("%.2f", lstprc);
								reqProductList.setUnitPrice(formtPrice);
								final BigDecimal data1 = new BigDecimal(entry.getQty());
								final BigDecimal data2 = new BigDecimal(lstprc).multiply(data1);
								final String data3 = String.format("%.2f", data2);
								reqProductList.setTotalPrice(String.valueOf(data3));
								reqProductList.setUOM(String.valueOf(inventoryUPCModel.getInventoryUPCDesc()));
							}
						}
						else
						{
							final List<InventoryUPCModel> invUpc = siteOneProductUOMService
									.getSellableUOMsById(String.valueOf(listEntry.getSKUID()));
							if (null != invUpc)
							{
								if (CollectionUtils.isNotEmpty(invUpc))
								{
									final Collection<InventoryUPCModel> counts = invUpc.stream()
											.filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
											.collect(Collectors.toList());
									if (CollectionUtils.isNotEmpty(counts))
									{
										for (final InventoryUPCModel upcData : counts)
										{
											if (upcData != null && upcData.getInventoryUPCDesc()
													.equalsIgnoreCase(entry.getProduct().getSingleUomDescription()))
											{
												if (entry.getQty() != null && listEntry.getUnitPrice() != null && upcData != null)
												{
													final double lstprc = (listEntry.getUnitPrice().doubleValue())
															* (upcData.getInventoryMultiplier()).intValue();
													final String formtPrice = String.format("%.2f", lstprc);
													reqProductList.setUnitPrice(formtPrice);
													final BigDecimal data1 = new BigDecimal(entry.getQty());
													final BigDecimal data2 = new BigDecimal(lstprc).multiply(data1);
													final String data3 = String.format("%.2f", data2);
													reqProductList.setTotalPrice(String.valueOf(data3));
													reqProductList.setUOM(String.valueOf(upcData.getInventoryUPCDesc()));
												}
											}
										}
									}
								}
							}
						}
						requestQuoteProductInfo.add(reqProductList);
					}
				}
			}
		}
		else if (StringUtils.isNotBlank(selectedProductList))
		{
			final String[] products = selectedProductList.split(Pattern.quote("|"));
			Double price = 0.0;
			for (final String product : products)
			{
				final String[] splitData = product.split(Pattern.quote("^"));
				final SiteOneRequestQuoteDetails prdList = new SiteOneRequestQuoteDetails();
				count++;
				prdList.setLineNumber(count);
				prdList.setItemNumber(splitData[0]);
				prdList.setItemDescription(splitData[1]);
				if (splitData.length >= 5)
				{
					prdList.setInventoryUOM(splitData[4]);
				}
				if (splitData.length == 6)
				{
					prdList.setSKUID(Integer.valueOf(splitData[5]));
				}
				if (splitData.length >= 3 && StringUtils.isNotBlank(splitData[2]))
				{
					prdList.setQuantity(Double.valueOf(splitData[2]));
				}
				final InventoryUPCModel inventoryUPCModel1 = siteOneProductUOMService
						.getInventoryUOMById(String.valueOf(prdList.getInventoryUOM()));
				if (splitData.length >= 4 && StringUtils.isNotBlank(splitData[3]))
				{
					prdList.setUnitPrice(Double.valueOf(splitData[3]));
				}
				else if (null != inventoryUPCModel1)
				{
					final String inventoryUomId = inventoryUPCModel1.getInventoryUPCID();
					final ProductModel pd = getProductService().getProductForCode(prdList.getSKUID().toString());
					final InventoryUPCModel inventoryUOM = pd.getUpcData().stream()
							.filter(upc -> upc.getInventoryUPCID().equals(inventoryUomId)).findFirst().orElse(null);
					final List<ProductOption> options = new ArrayList<>(Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
					final ProductData priceData = siteOneProductFacade.getProductBySearchTermForSearch(prdList.getSKUID().toString(),
							options);
					final BigDecimal data = priceData.getPrice().getValue().setScale(2, RoundingMode.HALF_UP);
					if (inventoryUOM != null)
					{
						final Double unitPrc = Double.valueOf(data.toString()) * (inventoryUOM.getInventoryMultiplier());
						prdList.setUnitPrice(unitPrc);
					}
					else
					{
						final List<InventoryUPCModel> inventoryUPCModel = siteOneProductUOMService
								.getSellableUOMsById(String.valueOf(prdList.getSKUID()));
						if (null != inventoryUPCModel)
						{
							if (CollectionUtils.isNotEmpty(inventoryUPCModel))
							{
								final Collection<InventoryUPCModel> counts = inventoryUPCModel.stream()
										.filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline())).collect(Collectors.toList());
								if (CollectionUtils.isNotEmpty(counts))
								{
									for (final InventoryUPCModel upcData : counts)
									{
										if (upcData != null && upcData.getInventoryUPCDesc().equalsIgnoreCase(prdList.getInventoryUOM()))
										{
											final Double unitPrc = Double.valueOf(data.toString()) * (upcData.getInventoryMultiplier());
											prdList.setUnitPrice(unitPrc);
										}
									}
								}
							}
						}
					}
				}
				prdList.setCreateDate(now.toString());
				prdList.setLastModfDate(now.toString());
				prdList.setCreateID(Integer.valueOf(108242));
				prdList.setLastModfID(Integer.valueOf(108242));
				quoteItemDetails.add(prdList);
				final QuoteItemDetailsModel reqProductLists = new QuoteItemDetailsModel();
				reqProductLists.setItemNumber(splitData[0]);
				reqProductLists.setItemDescription(splitData[1]);
				reqProductLists.setQuantity(splitData[2]);
				if (splitData.length >= 4 && prdList != null && prdList.getUnitPrice() != null)
				{
					reqProductLists.setUnitPrice(prdList.getUnitPrice().toString());
				}
				if (splitData.length >= 5)
				{
					reqProductLists.setUOM(splitData[4]);
				}
				if (reqProductLists != null && reqProductLists.getQuantity() != null && reqProductLists.getUnitPrice() != null)
				{
					price = (Double.parseDouble(reqProductLists.getUnitPrice()) * Double.parseDouble(reqProductLists.getQuantity()));
					final String seletformtPrice = String.format("%.2f", price);
					reqProductLists.setTotalPrice(seletformtPrice);
				}
				requestQuoteProductInfo.add(reqProductLists);
			}
		}
		quotesRequest.setDetails(quoteItemDetails);
		final SiteOneRequestQuoteResponseData quotesResponse = siteOneQuotesWebService.updateRequestQuote(quotesRequest);
		if (null != quotesResponse && null != quotesResponse.getQuoteHeaderID())
		{
			eventService.publishEvent(initializeEvent(new RequestQuoteEvent(), quotesResponse.getQuoteHeaderID(), quotesRequest,
					requestQuoteCustomInfo, requestQuoteProductInfo, comments));
			return "Success";
		}
		else
		{
			return "failure";
		}

	}

	@Override
	public RequestQuoteEvent initializeEvent(final RequestQuoteEvent event, final String quoteHeaderID,
			final SiteOneRequestQuoteRequestData quotesRequest, final List<QuoteItemDetailsModel> requestQuoteCustomInfo,
			final List<QuoteItemDetailsModel> requestQuoteProductInfo, final String comments) throws ParseException
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(quotesRequest.getBranchNumber());

		if (customer != null)
		{
			if (customer.getDefaultB2BUnit() != null)
			{
				if (customer.getDefaultB2BUnit().getUid().contains(SiteoneCoreConstants.INDEX_OF_US))
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_US_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_US));
				}
				else
				{
					event.setBaseStore(baseStoreService.getBaseStoreForUid(SiteoneCoreConstants.SITEONE_CA_BASESTORE));
					event.setSite(baseSiteService.getBaseSiteForUID(SiteoneCoreConstants.BASESITE_CA));
				}
			}
		}

		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());

		event.setJobName(quotesRequest.getJobName());
		event.setJobDescription(quotesRequest.getJobDescription());
		event.setJobStartDate(quotesRequest.getJobStartDate().replace("T00:00:00Z", ""));
		event.setBranch(quotesRequest.getBranchNumber());
		if (customer != null)
		{
			if (customer.getName() != null)
			{
				event.setCustomerName(customer.getName());
			}
			if (customer.getEmail() != null)
			{
				event.setCustomerEmailAddress(customer.getEmail());
			}
			if (customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getPhoneNumber() != null)
			{
				event.setPhoneNumber(customer.getDefaultB2BUnit().getPhoneNumber());
			}
			if (customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getAccountManagerEmail() != null)
			{
				event.setAccountManagerEmail(customer.getDefaultB2BUnit().getAccountManagerEmail());
			}
			if (customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getName() != null
					&& customer.getDefaultB2BUnit().getUid() != null)
			{
				event.setAccountName(customer.getDefaultB2BUnit().getName());
				event.setAccountId(customer.getDefaultB2BUnit().getUid());
			}
			if (customer.getDefaultB2BUnit() != null && customer.getDefaultB2BUnit().getInsideSalesRepEmail() != null)
			{
				event.setInsideSalesRepEmail(customer.getDefaultB2BUnit().getInsideSalesRepEmail());
			}
		}

		event.setQuoteHeaderID(quoteHeaderID);
		event.setComment(comments);

		if (pos != null && pos.getBranchManagerEmail() != null)
		{
			event.setBranchManagerEmail(pos.getBranchManagerEmail());
		}
		event.setItemDetails(requestQuoteProductInfo);
		event.setCustomItemDetails(requestQuoteCustomInfo);
		return event;
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

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService the baseSiteService to set
	 */
	public void setBaseSiteService(BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the b2bUnitService
	 */
	public B2BUnitService getB2bUnitService()
	{
		return b2bUnitService;
	}


	/**
	 * @param b2bUnitService
	 *           the b2bUnitService to set
	 */
	public void setB2bUnitService(final B2BUnitService b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

	/**
	 * @return the defaultSiteOneB2BUnitService
	 */
	public SiteOneB2BUnitService getDefaultSiteOneB2BUnitService()
	{
		return defaultSiteOneB2BUnitService;
	}


	/**
	 * @param defaultSiteOneB2BUnitService
	 *           the defaultSiteOneB2BUnitService to set
	 */
	public void setDefaultSiteOneB2BUnitService(final SiteOneB2BUnitService defaultSiteOneB2BUnitService)
	{
		this.defaultSiteOneB2BUnitService = defaultSiteOneB2BUnitService;
	}

	/**
	 * @return the b2bUnitFacade
	 */
	public B2BUnitFacade getB2bUnitFacade()
	{
		return b2bUnitFacade;
	}


	/**
	 * @param b2bUnitFacade
	 *           the b2bUnitFacade to set
	 */
	public void setB2bUnitFacade(final B2BUnitFacade b2bUnitFacade)
	{
		this.b2bUnitFacade = b2bUnitFacade;
	}

	/**
	 * @return the siteOneQuotesWebService
	 */
	public SiteOneQuotesWebService getSiteOneQuotesWebService()
	{
		return siteOneQuotesWebService;
	}


	/**
	 * @param siteOneQuotesWebService
	 *           the siteOneQuotesWebService to set
	 */
	public void setSiteOneQuotesWebService(final SiteOneQuotesWebService siteOneQuotesWebService)
	{
		this.siteOneQuotesWebService = siteOneQuotesWebService;
	}

	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	/**
	 * @param productService
	 *           the productService to set
	 */

	public Converter<B2BCustomerModel, CustomerData> getSiteonecustomerConverter()
	{
		return siteonecustomerConverter;
	}


	public void setSiteonecustomerConverter(final Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter)
	{
		this.siteonecustomerConverter = siteonecustomerConverter;
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService
	 *           the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(final SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

	/**
	 * @return the b2bCustomerService
	 */
	public B2BCustomerService getB2bCustomerService()
	{
		return b2bCustomerService;
	}

	/**
	 * @param b2bCustomerService
	 *           the b2bCustomerService to set
	 */
	public void setB2bCustomerService(final B2BCustomerService b2bCustomerService)
	{
		this.b2bCustomerService = b2bCustomerService;
	}

	/**
	 * @return the storeFinderFacade
	 */
	public SiteOneStoreFinderFacade getStoreFinderFacade()
	{
		return storeFinderFacade;
	}

	/**
	 * @param storeFinderFacade
	 *           the storeFinderFacade to set
	 */
	public void setStoreFinderFacade(final SiteOneStoreFinderFacade storeFinderFacade)
	{
		this.storeFinderFacade = storeFinderFacade;
	}
	
	/**
	 * @return the customerFacade
	 */
	public SiteOneCustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}

	/**
	 * @param customerFacade the customerFacade to set
	 */
	public void setCustomerFacade(SiteOneCustomerFacade customerFacade)
	{
		this.customerFacade = customerFacade;
	}
	

}
