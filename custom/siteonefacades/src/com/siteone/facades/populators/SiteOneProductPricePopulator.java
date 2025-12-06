/**
 *
 */

package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductPricePopulator;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.session.SessionService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.price.data.Prices;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.services.ue.SiteOnePriceWebService;



/**
 * @author 1124932
 *
 */

public class SiteOneProductPricePopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends ProductPricePopulator<SOURCE, TARGET>
{

	private static final Logger LOG = Logger.getLogger(SiteOneProductPricePopulator.class);

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	private SessionService sessionService;

	/**
	 * @return the siteOnePriceWebService
	 */
	public SiteOnePriceWebService getSiteOnePriceWebService()
	{
		return siteOnePriceWebService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	/**
	 * @param siteOnePriceWebService
	 *           the siteOnePriceWebService to set
	 */
	public void setSiteOnePriceWebService(final SiteOnePriceWebService siteOnePriceWebService)
	{
		this.siteOnePriceWebService = siteOnePriceWebService;
	}



	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}

	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}



	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	private SiteOnePriceWebService siteOnePriceWebService;
	
	private ProductService productService;


	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String D365_CSP_BRANCHES = "d365cspPriceBranches";
	private static final String D365_NEW_URL = "isD365NewUrlEnable";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.commercefacades.product.converters.populator.ProductPricePopulator#populate(de.hybris.platform.
	 * core.model.product.ProductModel, de.hybris.platform.commercefacades.product.data.ProductData)
	 */
	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		final PriceDataType priceType;
		final PriceInformation info;
		if (CollectionUtils.isEmpty(productModel.getVariants()))
		{
			priceType = PriceDataType.BUY;
		}
		else
		{
			priceType = PriceDataType.FROM;
		}
		if (BooleanUtils.isNotTrue(productModel.getHideList()) && null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			if(null == productData.getPrice() && BooleanUtils.isNotTrue(getSessionService().getAttribute("isSkipRetailAPI")))
			{
				final ProductModel product = getProductService().getProductForCode(productModel.getCode());
				final Map<ProductModel, Integer> products = new HashMap<>();
				final Map<ProductModel, String> productsUoms = new HashMap<>();
				products.put(product, 1);
				final InventoryUPCModel inventoryUOM = product.getUpcData().stream()
						.filter(upc -> !upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
				if (inventoryUOM != null)
				{
					productsUoms.put(product, inventoryUOM.getCode());
				}
				List<PointOfServiceData> nearbyStoresList = null;
				if (null != storeSessionFacade.getSessionStore())
				{
					nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
				}
				final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
				try
				{

					final SiteOneWsPriceResponseData siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products,
							productsUoms, storeSessionFacade.getSessionStore().getStoreId(), nearbyStoresList, null, currencyIso,
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
							siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),
							storeSessionFacade.getSessionStore().getCustomerRetailId());

					if (null != siteOneWsPriceResponseData)
					{

						final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
						if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isEmpty(productModel.getVariants()))
						{
							final String price = allPrices.get(0).getPrice();

							if (null != price && productModel.getCode().equals(allPrices.get(0).getSkuId()))
							{
								final PriceData priceData = getPriceDataFactory().create(priceType, new BigDecimal(price), currencyIso);
								productData.setPrice(priceData);
							}
							else
							{
								LOG.error("Data is null");
							}
						}

					}


				}
				catch (final Exception e)
				{
					LOG.error("Exception on retail api call " + e);
				}
			}
		}
		else
		{
			if (CollectionUtils.isEmpty(productModel.getVariants()))
			{
				info = getCommercePriceService().getWebPriceForProduct(productModel);
			}
			else
			{
				info = getCommercePriceService().getFromPriceForProduct(productModel);
			}

			if (info != null && info.getPrice() != null && info.getPrice().getValue() > 0.0)
			{
				final PriceData priceData = getPriceDataFactory().create(priceType,
						BigDecimal.valueOf(info.getPriceValue().getValue()), info.getPriceValue().getCurrencyIso());
				productData.setPrice(priceData);
			}
		}
	}

}
