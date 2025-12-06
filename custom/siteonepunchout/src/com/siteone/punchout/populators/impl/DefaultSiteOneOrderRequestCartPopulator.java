/*
 * [y] hybris Platform
 * 
 * Copyright (c) 2000-2019 SAP SE
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of SAP 
 * Hybris ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.punchout.populators.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.punchout.populators.impl.DefaultOrderRequestCartPopulator;
import de.hybris.platform.b2b.punchout.util.CXmlDateUtil;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jakarta.annotation.Resource;

import org.cxml.OrderRequestHeader;

import com.siteone.core.enums.MeridianTimeEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 */
public class DefaultSiteOneOrderRequestCartPopulator extends DefaultOrderRequestCartPopulator
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneOrderRequestCartPopulator.class);
	
	private CXmlDateUtil cXmlDateUtil;
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	@Resource(name = "siteOnePunchoutAddressPopulator")
	private DefaultSiteOnePunchoutAddressPopulator siteOnePunchoutAddressPopulator;
	
	
	@Override
	public void populate(final OrderRequestHeader source, final CartModel target) throws ConversionException
	{
		LOG.info("Within DefaultSiteOneOrderRequestCartPopulator.populate()");
		if (!StringUtils.equalsIgnoreCase(source.getType(), "new"))
		{
			throw new UnsupportedOperationException("Operation not supported yet: " + source.getType());
		}
		target.setPurchaseOrderNumber(source.getOrderID());

		if(source.getShipTo()!=null) {
			final AddressModel deliveryAddress = new AddressModel();
			getSiteOnePunchoutAddressPopulator().populate(source.getShipTo().getAddress(),deliveryAddress);
			deliveryAddress.setOwner(target);
			target.setDeliveryAddress(deliveryAddress);
		}
		if(source.getBillTo()!=null) {
			final AddressModel billToAddress = new AddressModel();
			getSiteOnePunchoutAddressPopulator().populate(source.getBillTo().getAddress(),billToAddress);
			billToAddress.setOwner(target);
			target.setPaymentAddress(billToAddress);
		}

		target.setDeliveryCost(getDeliveryCost(source));
		getTaxValuePopulator().populate(source.getTax(), target.getTotalTaxValues());
		target.setTotalTax(sumUpAllTaxes(target.getTotalTaxValues()));
		target.setTotalPrice(Double.valueOf(source.getTotal().getMoney().getvalue()));
		target.setSubtotal(Double.valueOf(source.getTotal().getMoney().getvalue()));
		target.setCurrency(getCommonI18NService().getCurrency(source.getTotal().getMoney().getCurrency()));
		target.setContactPerson((B2BCustomerModel)target.getUser());

		try
		{
			target.setDate(getcXmlDateUtil().parseString(source.getOrderDate()));
		}
		catch (final ParseException e)
		{
			throw new ConversionException("Could not parse date string: " + source.getOrderDate(), e);
		}
		target.setRequestedDate(getTomorrowDate());
		target.setRequestedMeridian(enumerationService.getEnumerationValue(MeridianTimeEnum.class, "PM"));
	}

	protected Date getTomorrowDate() 
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	/**
	 * @return the cXmlDateUtil
	 */
	public CXmlDateUtil getcXmlDateUtil()
	{
		return cXmlDateUtil;
	}

	/**
	 * @param cXmlDateUtil the cXmlDateUtil to set
	 */
	public void setcXmlDateUtil(CXmlDateUtil cXmlDateUtil)
	{
		this.cXmlDateUtil = cXmlDateUtil;
	}

	public DefaultSiteOnePunchoutAddressPopulator getSiteOnePunchoutAddressPopulator() {
		return siteOnePunchoutAddressPopulator;
	}

	public void setSiteOnePunchoutAddressPopulator(DefaultSiteOnePunchoutAddressPopulator siteOnePunchoutAddressPopulator) {
		this.siteOnePunchoutAddressPopulator = siteOnePunchoutAddressPopulator;
	}
	
	
}
