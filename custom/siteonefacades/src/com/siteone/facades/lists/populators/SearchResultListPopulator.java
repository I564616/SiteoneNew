/**
 *
 */
package com.siteone.facades.lists.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.List;

import com.siteone.facades.savedList.data.SavedListEntryData;




/**
 * @author AA04994
 *
 */
public class SearchResultListPopulator implements Populator<SearchResultValueData, SavedListEntryData>
{
	private Populator<MediaModel, ImageData> imagePopulator;

	@Override
	public void populate(final SearchResultValueData source, final SavedListEntryData target) throws ConversionException
	{
		final ProductData data = new ProductData();
		data.setCode(this.<String> getValue(source, "soproductcode"));
		data.setItemNumber(this.<String> getValue(source, "soitemnumber"));
		data.setProductShortDesc(this.<String> getValue(source, "soproductshortdesc"));
		data.setProductBrandName(this.<String> getValue(source, "sobrand"));

		//		data.setCategories(this.<String> getValue(source, "socategory"));

		final List<ImageData> imageList = new ArrayList<>();
		if (null != this.getValue(source, "soimages"))
		{
			final String imgValue = this.getValue(source, "soimages");

			if (imgValue != null && !imgValue.isEmpty())
			{
				final ImageData imageData = createImageData();
				imageData.setImageType(ImageDataType.PRIMARY);
				imageData.setFormat("product");
				imageData.setUrl(imgValue);
				imageList.add(imageData);

			}

		}
		data.setImages(imageList);
		target.setProduct(data);
		target.setQty(this.<Integer> getValue(source, "soquantity"));
		target.setInventoryUom(this.<Integer> getValue(source, "souomId"));
		target.setEntryComment(this.<String> getValue(source, "socomment"));
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
