package com.siteone.core.services.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.cronjob.dao.ConvertedProductCronJobDao;
import com.siteone.core.model.ConvertedProductMappingCronJobModel;
import com.siteone.core.services.ConvertedProductCronJobService;


/**
 * @author SD02010
 */
public class DefaultConvertedProductCronJobService implements ConvertedProductCronJobService
{
	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	private ConvertedProductCronJobDao convertedProductCronJobDao;
	private ModelService modelService;

	@Override
	public void convertedProductMapping(final ConvertedProductMappingCronJobModel mappingCronJobModel)
	{
		final List<ProductModel> convertedProducts = getConvertedProductCronJobDao().getConvertedProducts();
		ProductModel convertedProduct;
		if (CollectionUtils.isNotEmpty(convertedProducts))
		{
			for (final ProductModel product : convertedProducts)
			{
				if (BooleanUtils.isTrue(product.getIsConverted()) && null != product.getConvertedProduct())
				{
					convertedProduct = product.getConvertedProduct();
					if (CollectionUtils.isNotEmpty(product.getStores()))
					{
						convertedProduct.setStores(product.getStores());
					}
					if (CollectionUtils.isNotEmpty(product.getOnHandStores()))
					{
						convertedProduct.setOnHandStores(product.getOnHandStores());
					}
					product.setIsConverted(Boolean.FALSE);
					modelService.save(product);
					modelService.save(convertedProduct);
				}
			}
		}
	}

	/**
	 * @return the convertedProductCronJobDao
	 */
	public ConvertedProductCronJobDao getConvertedProductCronJobDao()
	{
		return convertedProductCronJobDao;
	}

	/**
	 * @param convertedProductCronJobDao
	 *           the convertedProductCronJobDao to set
	 */
	public void setConvertedProductCronJobDao(final ConvertedProductCronJobDao convertedProductCronJobDao)
	{
		this.convertedProductCronJobDao = convertedProductCronJobDao;
	}

}
