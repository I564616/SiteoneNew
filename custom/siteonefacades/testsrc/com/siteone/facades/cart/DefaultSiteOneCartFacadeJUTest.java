/**
 *
 */
package com.siteone.facades.cart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commercefacades.bag.data.BagInfoData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.converters.populator.ProductPromotionsPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.commerceservices.order.CommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.promotions.PromotionsService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.BagInfoModel;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneCalculationService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.populators.SiteOneStockPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
@UnitTest
public class DefaultSiteOneCartFacadeJUTest
{

	@InjectMocks
	private DefaultSiteOneCartFacade siteoneCartFacade;
	//SiteOneStoreSessionFacade storeSessionFacade;
	//CartFacade cartFacade;

	@Mock
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Mock
	private SiteOneStoreFinderService storeFinderService;

	@Mock
	private SessionService sessionService;

	@Mock
	private ModelService modelService;

	@Mock
	private B2BUnitService b2bUnitService;

	@Mock
	private SiteOneProductUOMService siteOneProductUOMService;

	@Mock
	private ProductPromotionsPopulator siteOneProductPromotionPopulator;

	@Mock
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Mock
	private VoucherFacade voucherFacade;

	@Mock
	private SiteOneProductFacade siteOneProductFacade;

	@Mock
	private ProductService productService;

	@Mock
	private Converter<ProductModel, ProductData> productConverter;

	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Mock
	private SiteOneStockPopulator stockPopulator;

	@Mock
	private SiteOneProductService siteOneProductService;

	@Mock
	private SiteOneCalculationService calculationService;

	@Mock
	private SiteOneStockLevelService siteOneStockLevelService;

	@Mock
	private PromotionsService promotionsService;

	@Mock
	private TimeService timeService;

	@Mock
	private UserService userService;

	@Mock
	private BaseSiteService baseSiteService;

	@Mock
	private CartFacade cartFacade;

	@Mock
	private UserFacade userFacade;

	@Mock
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Mock
	private DeliveryModeService deliveryModeService;

	@Mock
	private StoreFinderFacade storeFinderFacade;

	@Mock
	private MessageSource messageSource;

	@Mock
	private I18NService i18nService;

	@Mock
	private CommonI18NService commonI18NService;

	@Mock
	private ConfigurationService configurationService;

	@Mock
	private PriceDataFactory priceDataFactory;

	@Mock
	private CartService cartService;

	@Mock
	private CommerceAddToCartStrategy commerceAddToCartStrategy;

	@Mock
	private CommerceCartService commerceCartService;

	@Mock
	private CommerceCommonI18NService commerceCommonI18NService;

	@Mock
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Mock
	private CartModel cart;

	@Mock
	private AbstractOrderEntryModel entry;

	@Mock
	private BagInfoModel bagInfo;

