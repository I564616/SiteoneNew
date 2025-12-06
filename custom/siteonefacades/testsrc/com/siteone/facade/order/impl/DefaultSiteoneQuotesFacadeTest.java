/**
 * 
 */
package com.siteone.facade.order.impl;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.savedList.service.SiteoneSavedListService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.SiteOneAddressPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.quote.data.QuoteApprovalItemData;
import com.siteone.facades.quote.data.QuoteDetailsData;
import com.siteone.facades.quote.data.QuotesData;
import com.siteone.facades.quote.data.ShiptoitemData;
import com.siteone.facades.quote.data.ShiptoquoteData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.order.data.QuoteApprovalHistoryResponseData;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.order.data.SiteOneQuoteDetails;
import com.siteone.integration.order.data.SiteOneQuoteShiptoRequestData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoResponseData;
import com.siteone.integration.quotes.order.data.SiteOneQuotesListResponseData;
import com.siteone.integration.quotes.order.data.SiteoneQuotesRequestData;
import com.siteone.integration.quotes.order.data.SiteoneQuotesSortRequestData;
import com.siteone.integration.services.ue.SiteOneQuotesWebService;

import junit.framework.Assert;

/**
 * @author AA04994
 *
 */
@UnitTest
public class DefaultSiteoneQuotesFacadeTest
{
	
	@Mock
	@Resource(name = "siteOneQuotesWebService")
	private SiteOneQuotesWebService siteOneQuotesWebService;

	@Resource(name = "siteoneSavedListService")
	private SiteoneSavedListService siteoneSavedListService;

	@Resource(name = "siteoneSavedListConverter")
	private Converter<Wishlist2Model, SavedListData> siteoneSavedListConverter;

	@Resource(name = "defaultSiteOneAddressPopulator")
	private SiteOneAddressPopulator siteoneAddressPopulator;

	@Resource(name = "siteOneProductUOMService")
	private SiteOneProductUOMService siteOneProductUOMService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "eventService")
	private EventService eventService;
	
	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;
	
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Mock
	private DefaultSiteoneQuotesFacade defaultSiteoneQuotesFacade;
	
	@Mock
	private ProductService productService;

	@Mock
	private B2BCustomerService b2bCustomerService;
	
	@Mock
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Mock
	private BaseSiteService baseSiteService;
	
	@Mock
	private DefaultSiteOneCustomerFacade customerFacade;

	@Mock(extraInterfaces =
	{ SiteOneB2BUnitService.class })
	private B2BUnitService b2bUnitService;

	@Mock(extraInterfaces =
	{ SiteOneB2BUnitFacade.class })
	private B2BUnitFacade b2bUnitFacade;
	
	@Mock 
	private SiteOneB2BUnitService defaultSiteOneB2BUnitService;
	
	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;


	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteoneQuotesFacade = new DefaultSiteoneQuotesFacade();
		defaultSiteoneQuotesFacade.setSiteOneQuotesWebService(siteOneQuotesWebService);
		defaultSiteoneQuotesFacade.setB2bUnitService(b2bUnitService);
		defaultSiteoneQuotesFacade.setDefaultSiteOneB2BUnitService(defaultSiteOneB2BUnitService);
		defaultSiteoneQuotesFacade.setB2bUnitFacade(b2bUnitFacade);
		defaultSiteoneQuotesFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
		defaultSiteoneQuotesFacade.setB2bCustomerService(b2bCustomerService);
		defaultSiteoneQuotesFacade.setStoreFinderFacade(storeFinderFacade);
		defaultSiteoneQuotesFacade.setBaseSiteService(baseSiteService);
		defaultSiteoneQuotesFacade.setCustomerFacade(customerFacade);
	}
	
	@Test
	public void testShiptoQuoteResponse1()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
//		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertEquals(shiptoQuoteData.getCustomerNumber(), actual.getItemDetails().get(0).getCustomerNumber());
		
   }
	
	@Test
	public void testShiptoQuoteResponse2()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
//		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertEquals(shiptoQuoteData.getCustomerName(), actual.getItemDetails().get(0).getCustomerName());

   }

// Assert null checks	
	@Test
	public void testShiptoQuoteResponse4()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.FALSE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(shiptoRespData);
		Assert.assertNotNull(actual);

   }

	@Test
	public void testShiptoQuoteResponse5()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
//		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual.getItemDetails().get(0).getQuoteCount());

   }

	@Test
	public void testShiptoQuoteResponse6()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
//		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual.getItemDetails().get(0).getCustomerNumber());

   }

	@Test
	public void testShiptoQuoteResponse7()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
//		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual.getItemDetails().get(0).getCustomerName());

   }

// Manipulated data

	@Test
	public void testShiptoQuoteResponseMD1()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(Boolean.FALSE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual);

   }

	@Test
	public void testShiptoQuoteResponseMD2()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(0));
		shiptoRequest.setApprovalStatus("Fully Approved");
		shiptoRequest.setExpired(Boolean.FALSE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual);

   }

	@Test
	public void testShiptoQuoteResponseMD3()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
		shiptoRequest.setSkip(Integer.valueOf(1));
