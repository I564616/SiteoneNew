/**
 *
 */
package com.siteone.facades.savedList.populator;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.CustomerPriceData;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1003567
 *
 */
public class SiteoneSavedListPopulator implements Populator<Wishlist2Model, SavedListData>
{
	private static final String PRIVATE = "private.savedList";
	private static final String SHARED = "shared.savedList";


	@Resource(name = "siteoneSavedListEntryConverter")
	private Converter<Wishlist2EntryModel, SavedListEntryData> siteoneSavedListEntryConverter;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private MessageSource messageSource;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Override
	public void populate(final Wishlist2Model source, final SavedListData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setName(source.getName());
		target.setDescription(source.getDescription());
		target.setModifiedTime(source.getModifiedtime());

		final List<SavedListEntryData> entryDataList = new ArrayList<SavedListEntryData>();
		if (CollectionUtils.isNotEmpty(source.getEntries()))
		{
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
			final List<CustomerPriceData> customerPriceDataList = target.getCustomerPriceData();
			final List<CustomerPriceData> retailPriceDataList = target.getRetailPriceData();
			for (final Wishlist2EntryModel wishlist2EntryModel : source.getEntries())
			{
				final ProductData productData = new ProductData();
				final ProductModel productModel = wishlist2EntryModel.getProduct();

				final ProductModel product = productService.getProductForCode(productModel.getCode());

				productConverter.convert(product, productData);

				if (!CollectionUtils.isEmpty(customerPriceDataList))
				{
              for(CustomerPriceData customerPriceData : customerPriceDataList)
              {
					if (customerPriceData != null && productModel.getCode().equals(customerPriceData.getCode()))
					{

						final String price = customerPriceData.getPrice();

						if (null != price)
						{
							final PriceData priceData = priceDataFactory.create(PriceDataType.BUY, new BigDecimal(price), currencyIso);
							productData.setCustomerPrice(priceData);
						}
					}
              }
				}
				if (!CollectionUtils.isEmpty(retailPriceDataList))
				{
              for(CustomerPriceData retailPriceData : retailPriceDataList)
              {
					if (retailPriceData != null && productModel.getCode().equals(retailPriceData.getCode()))
					{

						final String price = retailPriceData.getPrice();

						if (null != price)
						{
							final PriceData priceData = priceDataFactory.create(PriceDataType.BUY, new BigDecimal(price), currencyIso);
							productData.setPrice(priceData);
						}
					}
              }
				}
				final List<ProductOption> options = new ArrayList<>(Arrays.asList(ProductOption.BASIC, ProductOption.URL,
						ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.GALLERY,
						ProductOption.CATEGORIES, ProductOption.REVIEW, ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION,
						ProductOption.VARIANT_FULL, ProductOption.STOCK, ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE,
						ProductOption.DELIVERY_MODE_AVAILABILITY, ProductOption.DATA_SHEET, ProductOption.AVAILABILITY_MESSAGE));

				if (!(null != storeSessionFacade.getSessionStore()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
								storeSessionFacade.getSessionStore().getStoreId())
						&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId())))
				{
					options.add(2, ProductOption.PRICE);
				}
				
				productConfiguredPopulator.populate(productModel, productData, options);

				final SavedListEntryData entryData = new SavedListEntryData();
				entryData.setProduct(productData);
				entryData.setQty(wishlist2EntryModel.getDesired());
				entryData.setEntryComment(wishlist2EntryModel.getComment());
				if (null != wishlist2EntryModel.getUomId())
				{
					entryData.setInventoryUom(wishlist2EntryModel.getUomId());
				}
				for(final InventoryUPCModel upcModel : wishlist2EntryModel.getProduct().getUpcData())
				{
					if(wishlist2EntryModel.getUomId() != null && upcModel.getInventoryUPCID().equalsIgnoreCase(wishlist2EntryModel.getUomId().toString()))
					{
						entryData.setInventoryUomDesc(upcModel.getInventoryUPCDesc());
					}
				}
				entryDataList.add(entryData);
			}
		}

		Collections.reverse(entryDataList);
		target.setEntries(entryDataList);

		target.setCreatedBy(source.getCreatedBy());

		if (source.getUser() != null)
		{
			target.setUser(source.getUser().getName());
		}

		if (userService.getCurrentUser().getUid().equals(source.getCreatedBy()))
		{
			target.setIsModified(Boolean.TRUE);
		}
		if (source.getIsShared())
		{
			target.setIsShared(getMessageSource().getMessage(SHARED, null, getI18nService().getCurrentLocale()));
		}
		else
		{
			target.setIsShared(getMessageSource().getMessage(PRIVATE, null, getI18nService().getCurrentLocale()));
		}
		target.setIsViewEdit(false);
		if (CollectionUtils.isNotEmpty(source.getViewEditOwners()))
		{
			for (final UserModel user : source.getViewEditOwners())
			{
				if (userService.getCurrentUser().getUid().equalsIgnoreCase(user.getUid()))
				{
					target.setIsModified(Boolean.TRUE);
					break;
				}

			}
		}

		target.setCode(source.getPk().toString());

	}

	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
}