package com.siteone.core.cronjob.dao;


import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;


/**
 * @author SD02010
 */

public interface ConvertedProductCronJobDao
{
	List<ProductModel> getConvertedProducts();
}
