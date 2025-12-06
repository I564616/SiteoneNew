/**
 *
 */
package com.siteone.storefront.forms;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author BD02006
 *
 */
public class SiteoneSavedListLogoUploadForm
{
	private String wishListCode;
	private MultipartFile uploadedImage;

	/**
	 * @return the uploadedImage
	 */
	public MultipartFile getUploadedImage()
	{
		return uploadedImage;
	}

	/**
	 * @param uploadedImage
	 *           the uploadedImage to set
	 */
	public void setUploadedImage(final MultipartFile uploadedImage)
	{
		this.uploadedImage = uploadedImage;
	}

	/**
	 * @return the wishListCode
	 */
	public String getWishListCode()
	{
		return wishListCode;
	}

	/**
	 * @param wishListCode
	 *           the wishListCode to set
	 */
	public void setWishListCode(final String wishListCode)
	{
		this.wishListCode = wishListCode;
	}

}