/**
 *
 */
package com.siteone.facades.cart;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.converters.populator.ProductPromotionsPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.commerceservices.order.CommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.converters.ConfigurablePopulator;
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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.services.SiteOneCalculationService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.populators.SiteOneStockPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;

import junit.framework.Assert;


/**
 *
 */
@UnitTest
public class DefaultSiteOneCartFacadeTest
{

	@Mock
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
	private static final String HOMEOWNER_CODE = "homeOwner.trade.class.code";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		siteoneCartFacade = new DefaultSiteOneCartFacade();
		siteoneCartFacade.setStoreSessionFacade(storeSessionFacade);
		siteoneCartFacade.setModelService(modelService);
		siteoneCartFacade.setSessionService(sessionService);
		siteoneCartFacade.setSiteOneFeatureSwitchCacheService(siteOneFeatureSwitchCacheService);
		siteoneCartFacade.setConfigurationService(configurationService);
	}


	@Test
	public final void testGetDefaultFulfillment()
	{
		final CartModel model = new CartModel();
		final CartData data = new CartData();
		final OrderTypeEnum initial = OrderTypeEnum.PICKUP;
		final boolean splitMixedCart = true;
		final OrderEntryData entryData_1 = new OrderEntryData();
		final ProductData prd_1 = new ProductData();
		prd_1.setStockAvailableOnlyHubStore(Boolean.TRUE);
		entryData_1.setProduct(prd_1);
		final OrderEntryData entryData_2 = new OrderEntryData();
		final ProductData prd_2 = new ProductData();
		prd_2.setOutOfStockImage(Boolean.FALSE);
		prd_2.setIsShippable(Boolean.FALSE);
		entryData_2.setProduct(prd_2);
		final List<OrderEntryData> entries = new ArrayList<>();
		entries.add(entryData_1);
		entries.add(entryData_2);
		data.setEntries(entries);
		final boolean actual = siteoneCartFacade.getDefaultFulfillment(model, data, initial, splitMixedCart);
		Assert.assertTrue(actual);
		Assert.assertEquals(initial, model.getOrderType());
	}

	@Test
	public final void testGetDefaultFulfillment_falseCondition()
	{
		final CartModel model = new CartModel();
		final CartData data = new CartData();
		final OrderTypeEnum initial = OrderTypeEnum.PICKUP;
		final boolean splitMixedCart = true;
		final OrderEntryData entryData_1 = new OrderEntryData();
		final ProductData prd_1 = new ProductData();
		prd_1.setStockAvailableOnlyHubStore(Boolean.TRUE);
		entryData_1.setProduct(prd_1);
		final OrderEntryData entryData_2 = new OrderEntryData();
		final ProductData prd_2 = new ProductData();
		prd_2.setOutOfStockImage(Boolean.FALSE);
		prd_2.setIsShippable(Boolean.TRUE);
		entryData_2.setProduct(prd_2);
		final List<OrderEntryData> entries = new ArrayList<>();
		entries.add(entryData_1);
		entries.add(entryData_2);
		data.setEntries(entries);
		final boolean actual = siteoneCartFacade.getDefaultFulfillment(model, data, initial, splitMixedCart);
		Assert.assertFalse(actual);
		Assert.assertEquals(null, model.getOrderType());
	}
}
