/**
 *
 */
package com.siteone.facades.populators;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.acceleratorfacades.order.data.PriceRangeData;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commercefacades.product.converters.populator.ProductPriceRangePopulator;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantProductModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.facades.populators.SiteOneProductPriceRangePopulator.CSPPriceRangeComparator;
import com.siteone.integration.price.data.Prices;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.services.ue.SiteOnePriceWebService;


/**
 * @author 1219341
 *
 */
public class SiteOneProductPriceRangePopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends ProductPriceRangePopulator<SOURCE, TARGET>
{
	@Autowired
	private B2BCustomerService b2bCustomerService;
	@Autowired
	private SiteOnePriceWebService siteOnePriceWebService;
	protected SessionService sessionService;
	@Autowired
	private CommonI18NService commonI18NService;
	private UserFacade userFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String D365_CSP_BRANCHES = "d365cspPriceBranches";
	private static final String D365_NEW_URL = "isD365NewUrlEnable";

	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		if (productModel != null && productData != null)
		{
			// make sure you have the baseProduct because variantProducts won't have other variants
			final ProductModel baseProduct;
			final Collection<VariantProductModel> variants = productModel.getVariants();
			
			if (!variants.isEmpty())
			{
				List<VariantProductModel> valiantList = variants.stream()
						.filter(variant-> BooleanUtils.isNotTrue(variant.getIsProductDiscontinued()))
						.collect(Collectors.toList());
				baseProduct = productModel;
				if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) && !userFacade.isAnonymousUser())
				{
					Map<ProductModel, Integer> products = new HashMap<>();
					final Map<String, Map<ProductModel, Integer>> productsWithStore = new HashMap<String, Map<ProductModel, Integer>>();
					final Map<String, SiteOneWsPriceResponseData> cspResponseForStore = new HashMap<>();
					String storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
					for (final VariantProductModel variant : valiantList)
					{
						if (null == variant.getVariantType())
						{
							if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
									((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
							{
								for (final VariantOptionData variantOptionData : productData.getVariantOptions())
								{
									if (variantOptionData.getCode().equalsIgnoreCase(variant.getCode()))
									{
										if (variantOptionData.getStock() != null)
										{
											if (!StringUtils.isEmpty(variantOptionData.getStock().getFullfillmentStoreId()))
											{
												storeId = variantOptionData.getStock().getFullfillmentStoreId();
											}
											else if (variantOptionData.getStock().getNearestBackorderableStore() != null)
											{
												if (StringUtils
														.isNotEmpty(variantOptionData.getStock().getNearestBackorderableStore().getStoreId()))
												{
													storeId = variantOptionData.getStock().getNearestBackorderableStore().getStoreId();
												}
											}
											else
											{
												storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore"))
														.getStoreId();
											}
										}
										else
										{
											storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
										}
										break;
									}
								}
							}
							if (productsWithStore.containsKey(storeId))
							{
								products = productsWithStore.get(storeId);
								products.put(variant, Integer.valueOf(1));
								productsWithStore.put(storeId, products);
							}
							else
							{
								final Map<ProductModel, Integer> newproduct = new HashMap<>();
								newproduct.put(variant, Integer.valueOf(1));
								productsWithStore.put(storeId, newproduct);
							}
						}
					}
					final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
					//final String storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
					final List<PointOfServiceData> nearbyStoresList = (List<PointOfServiceData>) getSessionService()
							.getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
					final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getB2bCustomerService().getCurrentB2BCustomer();

					final String currencyIso = currency.getIsocode();

					SiteOneWsPriceResponseData cspResponseData = null;
					if (!variants.isEmpty())
					{
						for (final Entry<String, Map<ProductModel, Integer>> product : productsWithStore.entrySet())
						{
							if (!product.getValue().isEmpty())
							{
								cspResponseData = siteOnePriceWebService.getPrice(product.getValue(), null, product.getKey(),
										nearbyStoresList, b2bCustomer, currencyIso,
										Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
										siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
										Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), null);
								cspResponseForStore.put(product.getKey(), cspResponseData);
							}
						}

					}
					final List<Double> allPrices = new ArrayList<Double>();
					if (!cspResponseForStore.isEmpty())
					{
					for (final VariantProductModel variant : valiantList)
					{
						if (null == variant.getVariantType())
						{
							if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
									((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
							{
								for (final VariantOptionData variantOptionData : productData.getVariantOptions())
								{
									if (variantOptionData.getCode().equalsIgnoreCase(variant.getCode()))
									{
										if (variantOptionData.getStock() != null)
										{
											if (!StringUtils.isEmpty(variantOptionData.getStock().getFullfillmentStoreId()))
											{
												storeId = variantOptionData.getStock().getFullfillmentStoreId();
											}
											else if (variantOptionData.getStock().getNearestBackorderableStore() != null)
											{
												if (StringUtils
														.isNotEmpty(variantOptionData.getStock().getNearestBackorderableStore().getStoreId()))
												{
													storeId = variantOptionData.getStock().getNearestBackorderableStore().getStoreId();
												}
											}
											else
											{
												storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore"))
														.getStoreId();
											}
										}
										else
										{
											storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
										}
										break;
									}
								}
							}
							cspResponseData = cspResponseForStore.get(storeId);
							if (null != cspResponseData && CollectionUtils.isNotEmpty(cspResponseData.getPrices()))
							{
								for (final Prices price : cspResponseData.getPrices())
								{
									if (price.getPrice() == null)
									{
										if (variant.getCode().equals(price.getSkuId()))
										{
											final List<PriceInformation> pricesInfos = getPriceService()
													.getPriceInformationsForProduct(variant);
											if (CollectionUtils.isNotEmpty(pricesInfos) && null != pricesInfos.get(0).getPriceValue()
													&& Double.compare(pricesInfos.get(0).getPriceValue().getValue(), 0.0d) != 0)
											{
												price.setPrice(String.valueOf(pricesInfos.get(0).getPriceValue().getValue()));
											}
										}
									}
									if (variant.getCode().equals(price.getSkuId()))
									{
									allPrices.add(Double.parseDouble(price.getPrice()));
									}
								}
							}
						}
					}

					//final List<Prices> allPrices = cspResponseData.getPrices();
						allPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);

						// sort the list
						Collections.sort(allPrices);
						final PriceRangeData priceRange = new PriceRangeData();
						if (!allPrices.isEmpty())
						{
							final BigDecimal minPrice = BigDecimal.valueOf(allPrices.get(0));
							final BigDecimal maxPrice = BigDecimal.valueOf(allPrices.get(allPrices.size() - 1));
							priceRange.setMinPrice(getPriceDataFactory().create(PriceDataType.FROM, minPrice,
									getCommonI18NService().getCurrentCurrency().getIsocode()));
							priceRange.setMaxPrice(getPriceDataFactory().create(PriceDataType.FROM, maxPrice,
									getCommonI18NService().getCurrentCurrency().getIsocode()));
						}
						//setting the LSP range for Logged in User.
						getListPriceRangeForLSP(productData, valiantList, priceRange);
						productData.setPriceRange(priceRange);

					}
					else
					{
						getListPriceRange(productData, valiantList);
					}
				}
				else
				{
					getListPriceRange(productData, valiantList);
				}
			}
		}
	}

	public void getListPriceRange(final ProductData productData, final Collection<VariantProductModel> variants)
	{
		final List<PriceInformation> allPricesInfos = new ArrayList<PriceInformation>();
		// collect all price infos
		for (final VariantProductModel variant : variants)
		{
			allPricesInfos.addAll(getPriceService().getPriceInformationsForProduct(variant));
		}

		allPricesInfos
				.removeIf(priceInfo -> BigDecimal.valueOf(priceInfo.getPriceValue().getValue()).compareTo(BigDecimal.ZERO) == 0);
		// sort the list
		Collections.sort(allPricesInfos, PriceRangeComparator.INSTANCE);


		final PriceRangeData priceRange = new PriceRangeData();
		// get the min and max
		if (!allPricesInfos.isEmpty())
		{
			priceRange.setMinPrice(createPriceData(PriceDataType.FROM, allPricesInfos.get(0)));
			priceRange.setMaxPrice(createPriceData(PriceDataType.FROM, allPricesInfos.get(allPricesInfos.size() - 1)));
		}

		productData.setPriceRange(priceRange);

	}

	public void getListPriceRangeForLSP(final ProductData productData, final Collection<VariantProductModel> variants,
			final PriceRangeData priceRange)
	{
		final List<PriceInformation> allPricesInfos = new ArrayList<PriceInformation>();
		// collect all price infos
		for (final VariantProductModel variant : variants)
		{
			allPricesInfos.addAll(getPriceService().getPriceInformationsForProduct(variant));
		}

		allPricesInfos
				.removeIf(priceInfo -> BigDecimal.valueOf(priceInfo.getPriceValue().getValue()).compareTo(BigDecimal.ZERO) == 0);
		// sort the list
		Collections.sort(allPricesInfos, PriceRangeComparator.INSTANCE);


		// final PriceRangeData priceRange = new PriceRangeData();
		// get the min and max
		if (!allPricesInfos.isEmpty())
		{
			priceRange.setMinLSPPrice(createPriceData(PriceDataType.FROM, allPricesInfos.get(0)));
			priceRange.setMaxLSPPrice(createPriceData(PriceDataType.FROM, allPricesInfos.get(allPricesInfos.size() - 1)));
		}

		productData.setPriceRange(priceRange);

	}

	public static class CSPPriceRangeComparator extends AbstractComparator<Prices>
	{
		public static final CSPPriceRangeComparator INSTANCE = new CSPPriceRangeComparator();

		@Override
		protected int compareInstances(final Prices price1, final Prices price2)
		{
			final double priceValue1 = Double.parseDouble(price1.getPrice());
			final double priceValue2 = Double.parseDouble(price2.getPrice());
			if (price1 == null)
			{
				return BEFORE;
			}
			if (price2 == null)
			{
				return AFTER;
			}

			return compareValues(priceValue1, priceValue2);
		}
	}

	/**
	 * @return the b2bCustomerService
	 */
	public B2BCustomerService getB2bCustomerService()
	{
		return b2bCustomerService;
	}

	/**
	 * @param b2bCustomerService
	 *           the b2bCustomerService to set
	 */
	public void setB2bCustomerService(final B2BCustomerService b2bCustomerService)
	{
		this.b2bCustomerService = b2bCustomerService;
	}

	/**
	 * @return the siteOnePriceWebService
	 */
	public SiteOnePriceWebService getSiteOnePriceWebService()
	{
		return siteOnePriceWebService;
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
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the userFacade
	 */
	public UserFacade getUserFacade()
	{
		return userFacade;
	}

	/**
	 * @param userFacade
	 *           the userFacade to set
	 */
	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}

}