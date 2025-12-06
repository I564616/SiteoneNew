/**
 *
 */
package com.siteone.storefront.forms;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 1003567
 *
 */
public class SiteoneSavedListCreateForm
{
	private String name;
	private String description;
	private String product;
	private MultipartFile csvFile;

	public MultipartFile getCsvFile()
	{
		return csvFile;
	}

	public void setCsvFile(final MultipartFile csvFile)
	{
		this.csvFile = csvFile;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *           the name to set
	 */
	public void setName(final String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *           the description to set
	 */
	public void setDescription(final String description)
	{
		this.description = description;
	}

	/**
	 * @return the product
	 */
	public String getProduct()
	{
		return product;
	}

	/**
	 * @param product
	 *           the product to set
	 */
	public void setProduct(final String product)
	{
		this.product = product;
	}
}