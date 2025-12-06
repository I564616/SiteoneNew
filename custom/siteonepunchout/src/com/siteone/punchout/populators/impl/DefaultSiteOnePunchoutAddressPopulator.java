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

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cxml.Address;
import org.cxml.CountryCode;
import org.cxml.Email;
import org.cxml.Fax;
import org.cxml.Phone;
import org.cxml.PostalAddress;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import de.hybris.platform.b2b.punchout.PunchOutException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

/**
 *
 */
public class DefaultSiteOnePunchoutAddressPopulator implements Populator<Address, AddressModel>
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePunchoutAddressPopulator.class);
	
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	private String phoneSegmentSeparator = "-";

	@Override
	public void populate(final Address source, final AddressModel target) throws ConversionException
	{
		final PostalAddress postalAddress = source.getPostalAddress();

		if (postalAddress.getCountry() == null || postalAddress.getState() == null || postalAddress.getStreet() == null || postalAddress.getCity() == null)
		{
			throw new PunchOutException(HttpStatus.BAD_REQUEST, "Miss required information in address");
		}
		target.setCompany(source.getName().getvalue());
		final String countryIsoCode = postalAddress.getCountry().getIsoCountryCode();
		final String stateIsoCode = countryIsoCode + "-" + postalAddress.getState().getvalue();
		if(!CollectionUtils.isEmpty(postalAddress.getStreet())) {
			postalAddress.getStreet().forEach(street->{
				if(StringUtils.isNotEmpty(street.getvalue())) {
					if(target.getStreetname()==null) {
						target.setStreetname(street.getvalue());
						target.setLine1(street.getvalue());
					}else {
						target.setLine2(street.getvalue());
					}
				}
			});
		}
		if(!CollectionUtils.isEmpty(postalAddress.getDeliverTo())) {
			postalAddress.getDeliverTo().forEach(name->{
				if(StringUtils.isNotEmpty(name.getvalue())) {
					if(target.getFirstname()==null) {
						target.setFirstname(name.getvalue());
					}else {
						target.setLastname(name.getvalue());
					}
				}
			});
		}
		target.setTown(postalAddress.getCity().getvalue());
		target.setCountry(getCommonI18NService().getCountry(countryIsoCode));
		target.setPostalcode(postalAddress.getPostalCode());
		target.setRegion(getCommonI18NService().getRegion(target.getCountry(), stateIsoCode));

		target.setPhone1(toPhoneString(source.getPhone()));
		target.setFax(toFaxString(source.getFax()));
		target.setEmail(toEmailString(source.getEmail()));
	}

	protected String toEmailString(final Email email)
	{
		if (email != null)
		{
			return email.getvalue();
		}
		return null;
	}

	protected String toFaxString(final Fax fax)
	{
		if (fax != null)
		{
			for (final Object obj : fax.getTelephoneNumberOrURLOrEmail())
			{
				if (obj instanceof Phone)
				{
					return toPhoneString((Phone) obj);
				}
			}
		}
		return null;
	}

	protected String toPhoneString(final Phone phone)
	{
		if (phone != null && phone.getTelephoneNumber() != null)
		{
			final String areaCode = phone.getTelephoneNumber().getAreaOrCityCode();
			final String number = phone.getTelephoneNumber().getNumber();
			final String extension = phone.getTelephoneNumber().getExtension();

			final StringBuilder phoneNumberBuilder = new StringBuilder();
			phoneNumberBuilder.append(areaCode);
			phoneNumberBuilder.append(getPhoneSegmentSeparator());
			phoneNumberBuilder.append(number);
			if (StringUtils.isNotBlank(extension))
			{
				phoneNumberBuilder.append(" ext. ");
				phoneNumberBuilder.append(extension);
			}
			return phoneNumberBuilder.toString();
		}
		return null;
	}

	protected String getPhoneSegmentSeparator()
	{
		return phoneSegmentSeparator;
	}

	public void setPhoneSegmentSeparator(final String phoneSegmentSeparator)
	{
		this.phoneSegmentSeparator = phoneSegmentSeparator;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}
	
	
}
