package com.siteone.facades.populators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.commerceservices.product.CommerceProductService;

import java.lang.reflect.Field;
import java.util.Collections;

import org.apache.commons.configuration2.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@UnitTest
public class SiteOneProductCategoriesPopulatorTest {

    @InjectMocks
    private SiteOneProductCategoriesPopulator siteOneProductCategoriesPopulator;

    @Mock
    private ProductService productService;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private CommerceProductService commerceProductService; 

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        
        Configuration mockConfig = Mockito.mock(Configuration.class);
        when(configurationService.getConfiguration()).thenReturn(mockConfig);
        when(mockConfig.getString("display.hierarchy.root.category")).thenReturn("DISPLAY_ROOT_CATEGORY"); 

        
        Field commerceProductServiceField = SiteOneProductCategoriesPopulator.class.getSuperclass().getDeclaredField("commerceProductService");
        commerceProductServiceField.setAccessible(true);
        commerceProductServiceField.set(siteOneProductCategoriesPopulator, commerceProductService);

        
        CategoryModel category = new CategoryModel();
        category.setCode("TEST_CATEGORY");
        category.setIsTransferrableCategory(Boolean.FALSE);

        when(commerceProductService.getSuperCategoriesExceptClassificationClassesForProduct(any()))
                .thenReturn(Collections.singletonList(category)); 
    }

    @Test
    public void testPopulate1() {
        ProductModel product = new ProductModel();
        ProductData productData = new ProductData();

        when(productService.getProductForCode(product.getCode())).thenReturn(product);

        siteOneProductCategoriesPopulator.populate(product, productData);

        assertNotNull(productData);
        assertFalse(productData.getIsTransferrable());
    }
}
