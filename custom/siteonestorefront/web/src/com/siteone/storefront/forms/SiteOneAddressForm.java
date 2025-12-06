/**
 *
 */
package com.siteone.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;


/**
 * @author 1129929
 *
 */
public class SiteOneAddressForm extends AddressForm
{
	private String companyName;
	private String deliveryInstructions;
	private String projectName;
	private String unitId;
	private String district;

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
