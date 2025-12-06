/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.product.ProductModel;


/**
 * @author 1188173
 *
 */
public class SharedProductEvent extends AbstractCommerceUserEvent
{
	/**
	 *
	 */
	public SharedProductEvent()
	{
		super();
	}


	private ProductModel productModel;
	private String userName;
	private String email;
	private String stockavailabilitymessage;

	/**
	 * @return the productModel
	 */
	public ProductModel getProductModel()
	{
		return productModel;
	}


	/**
	 * @param productModel
	 *           the productModel to set
	 */
	public void setProductModel(final ProductModel productModel)
	{
		this.productModel = productModel;
	}


	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}


	/**
	 * @param userName
	 *           the userName to set
	 */
	public void setUserName(final String userName)
	{
		this.userName = userName;
	}


	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}


	/**
	 * @param email
	 *           the email to set
	 */
	public void setEmail(final String email)
	{
		this.email = email;
	}


	/**
	 * @return the stockavailabilitymessage
	 */
	public String getStockavailabilitymessage()
	{
		return stockavailabilitymessage;
	}


	/**
	 * @param stockavailabilitymessage
	 *           the stockavailabilitymessage to set
	 */
	public void setStockavailabilitymessage(final String stockavailabilitymessage)
	{
		this.stockavailabilitymessage = stockavailabilitymessage;
	}



}