	/**
	 * @throws java.lang.Exception
	 *
	 */
	@BeforeClass
	public static void beforeClass()
	{
		final MockedStatic<Config> mockedConfig = mockStatic(Config.class);
		mockedConfig.when(() -> Config.getInt("currency.totalprice.digits", 2)).thenReturn(2);
		mockedConfig.when(() -> Config.getInt("currency.unitprice.digits", 3)).thenReturn(3);
	}

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		cart = new CartModel();
		entry = new AbstractOrderEntryModel();
		bagInfo = new BagInfoModel();
		entry.setProduct(new ProductModel());
		entry.setBigBagInfo(bagInfo);
		cart.setEntries(Arrays.asList(entry));
		cartService = mock(CartService.class);
		siteoneCartFacade = new DefaultSiteOneCartFacade();
		siteoneCartFacade.setCartService(cartService);
		siteoneCartFacade.setModelService(modelService);
		siteoneCartFacade.setStoreSessionFacade(storeSessionFacade);
		siteoneCartFacade.setSiteOneStoreFinderService(siteOneStoreFinderService);
		siteoneCartFacade.setProductService(productService);
	}

	@Test
	public void testUpdateBigBagInfo_WhenCartIsNull_ReturnsNull()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		ProductModel bgSize = new ProductModel();
		sessionStore.setStoreId("172");
		when(cartService.getSessionCart()).thenReturn(null);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		assertNull(siteoneCartFacade.updateBigBagInfo("98222", 10, 100.0, "Net Ton", true));
	}

	@Test
	public void testUpdateBigBagInfo_WhenEntryNotFound_ReturnsNull()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		ProductModel bgSize = new ProductModel();
		sessionStore.setStoreId("172");
		cart.setEntries(Arrays.asList());
		when(cartService.getSessionCart()).thenReturn(new CartModel());
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		assertNull(siteoneCartFacade.updateBigBagInfo("98222", 10, 100.0, "Cubic Yard", true));
	}

	@Test
	public void testUpdateBigBagInfo_WhenChecked()
	{		
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("15 lbs");
		upc.setInventoryUPCDesc("Cubic Yard");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId())).thenReturn(storeDetail);
		when(productService.getProductForCode(bgSize.getCode())).thenReturn(bgSize);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 10, 100.0, "Cubic Yard", true);
		assertNotNull(result);
		assertEquals("100.00", result.getUnitPrice());
		assertEquals(Integer.valueOf(10), result.getNumberOfBags());
		assertNotNull(result.getTotalPrice());
		assertEquals("1,000.00", result.getTotalPrice());
	}

	@Test
	public void testUpdateBigBagInfo_WhenUnchecked()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("15 lbs");
		upc.setInventoryUPCDesc("Net Ton");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 10, 100.0, "Net Ton", false);
		assertNotNull(result);
		assertNull(result.getNumberOfBags());
		assertNull(result.getTotalPrice());
	}
	
	@Test
	public void testUpdateBigBagInfo_BagCountNotNull()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("15 lbs");
		upc.setInventoryUPCDesc("Net Ton");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId())).thenReturn(storeDetail);
		when(productService.getProductForCode(bgSize.getCode())).thenReturn(bgSize);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 10, 100.0, "Net Ton", true);
		assertNotNull(result);
		assertNotNull(result.getNumberOfBags());
	}
	
	@Test
	public void testUpdateBigBagInfo_BagCountDefaultTo1()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("15 lbs");
		upc.setInventoryUPCDesc("Net Ton");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId())).thenReturn(storeDetail);
		when(productService.getProductForCode(bgSize.getCode())).thenReturn(bgSize);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 10, 100.0, "Cubic Yard", true);
		assertEquals(Integer.valueOf(1), result.getNumberOfBags());
	}
	
	@Test
	public void testUpdateBigBagInfo_BagCountLessThan2K()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("15 lbs");
		upc.setInventoryUPCDesc("Cubic Yard");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId())).thenReturn(storeDetail);
		when(productService.getProductForCode(bgSize.getCode())).thenReturn(bgSize);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 4, 100.0, "Cubic Yard", true);
		assertNotNull(result);
		assertEquals(Integer.valueOf(4), result.getNumberOfBags());
	}
	
	@Test
	public void testUpdateBigBagInfo_BagCountMoreThan2K()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("2350 lbs");
		upc.setInventoryUPCDesc("Cubic Yard");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId())).thenReturn(storeDetail);
		when(productService.getProductForCode(bgSize.getCode())).thenReturn(bgSize);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 4, 100.0, "Cubic Yard", true);
		assertNotNull(result);
		assertNotEquals(Integer.valueOf(4), result.getNumberOfBags());
	}
	
	@Test
	public void testUpdateBigBagInfo_BagCountMoreThan2KWithCorrectValue()
	{
		PointOfServiceData sessionStore = new PointOfServiceData();
		PointOfServiceModel storeDetail = new PointOfServiceModel();
		sessionStore.setStoreId("172");
		List<InventoryUPCModel> listUpc = new ArrayList();
		InventoryUPCModel upc = new InventoryUPCModel();
		upc.setWeight("2350 lbs");
		upc.setInventoryUPCDesc("Cubic Yard");
		listUpc.add(upc);
		ProductModel bgSize = new ProductModel();
		bgSize.setProductShortDesc("Net 1.0 Ton");
		bgSize.setUpcData(listUpc);
		bgSize.setCode("98222");
		storeDetail.setBigBagSize(bgSize);
		List<AbstractOrderEntryModel> listOrderEntry = new ArrayList();
		AbstractOrderEntryModel orderEntry = new AbstractOrderEntryModel();
		orderEntry.setEntryNumber(1);
		orderEntry.setProduct(bgSize);
		listOrderEntry.add(orderEntry);
		CartModel carts = new CartModel();
		carts.setEntries(listOrderEntry);
		BagInfoModel mockBgInfo = new BagInfoModel();
		when(modelService.create(BagInfoModel.class)).thenReturn(mockBgInfo);
		when(cartService.getSessionCart()).thenReturn(carts);
		when(storeSessionFacade.getSessionStore()).thenReturn(sessionStore);
		when(siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId())).thenReturn(storeDetail);
		when(productService.getProductForCode(bgSize.getCode())).thenReturn(bgSize);
		lenient().doNothing().when(modelService).save(any());
		lenient().doNothing().when(modelService).refresh(any());
		lenient().doNothing().when(modelService).remove(any());
		final BagInfoData result = siteoneCartFacade.updateBigBagInfo("98222", 4, 100.0, "Cubic Yard", true);
		assertNotNull(result);
		assertEquals(Integer.valueOf(5), result.getNumberOfBags());
	}
}
