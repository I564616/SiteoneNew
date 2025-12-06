/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;
import java.util.Map;


/**
 * @author 1197861
 *
 */
public class RequestAccountEvent extends AbstractEvent /* AbstractCommerceUserEvent<BaseSiteModel> */
{
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
	private BaseStoreModel baseStore;
	private LanguageModel language;
	private Boolean isAccountOwner;
	private Boolean isCreateCustomerFail;
	private Boolean isEmailUniqueInHybris;
	private String serviceTypes;
	private String branchManagerEmail;
	private Boolean branchNotification;
	private Integer dunsResponse;
	private Map<Integer, List<String>> candidates;
	private String storeNumber;
	private Boolean enrollInPartnersProgram;


	/**
	 * @return the dunsResponse
	 */
	public Integer getDunsResponse()
	{
		return dunsResponse;
	}

	/**
	 * @param dunsResponse
	 *           the dunsResponse to set
	 */
	public void setDunsResponse(final Integer dunsResponse)
	{
		this.dunsResponse = dunsResponse;
	}

	/**
	 * @return the branchNotification
	 */
	public Boolean getBranchNotification()
	{
		return branchNotification;
	}

	/**
	 * @param branchNotification
	 *           the branchNotification to set
	 */
	public void setBranchNotification(final Boolean branchNotification)
	{
		this.branchNotification = branchNotification;
	}

	/**
	 * @return the candidates
	 */
	public Map<Integer, List<String>> getCandidates()
	{
		return candidates;
	}

	/**
	 * @param candidates
	 *           the candidates to set
	 */
	public void setCandidates(final Map<Integer, List<String>> candidates)
	{
		this.candidates = candidates;
	}

	/**
	 * @return the serviceTypes
	 */
	public String getServiceTypes()
	{
		return serviceTypes;
	}

	/**
	 * @param serviceTypes
	 *           the serviceTypes to set
	 */
	public void setServiceTypes(final String serviceTypes)
	{
		this.serviceTypes = serviceTypes;
	}

	/**
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return branchManagerEmail;
	}

	/**
	 * @param branchManagerEmail
	 *           the branchManagerEmail to set
	 */
	public void setBranchManagerEmail(final String branchManagerEmail)
	{
		this.branchManagerEmail = branchManagerEmail;
	}

	/**
	 * @return the phoneNumber
	 */
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
	 * @return the baseStore
	 */
	public BaseStoreModel getBaseStore()
	{
		return baseStore;
	}

	/**
	 * @param baseStore
	 *           the baseStore to set
	 */
	public void setBaseStore(final BaseStoreModel baseStore)
	{
		this.baseStore = baseStore;
	}

	/**
	 * @return the site
	 */
	public BaseSiteModel getSite()
	{
		return site;
	}

	/**
	 * @param site
	 *           the site to set
	 */
	public void setSite(final BaseSiteModel site)
	{
		this.site = site;
	}

	private BaseSiteModel site;

	private String companyName;

	/**
	 * @return the companyName
	 */
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
	 * @param contrPrimaryBusiness
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
	 * @return the language
	 */
	public LanguageModel getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *           the language to set
	 */
	public void setLanguage(final LanguageModel language)
	{
		this.language = language;
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
	 * @return the isCreateCustomerFail
	 */
	public Boolean getIsCreateCustomerFail()
	{
		return isCreateCustomerFail;
	}

	/**
	 * @param isCreateCustomerFail
	 *           the isCreateCustomerFail to set
	 */
	public void setIsCreateCustomerFail(final Boolean isCreateCustomerFail)
	{
		this.isCreateCustomerFail = isCreateCustomerFail;
	}

	/**
	 * @return the isEmailUniqueInHybris
	 */
	public Boolean getIsEmailUniqueInHybris()
	{
		return isEmailUniqueInHybris;
	}

	/**
	 * @param isEmailUniqueInHybris
	 *           the isEmailUniqueInHybris to set
	 */
	public void setIsEmailUniqueInHybris(final Boolean isEmailUniqueInHybris)
	{
		this.isEmailUniqueInHybris = isEmailUniqueInHybris;
	}

	public String getStoreNumber()
	{
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber)
	{
		this.storeNumber = storeNumber;
	}

	public Boolean getEnrollInPartnersProgram()
	{
		return enrollInPartnersProgram;
	}

	public void setEnrollInPartnersProgram(Boolean enrollInPartnersProgram)
	{
		this.enrollInPartnersProgram = enrollInPartnersProgram;
	}
	

}