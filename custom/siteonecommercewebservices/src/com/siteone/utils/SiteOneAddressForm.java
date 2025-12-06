/**
 *
 */
package com.siteone.utils;



/**
 * @author pelango
 *
 */
public class SiteOneAddressForm
{
	
	private String companyName;
	private String addressId;
	private String line1;
	private String line2;
	private String townCity;
	private String regionIso;
	private String postcode;
	private String countryIso;
	private String phone;
	private Boolean saveInAddressBook;
	private String district;
	
	private String titleCode;
	private String firstName;
	private String lastName;
	private Boolean defaultAddress;
	private Boolean shippingAddress;
	private Boolean billingAddress;
	private Boolean editAddress;
	private String deliveryInstructions;
	private String projectName;
	private String unitId;
	

	
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getTownCity() {
		return townCity;
	}

	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}

	public String getRegionIso() {
		return regionIso;
	}

	public void setRegionIso(String regionIso) {
		this.regionIso = regionIso;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountryIso() {
		return countryIso;
	}

	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}

	public Boolean getSaveInAddressBook() {
		return saveInAddressBook;
	}

	public void setSaveInAddressBook(Boolean saveInAddressBook) {
		this.saveInAddressBook = saveInAddressBook;
	}

	public Boolean getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public Boolean getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Boolean shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Boolean getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Boolean billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Boolean getEditAddress() {
		return editAddress;
	}

	public void setEditAddress(Boolean editAddress) {
		this.editAddress = editAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId()
	{
		return unitId;
	}

	/**
	 * @param unitId
	 *           the unitId to set
	 */
	public void setUnitId(final String unitId)
	{
		this.unitId = unitId;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName()
	{
		return projectName;
	}

	/**
	 * @param projectName
	 *           the projectName to set
	 */
	public void setProjectName(final String projectName)
	{
		this.projectName = projectName;
	}

	/**
	 * @return the deliveryInstructions
	 */
	public String getDeliveryInstructions()
	{
		return deliveryInstructions;
	}

	/**
	 * @param deliveryInstructions
	 *           the deliveryInstructions to set
	 */
	public void setDeliveryInstructions(final String deliveryInstructions)
	{
		this.deliveryInstructions = deliveryInstructions;
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
	 * @return the district
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * @param district
	 *           the district to set
	 */
	public void setDistrict(final String district)
	{
		this.district = district;
	}



}