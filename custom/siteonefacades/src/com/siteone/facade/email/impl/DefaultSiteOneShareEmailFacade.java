/**
 *
 */
package com.siteone.facade.email.impl;

import jakarta.annotation.Resource;

import com.siteone.core.email.service.SiteOneShareEmailService;
import com.siteone.facade.email.SiteOneShareEmailFacade;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOneShareEmailFacade implements SiteOneShareEmailFacade
{
	@Resource(name = "siteOneShareEmailService")
	private SiteOneShareEmailService siteOneShareEmailService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facade.email.SiteOneShareEmailFacade#shareInvoiceEmailForCode(java.lang.String, java.lang.String)
	 */
	@Override
	public void shareInvoiceEmailForCode(final String code, final String emails, final String uid)
	{
		siteOneShareEmailService.shareInvoiceEmailForCode(code, emails, uid);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facade.email.SiteOneShareEmailFacade#shareOrderDetailEmailForCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void shareOrderDetailEmailForCode(final String orderCode, final String code, final String emails, final String uid)
	{
		siteOneShareEmailService.shareOrderDetailEmailForCode(orderCode, code, emails, uid);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facade.email.SiteOneShareEmailFacade#shareCartEmailForCode(java.lang.String, java.lang.String)
	 */
	@Override
	public void shareCartEmailForCode(final String code, final String emails)
	{
		siteOneShareEmailService.shareCartEmailForCode(code, emails);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facade.email.SiteOneShareEmailFacade#sharedProductEmailForCode(java.lang.String, java.lang.String)
	 */
	@Override
	public void sharedProductEmailForCode(final String code, final String emails)
	{
		siteOneShareEmailService.sharedProductEmailForCode(code, emails);
	}

}
