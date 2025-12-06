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
package com.siteone.fulfilmentprocess.strategy.impl;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.strategy.AbstractSplittingStrategy;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.DiscountValue;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.google.common.math.DoubleMath;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.fulfilmentprocess.constants.SiteoneFulfilmentProcessConstants;


public class SplitByPoS extends AbstractSplittingStrategy
{
	private static final double EPSILON = 0.01d;
	public static final String QUOTE_DISCOUNT_CODE = "QuoteDiscount";

	@Override
	public Object getGroupingObject(final AbstractOrderEntryModel orderEntry)
	{
		return orderEntry.getDeliveryPointOfService();
	}

	@Override
	public void afterSplitting(final Object groupingObject, final ConsignmentModel consignmentModel)
	{
		consignmentModel.setDeliveryPointOfService((PointOfServiceModel) groupingObject);
		consignmentModel.setWarehouse(consignmentModel.getDeliveryPointOfService().getWarehouses().get(0));
		consignmentModel.setShippingAddress(consignmentModel.getDeliveryPointOfService().getAddress());
		final String consignmentCode = formatConsignmentCode(consignmentModel.getCode());
		consignmentModel.setCode(consignmentCode);
		int index = 0;

		final OrderTypeEnum orderType = consignmentModel.getOrder().getOrderType();
		final AbstractOrderModel order = consignmentModel.getOrder();
		for (final ConsignmentEntryModel entry : consignmentModel.getConsignmentEntries())
		{
			entry.setEntryNumber(String.valueOf(index++));

		}
		double tax = 0;
		double totalPrice = 0;
		double subTotal = 0;
		if (orderType == null)
		{
			for (final ConsignmentEntryModel consignmentEntry : consignmentModel.getConsignmentEntries())
			{
				tax += consignmentEntry.getOrderEntry().getTotaltax() != null ? consignmentEntry.getOrderEntry().getTotaltax() : 0;
				subTotal += consignmentEntry.getOrderEntry().getTotalPrice() != null
						? consignmentEntry.getOrderEntry().getTotalPrice()
						: 0;
				subTotal -= consignmentEntry.getOrderEntry().getDiscountAmount() != null
						? consignmentEntry.getOrderEntry().getDiscountAmount()
						: 0;
			}
			totalPrice = subTotal + tax;
			final boolean firstConsignment = isFirstFulfilmentConsignment(consignmentModel);
			if (SiteoneFulfilmentProcessConstants.DELIVERYMODE_DELIVERY
					.equalsIgnoreCase(consignmentModel.getDeliveryMode().getCode()) && order.getDeliveryFreight() != null
					&& firstConsignment)
			{
				totalPrice += Double.parseDouble(order.getDeliveryFreight());
			}
			else if (SiteoneFulfilmentProcessConstants.DELIVERYMODE_SHIPPING
					.equalsIgnoreCase(consignmentModel.getDeliveryMode().getCode()) && order.getShippingFreight() != null
					&& firstConsignment)
			{
				totalPrice += Double.parseDouble(order.getShippingFreight());
			}
		}
		else
		{
			final double orderDiscountAmount = getOrderDiscountsAmount(consignmentModel.getOrder());
			subTotal = order.getSubtotal().doubleValue() - orderDiscountAmount;
			tax = order.getTotalTax();
			totalPrice = subTotal + tax;
			if (order.getFreight() != null)
			{
				totalPrice += Double.valueOf(order.getFreight());
			}
		}
		consignmentModel.setTax(Double.toString(tax));
		consignmentModel.setSubTotal(Double.toString(subTotal));
		consignmentModel.setTotal(Double.toString(totalPrice));


	}

	public String formatConsignmentCode(final String code)
	{
		final NumberFormat nf = new DecimalFormat("000");
		final int iCode = code.charAt(0) - 'a' + 1;
		return code.substring(1) + "-" + nf.format(iCode);
	}

	protected double getOrderDiscountsAmount(final AbstractOrderModel source)
	{
		double discounts = 0.0d;
		final List<DiscountValue> discountList = source.getGlobalDiscountValues(); // discounts on the cart itself
		if (discountList != null && !discountList.isEmpty())
		{
			for (final DiscountValue discount : discountList)
			{
				final double value = discount.getAppliedValue();
				if (DoubleMath.fuzzyCompare(value, 0, EPSILON) > 0 && !QUOTE_DISCOUNT_CODE.equals(discount.getCode()))
				{
					discounts += value;
				}
			}
		}
		return discounts;
	}


	protected boolean isFirstFulfilmentConsignment(final ConsignmentModel source)
	{
		boolean firstConsignment = false;
		final OrderModel order = (OrderModel) source.getOrder();
		if (order.getOrderType() == null)
		{
			final String deliveryMode = source.getDeliveryMode().getCode();
			int minConsignmentCode = -1;
			final String consignmentCode = source.getCode();
			String firstConsignmentCode = null;
			for (final ConsignmentModel consignment : order.getConsignments())
			{
				if ((deliveryMode != null && deliveryMode.equals(consignment.getDeliveryMode().getCode()))
						&& (minConsignmentCode == -1 || (consignment.getCode() != null && consignment.getCode().split("-").length == 2
								&& minConsignmentCode > Integer.parseInt(consignment.getCode().split("-")[1]))))
				{
					minConsignmentCode = Integer.parseInt(consignment.getCode().split("-")[1]);
					firstConsignmentCode = consignment.getCode();
				}

			}
			if (firstConsignmentCode != null && firstConsignmentCode.equals(consignmentCode))
			{
				firstConsignment = true;
			}
		}

		return firstConsignment;
	}

}