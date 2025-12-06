/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.orderprocessing.model.OrderModificationProcessModel;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.util.List;

import org.apache.velocity.tools.generic.NumberTool;


/**
 * Velocity context for email about partially order refund
 */
public class OrderPartiallyRefundedEmailContext extends OrderPartiallyModifiedEmailContext
{
	private PriceData refundAmount;
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");


	@Override
	public void init(final OrderModificationProcessModel orderProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(orderProcessModel, emailPageModel);
		calculateRefundAmount();
		put("numberTool", new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
	}

	protected void calculateRefundAmount()
	{
		BigDecimal refundAmountValue = BigDecimal.ZERO;
		PriceData totalPrice = null;
		for (final OrderEntryData entryData : getRefundedEntries())
		{
			totalPrice = entryData.getTotalPrice();
			refundAmountValue = refundAmountValue.add(totalPrice.getValue());
		}

		if (totalPrice != null)
		{
			refundAmount = getPriceDataFactory().create(totalPrice.getPriceType(), refundAmountValue, totalPrice.getCurrencyIso());
		}
	}

	public List<OrderEntryData> getRefundedEntries()
	{
		return super.getModifiedEntries();
	}

	public PriceData getRefundAmount()
	{
		return refundAmount;
	}
}
