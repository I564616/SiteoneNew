/**
 *
 */
package com.siteone.facades.savedList.populator;

import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1003567
 *
 */


public class SiteoneSavedListEntryPopulator implements Populator<Wishlist2EntryModel, SavedListEntryData>
{
	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;


	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Override
	public void populate(final Wishlist2EntryModel source, final SavedListEntryData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		if (source.getProduct() != null)
		{
			addProductToList(source, target);
		}

	}

	public void addProductToList(final Wishlist2EntryModel source, final SavedListEntryData target)
	{
		final List<ProductOption> options = new ArrayList<>(
				Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.SUMMARY,
						ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES, ProductOption.REVIEW,
						ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION,
						ProductOption.VARIANT_FULL, ProductOption.STOCK, ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE,
						ProductOption.DELIVERY_MODE_AVAILABILITY, ProductOption.DATA_SHEET));
	
		if (!(null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId())))
		{
			options.add(2, ProductOption.PRICE); 
		}
		
		final ProductModel productModel = productService.getProductForCode(source.getProduct().getCode());

		final ProductData productData = productConverter.convert(productModel);
		//productData.setCustomerPrice(target.getPrice());

		productConfiguredPopulator.populate(productModel, productData, options);

		target.setProduct(productData);
		target.setQty(source.getDesired());
		target.setEntryComment(source.getComment());
		target.setInventoryUom(source.getUomId());

	}

}