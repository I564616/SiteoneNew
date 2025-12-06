/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.data.PromotionOrderEntryConsumedData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ValidationException;

import org.apache.log4j.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.siteone.integration.jobs.promotions.data.action.PromotionContainerAction;
import com.siteone.integration.jobs.promotions.data.action.PromotionFixedPriceAction;


/**
 * @author 1091124
 *
 */
public final class PromotionPriceUtils
{
	private static final Logger LOG = Logger.getLogger(PromotionPriceUtils.class);


	/**
	 * Private constructor for avoiding instantiation.
	 */
	private PromotionPriceUtils()
	{
		//empty
	}

	public static String getPromotionPrice(final PromotionSourceRuleModel promotionSourceRule, final ProductData productData)
	{
		BigDecimal basePrice = null;
		if (null != productData.getCustomerPrice())
		{
			basePrice = productData.getCustomerPrice().getValue();
		}
		else
		{
			basePrice = productData.getPrice().getValue();
		}
		final Type actionListType = new TypeToken<ArrayList<PromotionFixedPriceAction>>()
		{}.getType();
		String salePrice = null;
		final Type containerActionListType = new TypeToken<ArrayList<PromotionContainerAction>>()
		{}.getType();

		try
		{
			final Gson gson = new Gson();
			if (!promotionSourceRule.getConditions().contains("y_qualifying_coupons"))
			{
				if (promotionSourceRule.getActions().contains("y_order_entry_percentage_discount"))
				{
					final List<PromotionContainerAction> promotionContainerAction = gson.fromJson(promotionSourceRule.getActions(),
							containerActionListType);
					if (promotionContainerAction.get(0).getDefinitionId().equalsIgnoreCase("y_order_entry_percentage_discount"))
					{
						final PromotionContainerAction.Parameters param = promotionContainerAction.get(0).getParameters();
						if (null != param.getValue())
						{
							final String price = promotionContainerAction.get(0).getParameters().getValue().getValue().toString();
							//basePrice = basePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
							final BigDecimal discountPromoPrice = new BigDecimal(price);
							final BigDecimal discountPrice = discountPromoPrice.multiply(basePrice).divide(BigDecimal.valueOf(100));
							//discountPrice = discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal salePromotionPrice = basePrice.subtract(discountPrice);
							salePromotionPrice = salePromotionPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
							salePrice = String.valueOf(salePromotionPrice);

						}

					}
				}
				else
				{
					if (promotionSourceRule.getActions().contains("y_order_entry_fixed_price")
							|| promotionSourceRule.getActions().contains("y_order_entry_fixed_discount"))
					{

						final List<PromotionFixedPriceAction> promotionFixedPriceAction = gson
								.fromJson(promotionSourceRule.getActions(), actionListType);

						if (promotionFixedPriceAction.get(0).getDefinitionId().equalsIgnoreCase("y_order_entry_fixed_price"))
						{
							final PromotionFixedPriceAction.Parameters param = promotionFixedPriceAction.get(0).getParameters();
							if (null != param.getValue())
							{
								salePrice = promotionFixedPriceAction.get(0).getParameters().getValue().getValue().getUSD().toString();
							}
						}
						else if (promotionFixedPriceAction.get(0).getDefinitionId().equalsIgnoreCase("y_order_entry_fixed_discount"))
						{
							final PromotionFixedPriceAction.Parameters param = promotionFixedPriceAction.get(0).getParameters();
							if (null != param.getValue())
							{
								final String price = promotionFixedPriceAction.get(0).getParameters().getValue().getValue().getUSD()
										.toString();
								final BigDecimal fixedPromoPrice = new BigDecimal(price);
								salePrice = String.valueOf(basePrice.subtract(fixedPromoPrice));


							}
						}

					}
				}
			}
			if (salePrice != null && salePrice.contains("-"))
			{
				salePrice = null;
			}
		}


		catch (final JsonSyntaxException jsonSyntaxException)
		{
			LOG.error("Exception occured while parsing json", jsonSyntaxException);
		}
		catch (final JsonParseException jsonParseException)
		{
			LOG.error("Exception occured in promotion feed File ", jsonParseException);
		}

		return salePrice;

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
		formattedFinalPrice = formattedFinalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		return formattedFinalPrice.doubleValue();
	}

	public static void findSalePriceForOrderEntries(final List<OrderEntryData> entries,
			final List<PromotionResultData> appliedPromotions)
	{
		Double saleprice = null;
		for (final OrderEntryData orderEntryData : entries)
		{
			for (final PromotionResultData promotionResultData : appliedPromotions)
			{
				final List<PromotionOrderEntryConsumedData> consumedEntries = promotionResultData.getConsumedEntries();
				if (StringUtils.isNotBlank(promotionResultData.getDescription())
						&& doesPromotionExistForOrderEntry(orderEntryData.getEntryNumber(), promotionResultData))
				{

					for (final PromotionOrderEntryConsumedData consumedEntry : consumedEntries)
					{
						if (consumedEntry.getOrderEntryNumber() == orderEntryData.getEntryNumber())
						{
							saleprice = PromotionPriceUtils.cartEntryTotalPromotionPrice(orderEntryData.getQuantity().intValue(),
									orderEntryData.getTotalPrice());

							if (null != saleprice)
							{
								orderEntryData.setSaleprice(saleprice);
							}
							orderEntryData.setIsPromotionApplied(PromotionPriceUtils.isPromotionAppliedWithAdjustedPrice(saleprice,
									orderEntryData.getBasePrice(), consumedEntry.getAdjustedUnitPrice()));
						}
					}

				}
			}
		}
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

}



