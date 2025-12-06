/**
 *
 */
package com.siteone.facades.checkout.impl;

import static org.mockito.BDDMockito.given;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.mockito.InjectMocks;

import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;

import com.siteone.core.store.services.SiteOneStoreFinderService;

import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 *
 */

@UnitTest
public class DefaultSiteOneB2BCheckoutFacadeTest
{

	@Mock
	private DefaultSiteOneB2BCheckoutFacade defaultSiteOneB2BCheckoutFacade;

	@Mock
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Mock
	private SiteOneStoreFinderService storeFinderService;

	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Mock
	private UserFacade userFacade;

	@Mock
	private UserService userService;

	@Mock
	private CartService cartService;
	
	@Mock
	private PointOfServiceData sessionStore;


	@Mock
	private PointOfServiceModel pos;
	
	@InjectMocks
	private DefaultSiteOneB2BCheckoutFacade checkoutFacade;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		defaultSiteOneB2BCheckoutFacade = new DefaultSiteOneB2BCheckoutFacade();
		defaultSiteOneB2BCheckoutFacade.setUserFacade(userFacade);
		defaultSiteOneB2BCheckoutFacade.setStoreSessionFacade(storeSessionFacade);
		defaultSiteOneB2BCheckoutFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
		defaultSiteOneB2BCheckoutFacade.setUserService(userService);
		defaultSiteOneB2BCheckoutFacade.setCartService(cartService);
	}

	@Test
	public void testSetMixedCartOrderType()
	{
		final CartModel model = new CartModel();
		final PointOfServiceData homestore = new PointOfServiceData();
		homestore.setStoreId("172");
		List<String> hubstores = new ArrayList<>();
		hubstores.add("636");
		homestore.setHubStores(hubstores);
		storeSessionFacade.setSessionStore(homestore);

		final List<AbstractOrderEntryModel> modelEntries = new ArrayList<>();
		final String dcbranches = "636|6.99";

		final AbstractOrderEntryModel entry_1 = new AbstractOrderEntryModel();
		entry_1.setIsShippingOnlyProduct(Boolean.TRUE);
		modelEntries.add(entry_1);
		final AbstractOrderEntryModel entry_2 = new AbstractOrderEntryModel();
		entry_2.setIsShippingOnlyProduct(Boolean.FALSE);
		modelEntries.add(entry_2);
		model.setEntries(modelEntries);
		model.setOrderType(OrderTypeEnum.PARCEL_SHIPPING);

		final String featureSwitch = "172";
		given(userFacade.isAnonymousUser()).willReturn(Boolean.FALSE);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("SplitMixedPickupBranches")).willReturn(featureSwitch);
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("DCShippingFeeBranches")).willReturn(dcbranches);

		defaultSiteOneB2BCheckoutFacade.setMixedCartOrderType(model);
		String homestorenumber = model.getHomeStoreNumber();
		Assert.assertEquals(model.getOrderType(),OrderTypeEnum.PARCEL_SHIPPING);
		
		
	}

	@Test
	public void testPopulateLOBList()
	{
		final List<OrderEntryData> entries = new ArrayList<>();
		final OrderEntryData entry1 = new OrderEntryData();
		final ProductData prod1 = new ProductData();
		prod1.setLevel1Category("Maintenance");
		entry1.setProduct(prod1);
		entries.add(entry1);
		final OrderEntryData entry2 = new OrderEntryData();
		final ProductData prod2 = new ProductData();
		prod2.setLevel1Category("Irrigation");
		entry2.setProduct(prod2);
		entries.add(entry2);

		final HashSet<String> actualLobSet = defaultSiteOneB2BCheckoutFacade.populateLOBList(entries);

		final HashSet<String> expectedLobSet = new HashSet<>();
		expectedLobSet.add("Agronomics");
		expectedLobSet.add("Irrigation");

		Assert.assertEquals(expectedLobSet, actualLobSet);

	}

	@Test
	public void testGetPageTitle()
	{
		final CartModel model = new CartModel();
		model.setOrderType(OrderTypeEnum.PICKUP);

		final CartData data = new CartData();

		given(cartService.getSessionCart()).willReturn(model);

		final String expectedTitle = "Pick-up Contact Information";
		final String actualTitle = defaultSiteOneB2BCheckoutFacade.getPageTitle(data);

		Assert.assertEquals(expectedTitle, actualTitle);

	}

	@Test
	public void testIsShippingStateValid()
	{
		final PointOfServiceData pos = new PointOfServiceData();
		final List<String> hubstores = new ArrayList<>();
		hubstores.add("8200");
		pos.setHubStores(hubstores);
		final String state = "FL";

		final String featureSwitch1 = "5,7,406,131,739";
		final Map<String, String> featureswitch2 = new HashMap<>();
		featureswitch2.put("8201", "6.99");
		featureswitch2.put("8200", "0.00");
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("ShippingandDeliveryFeeBranches")).willReturn(featureSwitch1);
		given(siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping("DCShippingFeeBranches")).willReturn(featureswitch2);

		final boolean expected = true;
		final boolean actual = defaultSiteOneB2BCheckoutFacade.isShippingStateValid(pos, state);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIsShippingStateValid2()
	{
		final PointOfServiceData pos = new PointOfServiceData();
		final List<String> hubstores = new ArrayList<>();
		hubstores.add("7");
		pos.setHubStores(hubstores);
		pos.setStoreId("7");
		final String state = "FL";

		final AddressData address = new AddressData();
		final RegionData region = new RegionData();
		region.setIsocodeShort("FL");
		address.setRegion(region);

		pos.setAddress(address);

		final String featureSwitch1 = "5,7,406,131,739";
		final Map<String, String> featureswitch2 = new HashMap<>();
		featureswitch2.put("8201", "6.99");
		featureswitch2.put("8200", "0.00");
		given(siteOneFeatureSwitchCacheService.getValueForSwitch("ShippingandDeliveryFeeBranches")).willReturn(featureSwitch1);
		given(siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping("DCShippingFeeBranches")).willReturn(featureswitch2);

		final boolean expected = true;
		final boolean actual = defaultSiteOneB2BCheckoutFacade.isShippingStateValid(pos, state);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIsPosValidForCheckout()
	{
		final CartData cart = new CartData();
		final boolean expected = false;
		final boolean actual = defaultSiteOneB2BCheckoutFacade.isPosValidForCheckout(cart);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIsPosValidForCheckout2()
	{
		final CartData cart = new CartData();
		final PointOfServiceData pos = new PointOfServiceData();
		pos.setStoreId("7");
		cart.setPointOfService(pos);

		given(storeSessionFacade.getSessionStore()).willReturn(pos);

		final boolean expected = true;
		final boolean actual = defaultSiteOneB2BCheckoutFacade.isPosValidForCheckout(cart);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIsPONumberRequired()
	{
		final B2BUnitData unit = new B2BUnitData();
		unit.setIsPONumberRequired(true);

		given(storeSessionFacade.getSessionShipTo()).willReturn(unit);

		Assert.assertTrue(defaultSiteOneB2BCheckoutFacade.isPONumberRequired());
	}

	@Test
	public void testIsPONumberRequired2()
	{
		final B2BUnitData unit = new B2BUnitData();
		unit.setIsPONumberRequired(false);

		given(storeSessionFacade.getSessionShipTo()).willReturn(unit);

		Assert.assertFalse(defaultSiteOneB2BCheckoutFacade.isPONumberRequired());
	}
	
	@Test
	public void testPopulateFreights()
	{
		final CartData data = new CartData();
		final Map<String, Boolean> fulfilmentStatus = new HashMap<>();
		fulfilmentStatus.put("free-standard-shipping", Boolean.FALSE);
		fulfilmentStatus.put("standard-net", Boolean.FALSE);
		data.setShippingFreight("35.00");
		data.setFreight("50.00");
		final PriceData totalPriceWithTax = new PriceData();
		totalPriceWithTax.setValue(BigDecimal.valueOf(185.76));
		data.setTotalPriceWithTax(totalPriceWithTax);
		final CartData result = defaultSiteOneB2BCheckoutFacade.populateFreights(data, fulfilmentStatus);
		final double actual = result.getTotalPriceWithTax().getValue().doubleValue();
		final double expected = 135.76d;
		final double delta = 0.05d;
		Assert.assertEquals(expected, actual, delta);
		
	}
	
	@Test
	public void testPopulateFreights2()
	{
		final CartData data = new CartData();
		final Map<String, Boolean> fulfilmentStatus = new HashMap<>();
		fulfilmentStatus.put("free-standard-shipping", Boolean.FALSE);
		fulfilmentStatus.put("standard-net", Boolean.TRUE);
		data.setShippingFreight("35.00");
		data.setFreight("50.00");
		final PriceData totalPriceWithTax = new PriceData();
		totalPriceWithTax.setValue(BigDecimal.valueOf(185.76));
		data.setTotalPriceWithTax(totalPriceWithTax);
		final CartData result = defaultSiteOneB2BCheckoutFacade.populateFreights(data, fulfilmentStatus);
		final double actual = result.getTotalPriceWithTax().getValue().doubleValue();
		final double expected = 185.76d;
		final double delta = 0.05d;
		Assert.assertEquals(expected, actual, delta);
		
	}
	
	@Test
	public void testGetStoreHolidayDates1()
	{
		final List<String> holidays = Arrays.asList("2025-12-25", "2025-12-31");


		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(sessionStore.getStoreId()).thenReturn("Store Id");
		when(storeFinderService.getStoreForId("Store Id")).thenReturn(pos);
		when(pos.getSiteoneHolidays()).thenReturn(holidays);


		final List<String> result = checkoutFacade.getStoreHolidayDates();


		assertEquals(holidays, result);
	}


	@Test
	public void testGetStoreHolidayDates2()
	{
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(sessionStore.getStoreId()).thenReturn("Store Id");
		when(storeFinderService.getStoreForId("Store Id")).thenReturn(pos);
		when(pos.getSiteoneHolidays()).thenReturn(null);


		final List<String> result = checkoutFacade.getStoreHolidayDates();


		assertNull(result);
	}
	
	
	
	@Test
	public void testPopulateFreights_deliverySubtracted() {
	    CartData cartData = new CartData();
	    cartData.setFreight("15.00");
	    PriceData priceData = new PriceData();
	    priceData.setValue(BigDecimal.valueOf(100.00));
	    cartData.setTotalPriceWithTax(priceData);

	    Map<String, Boolean> fulfilmentStatus = new HashMap<>();
	    fulfilmentStatus.put("delivery", false);

	    CartData result = defaultSiteOneB2BCheckoutFacade.populateFreights(cartData, fulfilmentStatus);

	    Assert.assertEquals(BigDecimal.valueOf(85.00), result.getTotalPriceWithTax().getValue());
	}

	@Test
	public void testPopulateFreights_noChange() {
	    CartData cartData = new CartData();
	    PriceData priceData = new PriceData();
	    priceData.setValue(BigDecimal.valueOf(100.00));
	    cartData.setTotalPriceWithTax(priceData);

	    Map<String, Boolean> fulfilmentStatus = new HashMap<>();
	    fulfilmentStatus.put("shipping", false);
	    fulfilmentStatus.put("delivery", true);

	    CartData result = defaultSiteOneB2BCheckoutFacade.populateFreights(cartData, fulfilmentStatus);

	    Assert.assertEquals(BigDecimal.valueOf(100.00), result.getTotalPriceWithTax().getValue());
	}

	



}
