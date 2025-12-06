/**
 *
 */
package com.siteone.storefront.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;



/**
 * @author 1003567
 *
 */
public class SiteOnePointsForEquipmentForm
{
	private String dealerContactName;
	private String dealerName;
	private String dealerAddressLine1;
	private String dealerAddressLine2;
	private String dealerCity;
	private String dealerState;
	private String dealerZipCode;
	private String customerContactName;
	private String companyName;
	private String jdlAccountNumber;
	private String customerAddressLine1;
	private String customerAddressLine2;
	private String customerCity;
	private String customerState;
	private String customerZipCode;
	private String emailAddress;
	private String phoneNumber;
	private String faxNumber;
	private String dateOfPurProduct1;
	private String dateOfPurProduct2;
	private String dateOfPurProduct3;
	private String dateOfPurProduct4;
	private String dateOfPurProduct5;
	private String itemDescProduct1;
	private String itemDescProduct2;
	private String itemDescProduct3;
	private String itemDescProduct4;
	private String itemDescProduct5;
	private String serialNumberProduct1;
	private String serialNumberProduct2;
	private String serialNumberProduct3;
	private String serialNumberProduct4;
	private String serialNumberProduct5;
	private String invoiceCostProduct1;
	private String invoiceCostProduct2;
	private String invoiceCostProduct3;
	private String invoiceCostProduct4;
	private String invoiceCostProduct5;



	/**
	 * @return the dateOfPurProduct1
	 */
	public String getDateOfPurProduct1()
	{
		return dateOfPurProduct1;
	}

	/**
	 * @param dateOfPurProduct1
	 *           the dateOfPurProduct1 to set
	 */
	public void setDateOfPurProduct1(final String dateOfPurProduct1)
	{
		this.dateOfPurProduct1 = dateOfPurProduct1;
	}

	/**
	 * @return the dateOfPurProduct2
	 */
	public String getDateOfPurProduct2()
	{
		return dateOfPurProduct2;
	}

	/**
	 * @param dateOfPurProduct2
	 *           the dateOfPurProduct2 to set
	 */
	public void setDateOfPurProduct2(final String dateOfPurProduct2)
	{
		this.dateOfPurProduct2 = dateOfPurProduct2;
	}

	/**
	 * @return the dateOfPurProduct3
	 */
	public String getDateOfPurProduct3()
	{
		return dateOfPurProduct3;
	}

	/**
	 * @param dateOfPurProduct3
	 *           the dateOfPurProduct3 to set
	 */
	public void setDateOfPurProduct3(final String dateOfPurProduct3)
	{
		this.dateOfPurProduct3 = dateOfPurProduct3;
	}

	/**
	 * @return the dateOfPurProduct4
	 */
	public String getDateOfPurProduct4()
	{
		return dateOfPurProduct4;
	}

	/**
	 * @param dateOfPurProduct4
	 *           the dateOfPurProduct4 to set
	 */
	public void setDateOfPurProduct4(final String dateOfPurProduct4)
	{
		this.dateOfPurProduct4 = dateOfPurProduct4;
	}

	/**
	 * @return the dateOfPurProduct5
	 */
	public String getDateOfPurProduct5()
	{
		return dateOfPurProduct5;
	}

	/**
	 * @param dateOfPurProduct5
	 *           the dateOfPurProduct5 to set
	 */
	public void setDateOfPurProduct5(final String dateOfPurProduct5)
	{
		this.dateOfPurProduct5 = dateOfPurProduct5;
	}

	/**
	 * @return the phoneNumber
	 */
	@NotNull(message = "{request.phonenumber.invalid}")
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
	 * @return the itemDescProduct1
	 */
	public String getItemDescProduct1()
	{
		return itemDescProduct1;
	}

	/**
	 * @param itemDescProduct1
	 *           the itemDescProduct1 to set
	 */
	public void setItemDescProduct1(final String itemDescProduct1)
	{
		this.itemDescProduct1 = itemDescProduct1;
	}

