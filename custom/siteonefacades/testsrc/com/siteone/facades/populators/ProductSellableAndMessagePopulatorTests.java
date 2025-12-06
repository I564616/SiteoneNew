/**
 *
 */
package com.siteone.facades.populators;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;

@UnitTest
public class ProductSellableAndMessagePopulatorTests
{

	@InjectMocks
	private ProductSellableAndMessagePopulator productSellableAndMessagePopulator;

	@Mock
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Mock
	private ProductService productService;

	@Mock
	private B2BUnitService b2bUnitService;

	@Mock
	private SiteOneStockLevelService siteOneStockLevelService;

	@Mock
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;

	@Mock
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Mock
	private I18NService i18nService;

	@Mock
	private SessionService sessionService;

	@Mock
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Mock
	private SiteOneProductFacade siteOneProductFacade;

	@Mock
	private StoreFinderFacade storeFinderFacade;

	@Mock
	private MessageSource messageSource;
	
	@Mock
	private UserFacade userFacade;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
   public void testPopulate1() {
       final ProductModel source = new ProductModel();
       final ProductData target = new ProductData();
       source.setCode("testProduct");

       StockData stockData = new StockData();
       stockData.setIsHomeStoreStockAvailable(Boolean.FALSE);
       stockData.setIsForceInStock(Boolean.TRUE);
       target.setStock(stockData);
       
       target.setIsForceInStock(Boolean.TRUE); 
       target.setInventoryCheck(Boolean.FALSE);

       when(storeSessionFacade.getSessionStore()).thenReturn(null);
       when(siteOneFeatureSwitchCacheService.isProductPresentUnderFeatureSwitch(any(), any())).thenReturn(false);
       when(i18nService.getCurrentLocale()).thenReturn(Locale.US);

       productSellableAndMessagePopulator.populate(source, target);

       assertNotNull(target);
       assertEquals("testProduct", source.getCode());
       assertTrue(target.getIsForceInStock());
       assertFalse(target.getInventoryCheck());
   }

   @Test
   public void testPopulate2() {
       final ProductModel source = new ProductModel();
       final ProductData target = new ProductData();
       source.setCode("testProduct");

       StockData stockData = new StockData();
       stockData.setIsHomeStoreStockAvailable(Boolean.TRUE);
       stockData.setIsForceInStock(Boolean.FALSE);
       target.setStock(stockData);
       
       target.setIsForceInStock(Boolean.FALSE); 
        

       when(storeSessionFacade.getSessionStore()).thenReturn(null);
       when(siteOneFeatureSwitchCacheService.isProductPresentUnderFeatureSwitch(any(), any())).thenReturn(false);
       when(i18nService.getCurrentLocale()).thenReturn(Locale.US);

       productSellableAndMessagePopulator.populate(source, target);

       assertNotNull(target);
       assertEquals("testProduct", source.getCode());
       assertFalse(target.getIsForceInStock());
       assertNull(target.getInventoryCheck());
   }
}

