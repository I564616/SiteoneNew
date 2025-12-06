/**
 *
 */
package com.siteone.storefront.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


/**
 * @author 1197861
 *
 */
public class SiteOneRequestAccountForm
{
	private String companyName;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
	private String phoneNumber;
	private String emailAddress;
	private Boolean hasAccountNumber;
	private String typeOfCustomer;
	private String contrYearsInBusiness;
	private String contrEmpCount;
	private String contrPrimaryBusiness;
	private String languagePreference;
	private Boolean isAccountOwner;
	private String uuid;
	private String ueType;
	private String storeNumber;
	private Boolean enrollInPartnersProgram;
	private Boolean landscapingIndustry;

	/**
	 * @return the companyName
	 */
	@NotNull(message = "{request.companyname.invalid}")
	@Size(min = 1, max = 255, message = "{request.companyname.invalid}")
	public String getCompanyName()
	{
		return companyName;
	}

	/**
	 * @param companyName
	 *           the companyName to set
	 */
	public void setCompanyName(final String companyName)
	{
		this.companyName = companyName;
	}

	/**
	 * @return the accountNumber
	 */
	@NotNull(message = "{request.accountnumber.invalid}")
	@Size(min = 1, max = 255, message = "{request.accountnumber.invalid}")
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *           the accountNumber to set
	 */
	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the firstName
	 */
	@NotNull(message = "{request.firstName.invalid}")
	@Size(min = 1, max = 255, message = "{profile.firstName.invalid}")
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@NotNull(message = "{request.lastName.invalid}")
	@Size(min = 1, max = 255, message = "{profile.lastName.invalid}")
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *           the lastName to set
	 */
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return the addressLine1
	 */
	@NotNull(message = "{request.address1.invalid}")
	@Size(min = 1, max = 255, message = "{request.address1.invalid}")
	public String getAddressLine1()
	{
		return addressLine1;
	}

	/**
	 * @param addressLine1
	 *           the addressLine1 to set
	 */
	public void setAddressLine1(final String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2()
	{
		return addressLine2;
	}

	/**
	 * @param addressLine2
	 *           the addressLine2 to set
	 */
	public void setAddressLine2(final String addressLine2)
	{
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the city
	 */
	@NotNull(message = "{request.city.invalid}")
	@Size(min = 1, max = 255, message = "{request.city.invalid}")
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *           the city to set
	 */
	public void setCity(final String city)
	{
		this.city = city;
	}

	/**
	 * @return the state
	 */
	@NotNull(message = "{request.state.invalid}")
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *           the state to set
	 */
	public void setState(final String state)
	{
		this.state = state;
	}

	/**
	 * @return the zipcode
	 */
	@NotNull(message = "{request.zipcode.invalid}")
	@Size(min = 1, max = 255, message = "{request.zipcode.invalid}")
	public String getZipcode()
	{
		return zipcode;
	}

	/**
	 * @param zipcode
	 *           the zipcode to set
	 */
	public void setZipcode(final String zipcode)
	{
		this.zipcode = zipcode;
	}



	/**
	 * @return the emailAddress
	 */
	@NotNull(message = "{request.emailAddress.invalid}")
	@Size(min = 1, max = 255, message = "{request.emailAddress.invalid}")
	@Email(message = "{request.emailAddress.invalid}")
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *           the emailAddress to set
	 */
	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the hasAccountNumber
	 */
	public Boolean getHasAccountNumber()
	{
		return hasAccountNumber;
	}

	/**
	 * @param hasAccountNumber
	 *           the hasAccountNumber to set
	 */
	public void setHasAccountNumber(final Boolean hasAccountNumber)
	{
		this.hasAccountNumber = hasAccountNumber;
	}

	/**
	 * @return the phoneNumber
	 */
	@NotNull(message = "{request.phonenumber.invalid}")
	@Size(min = 1, max = 14, message = "{request.phonenumber.invalid}")
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *           the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer()
	{
		return typeOfCustomer;
	}

	/**
	 * @param typeOfCustomer
	 *           the typeOfCustomer to set
	 */
	public void setTypeOfCustomer(final String typeOfCustomer)
	{
		this.typeOfCustomer = typeOfCustomer;
	}

	/**
	 * @return the contrYearsInBusiness
	 */
	@NotNull(message = "{request.contryearsinbusiness.invalid}")
	public String getContrYearsInBusiness()
	{
		return contrYearsInBusiness;
	}

	/**
	 * @param contrYearsInBusiness
	 *           the contrYearsInBusiness to set
	 */
	public void setContrYearsInBusiness(final String contrYearsInBusiness)
	{
		this.contrYearsInBusiness = contrYearsInBusiness;
	}

	/**
	 * @return the contrEmpCount
	 */
	@NotNull(message = "{request.contrempcount.invalid}")
	public String getContrEmpCount()
	{
		return contrEmpCount;
	}

	/**
	 * @param contrEmpCount
	 *           the contrEmpCount to set
	 */
	public void setContrEmpCount(final String contrEmpCount)
	{
		this.contrEmpCount = contrEmpCount;
	}

	/**
	 * @return the contrPrimaryBusiness
	 */
	public String getContrPrimaryBusiness()
	{
		return contrPrimaryBusiness;
	}

	/**
	 * @param contrPrimaryBuss
	 *           the contrPrimaryBusiness to set
	 */
	public void setContrPrimaryBusiness(final String contrPrimaryBusiness)
	{
		this.contrPrimaryBusiness = contrPrimaryBusiness;
	}

	/**
	 * @return the languagePreference
	 */
	public String getLanguagePreference()
	{
		return languagePreference;
	}

	/**
	 * @param languagePreference
	 *           the languagePreference to set
	 */
	public void setLanguagePreference(final String languagePreference)
	{
		this.languagePreference = languagePreference;
	}

	/**
	 * @return the isAccountOwner
	 */
	public Boolean getIsAccountOwner()
	{
		return isAccountOwner;
	}

	/**
	 * @param isAccountOwner
	 *           the isAccountOwner to set
	 */
	public void setIsAccountOwner(final Boolean isAccountOwner)
	{
		this.isAccountOwner = isAccountOwner;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid
	 *           the uuid to set
	 */
	public void setUuid(final String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return the storeNumber
	 */
	public String getStoreNumber()
	{
		return storeNumber;
	}

	/**
	 * @param storeNumber
	 *           the storeNumber to set
	 */
	public void setStoreNumber(final String storeNumber)
	{
		this.storeNumber = storeNumber;
	}

	public Boolean getEnrollInPartnersProgram()
	{
		return enrollInPartnersProgram;
	}

	public void setEnrollInPartnersProgram(final Boolean enrollInPartnersProgram)
	{
		this.enrollInPartnersProgram = enrollInPartnersProgram;
	}
	
	/**
	 * @return the landscapingIndustry
	 */
	public Boolean getLandscapingIndustry()
	{
		return landscapingIndustry;
	}

	/**
	 * @param landscapingIndustry the landscapingIndustry to set
	 */
	public void setLandscapingIndustry(Boolean landscapingIndustry)
	{
		this.landscapingIndustry = landscapingIndustry;
	}

	/**
	 * @return the ueType
	 */
	public String getUeType()
	{
		return ueType;
	}

	/**
	 * @param ueType
	 *           the ueType to set
	 */
	public void setUeType(final String ueType)
	{
		this.ueType = ueType;
	}



}