	/**
	 * @return the itemDescProduct2
	 */
	public String getItemDescProduct2()
	{
		return itemDescProduct2;
	}

	/**
	 * @param itemDescProduct2
	 *           the itemDescProduct2 to set
	 */
	public void setItemDescProduct2(final String itemDescProduct2)
	{
		this.itemDescProduct2 = itemDescProduct2;
	}

	/**
	 * @return the itemDescProduct3
	 */
	public String getItemDescProduct3()
	{
		return itemDescProduct3;
	}

	/**
	 * @param itemDescProduct3
	 *           the itemDescProduct3 to set
	 */
	public void setItemDescProduct3(final String itemDescProduct3)
	{
		this.itemDescProduct3 = itemDescProduct3;
	}

	/**
	 * @return the itemDescProduct4
	 */
	public String getItemDescProduct4()
	{
		return itemDescProduct4;
	}

	/**
	 * @param itemDescProduct4
	 *           the itemDescProduct4 to set
	 */
	public void setItemDescProduct4(final String itemDescProduct4)
	{
		this.itemDescProduct4 = itemDescProduct4;
	}

	/**
	 * @return the itemDescProduct5
	 */
	public String getItemDescProduct5()
	{
		return itemDescProduct5;
	}

	/**
	 * @param itemDescProduct5
	 *           the itemDescProduct5 to set
	 */
	public void setItemDescProduct5(final String itemDescProduct5)
	{
		this.itemDescProduct5 = itemDescProduct5;
	}

	/**
	 * @return the serialNumberProduct1
	 */
	public String getSerialNumberProduct1()
	{
		return serialNumberProduct1;
	}

	/**
	 * @param serialNumberProduct1
	 *           the serialNumberProduct1 to set
	 */
	public void setSerialNumberProduct1(final String serialNumberProduct1)
	{
		this.serialNumberProduct1 = serialNumberProduct1;
	}

	/**
	 * @return the serialNumberProduct2
	 */
	public String getSerialNumberProduct2()
	{
		return serialNumberProduct2;
	}

	/**
	 * @param serialNumberProduct2
	 *           the serialNumberProduct2 to set
	 */
	public void setSerialNumberProduct2(final String serialNumberProduct2)
	{
		this.serialNumberProduct2 = serialNumberProduct2;
	}

	/**
	 * @return the serialNumberProduct3
	 */
	public String getSerialNumberProduct3()
	{
		return serialNumberProduct3;
	}

	/**
	 * @param serialNumberProduct3
	 *           the serialNumberProduct3 to set
	 */
	public void setSerialNumberProduct3(final String serialNumberProduct3)
	{
		this.serialNumberProduct3 = serialNumberProduct3;
	}

	/**
	 * @return the serialNumberProduct4
	 */
	public String getSerialNumberProduct4()
	{
		return serialNumberProduct4;
	}

	/**
	 * @param serialNumberProduct4
	 *           the serialNumberProduct4 to set
	 */
	public void setSerialNumberProduct4(final String serialNumberProduct4)
	{
		this.serialNumberProduct4 = serialNumberProduct4;
	}

	/**
	 * @return the serialNumberProduct5
	 */
	public String getSerialNumberProduct5()
	{
		return serialNumberProduct5;
	}

	/**
	 * @param serialNumberProduct5
	 *           the serialNumberProduct5 to set
	 */
	public void setSerialNumberProduct5(final String serialNumberProduct5)
	{
		this.serialNumberProduct5 = serialNumberProduct5;
	}

	/**
	 * @return the invoiceCostProduct1
	 */
	public String getInvoiceCostProduct1()
	{
		return invoiceCostProduct1;
	}

	/**
	 * @param invoiceCostProduct1
	 *           the invoiceCostProduct1 to set
	 */
	public void setInvoiceCostProduct1(final String invoiceCostProduct1)
	{
		this.invoiceCostProduct1 = invoiceCostProduct1;
	}

