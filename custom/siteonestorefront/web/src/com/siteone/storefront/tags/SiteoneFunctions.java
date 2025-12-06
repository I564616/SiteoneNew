/**
 *
 */
package com.siteone.storefront.tags;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.PromotionOrderEntryConsumedData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.commercefacades.storelocator.data.OpeningScheduleData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;


/**
 * @author 1219341
 *
 */
public final class SiteoneFunctions
{

	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);

	private SiteoneFunctions()
	{
	}

	public static String getJSONFromList(final List<String> myStoreIdList)
	{
		if (CollectionUtils.isNotEmpty(myStoreIdList))
		{
			//final String json = new Gson().toJson(myStoreIdList);

			//return json;
			return new Gson().toJson(myStoreIdList);
		}
		return null;
	}

	public static String contructJSONForOpeningSchedule(final OpeningScheduleData openingScheduleData)
	{
		if (openingScheduleData != null)
		{
			return new Gson().toJson(openingScheduleData);
		}
		return null;
	}

	public static String contructJSONForCustomers(final Set<CustomerData> customers)
	{
		if (CollectionUtils.isNotEmpty(customers))
		{
			return new Gson().toJson(customers);
		}
		return null;
	}

	public static String contructJSONForCustomer(final CustomerData customer)
	{
		if (null != customer)
		{
			return new Gson().toJson(customer);
		}
		return null;
	}

	public static String contructJSONForaddresses(final Collection<AddressData> addresses)
	{
		if (CollectionUtils.isNotEmpty(addresses))
		{
			return new Gson().toJson(addresses);
		}
		return null;
	}

	public static ImageData getImageForTypeAndFormat(final Collection<ImageData> images, final String format)
	{
		if (images != null && !images.isEmpty())
		{
			for (final ImageData image : images)
			{
				if (ImageDataType.PRIMARY.equals(image.getImageType()) && format.equals(image.getFormat()))
				{
					return image;
				}
			}
		}
		return null;
	}

	/**
	 * This method converts the unit Id as display unit Id. i.e Remove the division from unit ID.
	 *
	 * @param unitId
	 *           - actual unit id in hybris DB
	 * @return displayUnitId - unit id for display purpose
	 */
	public static String getAccountNumberWithoutDivision(final String unitId)
	{

		String displayUnitId = "";

		if (null != unitId)
		{
			displayUnitId = StringUtils.substringBeforeLast(unitId.trim(), "_");
		}

		return displayUnitId;

	}



	public static boolean isAppliedPromotionExistForOrderEntry(final AbstractOrderData cart, final int entryNumber)
	{
		return cart != null && doesPromotionExistForOrderEntry(cart.getAppliedProductPromotions(), entryNumber);
	}


	public static boolean doesPromotionExistForOrderEntry(final List<PromotionResultData> productPromotions, final int entryNumber)
	{
		if (productPromotions != null && !productPromotions.isEmpty())
		{
			final Integer entryNumberToFind = Integer.valueOf(entryNumber);

			//Iterate over all promotion results and return true if any results match the entryNumber
			//Only exit the loop in the event of a match or all results returning false
			for (final PromotionResultData productPromotion : productPromotions)
			{
				if (StringUtils.isNotBlank(productPromotion.getDescription())
						&& doesPromotionExistForOrderEntry(entryNumberToFind, productPromotion))
				{
					return true;
				}
			}
		}
		return false;
	}

	protected static boolean doesPromotionExistForOrderEntry(final Integer entryNumberToFind,
			final PromotionResultData productPromotion)
	{
		final List<PromotionOrderEntryConsumedData> consumedEntries = productPromotion.getConsumedEntries();
		if (consumedEntries != null && !consumedEntries.isEmpty())
		{
			for (final PromotionOrderEntryConsumedData consumedEntry : consumedEntries)
			{
				if (entryNumberToFind.equals(consumedEntry.getOrderEntryNumber()))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isPromotionAppliedWithAdjustedPrice(final Double totalPrice, final PriceData basePrice,
			final Double adjustedPrice)
	{
		BigDecimal price = basePrice.getValue();
		price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (totalPrice.equals(price.doubleValue()) || (Double.compare(adjustedPrice, price.doubleValue()) == 0))
		{
			return true;
		}

		else
		{
			return false;
		}

	}

	public static Double cartEntryTotalPromotionPrice(final Integer quantity, final PriceData totalPrice)
	{
		final BigDecimal price = totalPrice.getValue();
		final double finalPriceforQty = price.doubleValue() / quantity;
		BigDecimal formattedFinalPrice = BigDecimal.valueOf(finalPriceforQty);
		formattedFinalPrice = formattedFinalPrice.setScale(CURRENCY_UNIT_PRICE_DIGITS, BigDecimal.ROUND_HALF_UP);
		return formattedFinalPrice.doubleValue();
	}
}
