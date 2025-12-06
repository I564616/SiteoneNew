/**
 *
 */
package com.siteone.facade.email;

/**
 * @author 1190626
 *
 */
public interface SiteOneShareEmailFacade
{
	public void shareInvoiceEmailForCode(String code, String emails, String uid);

	public void shareOrderDetailEmailForCode(String orderCode, String code, String emails, String uid);

	public void shareCartEmailForCode(String code, String emails);

	public void sharedProductEmailForCode(String code, String emails);

}