	/**
	 * @return the invoiceCostProduct2
	 */
	public String getInvoiceCostProduct2()
	{
		return invoiceCostProduct2;
	}

	/**
	 * @param invoiceCostProduct2
	 *           the invoiceCostProduct2 to set
	 */
	public void setInvoiceCostProduct2(final String invoiceCostProduct2)
	{
		this.invoiceCostProduct2 = invoiceCostProduct2;
	}

	/**
	 * @return the invoiceCostProduct3
	 */
	public String getInvoiceCostProduct3()
	{
		return invoiceCostProduct3;
	}

	/**
	 * @param invoiceCostProduct3
	 *           the invoiceCostProduct3 to set
	 */
	public void setInvoiceCostProduct3(final String invoiceCostProduct3)
	{
		this.invoiceCostProduct3 = invoiceCostProduct3;
	}

	/**
	 * @return the invoiceCostProduct4
	 */
	public String getInvoiceCostProduct4()
	{
		return invoiceCostProduct4;
	}

	/**
	 * @param invoiceCostProduct4
	 *           the invoiceCostProduct4 to set
	 */
	public void setInvoiceCostProduct4(final String invoiceCostProduct4)
	{
		this.invoiceCostProduct4 = invoiceCostProduct4;
	}

	/**
	 * @return the invoiceCostProduct5
	 */
	public String getInvoiceCostProduct5()
	{
		return invoiceCostProduct5;
	}

	/**
	 * @param invoiceCostProduct5
	 *           the invoiceCostProduct5 to set
	 */
	public void setInvoiceCostProduct5(final String invoiceCostProduct5)
	{
		this.invoiceCostProduct5 = invoiceCostProduct5;
	}

	/**
	 * @return the dealerContactName
	 */
	public String getDealerContactName()
	{
		return dealerContactName;
	}

	/**
	 * @param dealerContactName
	 *           the dealerContactName to set
	 */
	public void setDealerContactName(final String dealerContactName)
	{
		this.dealerContactName = dealerContactName;
	}

	/**
	 * @return the dealerName
	 */
	public String getDealerName()
	{
		return dealerName;
	}

	/**
	 * @param dealerName
	 *           the dealerName to set
	 */
	public void setDealerName(final String dealerName)
	{
		this.dealerName = dealerName;
	}

	/**
	 * @return the dealerAddressLine1
	 */
	@NotNull(message = "{request.address1.invalid}")
	@Size(min = 1, max = 255, message = "{request.address1.invalid}")
	public String getDealerAddressLine1()
	{
		return dealerAddressLine1;
	}

	/**
	 * @param dealerAddressLine1
	 *           the dealerAddressLine1 to set
	 */
	public void setDealerAddressLine1(final String dealerAddressLine1)
	{
		this.dealerAddressLine1 = dealerAddressLine1;
	}

	/**
	 * @return the dealerAddressLine2
	 */
	public String getDealerAddressLine2()
	{
		return dealerAddressLine2;
	}

	/**
	 * @param dealerAddressLine2
	 *           the dealerAddressLine2 to set
	 */
	public void setDealerAddressLine2(final String dealerAddressLine2)
	{
		this.dealerAddressLine2 = dealerAddressLine2;
	}

	/**
	 * @return the dealerCity
	 */
	@NotNull(message = "{request.city.invalid}")
	@Size(min = 1, max = 255, message = "{request.city.invalid}")
	public String getDealerCity()
	{
		return dealerCity;
	}

	/**
	 * @param dealerCity
	 *           the dealerCity to set
	 */
	public void setDealerCity(final String dealerCity)
	{
		this.dealerCity = dealerCity;
	}

	/**
	 * @return the dealerState
	 */
	@NotNull(message = "{request.state.invalid}")
	public String getDealerState()
	{
		return dealerState;
	}

