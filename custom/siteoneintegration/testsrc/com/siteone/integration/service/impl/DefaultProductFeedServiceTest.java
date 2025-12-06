package com.siteone.integration.service.impl;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.siteone.integration.jobs.product.dao.ProductFeedCronJobDao;
import com.siteone.integration.jobs.product.service.impl.DefaultProductFeedService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
@UnitTest
class DefaultProductFeedServiceTest {
	private static final Logger LOG = Logger.getLogger(DefaultProductFeedServiceTest.class);

    @InjectMocks
    private DefaultProductFeedService productFeedService;
    @Mock
    private ProductFeedCronJobDao productFeedCronJobDao;
    @Mock
    private ConfigurationService configurationService;
    @Mock
    private SiteOneBlobDataImportService blobDataImportService;
    @Mock
    private SessionService sessionService;
    @Mock
    private BaseSiteService baseSiteService;
    @Mock
    private CatalogVersionService catalogVersionService;
    @Mock
    private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
    @Mock
    private EnumerationService enumerationService;
    @Mock
    private CatalogVersionModel catalogVersion;
    @Mock
    private BaseSiteModel baseSite;
    
    // Add other mocks as needed
    @Test
    void testExportFullProductFeed() throws IOException {
    	LOG.info("Inside DefaultProductFeedServiceTest start");
        // Mock catalog version
        when(catalogVersionService.getCatalogVersion("siteoneProductCatalog", "Online")).thenReturn(catalogVersion);

        // Mock configuration
        Configuration config = mock(Configuration.class);
        when(configurationService.getConfiguration()).thenReturn(config);
        when(config.getString(anyString())).thenReturn("mock/path");

        // Mock base site
        when(baseSiteService.getBaseSiteForUID(anyString())).thenReturn(baseSite);

        // Mock product list
        ProductModel mockProduct = new ProductModel();
        mockProduct.setCode("100030");
        mockProduct.setProductShortDesc("Stabilizer Stake (Big) Brown Unique");
        mockProduct.setProductLongDesc("You can’t outsource pride. That’s why every product carrying the Vista name is made in America. To us, it’s not just a sticker on a box — it’s our heritage.");
        mockProduct.setItemNumber("STABILIZERSTAKE");
        mockProduct.setProductType("Lighting");
        mockProduct.setSupercategories(mockCategory("PH11","PH1100","PH1100100",null));

        when(productFeedCronJobDao.getDataForFullProductFeed(catalogVersion)).thenReturn(List.of(mockProduct));
        when(productFeedCronJobDao.getLOBAttributes(anyString())).thenReturn(List.of("lghtColor"));
        
        // Mock populator
        doAnswer(invocation -> {
            ProductData data = invocation.getArgument(1);
            data.setProductBrandName("Unique");
            data.setUrl("/product/100030");
            data.setCategories(mockCategoryData("PH11","PH1100","PH1100100",null));
            return null;
        }).when(productConfiguredPopulator).populate(any(), any(), any());

        // Run method
        productFeedService.exportFullProductFeed();
        LOG.info("Inside DefaultProductFeedServiceTest after export feed");
        // Verify blob write was called
        verify(blobDataImportService, atLeastOnce()).writeBlob(any(File.class), eq("mock/path"));
        LOG.info("Inside DefaultProductFeedServiceTest end");
    }

    private List<CategoryModel> mockCategory(String l1, String l2, String l3, String l4) {
        List<CategoryModel> category = new ArrayList<>();
        CategoryModel level1 = new CategoryModel();
        level1.setCode(l1);
        level1.setName("Lighting");
        category.add(level1);
        CategoryModel level2 = new CategoryModel();
        level2.setCode(l2);
        level2.setName("Accessories");
        category.add(level1);
        CategoryModel level3 = new CategoryModel();
        level3.setCode(l3);
        level3.setName("Fixture Mounting");
        category.add(level1);        
        return category;
    }
    
    private List<CategoryData> mockCategoryData(String l1, String l2, String l3, String l4) {
        List<CategoryData> category = new ArrayList<>();
        CategoryData level1 = new CategoryData();
        level1.setCode(l1);
        level1.setName("Lighting");
        category.add(level1);
        CategoryData level2 = new CategoryData();
        level2.setCode(l2);
        level2.setName("Accessories");
        category.add(level1);
        CategoryData level3 = new CategoryData();
        level3.setCode(l3);
        level3.setName("Fixture Mounting");
        category.add(level1);        
        return category;
    }
	
	@Test
	void testGetProductLineBasedOnLOBReturnsExpectedMap() {
    ProductModel product = new ProductModel();
    product.setCode("100030");
    product.setProductShortDesc("Stabilizer Stake (Big) Brown Unique");
    product.setProductLongDesc("You can’t outsource pride. That’s why every product carrying the Vista name is made in America. To us, it’s not just a sticker on a box — it’s our heritage.");
    product.setItemNumber("STABILIZERSTAKE");
    product.setProductType("Lighting");

    ProductData productData = new ProductData();
    productData.setProductBrandName("Unique");
    productData.setUrl("/product/100030");
    productData.setCategories(mockCategoryData("PH11","PH1100","PH1100100",null));

    Map<Integer, String> result = new HashMap<Integer, String>();
    result.put(Integer.valueOf(0), "100030");
    result.put(Integer.valueOf(1), "Stabilizer Stake (Big) Brown Unique");
    result.put(Integer.valueOf(2), "You can’t outsource pride. That’s why every product carrying the Vista name is made in America. To us, it’s not just a sticker on a box — it’s our heritage.");
    result.put(Integer.valueOf(3), "STABILIZERSTAKE");
    result.put(Integer.valueOf(4), "Lighting");
    result.put(Integer.valueOf(5), "Lighting");
    result.put(Integer.valueOf(6), "Accessories");
    result.put(Integer.valueOf(7), "Fixture Mounting");
    result.put(Integer.valueOf(8), "1");
    result.put(Integer.valueOf(9), "1");
    result.put(Integer.valueOf(10), "Brown");
    assertEquals("100030", result.get(0));
    assertEquals("Stabilizer Stake (Big) Brown Unique", result.get(1));
    assertEquals("You can’t outsource pride. That’s why every product carrying the Vista name is made in America. To us, it’s not just a sticker on a box — it’s our heritage.", result.get(2));
    assertEquals("STABILIZERSTAKE", result.get(3));
    assertEquals("Lighting", result.get(4));
    assertEquals("Lighting", result.get(5));
    assertEquals("Accessories", result.get(6));
    assertEquals("Fixture Mounting", result.get(7));
    assertEquals("1", result.get(8));
    assertEquals("1", result.get(9));
    assertEquals("Brown", result.get(10));
	}
	
	@Test
	void testGetLOBAttributeReturnsCorrectAttributes() {
    List<String> mockAttributes = List.of("lghtColor");
    when(productFeedCronJobDao.getLOBAttributes("lght%")).thenReturn(mockAttributes);

    List<String> result = new ArrayList<String>();
    result.add("lghtColor");

    assertEquals(mockAttributes, result);
	}

}
