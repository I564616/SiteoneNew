  
/**
 *
 */
package com.siteone.utils;

import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;

import com.siteone.commerceservices.dto.account.SiteOneAddressFormWsDTO;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;


/**
 * @author pelango
 *
 */
public class SiteOneAddressDataUtil
{
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
	private final String COUNTRY_CODE = "US";

	public void convertBasic(final AddressData source, final SiteOneAddressForm target)
	{
		target.setAddressId(source.getId());
		target.setProjectName(source.getProjectName());
		target.setCompanyName(source.getCompanyName());
		target.setDeliveryInstructions(source.getDeliveryInstructions());
		target.setTitleCode(source.getTitleCode());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setLine1(source.getLine1());
		target.setLine2(source.getLine2());
		target.setTownCity(source.getTown());
		target.setPostcode(source.getPostalCode());
		target.setCountryIso(source.getCountry().getIsocode());
		target.setUnitId(source.getUnitId());
	}

	public void convert(final AddressData source, final SiteOneAddressForm target)
	{
		convertBasic(source, target);
		target.setSaveInAddressBook(Boolean.valueOf(source.isVisibleInAddressBook()));
		target.setShippingAddress(Boolean.valueOf(source.isShippingAddress()));
		target.setBillingAddress(Boolean.valueOf(source.isBillingAddress()));
		target.setPhone(source.getPhone());

		if (source.getRegion() != null && !StringUtils.isEmpty(source.getRegion().getIsocode()))
		{
			target.setRegionIso(source.getRegion().getIsocode());
		}
	}

	public AddressData convertToVisibleAddressData(final SiteOneAddressForm addressForm)
	{
		final AddressData addressData = convertToAddressData(addressForm);
		addressData.setVisibleInAddressBook(true);
		addressData.setDefaultAddress(addressForm.getDefaultAddress() != null && addressForm.getDefaultAddress().booleanValue());
		return addressData;
	}

	public AddressData convertToAddressData(final SiteOneAddressForm addressForm)
	{
		final AddressData addressData = new AddressData();
		addressData.setId(addressForm.getAddressId());
		addressData.setTitleCode(addressForm.getTitleCode());
		addressData.setFirstName(addressForm.getFirstName());
		addressData.setLastName(addressForm.getLastName());
		addressData.setLine1(addressForm.getLine1());
		addressData.setLine2(addressForm.getLine2());
		addressData.setTown(addressForm.getTownCity());
		addressData.setPostalCode(addressForm.getPostcode());
		addressData.setBillingAddress(false);
		addressData.setShippingAddress(true);
		addressData.setPhone(addressForm.getPhone());
		addressData.setCompanyName(addressForm.getCompanyName());
		addressData.setProjectName(addressForm.getProjectName());
		addressData.setDeliveryInstructions(addressForm.getDeliveryInstructions());
		addressData.setUnitId(addressForm.getUnitId());
		addressData.setDistrict(addressForm.getDistrict());
		if (addressForm.getCountryIso() != null)
		{
			final CountryData countryData = getI18NFacade().getCountryForIsocode(addressForm.getCountryIso());
			addressData.setCountry(countryData);
		}
		if (addressForm.getRegionIso() != null && !StringUtils.isEmpty(addressForm.getRegionIso()))
		{
			final RegionData regionData = getI18NFacade().getRegion(addressForm.getCountryIso(), addressForm.getRegionIso());
			addressData.setRegion(regionData);
		}

		return addressData;
	}
	