	/**
	 * @param dealerState
	 *           the dealerState to set
	 */
	public void setDealerState(final String dealerState)
	{
		this.dealerState = dealerState;
	}

	/**
	 * @return the dealerZipCode
	 */
	@NotNull(message = "{request.zipcode.invalid}")
	@Size(min = 1, max = 255, message = "{request.zipcode.invalid}")
	public String getDealerZipCode()
	{
		return dealerZipCode;
	}

	/**
	 * @param dealerZipCode
	 *           the dealerZipCode to set
	 */
	public void setDealerZipCode(final String dealerZipCode)
	{
		this.dealerZipCode = dealerZipCode;
	}

	/**
	 * @return the customerContactName
	 */
	public String getCustomerContactName()
	{
		return customerContactName;
	}

	/**
	 * @param customerContactName
	 *           the customerContactName to set
	 */
	public void setCustomerContactName(final String customerContactName)
	{
		this.customerContactName = customerContactName;
	}

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
	 * @return the jdlAccountNumber
	 */
	@NotNull(message = "{request.accountnumber.invalid}")
	@Size(min = 1, max = 255, message = "{request.accountnumber.invalid}")
	public String getJdlAccountNumber()
	{
		return jdlAccountNumber;
	}

	/**
	 * @param jdlAccountNumber
	 *           the jdlAccountNumber to set
	 */
	public void setJdlAccountNumber(final String jdlAccountNumber)
	{
		this.jdlAccountNumber = jdlAccountNumber;
	}

	/**
	 * @return the customerAddressLine1
	 */
	@NotNull(message = "{request.address1.invalid}")
	@Size(min = 1, max = 255, message = "{request.address1.invalid}")
	public String getCustomerAddressLine1()
	{
		return customerAddressLine1;
	}

	/**
	 * @param customerAddressLine1
	 *           the customerAddressLine1 to set
	 */
	public void setCustomerAddressLine1(final String customerAddressLine1)
	{
		this.customerAddressLine1 = customerAddressLine1;
	}

	/**
	 * @return the customerAddressLine2
	 */
	public String getCustomerAddressLine2()
	{
		return customerAddressLine2;
	}

	/**
	 * @param customerAddressLine2
	 *           the customerAddressLine2 to set
	 */
	public void setCustomerAddressLine2(final String customerAddressLine2)
	{
		this.customerAddressLine2 = customerAddressLine2;
	}

	/**
	 * @return the customerCity
	 */
	@NotNull(message = "{request.city.invalid}")
	@Size(min = 1, max = 255, message = "{request.city.invalid}")
	public String getCustomerCity()
	{
		return customerCity;
	}

	/**
	 * @param customerCity
	 *           the customerCity to set
	 */
	public void setCustomerCity(final String customerCity)
	{
		this.customerCity = customerCity;
	}

	/**
	 * @return the customerState
	 */
	@NotNull(message = "{request.state.invalid}")
	public String getCustomerState()
	{
		return customerState;
	}

	/**
	 * @param customerState
	 *           the customerState to set
	 */
	public void setCustomerState(final String customerState)
	{
		this.customerState = customerState;
	}

	/**
	 * @return the customerZipCode
	 */
	@NotNull(message = "{request.zipcode.invalid}")
	@Size(min = 1, max = 255, message = "{request.zipcode.invalid}")
	public String getCustomerZipCode()
	{
		return customerZipCode;
	}

	/**
	 * @param customerZipCode
	 *           the customerZipCode to set
	 */
	public void setCustomerZipCode(final String customerZipCode)
	{
		this.customerZipCode = customerZipCode;
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


	public String getFaxNumber()
	{
		return faxNumber;
	}

	/**
	 * @param faxNumber
	 *           the faxNumber to set
	 */
	public void setFaxNumber(final String faxNumber)
	{
		this.faxNumber = faxNumber;
	}


}
