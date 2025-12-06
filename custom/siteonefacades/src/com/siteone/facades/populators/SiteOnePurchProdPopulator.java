/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.siteone.core.model.PurchasedProductModel;
import com.siteone.core.util.SiteOneB2BUnitUtil;
import com.siteone.integration.product.data.PurchasedProduct;


/**
 * @author 1188173
 *
 */
public class SiteOnePurchProdPopulator implements Populator<PurchasedProduct, ProductData>
{

	
	private static final Logger LOG = Logger.getLogger(SiteOnePurchProdPopulator.class);

	private Converter<ProductModel, ProductData> siteOneOrderEntriesProductConverter;

	private ProductService productService;

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(final ProductService productService) {
		this.productService = productService;
	}



	/**
	 * @return the siteOneOrderEntriesProductConverter
	 */
	public Converter<ProductModel, ProductData> getSiteOneOrderEntriesProductConverter()
	{
		return siteOneOrderEntriesProductConverter;
	}

	/**
	 * @param siteOneOrderEntriesProductConverter
	 *           the siteOneOrderEntriesProductConverter to set
	 */
	public void setSiteOneOrderEntriesProductConverter(
			final Converter<ProductModel, ProductData> siteOneOrderEntriesProductConverter)
	{
		this.siteOneOrderEntriesProductConverter = siteOneOrderEntriesProductConverter;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final PurchasedProduct purchasedProduct, final ProductData productData) throws ConversionException
	{
		if (purchasedProduct.getIsNonCatalog().booleanValue())
		{
			productData.setCode(purchasedProduct.getSkuID());
			productData.setItemNumber(purchasedProduct.getItemNumber());
			productData.setName(purchasedProduct.getItemDescription());
			productData.setAvailableStatus(purchasedProduct.getIsNonCatalog());
		}
		else
		{
			try{getSiteOneOrderEntriesProductConverter().convert(productService.getProductForCode(purchasedProduct.getSkuID()),
					productData);
			productData.setName(purchasedProduct.getItemDescription());
			}
			catch(final Exception exp){
				
				LOG.error("Error Finding product - "+purchasedProduct.getSkuID());
				productData.setCode(purchasedProduct.getSkuID());
				productData.setItemNumber(purchasedProduct.getItemNumber());
				productData.setName(purchasedProduct.getItemDescription());
				productData.setAvailableStatus(Boolean.TRUE);
			}
		}
	}

}