	public SiteOneAddressFormWsDTO convertToAddressFormDTO(final SiteOneAddressForm addressForm)
	{
		final SiteOneAddressFormWsDTO siteOneAddressFormWsDTO = new SiteOneAddressFormWsDTO();
		siteOneAddressFormWsDTO.setTitleCode(addressForm.getTitleCode());
		siteOneAddressFormWsDTO.setFirstName(addressForm.getFirstName());
		siteOneAddressFormWsDTO.setLastName(addressForm.getLastName());
		siteOneAddressFormWsDTO.setLine1(addressForm.getLine1());
		siteOneAddressFormWsDTO.setLine2(addressForm.getLine2());
		siteOneAddressFormWsDTO.setTownCity(addressForm.getTownCity());
		siteOneAddressFormWsDTO.setPostcode(addressForm.getPostcode());
		siteOneAddressFormWsDTO.setPhone(addressForm.getPhone());
		siteOneAddressFormWsDTO.setCompanyName(addressForm.getCompanyName());
		siteOneAddressFormWsDTO.setProjectName(addressForm.getProjectName());
		siteOneAddressFormWsDTO.setDeliveryInstructions(addressForm.getDeliveryInstructions());
		siteOneAddressFormWsDTO.setUnitId(addressForm.getUnitId());
		siteOneAddressFormWsDTO.setDistrict(addressForm.getDistrict());
		siteOneAddressFormWsDTO.setAddressId(addressForm.getAddressId());
		if (addressForm.getCountryIso() != null)
		{
			siteOneAddressFormWsDTO.setCountryIso(addressForm.getCountryIso());
		}
		if (addressForm.getRegionIso() != null && !StringUtils.isEmpty(addressForm.getRegionIso()))
		{
			siteOneAddressFormWsDTO.setRegionIso(addressForm.getRegionIso());
		}
		if(addressForm.getSaveInAddressBook() != null)
		{
			siteOneAddressFormWsDTO.setSaveInAddressBook(addressForm.getSaveInAddressBook());			
		}
		if(addressForm.getDefaultAddress() != null)
		{
			siteOneAddressFormWsDTO.setDefaultAddress(addressForm.getDefaultAddress());			
		}
		if(addressForm.getShippingAddress() != null)
		{
			siteOneAddressFormWsDTO.setShippingAddress(addressForm.getShippingAddress());			
		}
		if(addressForm.getBillingAddress() != null)
		{
			siteOneAddressFormWsDTO.setBillingAddress(addressForm.getBillingAddress());			
		}
		if(addressForm.getEditAddress() != null)
		{
			siteOneAddressFormWsDTO.setEditAddress(addressForm.getEditAddress());			
		}
		
		return siteOneAddressFormWsDTO;		
	}

	public AddressData convertToAddressData(final SiteOneGCContactForm siteOneGCContactForm)
	{
		final AddressData addressData = new AddressData();
	   	addressData.setFirstName(siteOneGCContactForm.getFirstName());
	   	addressData.setLastName(siteOneGCContactForm.getLastName());
	   	addressData.setEmail(siteOneGCContactForm.getEmail());
	   	addressData.setLine1(siteOneGCContactForm.getAddressLine1());
	   	addressData.setLine2(siteOneGCContactForm.getAddressLine2());
	   	addressData.setCompanyName(siteOneGCContactForm.getCompanyName());
	   	addressData.setDistrict(siteOneGCContactForm.getState());
	   	addressData.setPhone(siteOneGCContactForm.getPhone());
	   	final String regionCode = COUNTRY_CODE + "-" + siteOneGCContactForm.getState();
	   	final CountryData countryData = new CountryData();
	   	countryData.setIsocode(COUNTRY_CODE);
	   	addressData.setCountry(countryData);
	   	final RegionData regionData = new RegionData();
	   	regionData.setCountryIso(COUNTRY_CODE);
	   	regionData.setIsocodeShort(siteOneGCContactForm.getState());
	   	regionData.setIsocode(regionCode);
	   	addressData.setRegion(regionData);
	   	addressData.setTown(siteOneGCContactForm.getCity());
	   	addressData.setPostalCode(siteOneGCContactForm.getZip());

	   	return addressData;
	}
	
	public AddressData convertToAddressData(final SiteOneRequestAccountForm siteOneRequestAccountForm)
	{
		final AddressData addressData = new AddressData();
	   	addressData.setFirstName(siteOneRequestAccountForm.getFirstName());
	   	addressData.setLastName(siteOneRequestAccountForm.getLastName());
	   	addressData.setEmail(siteOneRequestAccountForm.getEmailAddress());
	   	addressData.setLine1(siteOneRequestAccountForm.getAddressLine1());
	   	addressData.setLine2(siteOneRequestAccountForm.getAddressLine2());
	   	addressData.setCompanyName(siteOneRequestAccountForm.getCompanyName());
	   	addressData.setDistrict(siteOneRequestAccountForm.getState());
	   	addressData.setPhone(siteOneRequestAccountForm.getPhoneNumber());
	   	final String regionCode = COUNTRY_CODE + "-" + siteOneRequestAccountForm.getState();
	   	final CountryData countryData = new CountryData();
	   	countryData.setIsocode(COUNTRY_CODE);
	   	addressData.setCountry(countryData);
	   	final RegionData regionData = new RegionData();
	   	regionData.setCountryIso(COUNTRY_CODE);
	   	regionData.setIsocodeShort(siteOneRequestAccountForm.getState());
	   	regionData.setIsocode(regionCode);
	   	addressData.setRegion(regionData);
	   	addressData.setTown(siteOneRequestAccountForm.getCity());
	   	addressData.setPostalCode(siteOneRequestAccountForm.getZipcode());
	
	   	return addressData;
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

	/**
	 * @return the i18NFacade
	 */
	public I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	/**
	 * @param i18nFacade
	 *           the i18NFacade to set
	 */
	public void setI18NFacade(final I18NFacade i18nFacade)
	{
		i18NFacade = i18nFacade;
	}

}