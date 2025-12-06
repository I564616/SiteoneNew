/**
 *
 */
package com.siteone.facades.populators;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.facades.product.data.DataSheetData;


/**
 * @author 1091124
 *
 */

public class SiteOneProductDataSheetPopulator implements Populator<ProductModel, ProductData>
{

	@Override
	public void populate(final ProductModel productModel, final ProductData productData) throws ConversionException
	{

		Collection<MediaModel> mediaModelColl = productModel.getData_sheet();
		final Map<String, List<DataSheetData>> dataSheetDataMap = new HashMap<>();
		if (!productModel.getVariants().isEmpty())
		{
			for (final ProductModel product : productModel.getVariants())
			{
				mediaModelColl = product.getData_sheet();
				if (CollectionUtils.isNotEmpty(mediaModelColl))
				{
					populateDataSheet(mediaModelColl, dataSheetDataMap, product);
				}

			}
		}
		else if (CollectionUtils.isNotEmpty(mediaModelColl))
		{
			populateDataSheet(mediaModelColl, dataSheetDataMap, productModel);
		}
		productData.setDataSheetList(dataSheetDataMap);

	}

	public void populateDataSheet(final Collection<MediaModel> mediaModelColl,
			final Map<String, List<DataSheetData>> dataSheetDataMap, final ProductModel product)
	{
		final List<DataSheetData> dataSheetList = new ArrayList<>();
		for (final MediaModel mediaModelObj : mediaModelColl)
		{
			final DataSheetData dataSheetDataObj = new DataSheetData();
			dataSheetDataObj.setAltText(mediaModelObj.getAltText());
			dataSheetDataObj.setMimeType(mediaModelObj.getMime());
			dataSheetDataObj.setUrl(mediaModelObj.getURL());
			dataSheetDataObj.setLabel(mediaModelObj.getCode());
			dataSheetDataObj.setSds(mediaModelObj.getSds());
			dataSheetDataObj.setSdsLabel(mediaModelObj.getLabel());
			dataSheetList.add(dataSheetDataObj);

		}
		dataSheetDataMap.put(product.getItemNumber(), dataSheetList);
	}


}



