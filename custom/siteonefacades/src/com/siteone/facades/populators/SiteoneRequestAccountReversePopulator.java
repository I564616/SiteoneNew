/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.facades.constants.SiteoneFacadesConstants;


/**
 * @author SMondal
 *
 */
public class SiteoneRequestAccountReversePopulator implements Populator<SiteoneRequestAccountData, SiteoneRequestAccountModel>
{

	@Override
	public void populate(final SiteoneRequestAccountData source, final SiteoneRequestAccountModel target)
			throws ConversionException
	{
		if (!StringUtils.isBlank(source.getAccountNumber()))
		{
			target.setAccountNumber(source.getAccountNumber());
		}
		if (!StringUtils.isBlank(source.getAddressLine1()))
		{
			target.setAddressLine1(source.getAddressLine1());
		}
		if (!StringUtils.isBlank(source.getAddressLine2()))
		{
			target.setAddressLine2(source.getAddressLine2());
		}
		if (!StringUtils.isBlank(source.getCity()))
		{
			target.setCity(source.getCity());
		}
		if (!StringUtils.isBlank(source.getCompanyName()))
		{
			target.setCompanyName(source.getCompanyName());
		}
		if (!StringUtils.isBlank(source.getContrEmpCount()))
		{
			target.setContrEmpCount(source.getContrEmpCount());
		}
		if (!StringUtils.isBlank(source.getContrPrimaryBusiness()))
		{
			final String[] primaryBusiness = source.getContrPrimaryBusiness().split(SiteoneFacadesConstants.PIPE);
			final String[] primaryBusinessCodes = primaryBusiness[SiteoneFacadesConstants.ZERO].split(SiteoneFacadesConstants.SLASH);
			final String[] primaryBusinessNames = primaryBusiness[SiteoneFacadesConstants.ONE].split(SiteoneFacadesConstants.SLASH);
			if (primaryBusinessCodes.length == 2)
			{
				target.setContrPrimaryBusiness(primaryBusinessCodes[SiteoneFacadesConstants.ZERO]);
				target.setContrPrimaryChildBusiness(primaryBusinessCodes[SiteoneFacadesConstants.ONE]);
				target.setTradeClassName(primaryBusinessNames[SiteoneFacadesConstants.ZERO]);
				target.setSubTradeClassName(primaryBusinessNames[SiteoneFacadesConstants.ONE]);
			}
			else
			{
				target.setContrPrimaryBusiness(primaryBusinessCodes[SiteoneFacadesConstants.ZERO]);
				target.setContrPrimaryChildBusiness(primaryBusinessCodes[SiteoneFacadesConstants.ZERO]);
				target.setTradeClassName(primaryBusiness[SiteoneFacadesConstants.ONE]);
				target.setSubTradeClassName(primaryBusiness[SiteoneFacadesConstants.ONE]);
			}
		}
		if (!StringUtils.isBlank(source.getContrYearsInBusiness()))
		{
			target.setContrYearsInBusiness(source.getContrYearsInBusiness());
		}
		if (!StringUtils.isBlank(source.getEmailAddress()))
		{
			target.setEmailAddress(source.getEmailAddress());
		}
		if (!StringUtils.isBlank(source.getFirstName()))
		{
			target.setFirstName(source.getFirstName());
		}
		target.setHasAccountNumber(source.getHasAccountNumber());
		target.setIsAccountOwner(source.getIsAccountOwner());
		if (!StringUtils.isBlank(source.getLanguagePreference()))
		{
			target.setLanguagePreference(source.getLanguagePreference());
		}
		if (!StringUtils.isBlank(source.getLastName()))
		{
			target.setLastName(source.getLastName());
		}
		if (!StringUtils.isBlank(source.getPhoneNumber()))
		{
			target.setPhoneNumber(source.getPhoneNumber());
		}
		if (!StringUtils.isBlank(source.getState()))
		{
			target.setState(source.getState());
		}
		if (!StringUtils.isBlank(source.getTypeOfCustomer()))
		{
			target.setTypeOfCustomer(source.getTypeOfCustomer());
		}
		if (!StringUtils.isBlank(source.getUuid()))
		{
			target.setUuid(source.getUuid());
		}
		if (!StringUtils.isBlank(source.getUuidType()))
		{
			target.setUuidType(source.getUuid());
		}
		if (!StringUtils.isBlank(source.getZipcode()))
		{
			target.setZipcode(source.getZipcode());
		}
		if (!StringUtils.isBlank(source.getStoreNumber()))
		{
			target.setStoreNumber(source.getStoreNumber());
		}
		target.setEnrollInPartnersProgram(BooleanUtils.isTrue(source.getEnrollInPartnersProgram()));
		target.setLandscapingIndustry(BooleanUtils.isTrue(source.getLandscapingIndustry()));
		if (!StringUtils.isBlank(source.getTradeClass()))
		{
			target.setTradeClass(source.getTradeClass());
		}
		if (!StringUtils.isBlank(source.getSubTradeClass()))
		{
			target.setSubTradeClass(source.getSubTradeClass());
		}
		if (!StringUtils.isBlank(source.getCreditCode()))
		{
			target.setCreditCode(source.getCreditCode());
		}
		if (!StringUtils.isBlank(source.getCreditTerms()))
		{
			target.setCreditTerms(source.getCreditTerms());
		}
		if (!StringUtils.isBlank(source.getAcctGroupCode()))
		{
			target.setAcctGroupCode(source.getAcctGroupCode());
		}
	}

}
