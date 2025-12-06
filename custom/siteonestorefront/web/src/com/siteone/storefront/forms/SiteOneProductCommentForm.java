/**
 *
 */
package com.siteone.storefront.forms;

/**
 * @author 1003567
 *
 */
public class SiteOneProductCommentForm
{
	private String comment;
	private String productCode;
	private String listCode;


	/**
	 * @return the listCode
	 */
	public String getListCode()
	{
		return listCode;
	}

	/**
	 * @param listCode
	 *           the listCode to set
	 */
	public void setListCode(final String listCode)
	{
		this.listCode = listCode;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return productCode;
	}

	/**
	 * @param productCode
	 *           the productCode to set
	 */
	public void setProductCode(final String productCode)
	{
		this.productCode = productCode;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *           the comment to set
	 */
	public void setComment(final String comment)
	{
		this.comment = comment;
	}

}