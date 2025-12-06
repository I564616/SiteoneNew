/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.model.PurchasedProductModel;


/**
 * @author 1190626
 *
 */
public class PurchasedProductPopulator implements Populator<PurchProductAndOrdersModel, ProductData>
{
	private Converter<ProductModel, ProductData> siteOneOrderEntriesProductConverter;

	private ProductService productService;

	public ProductService getProductService()
	{
		return productService;
	}

	public void setProductService(final ProductService productService)
	{
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
	public void populate(final PurchProductAndOrdersModel  purchasedProduct, final ProductData productData) throws ConversionException
	{

		if (purchasedProduct.getIsStoreProduct())
		{
			productData.setCode(purchasedProduct.getProductCode());
			productData.setItemNumber(purchasedProduct.getItemNumber());
			productData.setName(purchasedProduct.getProductName());
			productData.setAvailableStatus(Boolean.FALSE);
		}
		else
		{

			getSiteOneOrderEntriesProductConverter().convert(productService.getProductForCode(purchasedProduct.getProductCode()),
					productData);
			productData.setName(purchasedProduct.getProductName());
		}
	}

}
