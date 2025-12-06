/**
 *
 */
package com.siteone.core.email.service;

/**
 * @author 1190626
 *
 */
public interface SiteOneShareEmailService
{
	public void shareInvoiceEmailForCode(String code, String emails, String uid);

	public void shareOrderDetailEmailForCode(String orderCode, String code, String emails, String uid);

	public void sharedProductEmailForCode(String code, String emails);

	public void shareCartEmailForCode(String code, String emails);

}
