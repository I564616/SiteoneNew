/**
 *
 */
package com.siteone.facades.buyitagain.search.populator;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.facade.BuyItAgainData;
import org.apache.commons.lang3.StringUtils;


/**
 * @author SMondal
 *
 */
public class SearchResultBuyItAgainPopulator implements Populator<SearchResultValueData, BuyItAgainData>
{
	private Populator<MediaModel, ImageData> imagePopulator;

	@Override
	public void populate(final SearchResultValueData source, final BuyItAgainData target) throws ConversionException
	{
		target.setProductCode(this.<String> getValue(source, "soProductCode"));
		target.setProductDescription(this.<String> getValue(source, "soProductDescription"));
		target.setProductItemNumber(this.<String> getValue(source, "soProductItemNumber"));
		target.setLastPurchasedDate(this.getValue(source, "soLastPurchasedDate"));
		target.setIsOnlineProduct(this.getValue(source, "soIsOnlineProduct"));
		target.setCategoryName(this.getValue(source, "soCategoryName"));

		String productUOM=this.getValue(source, "soProductUOM");
		if(StringUtils.isNotBlank(productUOM)){
			String[] uomList=productUOM.split("\\|");
			if(uomList.length==4){
				target.setProductUOMUPCID(uomList[0]);
				target.setProductUOMCode(uomList[1]);
				target.setProductUOMSHortDesc(uomList[2]);
			}
		}

		target.setOrderNumber(this.getValue(source, "soOrderNumber"));
		if (null != this.getValue(source, "soPurchasedCount"))
		{
			target.setPurchasedCount(this.getValue(source, "soPurchasedCount"));
		}
		if (null != this.getValue(source, "soPurchasedCount"))
		{
			target.setOrderCount(this.getValue(source, "soPurchasedCount"));
		}
		if (null != this.getValue(source, "soPurchasedQuantity"))
		{
			target.setPurchasedQuantity(this.getValue(source, "soPurchasedQuantity"));
		}

		if (null != this.getValue(source, "soimg-96Wx96H"))
		{
			final String imgValue = this.getValue(source, "soimg-96Wx96H");
			if (imgValue != null && !imgValue.isEmpty())
			{
				final ImageData imageData = createImageData();
				imageData.setImageType(ImageDataType.PRIMARY);
				imageData.setFormat("thumbnail");
				imageData.setUrl(imgValue);
				target.setImage(imageData);
			}

		}
		target.setUnitPath(this.<String> getValue(source, "soUnitPath"));
	}

	protected <T> T getValue(final SearchResultValueData source, final String propertyName)
	{
		if (source.getValues() == null)
		{
			return null;
		}

		// DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
		return (T) source.getValues().get(propertyName);
	}

	protected ImageData createImageData()
	{
		return new ImageData();
	}
}