//		shiptoRequest.setApprovalStatus("Fully Approved");
		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual);

   }

	@Test
	public void testShiptoQuoteResponseMD4()
	{
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		final List<ShiptoquoteData> shipListData = new ArrayList<>();
		final ShiptoitemData shiptoQuoteItemData = new ShiptoitemData();
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final List<B2BUnitData> childs = new ArrayList<>();
		final B2BUnitModel defaultUnit = new B2BUnitModel();
		defaultUnit.setUid("1608411_US");
		final List<String> customerNum = new ArrayList<>(Arrays.asList("1608411"));
		final B2BUnitData B2BUnitData = new B2BUnitData();
		B2BUnitData.setUid("17058_US");
		final String date = "2023-09-30T05:54:21.997Z";
		childs.add(B2BUnitData);
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate("2024-09-30T05:54:21.997Z");
		shiptoRequest.setStartDate("2023-09-30T05:54:21.997Z");
		shiptoRequest.setLimit(Integer.valueOf(150));
//		shiptoRequest.setSkip(Integer.valueOf(1));
//		shiptoRequest.setApprovalStatus("Fully Approved");
//		shiptoRequest.setExpired(Boolean.TRUE);
			final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
			shiptoQuoteData.setCustomerNumber("1608411");
			shiptoQuoteData.setCustomerName("LMI Texas");
			shiptoQuoteData.setQuoteCount(Integer.valueOf(4));
			shiptoRespData.add(shiptoQuoteData);
			given(siteOneQuotesWebService.shiptoQuote(any())).willReturn(shiptoRespData);
			given(siteOneFeatureSwitchCacheService.getValueForSwitch("QuoteStartDate")).willReturn(date);
			given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
			given(((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit("1608411_US")).willReturn(childs);
		final ShiptoitemData actual = defaultSiteoneQuotesFacade.shiptoQuote(Boolean.TRUE, null);
		Assert.assertNotNull(actual);

   }

	@Test
	public void testQuoteDetails1() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119");
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("2060119");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber("377625-I");
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7300963));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer())).willReturn(null);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		given(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		Assert.assertNotNull(quotedetails.getHighmarkNumber());
		Assert.assertNotNull(actual.getLegalDisclaimer());
	}
	
	@Test
	public void testQuoteDetails2() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119");
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("2060119");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7300963));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer())).willReturn(null);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		given(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		Assert.assertNull(quotedetails.getHighmarkNumber());
		Assert.assertFalse(actual.getLegalDisclaimer());
	}
	
   @Test
   public void testUpdateQuote1()
   {
   	final String quoteHeaderID = "7440078";
   	final String source = "Web";
   	final String boomi = "false";
   	final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	mockQuoteDetails.setLastApprovalSource("Web");
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	detail1.setItemNumber("PT-SPF-ELB50");
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
      HttpServletRequest mockRequest = mock(HttpServletRequest.class);
      ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);

      when(mockRequest.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
      when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
      RequestContextHolder.setRequestAttributes(mockRequestAttributes);
      defaultSiteoneQuotesFacade.updateQuote(quoteHeaderID,productList);
   	
   	Assert.assertEquals(source,mockQuoteDetails.getLastApprovalSource());
   	Assert.assertNotNull(mockQuoteDetails.getLastApprovalSource());
   	Assert.assertNotNull(mockQuoteDetails.getDetails().get(0).getApprovalDate());
   }

   @Test
   public void testUpdateQuote2()
   {
   	final String quoteHeaderID = "7440078";
   	final String source = "App";
   	final String boomi = "false";
   	final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	mockQuoteDetails.setLastApprovalSource("App");
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	detail1.setItemNumber("PT-SPF-ELB50");
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
      HttpServletRequest mockRequest = mock(HttpServletRequest.class);
      ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);
   
      when(mockRequest.getHeader("User-Agent")).thenReturn("SiteOneEcomApp");
      when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
      RequestContextHolder.setRequestAttributes(mockRequestAttributes);
      defaultSiteoneQuotesFacade.updateQuote(quoteHeaderID,productList);
   	
   	Assert.assertEquals(source,mockQuoteDetails.getLastApprovalSource());
   	Assert.assertNotNull(mockQuoteDetails.getLastApprovalSource());
   	Assert.assertNotNull(mockQuoteDetails.getDetails().get(0).getApprovalDate());
   }

   @Test
   public void testUpdateQuote3()
   {
   	final String quoteHeaderID = "7440078";
   	final String source = "Web";
   	final String boomi = "false";
   	final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	mockQuoteDetails.setLastApprovalSource("Web");
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	detail1.setItemNumber("C");
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
      HttpServletRequest mockRequest = mock(HttpServletRequest.class);
      ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);
   
      when(mockRequest.getHeader("User-Agent")).thenReturn("SiteOneEcomApp");
      when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
      RequestContextHolder.setRequestAttributes(mockRequestAttributes);
      defaultSiteoneQuotesFacade.updateQuote(quoteHeaderID,productList);
   	
   	Assert.assertNotSame(source,mockQuoteDetails.getLastApprovalSource());
   	Assert.assertNotNull(mockQuoteDetails.getLastApprovalSource());
   	Assert.assertNull(mockQuoteDetails.getDetails().get(0).getApprovalDate());
   }
   
   @Test
   public void testUpdateQuote4()
   {
   	final String quoteHeaderID = "7754580";
   	final String boomi = "false";
   	final String productList = "1007BK^Colmet Steel Landscape Edging Black 7 Gauge 3/16 in. x 4 in. x 10 ft.^18^53.98^PC^971.64^199758457^false^18|C^description^otherData^moreData^additionalData^info^456^false^0";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	detail1.setQuoteDetailID(Integer.valueOf(199758457));
   	detail1.setApprovedQty(Integer.valueOf(18));
   	detail1.setApprovalDate("2025-06-09T11:07:26.957");
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
   

      defaultSiteoneQuotesFacade.updateQuote(quoteHeaderID,productList);
      
      SiteOneQuoteDetails updatedDetail = mockQuoteDetails.getDetails().get(0);
      Assert.assertEquals(Integer.valueOf(18), updatedDetail.getApprovedQty());
      Assert.assertNotNull(updatedDetail.getApprovalDate());
   }
   
   @Test
   public void testUpdateQuote5()
   {
   	final String quoteHeaderID = "7754580";
   	final String boomi = "false";
   	final String productList = "C^description^otherData^moreData^additionalData^info^456^false^0";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	detail1.setQuoteDetailID(Integer.valueOf(199758457));
   	detail1.setApprovedQty(Integer.valueOf(0));
   	detail1.setApprovalDate("2025-06-09T11:07:26.957");
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
   

      defaultSiteoneQuotesFacade.updateQuote(quoteHeaderID,productList);
      
      SiteOneQuoteDetails updatedDetail = mockQuoteDetails.getDetails().get(0);
      Assert.assertEquals(Integer.valueOf(0), updatedDetail.getApprovedQty());
      Assert.assertNotNull(updatedDetail.getApprovalDate());
   }
   
   @Test
   public void testUpdateQuote6()
   {
   	final String quoteHeaderID = "7440078";
   	final CustomerData cusData = new CustomerData();
   	cusData.setUid("junerune@siteone.com");
   	final String boomi = "false";
   	final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	mockQuoteDetails.setLastApproverEmail("junerune@tcs.com");
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	detail1.setItemNumber("PT-SPF-ELB50");
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(cusData);
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
      HttpServletRequest mockRequest = mock(HttpServletRequest.class);
      ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);

      when(mockRequest.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
      when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
      RequestContextHolder.setRequestAttributes(mockRequestAttributes);
      defaultSiteoneQuotesFacade.updateQuote(quoteHeaderID,productList);  	  	
   	Assert.assertNotNull(mockQuoteDetails.getLastApproverEmail());
   	Assert.assertEquals(cusData.getUid(),mockQuoteDetails.getLastApproverEmail());
   }
   
   @Test
   public void testUpdateQuoteDetail1() 
   {
       final String quoteHeaderID = "7440078";
       final String source = "Web";
       final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
       final String boomi = "false";
   
       given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
   
       final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
       mockQuoteDetails.setLastApprovalSource("Web");
   
       final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
       detail1.setQuoteDetailID(191585608);
       detail1.setItemNumber("PT-SPF-ELB50");
   
       final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
       listDetails.add(detail1); 
   
       mockQuoteDetails.setDetails(listDetails);
   
       given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(boomi), quoteHeaderID)).willReturn(mockQuoteDetails);
       given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   
       HttpServletRequest mockRequest = mock(HttpServletRequest.class);
       ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);
       
       when(mockRequest.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
       when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
       RequestContextHolder.setRequestAttributes(mockRequestAttributes);
   
       defaultSiteoneQuotesFacade.updateQuoteDetail(quoteHeaderID, productList);
       
       Assert.assertEquals(source,mockQuoteDetails.getLastApprovalSource());
    	 Assert.assertNotNull(mockQuoteDetails.getLastApprovalSource());
   
    }
   
   @Test
   public void testUpdateQuoteDetail2() 
   {
       final String quoteHeaderID = "7440078";
       final String source = "App";
       final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
       final String boomi = "false";
   
       given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
   
       final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
       mockQuoteDetails.setLastApprovalSource("App");
   
       final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
       detail1.setQuoteDetailID(191585608);
       detail1.setItemNumber("PT-SPF-ELB50");
   
       final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
       listDetails.add(detail1); 
   
       mockQuoteDetails.setDetails(listDetails);
   
       given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(boomi), quoteHeaderID)).willReturn(mockQuoteDetails);
       given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   
       HttpServletRequest mockRequest = mock(HttpServletRequest.class);
       ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);
       
       when(mockRequest.getHeader("User-Agent")).thenReturn("SiteOneEcomApp");
       when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
       RequestContextHolder.setRequestAttributes(mockRequestAttributes);
   
       defaultSiteoneQuotesFacade.updateQuoteDetail(quoteHeaderID, productList);
       
       Assert.assertEquals(source,mockQuoteDetails.getLastApprovalSource());
    	 Assert.assertNotNull(mockQuoteDetails.getLastApprovalSource());
   
    }
   
   @Test
   public void testUpdateQuoteDetail3() 
   {
       final String quoteHeaderID = "7440078";
       final String source = "Web";
       final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
       final String boomi = "false";
   
       given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
   
       final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
       mockQuoteDetails.setLastApprovalSource("Web");
   
       final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
       detail1.setQuoteDetailID(191585608);
       detail1.setItemNumber("PT-SPF-ELB50");
   
       final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
       listDetails.add(detail1); 
   
       mockQuoteDetails.setDetails(listDetails);
   
       given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(boomi), quoteHeaderID)).willReturn(mockQuoteDetails);
       given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   
       HttpServletRequest mockRequest = mock(HttpServletRequest.class);
       ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);
       
       when(mockRequest.getHeader("User-Agent")).thenReturn("SiteOneEcomApp");
       when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
       RequestContextHolder.setRequestAttributes(mockRequestAttributes);
   
       defaultSiteoneQuotesFacade.updateQuoteDetail(quoteHeaderID, productList);
       
       Assert.assertNotSame(source,mockQuoteDetails.getLastApprovalSource());
    	 Assert.assertNotNull(mockQuoteDetails.getLastApprovalSource());
   
    }
   
   @Test
   public void testUpdateQuoteDetail4() 
   {
   	final String quoteHeaderID = "7754580";
   	final String boomi = "false";
   	final String productList = "1007BK^Colmet Steel Landscape Edging Black 7 Gauge 3/16 in. x 4 in. x 10 ft.^18^53.98^PC^971.64^199758457^false^18|C^description^otherData^moreData^additionalData^info^456^false^0";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	detail1.setQuoteDetailID(Integer.valueOf(199758457));
   	detail1.setApprovedQty(Integer.valueOf(18));
   	detail1.setApprovalDate("2025-06-09T11:07:26.957");
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
   
       defaultSiteoneQuotesFacade.updateQuoteDetail(quoteHeaderID, productList);
       
       SiteOneQuoteDetails updatedDetail = mockQuoteDetails.getDetails().get(0);
       Assert.assertEquals(Integer.valueOf(18), updatedDetail.getApprovedQty());
       Assert.assertNotNull(updatedDetail.getApprovalDate());
   
    }
   
   @Test
   public void testUpdateQuoteDetail5() 
   {
   	final String quoteHeaderID = "7754580";
   	final String boomi = "false";
   	final String productList = "C^description^otherData^moreData^additionalData^info^456^false^0";
   	
   	final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
   	
   	final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
   	detail1.setQuoteDetailID(Integer.valueOf(199758457));
   	detail1.setApprovedQty(Integer.valueOf(0));
   	detail1.setApprovalDate("2025-06-09T11:07:26.957");
   	final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
   	listDetails.add(detail1);
   	
   	mockQuoteDetails.setDetails(listDetails);
   	given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(mockQuoteDetails);
   	given(customerFacade.getCurrentCustomer()).willReturn(new CustomerData());
   	given(siteOneFeatureSwitchCacheService.getValueForSwitch(any())).willReturn(boomi);
   

      defaultSiteoneQuotesFacade.updateQuoteDetail(quoteHeaderID,productList);
      
      SiteOneQuoteDetails updatedDetail = mockQuoteDetails.getDetails().get(0);
      Assert.assertEquals(Integer.valueOf(0), updatedDetail.getApprovedQty());
      Assert.assertNotNull(updatedDetail.getApprovalDate());
   
    }
   
   @Test
   public void testUpdateQuoteDetail6() 
   {
       final String quoteHeaderID = "7440078";
       final CustomerData custData = new CustomerData();
       custData.setUid("junerune@siteone.com");
       final String productList = "PT-SPF-ELB50^description^otherData^moreData^additionalData^info^123|C^description^otherData^moreData^additionalData^info^456";
       final String boomi = "false";
   
       given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
   
       final SiteOneQuoteDetailResponseData mockQuoteDetails = new SiteOneQuoteDetailResponseData();
       mockQuoteDetails.setLastApproverEmail("junerune@siteone.com");
   
       final SiteOneQuoteDetails detail1 = new SiteOneQuoteDetails();
       detail1.setQuoteDetailID(191585608);
       detail1.setItemNumber("PT-SPF-ELB50");
   
       final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
       listDetails.add(detail1); 
   
       mockQuoteDetails.setDetails(listDetails);
   
       given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(boomi), quoteHeaderID)).willReturn(mockQuoteDetails);
       given(customerFacade.getCurrentCustomer()).willReturn(custData);
   
       HttpServletRequest mockRequest = mock(HttpServletRequest.class);
       ServletRequestAttributes mockRequestAttributes = mock(ServletRequestAttributes.class);
       
       when(mockRequest.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
       when(mockRequestAttributes.getRequest()).thenReturn(mockRequest);
       RequestContextHolder.setRequestAttributes(mockRequestAttributes);
   
       defaultSiteoneQuotesFacade.updateQuoteDetail(quoteHeaderID, productList);
       
       Assert.assertNotNull(mockQuoteDetails.getLastApproverEmail());
       Assert.assertEquals(custData.getUid(),mockQuoteDetails.getLastApproverEmail());
   
    }
   
   @Test
   public void testQuoteLanding1() 
   {
   	List<QuotesData> quotesData = new ArrayList<QuotesData>();
   	final SiteoneQuotesRequestData quotesRequest = new SiteoneQuotesRequestData();
   	final B2BUnitModel defaultUnit = new B2BUnitModel();
   	final List<SiteoneQuotesSortRequestData> sortList = new ArrayList();
		final SiteoneQuotesSortRequestData sort = new SiteoneQuotesSortRequestData();
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		defaultUnit.setUid("2060119_US");
		quotesRequest.setCustomerNumber(defaultUnit.getUid());  
		quotesRequest.setIncludeDetails(false);	
		quotesRequest.setEndDate(now.plusDays(2).toString());
		quotesRequest.setStartDate(now.minusMonths(6).toString());
		quotesRequest.setLimit(250);
		quotesRequest.setSkip(0);
		quotesRequest.setApprovalStatus("Open");
		quotesRequest.setExpired(false);
		sort.setFieldName("LastModfDate");
		sort.setAscending(false);
		sortList.add(sort);
		quotesRequest.setSort(sortList);
		final List<SiteOneQuotesListResponseData> listOfQR = new ArrayList<>();
		final SiteOneQuotesListResponseData quotesResponse = new SiteOneQuotesListResponseData();
		quotesResponse.setQuoteHeaderID(Integer.valueOf(7300963));
		quotesResponse.setJobDescription("Celina, TX");
		quotesResponse.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotesResponse.setJobStartDate("2025-01-02T00:00:00");
		quotesResponse.setLastModfDate("2025-02-02T00:00:00");
		quotesResponse.setExpirationDate("2025-12-06T00:00:00");
		quotesResponse.setStatus("Bidding");
		quotesResponse.setIsFullApproval(false);
		quotesResponse.setNotes(null);
		quotesResponse.setCustomerName("C&B Landscape Management LLC");
		quotesResponse.setCustomerName("2060119");
		quotesResponse.setAccountManager("Edward");
		quotesResponse.setAccountManagerEmail("edward@siteone.com");
		quotesResponse.setBranchManager("Arthur");
		quotesResponse.setBranchManagerEmail("arthur@siteone.com");		
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);
		quotesResponse.setDetails(listDetails);
		listOfQR.add(quotesResponse);
		
		final List<String> customerNum = new ArrayList<>(Arrays.asList(quotesRequest.getCustomerNumber()));
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate(now.plusDays(2).toString());
		shiptoRequest.setStartDate(now.minusMonths(6).toString());
		shiptoRequest.setLimit(250);
		shiptoRequest.setSkip(0);
		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(false);
		final QuotesData quoteCountData = new QuotesData();
		quoteCountData.setQuoteCount(250);
		quoteCountData.setPageSize(25);
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
		shiptoQuoteData.setCustomerNumber("2060119");
		shiptoQuoteData.setCustomerName("C&B Landscape Management LLC");
		shiptoQuoteData.setQuoteCount(Integer.valueOf(150));
		shiptoRespData.add(shiptoQuoteData);
		
		given(siteOneQuotesWebService.getQuotes(quotesRequest)).willReturn(listOfQR);
		given(siteOneQuotesWebService.shiptoQuote(shiptoRequest)).willReturn(shiptoRespData);
		given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		
		assertNotNull(listOfQR);
		assertNotNull(shiptoRespData);
   }
   
   @Test
   public void testQuoteLanding2() 
   {
   	List<QuotesData> quotesData = new ArrayList<QuotesData>();
   	final SiteoneQuotesRequestData quotesRequest = new SiteoneQuotesRequestData();
   	final B2BUnitModel defaultUnit = new B2BUnitModel();
   	final List<SiteoneQuotesSortRequestData> sortList = new ArrayList();
		final SiteoneQuotesSortRequestData sort = new SiteoneQuotesSortRequestData();
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		defaultUnit.setUid("2060119_US");
		quotesRequest.setCustomerNumber(defaultUnit.getUid());  
		quotesRequest.setIncludeDetails(false);	
		quotesRequest.setEndDate(now.plusDays(2).toString());
		quotesRequest.setStartDate(now.minusMonths(6).toString());
		quotesRequest.setLimit(250);
		quotesRequest.setSkip(0);
		quotesRequest.setApprovalStatus("Open");
		quotesRequest.setExpired(false);
		sort.setFieldName("LastModfDate");
		sort.setAscending(false);
		sortList.add(sort);
		quotesRequest.setSort(sortList);
		final List<SiteOneQuotesListResponseData> listOfQR = new ArrayList<>();
		final SiteOneQuotesListResponseData quotesResponse = new SiteOneQuotesListResponseData();
		quotesResponse.setQuoteHeaderID(Integer.valueOf(7300963));
		quotesResponse.setJobDescription("Celina, TX");
		quotesResponse.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotesResponse.setJobStartDate("2025-01-02T00:00:00");
		quotesResponse.setLastModfDate("2025-02-02T00:00:00");
		quotesResponse.setExpirationDate("2025-12-06T00:00:00");
		quotesResponse.setStatus("Bidding");
		quotesResponse.setIsFullApproval(false);
		quotesResponse.setNotes(null);
		quotesResponse.setCustomerName("C&B Landscape Management LLC");
		quotesResponse.setCustomerName("2060119");
		quotesResponse.setAccountManager("Edward");
		quotesResponse.setAccountManagerEmail("edward@siteone.com");
		quotesResponse.setBranchManager("Arthur");
		quotesResponse.setBranchManagerEmail("arthur@siteone.com");		
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);
		quotesResponse.setDetails(listDetails);
		listOfQR.add(quotesResponse);
		
		final List<String> customerNum = new ArrayList<>(Arrays.asList(quotesRequest.getCustomerNumber()));
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate(now.plusDays(2).toString());
		shiptoRequest.setStartDate(now.minusMonths(6).toString());
		shiptoRequest.setLimit(250);
		shiptoRequest.setSkip(0);
		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(false);
		final QuotesData quoteCountData = new QuotesData();
		quoteCountData.setQuoteCount(250);
		quoteCountData.setPageSize(25);
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
		shiptoQuoteData.setCustomerNumber("2060119");
		shiptoQuoteData.setCustomerName("C&B Landscape Management LLC");
		shiptoQuoteData.setQuoteCount(Integer.valueOf(150));
		shiptoRespData.add(shiptoQuoteData);
		
		given(siteOneQuotesWebService.getQuotes(quotesRequest)).willReturn(listOfQR);
		given(siteOneQuotesWebService.shiptoQuote(shiptoRequest)).willReturn(shiptoRespData);
		given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		
		assertNotNull(listOfQR.get(0).getLastModfDate());
   }
   
   @Test
   public void testQuoteLanding3() 
   {
   	List<QuotesData> quotesData = new ArrayList<QuotesData>();
   	final SiteoneQuotesRequestData quotesRequest = new SiteoneQuotesRequestData();
   	final B2BUnitModel defaultUnit = new B2BUnitModel();
   	final List<SiteoneQuotesSortRequestData> sortList = new ArrayList();
		final SiteoneQuotesSortRequestData sort = new SiteoneQuotesSortRequestData();
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		defaultUnit.setUid("2060119_US");
		quotesRequest.setCustomerNumber(defaultUnit.getUid());  
		quotesRequest.setIncludeDetails(false);	
		quotesRequest.setEndDate(now.plusDays(2).toString());
		quotesRequest.setStartDate(now.minusMonths(6).toString());
		quotesRequest.setLimit(250);
		quotesRequest.setSkip(0);
		quotesRequest.setExpired(true);
		sort.setFieldName("LastModfDate");
		sort.setAscending(false);
		sortList.add(sort);
		quotesRequest.setSort(sortList);
		final List<SiteOneQuotesListResponseData> listOfQR = new ArrayList<>();
		final SiteOneQuotesListResponseData quotesResponse = new SiteOneQuotesListResponseData();
		quotesResponse.setQuoteHeaderID(Integer.valueOf(7300963));
		quotesResponse.setJobDescription("Celina, TX");
		quotesResponse.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotesResponse.setJobStartDate("2025-01-02T00:00:00");
		quotesResponse.setLastModfDate("2025-02-02T00:00:00");
		quotesResponse.setExpirationDate("2025-12-06T00:00:00");
		quotesResponse.setStatus("Bidding");
		quotesResponse.setIsFullApproval(false);
		quotesResponse.setNotes(null);
		quotesResponse.setCustomerName("C&B Landscape Management LLC");
		quotesResponse.setCustomerName("2060119");
		quotesResponse.setAccountManager("Edward");
		quotesResponse.setAccountManagerEmail("edward@siteone.com");
		quotesResponse.setBranchManager("Arthur");
		quotesResponse.setBranchManagerEmail("arthur@siteone.com");		
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);
		quotesResponse.setDetails(listDetails);
		listOfQR.add(quotesResponse);
		
		final List<String> customerNum = new ArrayList<>(Arrays.asList(quotesRequest.getCustomerNumber()));
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate(now.plusDays(2).toString());
		shiptoRequest.setStartDate(now.minusMonths(6).toString());
		shiptoRequest.setLimit(250);
		shiptoRequest.setSkip(0);
		shiptoRequest.setExpired(true);
		final QuotesData quoteCountData = new QuotesData();
		quoteCountData.setQuoteCount(150);
		quoteCountData.setPageSize(25);
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
		shiptoQuoteData.setCustomerNumber("2060119");
		shiptoQuoteData.setCustomerName("C&B Landscape Management LLC");
		shiptoQuoteData.setQuoteCount(Integer.valueOf(150));
		shiptoRespData.add(shiptoQuoteData);
		
		given(siteOneQuotesWebService.getQuotes(quotesRequest)).willReturn(listOfQR);
		given(siteOneQuotesWebService.shiptoQuote(shiptoRequest)).willReturn(shiptoRespData);
		given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		
		assertNotNull(shiptoRespData.get(0).getQuoteCount());
		assertEquals(quoteCountData.getQuoteCount(), shiptoRespData.get(0).getQuoteCount());
   }
   
   @Test
   public void testQuoteLandingWhenToggleAllNullChecks() 
   {
   	List<QuotesData> quotesData = new ArrayList<QuotesData>();
   	final SiteoneQuotesRequestData quotesRequest = new SiteoneQuotesRequestData();
   	final B2BUnitModel defaultUnit = new B2BUnitModel();
   	final List<SiteoneQuotesSortRequestData> sortList = new ArrayList();
		final SiteoneQuotesSortRequestData sort = new SiteoneQuotesSortRequestData();
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		defaultUnit.setUid("2060119_US");
		quotesRequest.setCustomerNumber(defaultUnit.getUid());  
		quotesRequest.setIncludeDetails(false);	
		quotesRequest.setEndDate(now.plusDays(2).toString());
		quotesRequest.setStartDate(now.minusMonths(6).toString());
		quotesRequest.setLimit(250);
		quotesRequest.setSkip(0);
		quotesRequest.setApprovalStatus("All");
		quotesRequest.setExpired(false);
		sort.setFieldName("LastModfDate");
		sort.setAscending(false);
		sortList.add(sort);
		quotesRequest.setSort(sortList);
		final List<SiteOneQuotesListResponseData> listOfQR = new ArrayList<>();
		final SiteOneQuotesListResponseData quotesResponse = new SiteOneQuotesListResponseData();
		quotesResponse.setQuoteHeaderID(Integer.valueOf(7300963));
		quotesResponse.setJobDescription("Celina, TX");
		quotesResponse.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotesResponse.setJobStartDate("2025-01-02T00:00:00");
		quotesResponse.setLastModfDate("2025-02-02T00:00:00");
		quotesResponse.setExpirationDate("2025-12-06T00:00:00");
		quotesResponse.setStatus("Bidding");
		quotesResponse.setIsFullApproval(false);
		quotesResponse.setNotes(null);
		quotesResponse.setCustomerName("C&B Landscape Management LLC");
		quotesResponse.setCustomerName("2060119");
		quotesResponse.setAccountManager("Edward");
		quotesResponse.setAccountManagerEmail("edward@siteone.com");
		quotesResponse.setBranchManager("Arthur");
		quotesResponse.setBranchManagerEmail("arthur@siteone.com");		
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);
		quotesResponse.setDetails(listDetails);
		listOfQR.add(quotesResponse);
		
		final List<String> customerNum = new ArrayList<>(Arrays.asList(quotesRequest.getCustomerNumber()));
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate(now.plusDays(2).toString());
		shiptoRequest.setStartDate(now.minusMonths(6).toString());
		shiptoRequest.setLimit(250);
		shiptoRequest.setSkip(0);
		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(false);
		final QuotesData quoteCountData = new QuotesData();
		quoteCountData.setQuoteCount(250);
		quoteCountData.setPageSize(25);
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
		shiptoQuoteData.setCustomerNumber("2060119");
		shiptoQuoteData.setCustomerName("C&B Landscape Management LLC");
		shiptoQuoteData.setQuoteCount(Integer.valueOf(150));
		shiptoRespData.add(shiptoQuoteData);
		
		given(siteOneQuotesWebService.getQuotes(quotesRequest)).willReturn(listOfQR);
		given(siteOneQuotesWebService.shiptoQuote(shiptoRequest)).willReturn(shiptoRespData);
		given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		
		assertNotNull(listOfQR);
		assertNotNull(shiptoRespData);
   }
   
   @Test
   public void testQuoteLandingWhenToggleAll() 
   {
   	List<QuotesData> quotesData = new ArrayList<QuotesData>();
   	final SiteoneQuotesRequestData quotesRequest = new SiteoneQuotesRequestData();
   	final B2BUnitModel defaultUnit = new B2BUnitModel();
   	final List<SiteoneQuotesSortRequestData> sortList = new ArrayList();
		final SiteoneQuotesSortRequestData sort = new SiteoneQuotesSortRequestData();
		final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		defaultUnit.setUid("2060119_US");
		quotesRequest.setCustomerNumber(defaultUnit.getUid());  
		quotesRequest.setIncludeDetails(false);	
		quotesRequest.setEndDate(now.plusDays(2).toString());
		quotesRequest.setStartDate(now.minusMonths(6).toString());
		quotesRequest.setLimit(250);
		quotesRequest.setSkip(0);
		quotesRequest.setApprovalStatus("All");
		quotesRequest.setExpired(false);
		sort.setFieldName("LastModfDate");
		sort.setAscending(false);
		sortList.add(sort);
		quotesRequest.setSort(sortList);
		final List<SiteOneQuotesListResponseData> listOfQR = new ArrayList<>();
		final SiteOneQuotesListResponseData quotesResponse = new SiteOneQuotesListResponseData();
		quotesResponse.setQuoteHeaderID(Integer.valueOf(7300963));
		quotesResponse.setJobDescription("Celina, TX");
		quotesResponse.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotesResponse.setJobStartDate("2025-01-02T00:00:00");
		quotesResponse.setLastModfDate("2025-02-02T00:00:00");
		quotesResponse.setExpirationDate("2025-12-06T00:00:00");
		quotesResponse.setStatus("Bidding");
		quotesResponse.setIsFullApproval(false);
		quotesResponse.setNotes(null);
		quotesResponse.setCustomerName("C&B Landscape Management LLC");
		quotesResponse.setCustomerName("2060119");
		quotesResponse.setAccountManager("Edward");
		quotesResponse.setAccountManagerEmail("edward@siteone.com");
		quotesResponse.setBranchManager("Arthur");
		quotesResponse.setBranchManagerEmail("arthur@siteone.com");		
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		listDetails.add(detail);
		quotesResponse.setDetails(listDetails);
		listOfQR.add(quotesResponse);
		
		final List<String> customerNum = new ArrayList<>(Arrays.asList(quotesRequest.getCustomerNumber()));
		final SiteOneQuoteShiptoRequestData shiptoRequest = new SiteOneQuoteShiptoRequestData();
		shiptoRequest.setCustomerNumbers(customerNum);
		shiptoRequest.setShowOnline(Boolean.TRUE);
		shiptoRequest.setEndDate(now.plusDays(2).toString());
		shiptoRequest.setStartDate(now.minusMonths(6).toString());
		shiptoRequest.setLimit(250);
		shiptoRequest.setSkip(0);
		shiptoRequest.setApprovalStatus("Open");
		shiptoRequest.setExpired(false);
		final QuotesData quoteCountData = new QuotesData();
		quoteCountData.setQuoteCount(150);
		quoteCountData.setPageSize(25);
		final List<SiteOneQuoteShiptoResponseData> shiptoRespData = new ArrayList<>();
		final SiteOneQuoteShiptoResponseData shiptoQuoteData = new SiteOneQuoteShiptoResponseData();
		shiptoQuoteData.setCustomerNumber("2060119");
		shiptoQuoteData.setCustomerName("C&B Landscape Management LLC");
		shiptoQuoteData.setQuoteCount(Integer.valueOf(150));
		shiptoRespData.add(shiptoQuoteData);
		
		given(siteOneQuotesWebService.getQuotes(quotesRequest)).willReturn(listOfQR);
		given(siteOneQuotesWebService.shiptoQuote(shiptoRequest)).willReturn(shiptoRespData);
		given(((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		
		assertNotNull(shiptoRespData.get(0).getQuoteCount());
		assertEquals(quoteCountData.getQuoteCount(), shiptoRespData.get(0).getQuoteCount());
   }
   
   @Test
	public void testDetailLvlLastModfDate() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		detail.setLastModfDate("2025-12-06T00:00:00");		
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setLastModfDate("2025-12-06T00:00:00");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("2060119");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7300963));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer())).willReturn(null);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		assertNotNull(actual);
		assertNotNull(quotedetails);
	}
   
   @Test
	public void testDetailLvlLastModfDateWhenNull() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		detail.setLastModfDate(null);		
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setLastModfDate("2025-12-06T00:00:00");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("2060119");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate(null);
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7300963));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		given(((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer())).willReturn(null);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		assertNotNull(quotedetails);
		assertNull(quotedetails.getLastModfDate());
	}
   
   @Test
	public void testDetailWhenMainAccountNumberMatches() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119");
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		detail.setLastModfDate("2025-12-06T00:00:00");		
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setLastModfDate("2025-12-06T00:00:00");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("2060119");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7768295));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		assertNotNull(actual);
		assertNotNull(quotedetails);
		assertNotNull(actual.getJobName());
	}
   
   @Test
	public void testDetailWhenMainAccountNumberNotMatches() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119");
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		detail.setLastModfDate("2025-12-06T00:00:00");		
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setLastModfDate("2025-12-06T00:00:00");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("17058");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7768295));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		assertNull(actual.getDateSubmitted());
		assertNull(actual.getJobName());
	}
   
   @Test
	public void testDetailWhenShipToAccountNumberMatches() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119");
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		detail.setLastModfDate("2025-12-06T00:00:00");		
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setLastModfDate("2025-12-06T00:00:00");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("2060119-3002");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7768295));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		assertNotNull(actual.getDateSubmitted());
		assertNotNull(actual.getJobName());
	}
   
   @Test
	public void testDetailWhenShipToAccountNumberNotMatches() throws ParseException
	{
		final SiteOneQuoteDetailResponseData quotedetails = new SiteOneQuoteDetailResponseData();
		final String quoteHeaderId = "7300963";
		final SiteOneQuoteDetails detail = new SiteOneQuoteDetails();
		final B2BUnitData defaultUnit = new B2BUnitData();
		defaultUnit.setUid("2060119-3002");
		final List<SiteOneQuoteDetails> listDetails = new ArrayList<>();
		detail.setItemNumber("PT-SPF-ELB50");
		detail.setItemDescription("Pro - Trade Spiral Barb Elbow 1/2 in. Mipt X 1/2 in. Barb (20 Pc Sold by the Bag)");
		detail.setLineNumber(Integer.valueOf(1));
		detail.setQuoteDetailID(Integer.valueOf(186120464));
		detail.setInventoryUOM("Each");
		detail.setApprovalDate(null);
		detail.setQuantity(Double.valueOf(2.0));
		detail.setUnitPrice(Double.valueOf(150.00));
		detail.setExtendedPrice(Double.valueOf(300.00));
		detail.setNotes(null);
		detail.setSKUID(Integer.valueOf(98222));
		detail.setLastModfDate("2025-12-06T00:00:00");		
		listDetails.add(detail);

		quotedetails.setAccountManager("Edward");
		quotedetails.setAccountManagerEmail("edward@siteone.com");
		quotedetails.setAccountManagerID(Integer.valueOf(172435));
		quotedetails.setApprovalStatus(Integer.valueOf(0));
		quotedetails.setBranchManager("Arthur");
		quotedetails.setBranchManagerEmail("arthur@siteone.com");
		quotedetails.setBranchManagerID("152637");
		quotedetails.setBranchManagerNodeID("1673892");
		quotedetails.setBranchNumber("172");
		quotedetails.setCreateDate("2024-10-25T09:25:36.86");
		quotedetails.setLastModfDate("2025-12-06T00:00:00");
		quotedetails.setCreateID(Integer.valueOf(108242));
		quotedetails.setCustomerNumber("17058");
		quotedetails.setCustTreeNodeID("16273849");
		quotedetails.setDeleted(Boolean.FALSE);
		quotedetails.setDeliveryCity(null);
		quotedetails.setDeliveryState(null);
		quotedetails.setDeliveryStreet(null);
		quotedetails.setDeliveryZip(null);
		quotedetails.setDivisionCode("1");
		quotedetails.setDivisionID(Integer.valueOf(1));
		quotedetails.setDueDate("2024-10-25T00:00:00");
		quotedetails.setExpectedAwardDate(null);
		quotedetails.setExpirationDate("2024-12-06T00:00:00");
		quotedetails.setHighmarkNumber(null);
		quotedetails.setId(null);
		quotedetails.setJobDescription("Celina, TX");
		quotedetails.setJobName("Walmart Supercenter #1250 New Store - Celina, TX");
		quotedetails.setJobStartDate("2024-11-02T00:00:00");
		quotedetails.setLastModfDate("2024-11-02T00:00:00");
		quotedetails.setLastModfID(Integer.valueOf(163745));
		quotedetails.setNotes(null);
		quotedetails.setParentQuoteHeaderID(null);
		quotedetails.setPricer("Aiden");
		quotedetails.setPricerEmail("aiden@siteone.com");
		quotedetails.setPricerID(null);
		quotedetails.setPricerNodeID(null);
		quotedetails.setProductType("Irrigation");
		quotedetails.setQuoteHeaderID(Integer.valueOf(7768295));
		quotedetails.setQuoteProductTypeID(null);
		quotedetails.setQuoteTypeID(null);
		quotedetails.setShipToAddressID(null);
		quotedetails.setShowOnline(Boolean.TRUE);
		quotedetails.setStatus("Bidding");
		quotedetails.setSupplyChainNodeID(Integer.valueOf(11094));
		quotedetails.setTotalQuoteAmt(Integer.valueOf(1548));
		quotedetails.setType(null);
		quotedetails.setWriter("cole");
		quotedetails.setWriterEmail("colemgrath@siteone.com");
		quotedetails.setWriterID(null);
		quotedetails.setWriterNodeID(null);
		quotedetails.setDetails(listDetails);
		final String boomi = "false";
		given(siteOneQuotesWebService.getQuotesDetails(Boolean.parseBoolean(any()), any())).willReturn(quotedetails);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi);
		BaseSiteModel baseSiteModel = mock(BaseSiteModel.class);
		given(baseSiteModel.getUid()).willReturn("siteone-us");
		given(baseSiteService.getCurrentBaseSite()).willReturn(baseSiteModel);
		given(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()).willReturn(defaultUnit);
		given(storeFinderFacade.getStoreForId(quotedetails.getBranchNumber())).willReturn(null);
		final QuoteDetailsData actual = defaultSiteoneQuotesFacade.getQuoteDetails(quoteHeaderId);
		assertNull(actual.getDateSubmitted());
		assertNull(actual.getJobName());
	}
      
   @Test
	public void testQuoteApprovalHistory1() throws ParseException
	{
   	
		final List<QuoteApprovalHistoryResponseData> historyRespData = new ArrayList<>();
		
		final QuoteApprovalHistoryResponseData approvalHistoryRespData = new QuoteApprovalHistoryResponseData();
		approvalHistoryRespData.setQuoteDetailID("1005128742");
		approvalHistoryRespData.setApprovalDate("2025-05-26T04:00:00.559");
		approvalHistoryRespData.setApprovedQty(Integer.valueOf(6));
		historyRespData.add(approvalHistoryRespData);
		final String boomi = "false";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi.toString());
		given(siteOneQuotesWebService.quoteApprovalHistory(Boolean.parseBoolean(any()), any())).willReturn(historyRespData);
		final QuoteApprovalItemData actual = defaultSiteoneQuotesFacade.quoteApprovalHistory("1005128742");
		assertNotNull(actual);
		assertNotNull(actual.getItemDetails());
	   assertEquals(1, actual.getItemDetails().size());
	   assertEquals("1005128742", actual.getItemDetails().get(0).getQuoteDetailID());
	   assertEquals(6, (int) actual.getItemDetails().get(0).getApprovedQty());
	   
   }
   
   @Test
	public void testQuoteApprovalHistory2() throws ParseException
	{
   	//Test with empty response
		final List<QuoteApprovalHistoryResponseData> historyRespData = new ArrayList<>();
		
		final QuoteApprovalHistoryResponseData approvalHistoryRespData = new QuoteApprovalHistoryResponseData();
		approvalHistoryRespData.setQuoteDetailID("1005128742");
		approvalHistoryRespData.setApprovalDate("2025-05-26T04:00:00.559");
		approvalHistoryRespData.setApprovedQty(Integer.valueOf(6));
		historyRespData.add(approvalHistoryRespData);
		final String boomi = "false";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi.toString());
		given(siteOneQuotesWebService.quoteApprovalHistory(Boolean.parseBoolean(any()), any())).willReturn(Collections.emptyList());
		final QuoteApprovalItemData actual = defaultSiteoneQuotesFacade.quoteApprovalHistory("someId");
		assertNotNull(actual);
		assertNotNull(actual.getItemDetails());
	   assertTrue(actual.getItemDetails().isEmpty());
	   
   }
   
   @Test
	public void testQuoteApprovalHistory3() throws ParseException
	{
   	//Test with null response
		final List<QuoteApprovalHistoryResponseData> historyRespData = new ArrayList<>();
		
		final QuoteApprovalHistoryResponseData approvalHistoryRespData = new QuoteApprovalHistoryResponseData();
		approvalHistoryRespData.setQuoteDetailID("1005128742");
		approvalHistoryRespData.setApprovalDate("2025-05-26T04:00:00.559");
		approvalHistoryRespData.setApprovedQty(Integer.valueOf(6));
		historyRespData.add(approvalHistoryRespData);
		final String boomi = "false";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi.toString());
		given(siteOneQuotesWebService.quoteApprovalHistory(Boolean.parseBoolean(any()), any())).willReturn(null);
		final QuoteApprovalItemData actual = defaultSiteoneQuotesFacade.quoteApprovalHistory("someId");
		assertNotNull(actual);
		assertNotNull(actual.getItemDetails());
	   assertTrue(actual.getItemDetails().isEmpty());
	   
   }
   
   @Test
	public void testQuoteApprovalHistory4() throws ParseException
	{
   	//Test entry with missing field
		final List<QuoteApprovalHistoryResponseData> historyRespData = new ArrayList<>();
		
		final QuoteApprovalHistoryResponseData approvalHistoryRespData = new QuoteApprovalHistoryResponseData();
		approvalHistoryRespData.setQuoteDetailID("1005128742");
		approvalHistoryRespData.setApprovalDate(null);
		approvalHistoryRespData.setApprovedQty(Integer.valueOf(6));
		historyRespData.add(approvalHistoryRespData);
		final String boomi = "false";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi.toString());
		given(siteOneQuotesWebService.quoteApprovalHistory(Boolean.parseBoolean(any()), any())).willReturn(historyRespData);
		final QuoteApprovalItemData actual = defaultSiteoneQuotesFacade.quoteApprovalHistory("1005128742");
		assertNotNull(actual);
		assertEquals(1, actual.getItemDetails().size());
	   assertNull(actual.getItemDetails().get(0).getApprovalDate());
	   
   }
   
   @Test
	public void testQuoteApprovalHistory5() throws ParseException
	{
   	//Test entry with approvedQty as null
		final List<QuoteApprovalHistoryResponseData> historyRespData = new ArrayList<>();
		
		final QuoteApprovalHistoryResponseData approvalHistoryRespData = new QuoteApprovalHistoryResponseData();
		approvalHistoryRespData.setQuoteDetailID("1005128742");
		approvalHistoryRespData.setApprovalDate("2025-05-26T04:00:00.559");
		approvalHistoryRespData.setApprovedQty(null);
		historyRespData.add(approvalHistoryRespData);
		final String boomi = "false";
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("BOOMI_PLATFORM")).willReturn(boomi.toString());
		given(siteOneQuotesWebService.quoteApprovalHistory(Boolean.parseBoolean(any()), any())).willReturn(historyRespData);
		final QuoteApprovalItemData actual = defaultSiteoneQuotesFacade.quoteApprovalHistory("1005128742");
		assertNotNull(actual);
		assertEquals(1, actual.getItemDetails().size());
	   assertNull(actual.getItemDetails().get(0).getApprovedQty());
	   
   }

}
